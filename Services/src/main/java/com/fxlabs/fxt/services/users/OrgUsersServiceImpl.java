package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class OrgUsersServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.users.OrgUsers, com.fxlabs.fxt.dto.users.OrgUsers, String> implements OrgUsersService {

    private OrgUsersRepository orgUsersRepository;
    private OrgUsersESRepository orgUsersESRepository;
    private OrgUsersConverter orgUsersConverter;

    @Autowired
    public OrgUsersServiceImpl(OrgUsersRepository orgUsersRepository, OrgUsersESRepository orgUsersESRepository,
                               OrgUsersConverter orgUsersConverter) {
        super(orgUsersRepository, orgUsersConverter);

        this.orgUsersRepository = orgUsersRepository;
        this.orgUsersESRepository = orgUsersESRepository;
        this.orgUsersConverter = orgUsersConverter;

    }

    public Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
