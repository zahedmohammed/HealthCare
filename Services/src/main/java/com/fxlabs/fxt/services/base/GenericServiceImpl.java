package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.converters.BaseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class GenericServiceImpl<E, D, ID extends Serializable> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected JpaRepository<E, ID> repository;
    protected BaseConverter<E, D> converter;


    public GenericServiceImpl(JpaRepository<E, ID> repository, BaseConverter<E, D> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    // CRUD
    public Response<D> save(D dto) {
        E e = repository.save(converter.convertToEntity(dto));
        D d = converter.convertToDto(e);
        return new Response<D>(dto);

    }

    public Response<D> findById(ID id) {
        E e = repository.findOne(id);
        D d = converter.convertToDto(e);
        return new Response<D>(d);

    }

    public Response<D> delete(ID id) {
        repository.delete(id);
        return new Response<D>(null);

    }
}
