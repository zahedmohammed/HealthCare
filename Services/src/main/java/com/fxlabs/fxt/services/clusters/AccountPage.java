package com.fxlabs.fxt.services.clusters;


import com.fxlabs.fxt.dao.entity.clusters.AccountType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 */
public enum AccountPage {

   NOTIFICATION_HUB {
       @Override
       public List<AccountType> getAccountTypes() {
           return Arrays.asList(AccountType.Slack , AccountType.Email);
       }
   },

    ISSUE_TRACKER {
        @Override
        public List<AccountType> getAccountTypes() {
            return Arrays.asList(AccountType.GitHub ,  AccountType.Jira);
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
            return Arrays.asList(AccountType.GitHub, AccountType.BitBucket, AccountType.Git, AccountType.GitLab, AccountType.Microsoft_TFS_Git, AccountType.Microsoft_VSTS_Git, AccountType.Local);
        }
    };


    public abstract List<AccountType> getAccountTypes();


}