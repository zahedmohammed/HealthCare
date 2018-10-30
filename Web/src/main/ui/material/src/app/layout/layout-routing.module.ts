import { Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { IssueNewComponent } from '../components/issue/issue-new/issue-new.component';
import { IssueEditComponent } from '../components/issue/issue-edit/issue-edit.component';
import { ProjectsListComponent } from '../components/projects/projects-list/projects-list.component';
import { ProjectsNewComponent } from '../components/projects/projects-new/projects-new.component';
import { ProjectsEditComponent } from './../components/projects/projects-edit/projects-edit.component';
import { IssuelistComponent } from '../components/issue/issue-list/issue-list.component';

const routes: Routes = [
  {
    path: 'app',
    component: LayoutComponent,
    children: [

      { path: '', redirectTo: '/app/projects', pathMatch: 'full' },
      { path: 'projects', component: ProjectsListComponent },
      { path: 'projects/new', component: ProjectsNewComponent },
      { path: 'projects/:id/edit', component: ProjectsEditComponent },
      { path: 'projects/:id/issue', component: IssuelistComponent },
      { path: 'projects/:id/issue/new', component: IssueNewComponent },
      { path: 'projects/:id/issue/:issueId/edit', component: IssueEditComponent },
    ]
  }
];

export const LayoutRoutingModule = RouterModule.forChild(routes);
