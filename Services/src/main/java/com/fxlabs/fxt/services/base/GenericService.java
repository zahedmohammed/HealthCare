package com.fxlabs.fxt.services.base;

import com.fxlabs.fxt.dto.base.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<D> {
    // CRUD
    public Response<D> save(D dto);
    public Response<D> findById(String id);
    public Response<List<D>> findAll(String user, Pageable pageable);
    public Response<D> delete(String id);
}
