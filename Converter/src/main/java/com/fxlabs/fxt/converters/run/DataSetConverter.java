package com.fxlabs.fxt.converters.run;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.run.DataSet;
import com.fxlabs.fxt.dao.entity.run.Run;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataSetConverter extends BaseConverter<DataSet, com.fxlabs.fxt.dto.run.DataSet> {
}
