package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dto.base.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//@Service
//@Transactional
public class GenericServiceImpl<E, D, ID extends Serializable> implements GenericService<D, ID> {

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

    public Response<List<D>> save(List<D> dtos) {
        List<E> e = repository.saveAll(converter.convertToEntities(dtos));
        List<D> d = converter.convertToDtos(e);
        return new Response<List<D>>();

    }

    public Response<List<D>> findAll(String user, Pageable pageable) {
        Page<E> page = repository.findAll(pageable);
        List<D> dtos = converter.convertToDtos(page.getContent());
        return new Response<List<D>>(dtos, page.getTotalElements(), page.getTotalPages());
    }

    public Response<D> findById(ID id) {
        Optional<E> eOptional = repository.findById(id);
        if (eOptional.isPresent()) {
            D d = converter.convertToDto(eOptional.get());
            return new Response<D>(d);
        }
        return new Response<D>().withErrors(true);

    }

    public Response<D> delete(ID id) {
        repository.deleteById(id);
        return new Response<D>(null);

    }
}
