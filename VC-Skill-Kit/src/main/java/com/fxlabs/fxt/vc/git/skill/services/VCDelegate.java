package com.fxlabs.fxt.vc.git.skill.services;

//import com.fxlabs.fxt.codegen.code.StubGenerator;

import com.fxlabs.fxt.codegen.code.CodegenThreadUtils;
import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.code.StubGenerator;
import com.fxlabs.fxt.codegen.generators.utils.AutoCodeConfigUtil;
import com.fxlabs.fxt.codegen.generators.utils.NameUtil;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.sdk.services.BotLogger;
import com.fxlabs.fxt.sdk.services.CredUtils;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import com.fxlabs.fxt.vc.git.skill.amqp.Sender;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class VCDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private VersionControlService versionControlService;

    @Autowired
    private FxCommandService service;

    @Autowired
    private StubGenerator stubGenerator;

    @Value(value = "${git.base.dir}")
    private String gitBaseDir;

    public void process(VCTask task) {
        logger.info("VCTask [{}]", task.getProjectName());
        VCTaskResponse response = null;
        String gitPushLogs = StringUtils.EMPTY;
        try {
            String path = gitBaseDir + RandomStringUtils.randomAlphabetic(6);
            Task _task = new Task();

            _task.setVcUrl(task.getVcUrl());
            _task.setVcBranch(task.getVcBranch());
            _task.setVcUsername(task.getVcUsername());
            _task.setVcPassword(task.getVcPassword());
            _task.setVcLastCommit(task.getVcLastCommit());

            // 1/4. Pull latest from VC
            response = versionControlService.process(_task, path);
            response.setProjectId(task.getProjectId());
            response.setProjectName(task.getProjectName());

            if (response.isSuccess()) {
                CodegenThreadUtils.taskLogger.set(new com.fxlabs.fxt.codegen.code.BotLogger());
                // 1.2 Setup Fxfile.yaml & AutoCodeConfig.yaml
                AutoCodeConfigMinimal autoCodeConfigContent = null;
                if (task.getAutoCodeConfigMinimal() != null) {
                    autoCodeConfigContent = task.getAutoCodeConfigMinimal();
                }

                stubGenerator.setupFXConfig(path, autoCodeConfigContent);
                // 2.1. AutoCode -> delete([] categories)
                // 2.1. AutoCode -> delete([] categories)
                // 2.2. AutoCode -> run()
                getInactiveCategoriesForDeletion(task);
                autoCodeDeletion(task, path);
                testSuiteDeletion(task, path);

                //set directory path for the test suite from controlplane and commit to git
                boolean isCreateTestSuiteFromControlPlane = false;
                if (!CollectionUtils.isEmpty(task.getTestSuiteAddToVCRequests())) {
                    setParent(task.getTestSuiteAddToVCRequests());
                    int count = stubGenerator.addTestSuite(path, task.getTestSuiteAddToVCRequests());
                    isCreateTestSuiteFromControlPlane = true;
                }

                if (task.isRecreate()) {
                    // TODO Generate tests
                    try {
                        // 2/4. Auto-Code
                        String openAPISpec = task.getOpenAPISpec();
                        Map<String, Integer> pathCountMap = stubGenerator.generate(path, openAPISpec, null, null);
                        if (pathCountMap != null && pathCountMap.size() > 0) {
                            List<Endpoint> endpoints = new ArrayList<>();
                            Iterator<String> itr = pathCountMap.keySet().iterator();
                            while (itr.hasNext()){
                                String key = itr.next();
                                Integer count = pathCountMap.get(key);
                                if (StringUtils.indexOf(key,"__") > 0){
                                    String method = StringUtils.substring(key,0,StringUtils.indexOf(key,"__"));
                                    String endpoint = StringUtils.substring(key,StringUtils.indexOf(key,"__")+2,key.length());
                                    Endpoint endpointObj = new Endpoint(endpoint, method);
                                    endpointObj.setProjectId(task.getProjectId());
                                    endpoints.add(endpointObj);
                                }
//
//                                Iterator<String> itrM = methodsMap.keySet().iterator();
//                                while (itrM.hasNext()) {
//                                    String method = itrM.next();
//                                    Endpoint endpoint = new Endpoint(key, method);
//                                    endpoint.setProjectId(task.getProjectId());
//                                    endpoints.add(endpoint);
//                                }
                            }

                            response.setApiEndpoints(endpoints);
                            int count = 0;
                            Iterator<String> itr_ = pathCountMap.keySet().iterator();
                            while (itr_.hasNext()) {
                                count += pathCountMap.get(itr_.next());

//                                Map<String, Integer> methodsTSMap = pathCountMap.get(itr_.next());
//                                Iterator<String> itrM = methodsTSMap.keySet().iterator();
//                                while (itrM.hasNext()) {
//                                    count += methodsTSMap.get(itrM.next());
//                                }
                            }
                            response.setAutoGenSuitesCount(count);
                        }
                    } catch (Exception e) {
                        logger.warn(e.getLocalizedMessage(), e);
                        gitPushLogs = e.getLocalizedMessage();
                        //CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "Push", e.getLocalizedMessage());
                    }
                }

                // 3/4. Push to VC
                try {
                    gitPushLogs = versionControlService.push(path, task.getVcUrl(), task.getVcUsername(), task.getVcPassword());
                } catch (Exception e) {
                    logger.warn(e.getLocalizedMessage(), e);
                    gitPushLogs = e.getLocalizedMessage();
                }

                CredUtils.taskLogger.set(new BotLogger());
                CredUtils.url.set(task.getFxUrl());
                CredUtils.username.set(task.getProjectUser());
                CredUtils.password.set(task.getProjectGrant());
                CredUtils.errors.set(Boolean.FALSE);

                // 4/4. Push to Control-Plane
                if (!isCreateTestSuiteFromControlPlane) {
                    Project project = service.load(response.getPath(), task.getProjectId());
                }

                response.setSuccess(!BooleanUtils.isTrue(CredUtils.errors.get()));
            }

            String gitPullLogs = new String();
            if (response.getLogs() != null) {
                gitPullLogs = response.getLogs();
            }

            String codegenLogs = new String();
            if (CodegenThreadUtils.taskLogger.get() != null) {
                codegenLogs = CodegenThreadUtils.taskLogger.get().getLogs();
            }

            String controlPlanePushLogs = new String();
            if (CredUtils.taskLogger.get() != null) {
                controlPlanePushLogs = CredUtils.taskLogger.get().getLogs();
            }

            response.setLogs(gitPullLogs + "\n" + codegenLogs + "\n" + gitPushLogs + "\n" + controlPlanePushLogs);
            logger.info(response.toString());
            response.setTaskId(task.getTaskId());
            sender.sendTask(response);

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        } finally {
            if (response != null) {
                FileUtils.deleteQuietly(new File(response.getPath()));
            }
        }
    }

    private void getInactiveCategoriesForDeletion(VCTask task) {


        if (task.getAutoCodeConfigMinimal() != null) {

            AutoCodeConfigMinimal minimal = task.getAutoCodeConfigMinimal();

            if (CollectionUtils.isEmpty(task.getCategories())) {
                task.setCategories(new ArrayList<>());
            }
            for (AutoCodeGeneratorMinimal generator : minimal.getGenerators()) {

                if (generator.isInactive()) {
                    task.getCategories().add(generator.getType());
                }

            }
        }

    }

    private void autoCodeDeletion(VCTask task, String path) {

        String path_ = path + "/test-suites/AutoCode";

        if (task.isDeleteAll()) {
            delete(new File(path_));
        }


        if (!CollectionUtils.isEmpty(task.getCategories())) {

            List<File> filesToDelete = new ArrayList<>();
            List<String> list = AutoCodeConfigUtil.getTypes(task.getCategories());

            if (CollectionUtils.isEmpty(list)){
                return;
            }

            list.stream().forEach(category_ -> {
                String category = CaseUtils.toCamelCase(category_, true, '_','-');
                finder(category, filesToDelete, path_);
            });
            filesToDelete.stream().forEach(f -> {
                delete(f);
            });

        }
    }


    private void testSuiteDeletion(VCTask task, String path) {

        String path_ = path + "/test-suites";

        if (!CollectionUtils.isEmpty(task.getCategories()) && task.isDeleteManualTestSuite()) {

            List<File> filesToDelete = new ArrayList<>();
            List<String> list = task.getCategories();

            if (CollectionUtils.isEmpty(list)){
                return;
            }

            list.stream().forEach(category -> {
                finder(category, filesToDelete, path_);
            });
            filesToDelete.stream().forEach(f -> {
                delete(f);
            });

        }
    }


    private void finder(String category, List<File> files, String path) {


        File dir = new File(path);

        List<File> list = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            listfiles(dir, list);
            for (File fileName : list) {

                if (!org.apache.commons.lang3.StringUtils.endsWith(fileName.toString(), ".yaml")) {
                    continue;
                }

                String[] tokens = org.springframework.util.StringUtils.split(fileName.toString(), ".");
                String file = tokens[0];

                if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(file, category)) {
                    files.add(fileName);
                }


            }
        }
    }

    private boolean delete(File path) {
        try {
            boolean deleted = org.apache.commons.io.FileUtils.deleteQuietly(path);
            return true;
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
        return false;
    }


    private void listfiles(File directoryName, List<File> files) {
        // Get all the files from a directory.
        File[] fList = directoryName.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listfiles(new File(file.getAbsolutePath()), files);
            }
        }
    }

    private void setParent(List<TestSuiteAddToVCRequest> suiteAddToVCRequests) {

        if (CollectionUtils.isEmpty(suiteAddToVCRequests)) {
            return;
        }
        suiteAddToVCRequests.forEach(testSuiteAddToVCRequest -> {

            if (testSuiteAddToVCRequest == null || testSuiteAddToVCRequest.getTestSuiteMin() == null) {
                return;
            }


            try {
                String path = NameUtil.extractBaseForTestSuiteFromControlPlane(testSuiteAddToVCRequest.getTestSuiteMin().getParent());
                logger.info("File path  [{}] for testsuite from control plane", path);
                testSuiteAddToVCRequest.getTestSuiteMin().setParent(path);
            } catch (Exception ex) {
                logger.warn(ex.getLocalizedMessage());
            }
        });
    }
}
