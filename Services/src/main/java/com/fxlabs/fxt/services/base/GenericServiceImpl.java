package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
//@Service
//@Transactional
public abstract class GenericServiceImpl<E, D, ID extends Serializable> implements GenericService<D, ID> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected JpaRepository<E, ID> repository;
    protected BaseConverter<E, D> converter;


    public GenericServiceImpl(JpaRepository<E, ID> repository, BaseConverter<E, D> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public Response<D> save(D dto) {
        return save(dto, null);
    }

    // CRUD
    public Response<D> save(D dto, String user) {
        if (!StringUtils.isEmpty(((BaseDto) dto).getId())) {
            isUserEntitled((ID) ((BaseDto) dto).getId(), user);
        }
        E e = repository.saveAndFlush(converter.convertToEntity(dto));
        D d = converter.convertToDto(e);
        return new Response<D>(d);

    }

    public Response<List<D>> save(List<D> dtos, String user) {

        if (CollectionUtils.isEmpty(dtos)) {
            throw new FxException("Invalid arguments");
        }

        final List<Message> messages = new ArrayList<>();
        Response response = new Response<>();
        dtos.stream().forEach(dto -> {
            Response<D> jobResponse = save(dto, user);
            if (!CollectionUtils.isEmpty(jobResponse.getMessages())) {
                messages.addAll(jobResponse.getMessages());
            }
            if (jobResponse.isErrors()) {
                response.withErrors(true);
            }
        });

        return response.withMessages(messages);

    }

    public Response<List<D>> findAll(String user, Pageable pageable) {
        return null;
    }

    public Response<D> findById(ID id, String user) {
        isUserEntitled(id, user);
        Optional<E> eOptional = repository.findById(id);
        if (eOptional.isPresent()) {
            D d = converter.convertToDto(eOptional.get());
            return new Response<D>(d);
        }
        return new Response<D>().withErrors(true);

    }

    public Response<D> delete(ID id, String user) {
        isUserEntitled(id, user);
        repository.deleteById(id);
        return new Response<D>(null);

    }

    public abstract void isUserEntitled(ID id, String user);
}
