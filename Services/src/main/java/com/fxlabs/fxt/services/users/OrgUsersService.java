package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.ProjectUsers;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersService extends GenericService<OrgUsers, String> {

    Response<List<OrgUsers>> findByAccess(String user, Pageable pageable);
}
