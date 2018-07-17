package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.DataRecordConverter;
import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.DataSet;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectImports;
import com.fxlabs.fxt.dao.repository.es.DataRecordESRepository;
import com.fxlabs.fxt.dao.repository.es.DataSetESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectImportsRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import com.fxlabs.fxt.services.util.DataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class MarketplaceDataProvider {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ProjectImportsRepository projectImportsRepository;
    private TestSuiteESRepository testSuiteESRepository;
    private DataSetESRepository dataSetESRepository;
    private DataRecordESRepository dataRecordESRepository;
    private ProjectRepository projectRepository;
    private DataResolver dataResolver;
    private DataRecordConverter dataRecordConverter;

    @Autowired
    public MarketplaceDataProvider(ProjectImportsRepository projectImportsRepository, TestSuiteESRepository testSuiteESRepository,
                                   ProjectRepository projectRepository, DataResolver dataResolver, DataSetESRepository datasetESRepository,
                                   DataRecordESRepository dataRecordESRepository, DataRecordConverter dataRecordConverter) {

        this.projectImportsRepository = projectImportsRepository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectRepository = projectRepository;
        this.dataResolver = dataResolver;
        this.dataSetESRepository = datasetESRepository;
        this.dataRecordESRepository = dataRecordESRepository;
        this.dataRecordConverter = dataRecordConverter;
    }

    /**
     * e.g. Fxfile.yaml decleration
     * imports:
     * - @Names:  Fxlabs/DS/Arabic_Names
     * - @Address: Fxlabs/DS/US_Addresses
     *
     * @param
     * @return
     */
    public MarketplaceDataTask get(MarketplaceDataTask task) {
        try {
            String name = task.getImportName();
            String projectId = task.getProjectId();

            if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(name, "@Vault")) {
                String response = handleVault(task);
                task.setEval(response);
                return task;
            }

            // TODO project or org should be authorized and subscribed to the data-provider.
            Optional<ProjectImports> projectImportsOptional = projectImportsRepository.findByProjectId(projectId);
            if (!projectImportsOptional.isPresent()) {
                //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No imports defined in Fxfile.yaml"));
                task.setErrors("No imports defined in Fxfile.yaml");
                return task;
            }
            ProjectImports imports = projectImportsOptional.get();
            String module = imports.getImports().get(name);
            if (StringUtils.isEmpty(module)) {
                //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No imports defined for [%s] in Fxfile.yaml", name)));
                task.setErrors(String.format("No imports defined for [%s] in Fxfile.yaml", name));
                return task;
            }
            // load test-suite based on Org/Project/Test-Suite
            String[] tokens = module.split("/");
            if (tokens == null || tokens.length != 3) {
                //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid module name [%s] defined in Fxfile.yaml", module)));
                task.setErrors(String.format("Invalid module name [%s] defined in Fxfile.yaml", module));
                return task;
            }
            String org = tokens[0];
            String proj = tokens[1];
            String testSuite = tokens[2];

            Optional<Project> projectOptional = projectRepository.findByOrgNameAndNameAndInactive(org, proj, false);

            if (!projectOptional.isPresent()) {
                //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No module found with the name [%s] defined in Fxfile.yaml", module)));
                task.setErrors(String.format("No module found with the name [%s] defined in Fxfile.yaml", module));
                return task;
            }

            // TODO load all records
            if (!StringUtils.isEmpty(task.getPolicy()) && task.getPolicy().equals("repeatModule")) {
                load(projectOptional.get().getId(), testSuite, task);
            } else {
                loadRandom(projectOptional.get().getId(), testSuite, task);
            }


            return task;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            logger.warn(ex.getLocalizedMessage(), ex);
            //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
            task.setErrors(ex.getLocalizedMessage());
            return task;
        }
    }

    private String handleVault(MarketplaceDataTask task) {

        String orgName = null;
        Optional<Project> projectOptional = projectRepository.findById(task.getProjectId());
        if (projectOptional.isPresent()){
            orgName = projectOptional.get().getOrg().getName();
        }
        return dataResolver.resolve(task.getImportName(), orgName);
    }

    private void loadRandom(String projectId, String testSuite, MarketplaceDataTask task) {
        // pull from data records
        Optional<DataSet> dataSetOptional = dataSetESRepository.findByProjectIdAndName(projectId, testSuite);

        if (!dataSetOptional.isPresent()) {
            //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No DataSet found with the name [%s] defined in Fxfile.yaml", module)));
            task.setErrors(String.format("No DataSet found with the name [%s] defined in Fxfile.yaml", testSuite));
            return;
        }

        DataSet dataSet = dataSetOptional.get();
        Stream<DataRecord> dataRecordStream = dataRecordESRepository.findByDataSet(dataSet.getId());

        Supplier<Stream<DataRecord>> streamSupplier = () -> dataRecordStream;

        DataRecord dataRecord = streamSupplier.get().findAny().get();
        // TODO - Ignoring inactive flag.
        task.setEval(dataRecord.getRecord());
    }

    private void load(String projectId, String testSuite, MarketplaceDataTask task) {
        // pull from data records
        Optional<DataSet> dataSetOptional = dataSetESRepository.findByProjectIdAndName(projectId, testSuite);

        if (!dataSetOptional.isPresent()) {
            //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No DataSet found with the name [%s] defined in Fxfile.yaml", module)));
            task.setErrors(String.format("No module found with the name [%s] defined in Fxfile.yaml", testSuite));
            return;
        }

        DataSet dataSet = dataSetOptional.get();
        Page<DataRecord> page1 = dataRecordESRepository.findByDataSet(dataSet.getId(), PageRequest.of(task.getCurrentPage(), 100));

        task.setTotalElements(page1.getTotalElements());
        task.setRecords(dataRecordConverter.convertToDtos(page1.getContent()));
    }

    // TODO
    public void isUserEntitled(String id, String user) {

    }
}
