package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dao.entity.project.*;
import com.fxlabs.fxt.dao.repository.es.DataRecordESRepository;
import com.fxlabs.fxt.dao.repository.es.DataSetESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectImportsRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import com.fxlabs.fxt.dto.skills.Opt;
import com.fxlabs.fxt.services.util.DataResolver;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
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

    @Autowired
    public MarketplaceDataProvider(ProjectImportsRepository projectImportsRepository, TestSuiteESRepository testSuiteESRepository,
                                   ProjectRepository projectRepository, DataResolver dataResolver, DataSetESRepository datasetESRepository,
                                   DataRecordESRepository dataRecordESRepository) {

        this.projectImportsRepository = projectImportsRepository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectRepository = projectRepository;
        this.dataResolver = dataResolver;
        this.dataSetESRepository = datasetESRepository;
        this.dataRecordESRepository = dataRecordESRepository;
    }

    /**
     * e.g. Fxfile.yaml decleration
     *   imports:
     *     - @Names:  Fxlabs/DS/Arabic_Names
     *     - @Address: Fxlabs/DS/US_Addresses
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

            // TODO project or org should be authorized and subscribe to the data-provider.
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

            // pull from data records
            Optional<DataSet> dataSetOptional = dataSetESRepository.findByProjectIdAndName(projectOptional.get().getId(), testSuite);

//            dataSetOptional.get().
//            Optional<TestSuite> testSuiteOptional = testSuiteESRepository.findByProjectIdAndName(projectOptional.get().getId(), testSuite);

            if (!dataSetOptional.isPresent()) {
                //return new Response<>("").withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No DataSet found with the name [%s] defined in Fxfile.yaml", module)));
                task.setErrors(String.format("No DataSet found with the name [%s] defined in Fxfile.yaml", module));
                return task;
            }

            DataSet dataSet = dataSetOptional.get();
            Stream<DataRecord> dataRecordStream = dataRecordESRepository.findByDataSet(dataSet.getId());

            Supplier<Stream<DataRecord>> streamSupplier = () -> dataRecordStream;

//            long size =  dataRecordStream.count();//testSuiteOptional.get().getTestCases().size();
//            int random = (int) RandomUtils.nextLong(0, size-1);

            DataRecord dataRecord = streamSupplier.get().findAny().get();
//            TestCase testCase = testSuiteOptional.get().getTestCases().get(random);
            // TODO - Ignoring inactive flag.
            task.setEval(dataRecord.getRecord());
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
        return dataResolver.resolve(task.getImportName());
    }

    // TODO
    public void isUserEntitled(String id, String user) {

    }
}
