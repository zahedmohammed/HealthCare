package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.converters.skills.SkillConverter;
import com.fxlabs.fxt.converters.skills.SkillSubscriptionConverter;
import com.fxlabs.fxt.dao.repository.jpa.SkillRepository;
import com.fxlabs.fxt.dao.repository.jpa.SkillSubscriptionRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SkillSubscriptionServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.skills.SkillSubscription, com.fxlabs.fxt.dto.skills.SkillSubscription, String> implements SkillSubscriptionService {

    private SkillSubscriptionRepository repository;
    private SkillSubscriptionConverter converter;

    @Autowired
    public SkillSubscriptionServiceImpl(SkillSubscriptionRepository repository, SkillSubscriptionConverter converter) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;

    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
