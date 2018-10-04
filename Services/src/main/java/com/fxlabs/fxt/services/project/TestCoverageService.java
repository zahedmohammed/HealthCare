package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dao.entity.project.Endpoint;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteCount;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.EndpointRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.project.Coverage;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TestCoverageService {


    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private TestSuiteRepository testSuiteRepository;

    @Autowired
    private TestSuiteESRepository testSuiteESRepository;

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public List<Coverage> getAuoCodeCoverage(String projectId) {

        List<Coverage> coverages = new ArrayList<>();

        List<Endpoint> endpoints = endpointRepository.findByProjectId(projectId);
        Stream<TestSuite> testSuites = testSuiteESRepository.findByProjectIdAndInactive(projectId, false);

        Map<String, Map<String, Integer>> endpointTSByCategory = new HashMap<>();


        testSuites.forEach(testSuite -> {
            if (testSuite.getCategory() != null && endpointTSByCategory.get(testSuite.getCategory().toString()) == null) {
                endpointTSByCategory.put(testSuite.getCategory().toString(), new HashMap<String, Integer>());
            }

            for (Endpoint ep  : endpoints){

                String endpoint = ep.getEndpoint();
                String method = ep.getMethod();

                String endpoint_ = testSuite.getEndpoint();
                String method_ = testSuite.getMethod().toString();


                if ( PATH_MATCHER.match(endpoint,endpoint_) && StringUtils.equalsIgnoreCase(method,method_)){

                    Integer count = endpointTSByCategory.get(testSuite.getCategory().toString()).get(method+"_"+endpoint);
                    if (count != null){
                        count += 1;
                    }else {
                        count = new Integer(1);
                    }
                    endpointTSByCategory.get(testSuite.getCategory().toString()).put(method+"_"+endpoint,count);
                }

            }
        });


        System.out.println(endpointTSByCategory);

        int totalEndpoints = endpoints != null ? endpoints.size() : 0;

//        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.SQL_Injection.toString() , totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.UnSecured.toString() , totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.RBAC.toString(), totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.Negative.toString() , totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.Functional.toString() , totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.SLA.toString() , totalEndpoints));
        coverages.add(getCoverage(endpointTSByCategory, TestSuiteCategory.DDOS.toString() , totalEndpoints));

//        coverages.add(getCoverage(endpointTSByCategory, "ALL", totalTS.intValue() , totalEndpoints, totalEndpoints * 5));

        return coverages;
    }

    private Coverage getCoverage(Map<String, Map<String, Integer>> endpointTSByCategory, String category, int totalEndpoints){

        int coveredEndpoints = 0;
        int tsCount = 0;

        Map<String, Integer> endpointsTSCount = endpointTSByCategory.get(category);
        if (endpointsTSCount != null){
            coveredEndpoints = endpointsTSCount.size();

            Iterator<String> itr = endpointsTSCount.keySet().iterator();
            while(itr.hasNext()){
                Integer count = endpointsTSCount.get(itr.next());
                if (count != null){
                    tsCount += count;
                }
            }
        }

        Coverage coverage = new Coverage();
        coverage.setCategory(category);
        coverage.setCoveredEndpoints(coveredEndpoints);
        coverage.setTsCount(tsCount);
        coverage.setTotalEndpoints(totalEndpoints);
//        coverage.setRelevantEndpoints(expectedTS);
        if (totalEndpoints > 0) {
            coverage.setCoveragePercentage(coveredEndpoints * 100 / totalEndpoints);
        }
        return coverage;
    }

}

