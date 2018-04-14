package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface DataRecordConverter extends BaseConverter<DataRecord, com.fxlabs.fxt.dto.project.DataRecord> {
}
