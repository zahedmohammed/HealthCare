import { NgModule, ApplicationRef } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule
} from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// Layout
import { LayoutComponent } from './layout/layout.component';
import { PreloaderDirective } from './layout/preloader.directive';
// Header
import { AppHeaderComponent } from './layout/header/header.component';
// Sidenav
import { AppSidenavComponent } from './layout/sidenav/sidenav.component';
import { ToggleOffcanvasNavDirective } from './layout/sidenav/toggle-offcanvas-nav.directive';
import { AutoCloseMobileNavDirective } from './layout/sidenav/auto-close-mobile-nav.directive';
import { AppSidenavMenuComponent } from './layout/sidenav/sidenav-menu/sidenav-menu.component';
import { AccordionNavDirective } from './layout/sidenav/sidenav-menu/accordion-nav.directive';
import { AppendSubmenuIconDirective } from './layout/sidenav/sidenav-menu/append-submenu-icon.directive';
import { HighlightActiveItemsDirective } from './layout/sidenav/sidenav-menu/highlight-active-items.directive';
// Customizer
import { AppCustomizerComponent } from './layout/customizer/customizer.component';
import { ToggleQuickviewDirective } from './layout/customizer/toggle-quickview.directive';
// Footer
import { AppFooterComponent } from './layout/footer/footer.component';
// Search Overaly
import { AppSearchOverlayComponent } from './layout/search-overlay/search-overlay.component';
import { SearchOverlayDirective } from './layout/search-overlay/search-overlay.directive';
import { OpenSearchOverlaylDirective } from './layout/search-overlay/open-search-overlay.directive';

// Pages
import { DashboardComponent } from './dashboard/dashboard.component';
import { PageLayoutFullscreenComponent } from './page-layouts/fullscreen/fullscreen.component';

// Sub modules
import { LayoutModule } from './layout/layout.module';
import { SharedModule } from './shared/shared.module';

// hmr
//import { removeNgStyles, createNewHosts } from '@angularclass/hmr';
import { JobslistComponent } from './components/jobs-list/jobs-list.component';

import { ProjectsListComponent } from './components/projects/projects-list/projects-list.component';
import { ProjectsNewComponent } from './components/projects/projects-new/projects-new.component';
import { ProjectsEditComponent } from './components/projects/projects-edit/projects-edit.component';

import { RegionsListComponent } from './components/regions-list/regions-list.component';
import { RunListComponent } from './components/run-list/run-list.component';

import { MessageListComponent } from './components/message-list/message-list.component';
import { MessageDetailComponent } from './components/message-detail/message-detail.component';
import { RunDetailComponent } from './components/run-detail/run-detail.component';

import { IssuesListComponent } from './components/issues/issues-list/issues-list.component';
import { IssuesNewComponent } from './components/issues/issues-new/issues-new.component';
import { IssuesEditComponent } from './components/issues/issues-edit/issues-edit.component';

import { MarketplaceListComponent } from './components/marketplace/marketplace-list/marketplace-list.component';
import { MarketplaceNewComponent } from './components/marketplace/marketplace-new/marketplace-new.component';
import { MarketplaceEditComponent } from './components/marketplace/marketplace-edit/marketplace-edit.component';

import { MonitorAllComponent } from './components/monitor/monitor-all/monitor-all.component';
import { MonitorNewComponent } from './components/monitor/monitor-new/monitor-new.component';
import { MonitorEditComponent } from './components/monitor/monitor-edit/monitor-edit.component';

import { DocListComponent } from './components/doc/doc-list/doc-list.component';
import { DocNewComponent } from './components/doc/doc-new/doc-new.component';
import { DocEditComponent } from './components/doc/doc-edit/doc-edit.component';

import { SupportListComponent } from './components/support/support-list/support-list.component';
import { SupportNewComponent } from './components/support/support-new/support-new.component';
import { SupportEditComponent } from './components/support/support-edit/support-edit.component';

import { AnalyticsListComponent } from './components/analytics/analytics-list/analytics-list.component';
import { AnalyticsNewComponent } from './components/analytics/analytics-new/analytics-new.component';
import { AnalyticsEditComponent } from './components/analytics/analytics-edit/analytics-edit.component';

import { VaultListComponent } from './components/vault/vault-list/vault-list.component';
import { VaultNewComponent } from './components/vault/vault-new/vault-new.component';
import { VaultEditComponent } from './components/vault/vault-edit/vault-edit.component';

import { BillingListComponent } from './components/billing/billing-list/billing-list.component';
import { BillingNewComponent } from './components/billing/billing-new/billing-new.component';
import { BillingEditComponent } from './components/billing/billing-edit/billing-edit.component';

import { OrgListComponent } from './components/org/org-list/org-list.component';
import { OrgNewComponent } from './components/org/org-new/org-new.component';
import { OrgEditComponent } from './components/org/org-edit/org-edit.component';

import { SkillsListComponent } from './components/skills/skills-list/skills-list.component';
import { SkillsNewComponent } from './components/skills/skills-new/skills-new.component';
import { SkillsEditComponent } from './components/skills/skills-edit/skills-edit.component';
import { SkillsVersionControlComponent } from './components/skills/skills-version-control/skills-version-control.component';
import { SkillsBotDeploymentComponent } from './components/skills/skills-bot-deployment/skills-bot-deployment.component';
import { SkillsIssueTrackerComponent } from './components/skills/skills-issue-tracker/skills-issue-tracker.component';
import { SkillsAnalyticsComponent } from './components/skills/skills-analytics/skills-analytics.component';
import { DashboardService } from './services/dashboard.service';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    AppRoutingModule,
    HttpClientModule,

    // Sub modules
    LayoutModule,
    SharedModule,
  ],
  declarations: [
    AppComponent,
    // Layout
    LayoutComponent,
    PreloaderDirective,
    // Header
    AppHeaderComponent,
    // Sidenav
    AppSidenavComponent,
    ToggleOffcanvasNavDirective,
    AutoCloseMobileNavDirective,
    AppSidenavMenuComponent,
    AccordionNavDirective,
    AppendSubmenuIconDirective,
    HighlightActiveItemsDirective,
    // Customizer
    AppCustomizerComponent,
    ToggleQuickviewDirective,
    // Footer
    AppFooterComponent,
    // Search overlay
    AppSearchOverlayComponent,
    SearchOverlayDirective,
    OpenSearchOverlaylDirective,
    //
    DashboardComponent,
    // Pages
    PageLayoutFullscreenComponent,
    JobslistComponent,
    ProjectsListComponent,
    RegionsListComponent,
    RunListComponent,
    ProjectsNewComponent,
    ProjectsEditComponent,
    MessageListComponent,
    MessageDetailComponent,
    RunDetailComponent,
    IssuesListComponent,
    IssuesNewComponent,
    IssuesEditComponent,
    MarketplaceListComponent,
    MarketplaceNewComponent,
    MarketplaceEditComponent,
    MonitorAllComponent,
    MonitorNewComponent,
    MonitorEditComponent,
    DocListComponent,
    DocNewComponent,
    DocEditComponent,
    SupportListComponent,
    SupportNewComponent,
    SupportEditComponent,
    AnalyticsListComponent,
    AnalyticsNewComponent,
    AnalyticsEditComponent,
    VaultListComponent,
    VaultNewComponent,
    VaultEditComponent,
    BillingListComponent,
    BillingNewComponent,
    BillingEditComponent,
    OrgListComponent,
    OrgNewComponent,
    OrgEditComponent,
    SkillsListComponent,
    SkillsNewComponent,
    SkillsEditComponent,
    SkillsVersionControlComponent,
    SkillsBotDeploymentComponent,
    SkillsIssueTrackerComponent,
    SkillsAnalyticsComponent,
  ],
  bootstrap: [AppComponent],
  providers: [DashboardService]
})

export class AppModule {
  constructor(public appRef: ApplicationRef) {}
}
