package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgService extends GenericService<Org, String> {

    Response<List<Org>> findAll(String user, Pageable pageable);

    Response<Org> delete(String id, String user);

    Response<Org> save(Org dto, String user);

    Response<com.fxlabs.fxt.dto.users.Org> update(com.fxlabs.fxt.dto.users.Org dto, String user);
}
