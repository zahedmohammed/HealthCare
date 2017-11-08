package com.fxlabs.fxt.services.base;

public interface GenericService<D> {
    // CRUD
    public Response<D> save(D dto);
    public Response<D> findById(String id);
    public Response<D> delete(String id);
}
