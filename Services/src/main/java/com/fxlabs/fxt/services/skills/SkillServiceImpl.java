package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.converters.skills.SkillConverter;
import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.SkillRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.users.OrgUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SkillServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.skills.Skill, com.fxlabs.fxt.dto.skills.Skill, String> implements SkillService {

    private SkillRepository repository;
    private SkillConverter converter;

    @Autowired
    public SkillServiceImpl(SkillRepository repository, SkillConverter converter) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;

    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
