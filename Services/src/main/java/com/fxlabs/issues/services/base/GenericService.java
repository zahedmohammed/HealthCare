package com.fxlabs.issues.services.base;

import com.fxlabs.issues.dto.base.Response;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface GenericService<D, ID extends Serializable> {
    // CRUD

    Response<D> save(D dto);

    Response<D> save(D dto, String user);

    Response<List<D>> save(List<D> dtos, String user);

    Response<D> findById(ID id, String user);

    Response<List<D>> findAll(String user, Pageable pageable);

    Response<D> delete(ID id, String user);

    void isUserEntitled(ID id, String user);
}
