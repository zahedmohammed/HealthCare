package com.fxlabs.fxt.converters.clusters;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.clusters.CloudAccount;
import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
@Mapper(componentModel = "spring")
public interface CloudAccountConverter extends BaseConverter<CloudAccount, com.fxlabs.fxt.dto.clusters.CloudAccount> {
}
