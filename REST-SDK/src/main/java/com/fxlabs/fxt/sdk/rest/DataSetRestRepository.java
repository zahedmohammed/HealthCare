package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.project.DataSet;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
public class DataSetRestRepository extends GenericRestRespository<DataSet> {


    @Autowired
    public DataSetRestRepository() {
        super(paramTypeRefMap.get(DataSet.class), paramTypeRefMap.get(DataSet[].class));
    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/data-sets";
    }

}
