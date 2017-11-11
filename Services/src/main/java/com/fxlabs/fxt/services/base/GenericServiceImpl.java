package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

//@Service
//@Transactional
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
        return new Response<D>(d);

    }

    public Response<List<D>> findAll(String user, Pageable pageable) {
        Page<E> page = repository.findAll(pageable);
        List<D> dtos = converter.convertToDtos(page.getContent());
        return new Response<List<D>>(dtos, page.getTotalElements(), (long) page.getTotalPages());
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
