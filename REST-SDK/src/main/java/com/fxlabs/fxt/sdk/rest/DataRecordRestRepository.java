package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.project.DataRecord;
import com.fxlabs.fxt.dto.project.DataSet;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return CredUtils.url.get() + "/api/v1/data-record";
    }

}
