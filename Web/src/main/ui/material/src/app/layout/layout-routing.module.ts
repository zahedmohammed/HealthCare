import { Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LayoutComponent } from './layout.component';

import { DashboardComponent } from '../dashboard/dashboard.component';

import { JobslistComponent } from '../components/jobs/jobs-list/jobs-list.component';
import { JobDetailsComponent} from '../components/jobs/job-details/job-details.component';

import { RunListComponent } from '../components/run-list/run-list.component';
import { RunDetailComponent } from '../components/run-detail/run-detail.component';

import { RunHistoryComponent } from '../components/analytics/run-history/run-history.component';

import { ProjectsListComponent } from '../components/projects/projects-list/projects-list.component';
import { ProjectsNewComponent } from '../components/projects/projects-new/projects-new.component';
import { ProjectsEditComponent } from '../components/projects/projects-edit/projects-edit.component';

import { RegionsListComponent } from '../components/regions-list/regions-list.component';
import { RegionEditComponent } from '../components/regions/region-edit/region-edit.component';
import { RegionNewComponent } from '../components/regions/region-new/region-new.component';
import { SuperbotnetworkListComponent } from '../components/superbotnetwork/superbotnetwork-list/superbotnetwork-list.component';

import { MessageListComponent } from '../components/message-list/message-list.component';
import { MessageDetailComponent } from '../components/message-detail/message-detail.component';

import { IssuesListComponent } from '../components/issues/issues-list/issues-list.component';
import { IssuesNewComponent } from '../components/issues/issues-new/issues-new.component';
import { IssuesEditComponent } from '../components/issues/issues-edit/issues-edit.component';

import { MarketplaceListComponent } from '../components/marketplace/marketplace-list/marketplace-list.component';
import { MarketplaceNewComponent } from '../components/marketplace/marketplace-new/marketplace-new.component';
import { MarketplaceEditComponent } from '../components/marketplace/marketplace-edit/marketplace-edit.component';

import { MonitorAllComponent } from '../components/monitor/monitor-all/monitor-all.component';
import { MonitorNewComponent } from '../components/monitor/monitor-new/monitor-new.component';

import { MonitorEditComponent } from '../components/monitor/monitor-edit/monitor-edit.component';

import { DocListComponent } from '../components/doc/doc-list/doc-list.component';
import { DocNewComponent } from '../components/doc/doc-new/doc-new.component';
import { DocEditComponent } from '../components/doc/doc-edit/doc-edit.component';

import { SupportListComponent } from '../components/support/support-list/support-list.component';
import { SupportNewComponent } from '../components/support/support-new/support-new.component';
import { SupportEditComponent } from '../components/support/support-edit/support-edit.component';

import { AnalyticsListComponent } from '../components/analytics/analytics-list/analytics-list.component';
import { AnalyticsNewComponent } from '../components/analytics/analytics-new/analytics-new.component';
import { AnalyticsEditComponent } from '../components/analytics/analytics-edit/analytics-edit.component';

import { VaultListComponent } from '../components/vault/vault-list/vault-list.component';
import { VaultNewComponent } from '../components/vault/vault-new/vault-new.component';
import { VaultEditComponent } from '../components/vault/vault-edit/vault-edit.component';

import { BillingListComponent } from '../components/billing/billing-list/billing-list.component';
import { BillingNewComponent } from '../components/billing/billing-new/billing-new.component';
import { BillingEditComponent } from '../components/billing/billing-edit/billing-edit.component';

import { OrgListComponent } from '../components/org/org-list/org-list.component';
import { OrgNewComponent } from '../components/org/org-new/org-new.component';
import { OrgEditComponent } from '../components/org/org-edit/org-edit.component';
import { OrgUsersComponent } from '../components/org/org-users/org-users.component';

import { UserListComponent } from '../components/users/user-list/user-list.component';
import { UserNewComponent } from '../components/users/user-new/user-new.component';
import { UserEditComponent } from '../components/users/user-edit/user-edit.component';
import { PasswordResetComponent } from '../components/users/password-reset/password-reset.component';
import { UserProfileComponent } from '../components/users/user-profile/user-profile.component';

import { AccountListComponent } from '../components/manage/account-list/account-list.component';
import { AccountNewComponent } from '../components/manage/account-new/account-new.component';
import { AccountEditComponent } from '../components/manage/account-edit/account-edit.component';

import { NotificationListComponent } from '../components/notify/notification-list/notification-list.component';
import { NotificationNewComponent } from '../components/notify/notification-new/notification-new.component';
import { NotificationEditComponent } from '../components/notify/notification-edit/notification-edit.component';

import { SkillsListComponent } from '../components/skills/skills-list/skills-list.component';
import { SkillsNewComponent } from '../components/skills/skills-new/skills-new.component';
import { SkillsEditComponent } from '../components/skills/skills-edit/skills-edit.component';
import { SkillsVersionControlComponent } from '../components/skills/skills-version-control/skills-version-control.component';
import { SkillsBotDeploymentComponent } from '../components/skills/skills-bot-deployment/skills-bot-deployment.component';
import { SkillsIssueTrackerComponent } from '../components/skills/skills-issue-tracker/skills-issue-tracker.component';
import { SkillsAnalyticsComponent } from '../components/skills/skills-analytics/skills-analytics.component';
import { StepThreeComponent } from './../components/projects/step-three/step-three.component';
import { StepTwoComponent } from './../components/projects/step-two/step-two.component';
import { StepOneComponent } from './../components/projects/step-one/step-one.component';

const routes: Routes = [
  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: '/app/projects', pathMatch: 'full' },

      { path: 'dashboard', component: DashboardComponent },

      { path: 'projects', component: ProjectsListComponent },

      { path: 'projects/new', component: ProjectsNewComponent },
      // { path: 'projects/new', component: ProjectsNewComponent,
      //   children: 
      //   [
      //     { path: 'auto-code', component: StepTwoComponent },
      //     { path: 'step-jobs', component: StepThreeComponent }
      //   ]
      // },
      // { path: 'projects/auto-code', component: StepTwoComponent},
      // { path: 'projects/step-jobs', component: StepThreeComponent},

      { path: 'projects/:id', component: ProjectsEditComponent },
      { path: 'jobs', component: JobslistComponent },
      { path: 'jobs/:id', component: JobDetailsComponent},
      { path: 'jobs/:jobId/runs', component: RunListComponent },
      { path: 'jobs/:jobId/runs/:runId', component: RunDetailComponent },
      //{ path: 'autocode', component: StepOneComponent},
      
      { path: 'messages', component: MessageListComponent },
      { path: 'message/:id', component: MessageDetailComponent },

      { path: 'regions', component: RegionsListComponent },
      { path: 'regions/new', component: RegionNewComponent },
      { path: 'regions/:id', component: RegionEditComponent },
      { path: 'superbotnetwork', component: SuperbotnetworkListComponent },

      { path: 'issues', component: IssuesListComponent },
      { path: 'issues/new', component: IssuesNewComponent },
      { path: 'issues/:id', component: IssuesEditComponent },

      { path: 'marketplace', component: MarketplaceListComponent },
      { path: 'marketplace/new', component: MarketplaceNewComponent },
      { path: 'marketplace/:id', component: MarketplaceEditComponent },

      { path: 'monitor', component: MonitorAllComponent },
      { path: 'monitor/new', component: MonitorNewComponent },
      { path: 'monitor/:id', component: MonitorEditComponent },

      { path: 'notification-accounts', component: NotificationListComponent },
      { path: 'notification-accounts/new', component: NotificationNewComponent },
      { path: 'notification-accounts/:id', component: NotificationEditComponent },

      { path: 'doc', component: DocListComponent },
      { path: 'doc/new', component: DocNewComponent },
      { path: 'doc/:id', component: DocEditComponent },

      { path: 'support', component: SupportListComponent },
      { path: 'support/new', component: SupportNewComponent },
      { path: 'support/:id', component: SupportEditComponent },

      { path: 'analytics', component: AnalyticsListComponent },
      { path: 'analytics/new', component: AnalyticsNewComponent },
      { path: 'analytics/:id', component: AnalyticsEditComponent },

      { path: 'support', component: SupportListComponent },
      { path: 'support/new', component: SupportNewComponent },
      { path: 'support/:id', component: SupportEditComponent },

      { path: 'vault', component: VaultListComponent },
      { path: 'vault/new', component: VaultNewComponent },
      { path: 'vault/:id', component: VaultEditComponent },

      { path: 'billing', component: BillingListComponent },
      { path: 'billing/new', component: BillingNewComponent },
      { path: 'billing/:id', component: BillingEditComponent },

      { path: 'orgs', component: OrgListComponent },
      { path: 'orgs/new', component: OrgNewComponent },
      //{ path: 'orgs/users', component: OrgUsersComponent },
      { path: 'orgs/:id', component: OrgEditComponent },

      { path: 'orgs/:id/users', component: UserListComponent },
      { path: 'orgs/:orgId/users/new', component: UserNewComponent },
      { path: 'orgs/:orgId/users/:id/password-reset', component: PasswordResetComponent },
      { path: 'orgs/:orgId/users/:id', component: UserEditComponent },
      { path: 'my-profile', component: UserProfileComponent },

      { path: 'accounts', component: AccountListComponent },
      { path: 'accounts/new', component: AccountNewComponent },
      { path: 'accounts/:id', component: AccountEditComponent },

      { path: 'skills/version-control', component: SkillsVersionControlComponent },

      { path: 'skills/bot-deployment', component: SkillsBotDeploymentComponent },

      { path: 'skills/issue-tracker', component: SkillsIssueTrackerComponent },

      { path: 'skills/analytics', component: SkillsAnalyticsComponent },

      { path: 'jobs/:jobId/history/:suiteId', component: RunHistoryComponent },

      // { path: 'chart', loadChildren: '../charts/charts.module#ChartsModule' },
      // { path: 'ecommerce', loadChildren: '../ecommerce/ecommerce.module#ECommerceModule' },
      // { path: 'form', loadChildren: '../forms/forms.module#MyFormsModule' },
      // { path: 'page', loadChildren: '../pages/pages.module#PagesModule' },
      // { path: 'pglayout', loadChildren: '../page-layouts/page-layouts.module#PageLayoutsModule' },
      // { path: 'table', loadChildren: '../tables/tables.module#MyTablesModule' },
      // { path: 'ui', loadChildren: '../ui/ui.module#UIModule' },
    ]
  }
];

export const LayoutRoutingModule = RouterModule.forChild(routes);
