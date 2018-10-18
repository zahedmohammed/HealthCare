package com.fxlabs.issues.services.users;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.OrgUsers;
import com.fxlabs.issues.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersService extends GenericService<OrgUsers, String> {

    Response<List<OrgUsers>> findByAccess(String user, Pageable pageable);

    Response<OrgUsers> findByName(String name, String user);
}
