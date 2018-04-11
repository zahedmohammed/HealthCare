package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.converters.skills.SkillConverter;
import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.dao.entity.skills.Skill;
import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.SkillRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.OrgUsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SkillServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.skills.Skill, com.fxlabs.fxt.dto.skills.Skill, String> implements SkillService {

    private SkillRepository repository;
    private SkillConverter converter;
    private UsersRepository usersRepository;
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository repository, SkillConverter converter, UsersRepository usersRepository, OrgUsersRepository orgUsersRepository) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;

        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.skills.Skill>> findAll(String user, Pageable pageable) {
        Page<Skill> entities = this.repository.findAll(pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.skills.Skill>> findByType(SkillType skillType, String user, Pageable pageable) {
        Page<Skill> entities = this.repository.findBySkillType(com.fxlabs.fxt.dao.entity.skills.SkillType.valueOf(skillType.name()), pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.skills.Skill> save(com.fxlabs.fxt.dto.skills.Skill dto, String user) {

        if (dto.getOrg() == null) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            OrgUsers orgUsers = null;
            orgUsers = set.iterator().next();
            NameDto org = new NameDto();
            org.setId(orgUsers.getOrg().getId());
            dto.setOrg(org);

        }
        return super.save(dto, user);
    }

    @Override
    public Response<String> findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>(StringUtils.EMPTY);
        }
        String[] tokens = name.split("/");
        String org = tokens[0];
        String key = tokens[1];

        Optional<Skill> optional = this.repository.findByOrgNameAndName(org, key);

        if (!optional.isPresent()) {
            return new Response<>(StringUtils.EMPTY);
        }

        return new Response<>(optional.get().getKey());

    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
