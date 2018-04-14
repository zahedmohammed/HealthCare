package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.DataSet;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface DataSetConverter extends BaseConverter<DataSet, com.fxlabs.fxt.dto.project.DataSet> {
}
