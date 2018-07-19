package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.DataRecord;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
public class DataRecordRestRepository extends GenericRestRespository<DataRecord> {


    @Autowired
    public DataRecordRestRepository() {
        super(paramTypeRefMap.get(DataRecord.class), paramTypeRefMap.get(DataRecord[].class));
    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/data-records";
    }

    public void deleteAllByDataset(String datasetId) {

        try {
            HttpEntity<String> request = new HttpEntity<>(datasetId, this.getHeaders());
            String url = getUrl();

            ResponseEntity<Response<String>> response = restTemplate.exchange(url + "/" + datasetId + "/delete-all", HttpMethod.DELETE, request, referenceList);
        }catch(Exception ex){
            logger.warn(ex.getLocalizedMessage());
        }
//        return response.getBody();

    }

}
