package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.dto.base.Response;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface GenericService<D, ID extends Serializable> {
    // CRUD
    public Response<D> save(D dto);
    public Response<D> save(List<D> dtos);
    public Response<D> findById(ID id);
    public Response<List<D>> findAll(String user, Pageable pageable);
    public Response<D> delete(ID id);
}
