package com.fxlabs.fxt.converters.clusters;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface ClusterConverter extends BaseConverter<Cluster, com.fxlabs.fxt.dto.clusters.Cluster> {
}
