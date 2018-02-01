package com.fxlabs.fxt.converters.base;


import org.mapstruct.InheritInverseConfiguration;

import java.util.List;
import java.util.Set;

//@Mapper//(componentModel = "spring")
public interface BaseConverter<E, D> {

    //@Mapping(source = "qax", target = "baz")
    //@Mapping(source = "baz", target = "qax")
    E convertToEntity(D dto);

    @InheritInverseConfiguration
    D convertToDto(E entity);

    D copyToDto(D dto);

    List<E> convertToEntities(List<D> dtos);

    List<D> convertToDtos(List<E> entities);

    Set<E> convertToEntities(Set<D> dtos);

    Set<D> convertToDtos(Set<E> entities);
}
