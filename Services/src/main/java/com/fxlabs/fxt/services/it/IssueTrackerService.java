package com.fxlabs.fxt.services.it;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.it.IssueTrackerSaving;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface IssueTrackerService extends GenericService<IssueTracker, String> {

    Response<IssueTracker> save(IssueTracker dto, String org, String user);

    Response<IssueTracker> findByName(String name);

    Response<List<IssueTracker>> findBySkillType(String skillType, String user, Pageable pageable);

    Response<IssueTracker> addITBot(IssueTracker dto, String o, String user);

    Response<IssueTracker> deleteITBot(String id, String o, String user);

//    Response<SkillSubscription> deleteExecBot(Cluster dto, String user);

    Response<Long> count(String user);

    Response<IssueTrackerSaving> getIssueTrackerSavings(String id, String org, String owner);
}
