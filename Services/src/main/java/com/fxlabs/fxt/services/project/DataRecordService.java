package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.DataRecord;
import com.fxlabs.fxt.services.base.GenericService;

/**
 * @author Mohammed Shoukath Ali
 */
public interface DataRecordService extends GenericService<DataRecord, String> {

    Response<String> deleteAllByDataset(String datasetId, String user);
}
