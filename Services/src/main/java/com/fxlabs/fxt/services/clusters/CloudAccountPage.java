package com.fxlabs.fxt.services.clusters;


import com.fxlabs.fxt.dao.entity.clusters.AccountType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 */
public enum CloudAccountPage {

   NOTIFICATION_HUB {
       @Override
       public List<AccountType> getAccountTypes() {
           return Arrays.asList(AccountType.SLACK , AccountType.EMAIL);
       }
   },

    ISSUE_TRACKER {
        @Override
        public List<AccountType> getAccountTypes() {
            return Arrays.asList(AccountType.GitHub );
        }
    },
    BOT_HUB {
        @Override
        public List<AccountType> getAccountTypes( ) {
            return Arrays.asList(AccountType.AWS, AccountType.Self_Hosted);
        }
    },
    PROJECT {
        @Override
        public List<AccountType> getAccountTypes() {
            return Arrays.asList(AccountType.GitHub, AccountType.Git, AccountType.GitLab, AccountType.Microsoft_TFS_Git, AccountType.Microsoft_VSTS_Git, AccountType.Local);
        }
    };


    public abstract List<AccountType> getAccountTypes();


}