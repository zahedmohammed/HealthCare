import { RouterModule, Routes } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { JobslistComponent } from '../components/jobs-list/jobs-list.component';
import { RunListComponent } from '../components/run-list/run-list.component';
import { ProjectlistComponent } from '../components/projects-list/projects-list.component';
import { NewProjectComponent } from '../components/new-project/new-project.component';
import { EditProjectComponent } from '../components/edit-project/edit-project.component';
import { RegionsListComponent } from '../components/regions-list/regions-list.component';
import { MessageListComponent } from '../components/message-list/message-list.component';
import { MessageDetailComponent } from '../components/message-detail/message-detail.component';


const routes: Routes = [
  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: '/app/jobs', pathMatch: 'full' },
      //{ path: 'jobs', component: JobslistComponent },
      //{ path: 'projects', component: ProjectlistComponent },
      //{ path: 'dashboard', component: DashboardComponent },
      { path: 'jobs', component: JobslistComponent },
      { path: 'runs/:id', component: RunListComponent },
      { path: 'projects', component: ProjectlistComponent },
      { path: 'new-project', component: NewProjectComponent },
      { path: 'project/:id', component: EditProjectComponent },
      { path: 'messages', component: MessageListComponent },
      { path: 'message/:id', component: MessageDetailComponent },
      { path: 'regions', component: RegionsListComponent },
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
