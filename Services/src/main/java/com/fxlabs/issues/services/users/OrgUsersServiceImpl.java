package com.fxlabs.issues.services.users;

import com.fxlabs.issues.converters.users.OrgUsersConverter;
import com.fxlabs.issues.dao.entity.users.OrgUserStatus;
import com.fxlabs.issues.dao.entity.users.OrgUsers;
import com.fxlabs.issues.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class OrgUsersServiceImpl extends GenericServiceImpl<OrgUsers, com.fxlabs.issues.dto.users.OrgUsers, String> implements OrgUsersService {

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

    public Response<List<com.fxlabs.issues.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRoleIn(user, OrgUserStatus.ACTIVE, OrgServiceImpl.roles, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    public Response<com.fxlabs.issues.dto.users.OrgUsers> findByName(String name, String user) {
        Optional<OrgUsers> optional = this.orgUsersRepository.findByOrgNameAndUsersIdAndStatus(name, user, OrgUserStatus.ACTIVE);
        return new Response<>(orgUsersConverter.convertToDto(optional.get()));
    }


    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
