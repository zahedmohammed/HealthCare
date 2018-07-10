package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.project.JobNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface JobNotificationConverter extends BaseConverter<JobNotification, com.fxlabs.fxt.dto.project.JobNotification> {

//    @Mappings({
//            @Mapping(source = "to", target = "to_"),
//    })
//    JobNotification convertToEntity(com.fxlabs.fxt.dto.project.JobNotification dto);
//
//    @Mappings({
//            @Mapping(source = "to_", target = "to"),
//    })
//    com.fxlabs.fxt.dto.project.JobNotification convertToDto(JobNotification entity);
}
