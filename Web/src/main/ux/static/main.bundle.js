webpackJsonp(["main"],{

/***/ "../../../../../src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"./extra-pages/extra-pages.module": [
		"../../../../../src/app/extra-pages/extra-pages.module.ts",
		"extra-pages.module"
	]
};
function webpackAsyncContext(req) {
	var ids = map[req];
	if(!ids)
		return Promise.reject(new Error("Cannot find module '" + req + "'."));
	return __webpack_require__.e(ids[1]).then(function() {
		return __webpack_require__(ids[0]);
	});
};
webpackAsyncContext.keys = function webpackAsyncContextKeys() {
	return Object.keys(map);
};
webpackAsyncContext.id = "../../../../../src/$$_lazy_route_resource lazy recursive";
module.exports = webpackAsyncContext;

/***/ }),

/***/ "../../../../../src/app/app-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__layout_layout_component__ = __webpack_require__("../../../../../src/app/layout/layout.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__page_layouts_fullscreen_fullscreen_component__ = __webpack_require__("../../../../../src/app/page-layouts/fullscreen/fullscreen.component.ts");


// import { DashboardComponent } from './dashboard/dashboard.component';
// Page Layouts

var AppRoutes = [
    { path: '', redirectTo: '/app/dashboard', pathMatch: 'full' },
    { path: 'app', component: __WEBPACK_IMPORTED_MODULE_1__layout_layout_component__["a" /* LayoutComponent */] },
    { path: 'extra', loadChildren: './extra-pages/extra-pages.module#ExtraPagesModule' },
    { path: 'fullscreen', component: __WEBPACK_IMPORTED_MODULE_2__page_layouts_fullscreen_fullscreen_component__["a" /* PageLayoutFullscreenComponent */] },
    { path: '**', redirectTo: '/app/jobs', pathMatch: 'full' },
];
var AppRoutingModule = __WEBPACK_IMPORTED_MODULE_0__angular_router__["g" /* RouterModule */].forRoot(AppRoutes, { useHash: true });


/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "\n<div myPreloader class=\"preloaderbar hide\"><span class=\"bar\"></span></div>\n<div id=\"app-main\" class=\"app-main full-height\"\n   [ngClass]=\"{'fixed-header' : AppConfig.fixedHeader,\n               'nav-collapsed' : AppConfig.navCollapsed,\n               'nav-behind' : AppConfig.navBehind,\n               'layout-boxed' : AppConfig.layoutBoxed,\n               'theme-gray' : AppConfig.theme == 'gray',\n               'theme-dark' : AppConfig.theme == 'dark',\n               'sidebar-sm' : AppConfig.sidebarWidth == 'small',\n               'sidebar-lg' : AppConfig.sidebarWidth == 'large'}\">\n  <router-outlet></router-outlet>\n</div>"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__config__ = __webpack_require__("../../../../../src/app/config.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__layout_layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_styles_material2_theme_scss__ = __webpack_require__("../../../../../src/styles/material2-theme.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_styles_material2_theme_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_styles_material2_theme_scss__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_styles_bootstrap_scss__ = __webpack_require__("../../../../../src/styles/bootstrap.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_styles_bootstrap_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_styles_bootstrap_scss__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_styles_layout_scss__ = __webpack_require__("../../../../../src/styles/layout.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_styles_layout_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_styles_layout_scss__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_styles_theme_scss__ = __webpack_require__("../../../../../src/styles/theme.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_styles_theme_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_7_styles_theme_scss__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_styles_ui_scss__ = __webpack_require__("../../../../../src/styles/ui.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_styles_ui_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8_styles_ui_scss__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_styles_app_scss__ = __webpack_require__("../../../../../src/styles/app.scss");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_styles_app_scss___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_9_styles_app_scss__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




// 3rd


// custom




var AppComponent = (function () {
    function AppComponent(router) {
        this.router = router;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_2__config__["a" /* APPCONFIG */];
        // Scroll to top on route change
        this.router.events.subscribe(function (evt) {
            if (!(evt instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* NavigationEnd */])) {
                return;
            }
            window.scrollTo(0, 0);
        });
    };
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_3__layout_layout_service__["a" /* LayoutService */]],
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_routing_module__ = __webpack_require__("../../../../../src/app/app-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__layout_layout_component__ = __webpack_require__("../../../../../src/app/layout/layout.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__layout_preloader_directive__ = __webpack_require__("../../../../../src/app/layout/preloader.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__layout_header_header_component__ = __webpack_require__("../../../../../src/app/layout/header/header.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__layout_sidenav_sidenav_component__ = __webpack_require__("../../../../../src/app/layout/sidenav/sidenav.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__layout_sidenav_toggle_offcanvas_nav_directive__ = __webpack_require__("../../../../../src/app/layout/sidenav/toggle-offcanvas-nav.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__layout_sidenav_auto_close_mobile_nav_directive__ = __webpack_require__("../../../../../src/app/layout/sidenav/auto-close-mobile-nav.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__layout_sidenav_sidenav_menu_sidenav_menu_component__ = __webpack_require__("../../../../../src/app/layout/sidenav/sidenav-menu/sidenav-menu.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__layout_sidenav_sidenav_menu_accordion_nav_directive__ = __webpack_require__("../../../../../src/app/layout/sidenav/sidenav-menu/accordion-nav.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__layout_sidenav_sidenav_menu_append_submenu_icon_directive__ = __webpack_require__("../../../../../src/app/layout/sidenav/sidenav-menu/append-submenu-icon.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__layout_sidenav_sidenav_menu_highlight_active_items_directive__ = __webpack_require__("../../../../../src/app/layout/sidenav/sidenav-menu/highlight-active-items.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__layout_customizer_customizer_component__ = __webpack_require__("../../../../../src/app/layout/customizer/customizer.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__layout_customizer_toggle_quickview_directive__ = __webpack_require__("../../../../../src/app/layout/customizer/toggle-quickview.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__layout_footer_footer_component__ = __webpack_require__("../../../../../src/app/layout/footer/footer.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__layout_search_overlay_search_overlay_component__ = __webpack_require__("../../../../../src/app/layout/search-overlay/search-overlay.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_23__layout_search_overlay_search_overlay_directive__ = __webpack_require__("../../../../../src/app/layout/search-overlay/search-overlay.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_24__layout_search_overlay_open_search_overlay_directive__ = __webpack_require__("../../../../../src/app/layout/search-overlay/open-search-overlay.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_25__dashboard_dashboard_component__ = __webpack_require__("../../../../../src/app/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_26__page_layouts_fullscreen_fullscreen_component__ = __webpack_require__("../../../../../src/app/page-layouts/fullscreen/fullscreen.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_27__layout_layout_module__ = __webpack_require__("../../../../../src/app/layout/layout.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_28__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_29__components_jobs_list_jobs_list_component__ = __webpack_require__("../../../../../src/app/components/jobs-list/jobs-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_30__components_projects_projects_list_projects_list_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-list/projects-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_31__components_projects_projects_new_projects_new_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-new/projects-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_32__components_projects_projects_edit_projects_edit_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-edit/projects-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_33__components_regions_list_regions_list_component__ = __webpack_require__("../../../../../src/app/components/regions-list/regions-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_34__components_run_list_run_list_component__ = __webpack_require__("../../../../../src/app/components/run-list/run-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_35__components_message_list_message_list_component__ = __webpack_require__("../../../../../src/app/components/message-list/message-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_36__components_message_detail_message_detail_component__ = __webpack_require__("../../../../../src/app/components/message-detail/message-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_37__components_run_detail_run_detail_component__ = __webpack_require__("../../../../../src/app/components/run-detail/run-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_38__components_issues_issues_list_issues_list_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-list/issues-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_39__components_issues_issues_new_issues_new_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-new/issues-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_40__components_issues_issues_edit_issues_edit_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-edit/issues-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_41__components_marketplace_marketplace_list_marketplace_list_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_42__components_marketplace_marketplace_new_marketplace_new_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_43__components_marketplace_marketplace_edit_marketplace_edit_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_44__components_monitor_monitor_all_monitor_all_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-all/monitor-all.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_45__components_monitor_monitor_new_monitor_new_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-new/monitor-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_46__components_monitor_monitor_edit_monitor_edit_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_47__components_doc_doc_list_doc_list_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-list/doc-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_48__components_doc_doc_new_doc_new_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-new/doc-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_49__components_doc_doc_edit_doc_edit_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-edit/doc-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_50__components_support_support_list_support_list_component__ = __webpack_require__("../../../../../src/app/components/support/support-list/support-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_51__components_support_support_new_support_new_component__ = __webpack_require__("../../../../../src/app/components/support/support-new/support-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_52__components_support_support_edit_support_edit_component__ = __webpack_require__("../../../../../src/app/components/support/support-edit/support-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_53__components_analytics_analytics_list_analytics_list_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-list/analytics-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_54__components_analytics_analytics_new_analytics_new_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-new/analytics-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_55__components_analytics_analytics_edit_analytics_edit_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_56__components_vault_vault_list_vault_list_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-list/vault-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_57__components_vault_vault_new_vault_new_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-new/vault-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_58__components_vault_vault_edit_vault_edit_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-edit/vault-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_59__components_billing_billing_list_billing_list_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-list/billing-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_60__components_billing_billing_new_billing_new_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-new/billing-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_61__components_billing_billing_edit_billing_edit_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-edit/billing-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_62__components_org_org_list_org_list_component__ = __webpack_require__("../../../../../src/app/components/org/org-list/org-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_63__components_org_org_new_org_new_component__ = __webpack_require__("../../../../../src/app/components/org/org-new/org-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_64__components_org_org_edit_org_edit_component__ = __webpack_require__("../../../../../src/app/components/org/org-edit/org-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_65__components_skills_skills_list_skills_list_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-list/skills-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_66__components_skills_skills_new_skills_new_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-new/skills-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_67__components_skills_skills_edit_skills_edit_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-edit/skills-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_68__components_skills_skills_version_control_skills_version_control_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_69__components_skills_skills_bot_deployment_skills_bot_deployment_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_70__components_skills_skills_issue_tracker_skills_issue_tracker_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_71__components_skills_skills_analytics_skills_analytics_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_72__services_dashboard_service__ = __webpack_require__("../../../../../src/app/services/dashboard.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};










// Layout


// Header

// Sidenav







// Customizer


// Footer

// Search Overaly



// Pages


// Sub modules


// hmr
//import { removeNgStyles, createNewHosts } from '@angularclass/hmr';












































var AppModule = (function () {
    function AppModule(appRef) {
        this.appRef = appRef;
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_http__["a" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_5__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_5__angular_platform_browser_animations__["b" /* NoopAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["a" /* MatAutocompleteModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["b" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["c" /* MatButtonToggleModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["d" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["e" /* MatCheckboxModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["f" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["A" /* MatStepperModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["g" /* MatDatepickerModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["h" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["i" /* MatExpansionModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["j" /* MatGridListModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["k" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["l" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["m" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["n" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["o" /* MatNativeDateModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["p" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["q" /* MatProgressBarModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["r" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["s" /* MatRadioModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["t" /* MatRippleModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["u" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["v" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["x" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["w" /* MatSlideToggleModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["y" /* MatSnackBarModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["z" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["B" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["C" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["D" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_6__angular_material__["E" /* MatTooltipModule */],
                __WEBPACK_IMPORTED_MODULE_7__app_routing_module__["a" /* AppRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_common_http__["b" /* HttpClientModule */],
                // Sub modules
                __WEBPACK_IMPORTED_MODULE_27__layout_layout_module__["a" /* LayoutModule */],
                __WEBPACK_IMPORTED_MODULE_28__shared_shared_module__["a" /* SharedModule */],
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */],
                // Layout
                __WEBPACK_IMPORTED_MODULE_9__layout_layout_component__["a" /* LayoutComponent */],
                __WEBPACK_IMPORTED_MODULE_10__layout_preloader_directive__["a" /* PreloaderDirective */],
                // Header
                __WEBPACK_IMPORTED_MODULE_11__layout_header_header_component__["a" /* AppHeaderComponent */],
                // Sidenav
                __WEBPACK_IMPORTED_MODULE_12__layout_sidenav_sidenav_component__["a" /* AppSidenavComponent */],
                __WEBPACK_IMPORTED_MODULE_13__layout_sidenav_toggle_offcanvas_nav_directive__["a" /* ToggleOffcanvasNavDirective */],
                __WEBPACK_IMPORTED_MODULE_14__layout_sidenav_auto_close_mobile_nav_directive__["a" /* AutoCloseMobileNavDirective */],
                __WEBPACK_IMPORTED_MODULE_15__layout_sidenav_sidenav_menu_sidenav_menu_component__["a" /* AppSidenavMenuComponent */],
                __WEBPACK_IMPORTED_MODULE_16__layout_sidenav_sidenav_menu_accordion_nav_directive__["a" /* AccordionNavDirective */],
                __WEBPACK_IMPORTED_MODULE_17__layout_sidenav_sidenav_menu_append_submenu_icon_directive__["a" /* AppendSubmenuIconDirective */],
                __WEBPACK_IMPORTED_MODULE_18__layout_sidenav_sidenav_menu_highlight_active_items_directive__["a" /* HighlightActiveItemsDirective */],
                // Customizer
                __WEBPACK_IMPORTED_MODULE_19__layout_customizer_customizer_component__["a" /* AppCustomizerComponent */],
                __WEBPACK_IMPORTED_MODULE_20__layout_customizer_toggle_quickview_directive__["a" /* ToggleQuickviewDirective */],
                // Footer
                __WEBPACK_IMPORTED_MODULE_21__layout_footer_footer_component__["a" /* AppFooterComponent */],
                // Search overlay
                __WEBPACK_IMPORTED_MODULE_22__layout_search_overlay_search_overlay_component__["a" /* AppSearchOverlayComponent */],
                __WEBPACK_IMPORTED_MODULE_23__layout_search_overlay_search_overlay_directive__["a" /* SearchOverlayDirective */],
                __WEBPACK_IMPORTED_MODULE_24__layout_search_overlay_open_search_overlay_directive__["a" /* OpenSearchOverlaylDirective */],
                //
                __WEBPACK_IMPORTED_MODULE_25__dashboard_dashboard_component__["a" /* DashboardComponent */],
                // Pages
                __WEBPACK_IMPORTED_MODULE_26__page_layouts_fullscreen_fullscreen_component__["a" /* PageLayoutFullscreenComponent */],
                __WEBPACK_IMPORTED_MODULE_29__components_jobs_list_jobs_list_component__["a" /* JobslistComponent */],
                __WEBPACK_IMPORTED_MODULE_30__components_projects_projects_list_projects_list_component__["a" /* ProjectsListComponent */],
                __WEBPACK_IMPORTED_MODULE_33__components_regions_list_regions_list_component__["a" /* RegionsListComponent */],
                __WEBPACK_IMPORTED_MODULE_34__components_run_list_run_list_component__["a" /* RunListComponent */],
                __WEBPACK_IMPORTED_MODULE_31__components_projects_projects_new_projects_new_component__["a" /* ProjectsNewComponent */],
                __WEBPACK_IMPORTED_MODULE_32__components_projects_projects_edit_projects_edit_component__["a" /* ProjectsEditComponent */],
                __WEBPACK_IMPORTED_MODULE_35__components_message_list_message_list_component__["a" /* MessageListComponent */],
                __WEBPACK_IMPORTED_MODULE_36__components_message_detail_message_detail_component__["a" /* MessageDetailComponent */],
                __WEBPACK_IMPORTED_MODULE_37__components_run_detail_run_detail_component__["a" /* RunDetailComponent */],
                __WEBPACK_IMPORTED_MODULE_38__components_issues_issues_list_issues_list_component__["a" /* IssuesListComponent */],
                __WEBPACK_IMPORTED_MODULE_39__components_issues_issues_new_issues_new_component__["a" /* IssuesNewComponent */],
                __WEBPACK_IMPORTED_MODULE_40__components_issues_issues_edit_issues_edit_component__["a" /* IssuesEditComponent */],
                __WEBPACK_IMPORTED_MODULE_41__components_marketplace_marketplace_list_marketplace_list_component__["a" /* MarketplaceListComponent */],
                __WEBPACK_IMPORTED_MODULE_42__components_marketplace_marketplace_new_marketplace_new_component__["a" /* MarketplaceNewComponent */],
                __WEBPACK_IMPORTED_MODULE_43__components_marketplace_marketplace_edit_marketplace_edit_component__["a" /* MarketplaceEditComponent */],
                __WEBPACK_IMPORTED_MODULE_44__components_monitor_monitor_all_monitor_all_component__["a" /* MonitorAllComponent */],
                __WEBPACK_IMPORTED_MODULE_45__components_monitor_monitor_new_monitor_new_component__["a" /* MonitorNewComponent */],
                __WEBPACK_IMPORTED_MODULE_46__components_monitor_monitor_edit_monitor_edit_component__["a" /* MonitorEditComponent */],
                __WEBPACK_IMPORTED_MODULE_47__components_doc_doc_list_doc_list_component__["a" /* DocListComponent */],
                __WEBPACK_IMPORTED_MODULE_48__components_doc_doc_new_doc_new_component__["a" /* DocNewComponent */],
                __WEBPACK_IMPORTED_MODULE_49__components_doc_doc_edit_doc_edit_component__["a" /* DocEditComponent */],
                __WEBPACK_IMPORTED_MODULE_50__components_support_support_list_support_list_component__["a" /* SupportListComponent */],
                __WEBPACK_IMPORTED_MODULE_51__components_support_support_new_support_new_component__["a" /* SupportNewComponent */],
                __WEBPACK_IMPORTED_MODULE_52__components_support_support_edit_support_edit_component__["a" /* SupportEditComponent */],
                __WEBPACK_IMPORTED_MODULE_53__components_analytics_analytics_list_analytics_list_component__["a" /* AnalyticsListComponent */],
                __WEBPACK_IMPORTED_MODULE_54__components_analytics_analytics_new_analytics_new_component__["a" /* AnalyticsNewComponent */],
                __WEBPACK_IMPORTED_MODULE_55__components_analytics_analytics_edit_analytics_edit_component__["a" /* AnalyticsEditComponent */],
                __WEBPACK_IMPORTED_MODULE_56__components_vault_vault_list_vault_list_component__["a" /* VaultListComponent */],
                __WEBPACK_IMPORTED_MODULE_57__components_vault_vault_new_vault_new_component__["a" /* VaultNewComponent */],
                __WEBPACK_IMPORTED_MODULE_58__components_vault_vault_edit_vault_edit_component__["a" /* VaultEditComponent */],
                __WEBPACK_IMPORTED_MODULE_59__components_billing_billing_list_billing_list_component__["a" /* BillingListComponent */],
                __WEBPACK_IMPORTED_MODULE_60__components_billing_billing_new_billing_new_component__["a" /* BillingNewComponent */],
                __WEBPACK_IMPORTED_MODULE_61__components_billing_billing_edit_billing_edit_component__["a" /* BillingEditComponent */],
                __WEBPACK_IMPORTED_MODULE_62__components_org_org_list_org_list_component__["a" /* OrgListComponent */],
                __WEBPACK_IMPORTED_MODULE_63__components_org_org_new_org_new_component__["a" /* OrgNewComponent */],
                __WEBPACK_IMPORTED_MODULE_64__components_org_org_edit_org_edit_component__["a" /* OrgEditComponent */],
                __WEBPACK_IMPORTED_MODULE_65__components_skills_skills_list_skills_list_component__["a" /* SkillsListComponent */],
                __WEBPACK_IMPORTED_MODULE_66__components_skills_skills_new_skills_new_component__["a" /* SkillsNewComponent */],
                __WEBPACK_IMPORTED_MODULE_67__components_skills_skills_edit_skills_edit_component__["a" /* SkillsEditComponent */],
                __WEBPACK_IMPORTED_MODULE_68__components_skills_skills_version_control_skills_version_control_component__["a" /* SkillsVersionControlComponent */],
                __WEBPACK_IMPORTED_MODULE_69__components_skills_skills_bot_deployment_skills_bot_deployment_component__["a" /* SkillsBotDeploymentComponent */],
                __WEBPACK_IMPORTED_MODULE_70__components_skills_skills_issue_tracker_skills_issue_tracker_component__["a" /* SkillsIssueTrackerComponent */],
                __WEBPACK_IMPORTED_MODULE_71__components_skills_skills_analytics_skills_analytics_component__["a" /* SkillsAnalyticsComponent */],
            ],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_8__app_component__["a" /* AppComponent */]],
            providers: [__WEBPACK_IMPORTED_MODULE_72__services_dashboard_service__["a" /* DashboardService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["g" /* ApplicationRef */]])
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "../../../../../src/app/charts/charts.config.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CHARTCONFIG; });
var CHARTCONFIG = {
    primary: 'rgba(33,150,243,.85)',
    success: 'rgba(102,187,106,.85)',
    info: 'rgba(0,188,212,.85)',
    infoAlt: 'rgba(126,87,194,.85)',
    warning: 'rgba(255,202,40,.85)',
    danger: 'rgba(233,75,59,.85)',
    gray: 'rgba(221,221,221,.3)',
    textColor: '#989898',
    splitLineColor: 'rgba(0,0,0,.05)',
    splitAreaColor: ['rgba(250,250,250,0.035)', 'rgba(200,200,200,0.1)'],
};


/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  analytics-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AnalyticsEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AnalyticsEditComponent = (function () {
    function AnalyticsEditComponent() {
    }
    AnalyticsEditComponent.prototype.ngOnInit = function () {
    };
    AnalyticsEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-analytics-edit',
            template: __webpack_require__("../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], AnalyticsEditComponent);
    return AnalyticsEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-list/analytics-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Analytics Hub</h1>\n    </div>\n    <p class=\"text-muted\">Integrate with in-house products or use in-built reports.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-list/analytics-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-list/analytics-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AnalyticsListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AnalyticsListComponent = (function () {
    function AnalyticsListComponent() {
    }
    AnalyticsListComponent.prototype.ngOnInit = function () {
    };
    AnalyticsListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-analytics-list',
            template: __webpack_require__("../../../../../src/app/components/analytics/analytics-list/analytics-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/analytics/analytics-list/analytics-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], AnalyticsListComponent);
    return AnalyticsListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-new/analytics-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  analytics-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-new/analytics-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/analytics/analytics-new/analytics-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AnalyticsNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AnalyticsNewComponent = (function () {
    function AnalyticsNewComponent() {
    }
    AnalyticsNewComponent.prototype.ngOnInit = function () {
    };
    AnalyticsNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-analytics-new',
            template: __webpack_require__("../../../../../src/app/components/analytics/analytics-new/analytics-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/analytics/analytics-new/analytics-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], AnalyticsNewComponent);
    return AnalyticsNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/billing/billing-edit/billing-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  billing-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-edit/billing-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-edit/billing-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BillingEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var BillingEditComponent = (function () {
    function BillingEditComponent() {
    }
    BillingEditComponent.prototype.ngOnInit = function () {
    };
    BillingEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-billing-edit',
            template: __webpack_require__("../../../../../src/app/components/billing/billing-edit/billing-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/billing/billing-edit/billing-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], BillingEditComponent);
    return BillingEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/billing/billing-list/billing-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Billing Hub</h1>\n    </div>\n    <p class=\"text-muted\">View team, individual itemized usage reports.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-list/billing-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-list/billing-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BillingListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var BillingListComponent = (function () {
    function BillingListComponent() {
    }
    BillingListComponent.prototype.ngOnInit = function () {
    };
    BillingListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-billing-list',
            template: __webpack_require__("../../../../../src/app/components/billing/billing-list/billing-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/billing/billing-list/billing-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], BillingListComponent);
    return BillingListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/billing/billing-new/billing-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  billing-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-new/billing-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/billing/billing-new/billing-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BillingNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var BillingNewComponent = (function () {
    function BillingNewComponent() {
    }
    BillingNewComponent.prototype.ngOnInit = function () {
    };
    BillingNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-billing-new',
            template: __webpack_require__("../../../../../src/app/components/billing/billing-new/billing-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/billing/billing-new/billing-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], BillingNewComponent);
    return BillingNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/doc/doc-edit/doc-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  doc-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-edit/doc-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-edit/doc-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DocEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var DocEditComponent = (function () {
    function DocEditComponent() {
    }
    DocEditComponent.prototype.ngOnInit = function () {
    };
    DocEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-doc-edit',
            template: __webpack_require__("../../../../../src/app/components/doc/doc-edit/doc-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/doc/doc-edit/doc-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], DocEditComponent);
    return DocEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/doc/doc-list/doc-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Doc Hub</h1>\n    </div>\n    <p class=\"text-muted\">Auto-Generated Project API documentation.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-list/doc-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-list/doc-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DocListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var DocListComponent = (function () {
    function DocListComponent() {
    }
    DocListComponent.prototype.ngOnInit = function () {
    };
    DocListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-doc-list',
            template: __webpack_require__("../../../../../src/app/components/doc/doc-list/doc-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/doc/doc-list/doc-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], DocListComponent);
    return DocListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/doc/doc-new/doc-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  doc-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-new/doc-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/doc/doc-new/doc-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DocNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var DocNewComponent = (function () {
    function DocNewComponent() {
    }
    DocNewComponent.prototype.ngOnInit = function () {
    };
    DocNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-doc-new',
            template: __webpack_require__("../../../../../src/app/components/doc/doc-new/doc-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/doc/doc-new/doc-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], DocNewComponent);
    return DocNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/issues/issues-edit/issues-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n\n    <h2 class=\"article-title\">Bug Bot</h2>\n    <div class=\"box box-default\">\n      <div class=\"box-body padding-xl\">\n\n        <form role=\"form\" #heroForm=\"ngForm\">\n\n          <div class=\"form-group row\">\n            <label for=\"name\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required disabled [(ngModel)]=\"subscription.name\" name=\"name\" id=\"name\"\n                       matInput placeholder=\"Bot name\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Access level\" [(ngModel)]=\"subscription.visibility\" name=\"Access level\">\n                  <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                    {{visibility}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n          <div class=\"divider divider-dashed divider-lg pull-in\"></div>\n\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Issue Tracker Type\" disabled [(ngModel)]=\"subscription.skill\" name=\"skill\" id=\"skill\">\n                  <mat-option *ngFor=\"let skill of skills\" [value]=\"skill\">\n                    {{skill.name}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"subscription.prop1\" name=\"url\"\n                       matInput placeholder=\"Issue Tracker URL\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <div class=\"col-md-2\"></div>\n            <div class=\"col-md-10\">\n              <button mat-raised-button color=\"primary\" (click)=\"update(); heroForm.reset()\"\n                      class=\"btn-w-md no-margin-left\">Save\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" color=\"warn\" class=\"btn-w-md\" (click)=\"delete(); heroForm.reset()\">Delete\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/issues']\">Cancel\n              </button>\n            </div>\n          </div>\n\n        </form>\n\n      </div>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-edit/issues-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-edit/issues-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return IssuesEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__ = __webpack_require__("../../../../../src/app/services/skill-subscription.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_skill_service__ = __webpack_require__("../../../../../src/app/services/skill.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__models_subscription_model__ = __webpack_require__("../../../../../src/app/models/subscription.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var IssuesEditComponent = (function () {
    function IssuesEditComponent(skillSubscriptionService, skillService, route, router) {
        this.skillSubscriptionService = skillSubscriptionService;
        this.skillService = skillService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.subscription = new __WEBPACK_IMPORTED_MODULE_4__models_subscription_model__["a" /* Subscription */]();
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    IssuesEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.getById(params['id']);
            }
        });
        //this.listSkills();
    };
    IssuesEditComponent.prototype.getById = function (id) {
        var _this = this;
        this.showSpinner = true;
        this.skillSubscriptionService.getById(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.subscription = results['data'];
            console.log(_this.subscription);
        }, function (error) {
            console.log("Unable to fetch vault");
        });
    };
    IssuesEditComponent.prototype.update = function () {
        var _this = this;
        console.log(this.subscription);
        this.skillSubscriptionService.update(this.subscription).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/issues']);
        }, function (error) {
            console.log("Unable to update vault");
        });
    };
    IssuesEditComponent.prototype.delete = function () {
        var _this = this;
        console.log(this.subscription);
        this.skillSubscriptionService.delete(this.subscription).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/issues']);
        }, function (error) {
            console.log("Unable to delete subscription");
        });
    };
    IssuesEditComponent.prototype.listSkills = function () {
        var _this = this;
        console.log('listing skills');
        this.skillService.get().subscribe(function (results) {
            if (results['errors']) {
                console.log(results);
                return;
            }
            _this.skills = results['data'];
        });
    };
    IssuesEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-issues-edit',
            template: __webpack_require__("../../../../../src/app/components/issues/issues-edit/issues-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/issues/issues-edit/issues-edit.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__["a" /* SkillSubscriptionService */], __WEBPACK_IMPORTED_MODULE_3__services_skill_service__["a" /* SkillService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__["a" /* SkillSubscriptionService */], __WEBPACK_IMPORTED_MODULE_3__services_skill_service__["a" /* SkillService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], IssuesEditComponent);
    return IssuesEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/issues/issues-list/issues-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n    <section class=\"hero\">\n        <div class=\"hero-content\">\n            <h1 class=\"hero-title\">Issue-Tracker Hub</h1>\n        </div>\n        <p class=\"text-muted\">AI Bug Bot can auto file/close bugs in issue-tracker systems.</p>\n    </section>\n\n    <article class=\"article padding-lg-v1 article-dark article-bordered\">\n        <div class=\"container-fluid with-maxwidth chapter\">\n            <article class=\"article\">\n                <h2 class=\"article-title\">\n                    <button mat-raised-button color=\"primary\" class=\"btn-w-md pull-right\"\n                            [routerLink]=\"['/app/issues/new']\">Launch Bug Bot\n                    </button>\n                    <div class=\"divider divider-sm\"></div>\n                </h2>\n                <br/>\n\n                <div class=\"box box-default table-box mdl-shadow--2dp\">\n                    <table class=\"mdl-data-table\">\n                        <thead>\n                        <tr>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Type</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Create-Date</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Owner</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                        </tr>\n                        </thead>\n                        <tbody>\n                        <tr *ngFor=\"let key of keys\">\n                            <td class=\"mdl-data-table__cell--non-numeric bold\">{{key.org.name}}/{{key.name}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric bold\">{{key.skill.name}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{key.visibility}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{key.createdDate | date:'shortDate'}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{key.createdBy}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\"><a href=\"javascript:;\"\n                                                                             [routerLink]=\"['/app/issues', key.id]\">Edit</a>\n                            </td>\n                        </tr>\n                        </tbody>\n                    </table>\n                </div>\n            </article>\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-list/issues-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-list/issues-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return IssuesListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_skill_subscription_service__ = __webpack_require__("../../../../../src/app/services/skill-subscription.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var IssuesListComponent = (function () {
    function IssuesListComponent(skillSubscriptionService) {
        this.skillSubscriptionService = skillSubscriptionService;
        this.showSpinner = false;
    }
    IssuesListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.skillSubscriptionService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.keys = results['data'];
        }, function (error) {
            console.log("Unable to fetch Subscriptions");
        });
    };
    IssuesListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-issues-list',
            template: __webpack_require__("../../../../../src/app/components/issues/issues-list/issues-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/issues/issues-list/issues-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_1__services_skill_subscription_service__["a" /* SkillSubscriptionService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_skill_subscription_service__["a" /* SkillSubscriptionService */]])
    ], IssuesListComponent);
    return IssuesListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/issues/issues-new/issues-new.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n    <article class=\"article\">\n\n        <h2 class=\"article-title\">New Issue-Tracker Bot</h2>\n        <div class=\"box box-default\">\n            <div class=\"box-body padding-xl\">\n\n                <form role=\"form\" #heroForm=\"ngForm\">\n\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Owner\" required [(ngModel)]=\"entry.org.id\" name=\"type\">\n                                    <mat-option *ngFor=\"let org of orgs\" [value]=\"org.org.id\">\n                                        {{org.org.name}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n\n                    <div class=\"form-group row\">\n                        <label for=\"name\" class=\"col-md-2 control-label\"></label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                                <input required [(ngModel)]=\"entry.name\" name=\"name\" id=\"name\"\n                                       matInput placeholder=\"Bot name\">\n                            </mat-input-container>\n                        </div>\n                    </div>\n\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Access level\" [(ngModel)]=\"entry.visibility\" name=\"Access level\">\n                                    <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                                        {{visibility}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n                    <div class=\"divider divider-dashed divider-lg pull-in\"></div>\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Issue Tracker Type\" [(ngModel)]=\"entry.skill\" name=\"skill\">\n                                    <mat-option *ngFor=\"let skill of skills\" [value]=\"skill\">\n                                        {{skill.name}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                                <input required [(ngModel)]=\"entry.prop1\" name=\"url\" id=\"repo\"\n                                       matInput placeholder=\"Issue Tracker URL\">\n                            </mat-input-container>\n                        </div>\n                    </div>\n\n                    <div class=\"form-group row\">\n                        <div class=\"col-md-2\"></div>\n                        <div class=\"col-md-10\">\n                            <button mat-raised-button color=\"primary\" (click)=\"create(); heroForm.reset()\"\n                                    class=\"btn-w-md no-margin-left\">Launch\n                            </button>\n                            <span class=\"space space-md\"></span>\n                            <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/issues']\">\n                                Cancel\n                            </button>\n                        </div>\n                    </div>\n\n                </form>\n\n            </div>\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-new/issues-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/issues/issues-new/issues-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return IssuesNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__ = __webpack_require__("../../../../../src/app/services/skill-subscription.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_skill_service__ = __webpack_require__("../../../../../src/app/services/skill.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__services_org_service__ = __webpack_require__("../../../../../src/app/services/org.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__models_subscription_model__ = __webpack_require__("../../../../../src/app/models/subscription.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var IssuesNewComponent = (function () {
    function IssuesNewComponent(skillSubscriptionService, skillService, orgService, route, router) {
        this.skillSubscriptionService = skillSubscriptionService;
        this.skillService = skillService;
        this.orgService = orgService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.entry = new __WEBPACK_IMPORTED_MODULE_5__models_subscription_model__["a" /* Subscription */]();
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    IssuesNewComponent.prototype.ngOnInit = function () {
        this.listSkills();
        this.getOrgs();
    };
    IssuesNewComponent.prototype.listSkills = function () {
        var _this = this;
        console.log('listing skills');
        this.skillService.get().subscribe(function (results) {
            if (results['errors']) {
                console.log(results);
                return;
            }
            _this.skills = results['data'];
        });
    };
    IssuesNewComponent.prototype.create = function () {
        var _this = this;
        console.log(this.entry);
        this.showSpinner = true;
        this.skillSubscriptionService.create(this.entry).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/issues']);
        }, function (error) {
            console.log("Unable to save subscription entry");
        });
    };
    IssuesNewComponent.prototype.getOrgs = function () {
        var _this = this;
        this.orgService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.orgs = results['data'];
        }, function (error) {
            console.log("Unable to fetch orgs");
        });
    };
    IssuesNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-issues-new',
            template: __webpack_require__("../../../../../src/app/components/issues/issues-new/issues-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/issues/issues-new/issues-new.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__["a" /* SkillSubscriptionService */], __WEBPACK_IMPORTED_MODULE_3__services_skill_service__["a" /* SkillService */], __WEBPACK_IMPORTED_MODULE_4__services_org_service__["a" /* OrgService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_skill_subscription_service__["a" /* SkillSubscriptionService */], __WEBPACK_IMPORTED_MODULE_3__services_skill_service__["a" /* SkillService */], __WEBPACK_IMPORTED_MODULE_4__services_org_service__["a" /* OrgService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], IssuesNewComponent);
    return IssuesNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/jobs-list/jobs-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n    <h2 class=\"article-title\">\n      <a href=\"javascript:;\" [routerLink]=\"['/app/projects']\"> Projects</a>\n      / {{project.name}} / Jobs</h2>\n    <div class=\"box box-default table-box mdl-shadow--2dp\">\n      <table class=\"mdl-data-table\">\n        <thead>\n        <tr>\n          <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Env</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Region</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Next-Fire</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Action</th>\n        </tr>\n        </thead>\n        <tbody>\n        <tr *ngFor=\"let job of jobs\">\n          <td class=\"mdl-data-table__cell--non-numeric\">{{job.project.org.name}}/{{job.project.name}}/{{job.name}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{job.environment}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{job.regions}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{job.nextFire | date:'short'}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs', job.id, 'runs']\">History</a> &nbsp;\n            <a href=\"javascript:;\" (click)=\"runJob(job.id)\">Run</a>\n          </td>\n        </tr>\n        </tbody>\n      </table>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/jobs-list/jobs-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/jobs-list/jobs-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return JobslistComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_jobs_service__ = __webpack_require__("../../../../../src/app/services/jobs.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_run_service__ = __webpack_require__("../../../../../src/app/services/run.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__models_base_model__ = __webpack_require__("../../../../../src/app/models/base.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
var JobslistComponent = (function () {
    function JobslistComponent(jobsService, runService, projectService, route, router) {
        this.jobsService = jobsService;
        this.runService = runService;
        this.projectService = projectService;
        this.route = route;
        this.router = router;
        this.projectId = "";
        this.project = new __WEBPACK_IMPORTED_MODULE_5__models_base_model__["a" /* Base */]();
        this.showSpinner = false;
    }
    JobslistComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.projectId = params['id'];
                _this.list(_this.projectId);
                _this.loadProject(_this.projectId);
            }
        });
    };
    JobslistComponent.prototype.loadProject = function (id) {
        var _this = this;
        this.projectService.getById(id).subscribe(function (results) {
            if (!results)
                return;
            _this.project = results['data'];
        });
    };
    JobslistComponent.prototype.list = function (id) {
        var _this = this;
        this.jobsService.getJobs(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (!results)
                return;
            _this.jobs = results['data'];
        }, function (error) {
            console.log("Unable to fetch jobs");
        });
    };
    JobslistComponent.prototype.runJob = function (id) {
        var _this = this;
        this.runService.run(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (!results)
                return;
            //this.jobs = results['data'];
            _this.router.navigate(['/app/runs', id]);
        }, function (error) {
            console.log("Unable to fetch jobs");
        });
    };
    JobslistComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-jobs-list',
            template: __webpack_require__("../../../../../src/app/components/jobs-list/jobs-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/jobs-list/jobs-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], JobslistComponent);
    return JobslistComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  marketplace-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MarketplaceEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MarketplaceEditComponent = (function () {
    function MarketplaceEditComponent() {
    }
    MarketplaceEditComponent.prototype.ngOnInit = function () {
    };
    MarketplaceEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-marketplace-edit',
            template: __webpack_require__("../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MarketplaceEditComponent);
    return MarketplaceEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Marketplace</h1>\n    </div>\n    <p class=\"text-muted\">Discover - Expert exchange, Skills, Test-Suites, & Services.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MarketplaceListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MarketplaceListComponent = (function () {
    function MarketplaceListComponent() {
    }
    MarketplaceListComponent.prototype.ngOnInit = function () {
    };
    MarketplaceListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-marketplace-list',
            template: __webpack_require__("../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MarketplaceListComponent);
    return MarketplaceListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  marketplace-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MarketplaceNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MarketplaceNewComponent = (function () {
    function MarketplaceNewComponent() {
    }
    MarketplaceNewComponent.prototype.ngOnInit = function () {
    };
    MarketplaceNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-marketplace-new',
            template: __webpack_require__("../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MarketplaceNewComponent);
    return MarketplaceNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/message-detail/message-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n    <article class=\"article\">\n        <h2 class=\"article-title\"> <a href=\"javascript:;\" [routerLink]=\"['/app/messages']\">Alerts</a> / {{item.subject}}\n        </h2>\n\n        <div class=\"box box-default table-box mdl-shadow--2dp\">\n            <div class=\"item-card card__horizontal1\">\n                <div class=\"card__body card-white\">\n                    <div class=\"card__title\">\n                        <h4>{{item.subject}}</h4>\n                        <h6>{{item.type}}</h6>\n                    </div>\n                    <div class=\"card__price\">\n                        <span>Date: {{item.createdDate | date:'short'}}</span>\n                        <span>Task:  {{item.taskType}}</span>\n                        <span>Status: {{item.status}}</span>\n\n                    </div>\n                    <div class=\"divider divider-solid divider-lg\"></div>\n                    <p class=\"card__desc\" style=\"white-space: pre;\">{{item.message}}</p>\n                </div>\n            </div>\n        </div>\n    </article>\n</section>"

/***/ }),

/***/ "../../../../../src/app/components/message-detail/message-detail.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/message-detail/message-detail.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MessageDetailComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_message_service__ = __webpack_require__("../../../../../src/app/services/message.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__models_message_model__ = __webpack_require__("../../../../../src/app/models/message.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var MessageDetailComponent = (function () {
    function MessageDetailComponent(messageService, route) {
        this.messageService = messageService;
        this.route = route;
        this.item = new __WEBPACK_IMPORTED_MODULE_3__models_message_model__["a" /* Message */]('', '', '', '', '', '');
        this.showSpinner = false;
    }
    MessageDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.getById(params['id']);
            }
        });
    };
    MessageDetailComponent.prototype.getById = function (id) {
        var _this = this;
        this.showSpinner = true;
        this.messageService.getById(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.item = results['data'];
            _this.msg = _this.item.message.replace(new RegExp('\n', 'g'), "<br />");
            console.log(_this.item);
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    MessageDetailComponent.prototype.filterNl = function (txt) {
        if (txt) {
            return txt.replace(/(?:\r\n|\r|\n)/g, '<br />');
        }
        return txt;
    };
    MessageDetailComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-message-detail',
            template: __webpack_require__("../../../../../src/app/components/message-detail/message-detail.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/message-detail/message-detail.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_message_service__["a" /* MessageService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_message_service__["a" /* MessageService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]])
    ], MessageDetailComponent);
    return MessageDetailComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/message-list/message-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n    <h2 class=\"article-title\">Alerts\n    </h2>\n\n    <div class=\"box box-default table-box mdl-shadow--2dp\">\n      <table class=\"mdl-data-table\">\n        <thead>\n        <tr>\n          <th class=\"mdl-data-table__cell--non-numeric\">Subject</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Task</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Type</th>\n          <th class=\"mdl-data-table__cell--non-numeric\">Create-Date</th>\n        </tr>\n        </thead>\n        <tbody>\n        <tr *ngFor=\"let item of items\">\n          <td class=\"mdl-data-table__cell--non-numeric\"><a href=\"javascript:;\" [routerLink]=\"['/app/message', item.id]\">{{item.subject}}</a></td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{item.taskType}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{item.type}}</td>\n          <td class=\"mdl-data-table__cell--non-numeric\">{{item.createdDate | date:'short'}}</td>\n        </tr>\n        </tbody>\n      </table>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/message-list/message-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/message-list/message-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MessageListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_message_service__ = __webpack_require__("../../../../../src/app/services/message.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MessageListComponent = (function () {
    function MessageListComponent(messageService) {
        this.messageService = messageService;
        this.showSpinner = false;
    }
    MessageListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.messageService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.items = results['data'];
        }, function (error) {
            console.log("Unable to fetch projects");
        });
    };
    MessageListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-message-list',
            template: __webpack_require__("../../../../../src/app/components/message-list/message-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/message-list/message-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_1__services_message_service__["a" /* MessageService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_message_service__["a" /* MessageService */]])
    ], MessageListComponent);
    return MessageListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-all/monitor-all.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Monitoring Hub</h1>\n    </div>\n    <p class=\"text-muted\">Real-time monitoring of APIs from across the globe.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-all/monitor-all.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-all/monitor-all.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MonitorAllComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MonitorAllComponent = (function () {
    function MonitorAllComponent() {
    }
    MonitorAllComponent.prototype.ngOnInit = function () {
    };
    MonitorAllComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-monitor-all',
            template: __webpack_require__("../../../../../src/app/components/monitor/monitor-all/monitor-all.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/monitor/monitor-all/monitor-all.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MonitorAllComponent);
    return MonitorAllComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  monitor-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MonitorEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MonitorEditComponent = (function () {
    function MonitorEditComponent() {
    }
    MonitorEditComponent.prototype.ngOnInit = function () {
    };
    MonitorEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-monitor-edit',
            template: __webpack_require__("../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MonitorEditComponent);
    return MonitorEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-new/monitor-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  monitor-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-new/monitor-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/monitor/monitor-new/monitor-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MonitorNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MonitorNewComponent = (function () {
    function MonitorNewComponent() {
    }
    MonitorNewComponent.prototype.ngOnInit = function () {
    };
    MonitorNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-monitor-new',
            template: __webpack_require__("../../../../../src/app/components/monitor/monitor-new/monitor-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/monitor/monitor-new/monitor-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], MonitorNewComponent);
    return MonitorNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/org/org-edit/org-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  org-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/org/org-edit/org-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/org/org-edit/org-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OrgEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var OrgEditComponent = (function () {
    function OrgEditComponent() {
    }
    OrgEditComponent.prototype.ngOnInit = function () {
    };
    OrgEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-org-edit',
            template: __webpack_require__("../../../../../src/app/components/org/org-edit/org-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/org/org-edit/org-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], OrgEditComponent);
    return OrgEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/org/org-list/org-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Org Hub</h1>\n    </div>\n    <p class=\"text-muted\">Manage Org, Users, & Access-Controls</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/org/org-list/org-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/org/org-list/org-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OrgListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var OrgListComponent = (function () {
    function OrgListComponent() {
    }
    OrgListComponent.prototype.ngOnInit = function () {
    };
    OrgListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-org-list',
            template: __webpack_require__("../../../../../src/app/components/org/org-list/org-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/org/org-list/org-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], OrgListComponent);
    return OrgListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/org/org-new/org-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  org-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/org/org-new/org-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/org/org-new/org-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OrgNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var OrgNewComponent = (function () {
    function OrgNewComponent() {
    }
    OrgNewComponent.prototype.ngOnInit = function () {
    };
    OrgNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-org-new',
            template: __webpack_require__("../../../../../src/app/components/org/org-new/org-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/org/org-new/org-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], OrgNewComponent);
    return OrgNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/projects/projects-edit/projects-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n\n    <h2 class=\"article-title\"><a href=\"javascript:;\" [routerLink]=\"['/app/projects']\"> Projects </a> / {{project.name}}</h2>\n    <div class=\"box box-default\">\n      <div class=\"box-body padding-xl\">\n\n        <form role=\"form\" #heroForm=\"ngForm\">\n          <div class=\"form-group row\">\n            <label for=\"name\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input [(ngModel)]=\"project.name\" disabled name=\"name\" id=\"name\"\n                       matInput placeholder=\"Project name\">\n              </mat-input-container>\n\n            </div>\n          </div>\n          <div class=\"form-group row\">\n            <label for=\"repo\" class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Access level\" [(ngModel)]=\"project.visibility\" name=\"Access level\">\n                  <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                    {{visibility}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n          <div class=\"divider divider-dashed divider-lg pull-in\"></div>\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Type\" required [(ngModel)]=\"project.projectType\" name=\"type\" id=\"type\">\n                  <mat-option *ngFor=\"let projectType of projectTypes\" [value]=\"projectType\">\n                    {{projectType}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n          <div class=\"form-group row\">\n            <label for=\"repo\" class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"project.url\" name=\"url\" id=\"repo\"\n                       matInput placeholder=\"Repository URL e.g. https://github.com/username/Fx-UI.git\">\n              </mat-input-container>\n            </div>\n          </div>\n          <div class=\"form-group row\">\n            <label for=\"username\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input [(ngModel)]=\"project.username\" name=\"username\" id=\"username\"\n                       matInput placeholder=\"Repository access-key or username\">\n              </mat-input-container>\n            </div>\n          </div>\n          <div class=\"form-group row\">\n            <label for=\"pass\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input type=\"password\" [(ngModel)]=\"project.password\" name=\"password\" id=\"pass\"\n                       matInput placeholder=\"Repository secret-key or password\">\n              </mat-input-container>\n            </div>\n          </div>\n\n\n          <div class=\"divider divider-md\"></div>\n          <div class=\"form-group row\">\n            <div class=\"col-md-2\"></div>\n            <div class=\"col-md-8\">\n              <button mat-raised-button color=\"primary\" (click)=\"update(); heroForm.reset()\"\n                      class=\"btn-w-md no-margin-left\">Save\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" color=\"warn\" class=\"btn-w-md\" (click)=\"delete(); heroForm.reset()\">Delete\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/projects']\">Cancel\n              </button>\n            </div>\n          </div>\n\n        </form>\n\n      </div>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-edit/projects-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-edit/projects-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectsEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__models_project_model__ = __webpack_require__("../../../../../src/app/models/project.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ProjectsEditComponent = (function () {
    function ProjectsEditComponent(projectService, route, router) {
        this.projectService = projectService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.project = new __WEBPACK_IMPORTED_MODULE_3__models_project_model__["a" /* Project */]('', '', '', '', 'GIT');
        this.projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab'];
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    ProjectsEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.getById(params['id']);
            }
        });
    };
    ProjectsEditComponent.prototype.getById = function (id) {
        var _this = this;
        this.showSpinner = true;
        this.projectService.getById(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.project = results['data'];
            console.log(_this.project);
        }, function (error) {
            console.log("Unable to fetch projects");
        });
    };
    ProjectsEditComponent.prototype.update = function () {
        var _this = this;
        console.log(this.project);
        this.projectService.update(this.project).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/projects']);
        }, function (error) {
            console.log("Unable to update project");
        });
    };
    ProjectsEditComponent.prototype.delete = function () {
        var _this = this;
        console.log(this.project);
        this.projectService.delete(this.project).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/projects']);
        }, function (error) {
            console.log("Unable to delete project");
        });
    };
    ProjectsEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-projects-edit',
            template: __webpack_require__("../../../../../src/app/components/projects/projects-edit/projects-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/projects/projects-edit/projects-edit.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_project_service__["a" /* ProjectService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], ProjectsEditComponent);
    return ProjectsEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/projects/projects-list/projects-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n    <article class=\"article\">\n        <h2 class=\"article-title\">Projects\n            <button mat-raised-button color=\"primary\" class=\"btn-w-md pull-right\" [routerLink]=\"['/app/projects/new']\">New Project</button><div class=\"divider divider-sm\"></div>\n        </h2>\n\n        <div class=\"box box-default table-box mdl-shadow--2dp\">\n            <table class=\"mdl-data-table\">\n                <thead>\n                <tr>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Create-Date</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Jobs</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                </tr>\n                </thead>\n                <tbody>\n                <tr *ngFor=\"let project of projects\">\n                    <td class=\"mdl-data-table__cell--non-numeric bold\">{{project.org.name}}/{{project.name}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{project.visibility}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{project.createdDate | date:'shortDate'}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\"><a href=\"javascript:;\" [routerLink]=\"['/app/projects', project.id, 'jobs']\">Jobs</a></td>\n                    <td class=\"mdl-data-table__cell--non-numeric\"><a href=\"javascript:;\" [routerLink]=\"['/app/projects', project.id]\">Edit</a></td>\n                </tr>\n                </tbody>\n            </table>\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-list/projects-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-list/projects-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectsListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProjectsListComponent = (function () {
    function ProjectsListComponent(projectService) {
        this.projectService = projectService;
        this.projectTitle = "Projects";
        this.showSpinner = false;
    }
    ProjectsListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.projectService.getProjects().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.projects = results['data'];
        }, function (error) {
            console.log("Unable to fetch projects");
        });
    };
    ProjectsListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-projects-list',
            template: __webpack_require__("../../../../../src/app/components/projects/projects-list/projects-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/projects/projects-list/projects-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_1__services_project_service__["a" /* ProjectService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_project_service__["a" /* ProjectService */]])
    ], ProjectsListComponent);
    return ProjectsListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/projects/projects-new/projects-new.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n    <article class=\"article\">\n\n        <h2 class=\"article-title\">New Project</h2>\n        <div class=\"box box-default\">\n            <div class=\"box-body padding-xl\">\n\n                <form role=\"form\" #heroForm=\"ngForm\">\n                    <div class=\"form-group row\">\n                        <label class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Owner\" required [(ngModel)]=\"project.orgId\" name=\"owner\">\n                                    <mat-option *ngFor=\"let org of orgs\" [value]=\"org.org.id\">\n                                        {{org.org.name}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <label for=\"name\" class=\"col-md-2 control-label\"></label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                                <input required [(ngModel)]=\"project.name\" name=\"name\" id=\"name\"\n                                       matInput placeholder=\"Project name\">\n                            </mat-input-container>\n\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Access level\" [(ngModel)]=\"project.visibility\" name=\"Access level\">\n                                    <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                                        {{visibility}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n                    <div class=\"divider divider-dashed divider-lg pull-in\"></div>\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-form-field>\n                                <mat-select placeholder=\"Type\" required [(ngModel)]=\"project.projectType\" name=\"type\">\n                                    <mat-option *ngFor=\"let projectType of projectTypes\" [value]=\"projectType\">\n                                        {{projectType}}\n                                    </mat-option>\n                                </mat-select>\n                            </mat-form-field>\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <label for=\"repo\" class=\"col-md-2 control-label\">\n                        </label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                                <input required [(ngModel)]=\"project.url\" name=\"url\" id=\"repo\"\n                                       matInput placeholder=\"Repository URL e.g. https://github.com/username/Fx-UI.git\">\n                            </mat-input-container>\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <label for=\"username\" class=\"col-md-2 control-label\"></label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                            <input [(ngModel)]=\"project.username\" name=\"username\" id=\"username\"\n                                   matInput placeholder=\"Repository access-key or username\">\n                            </mat-input-container>\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <label for=\"pass\" class=\"col-md-2 control-label\"></label>\n                        <div class=\"col-md-8\">\n                            <mat-input-container class=\"full-width\">\n                            <input type=\"password\" [(ngModel)]=\"project.password\" name=\"password\" id=\"pass\"\n                                   matInput placeholder=\"Repository secret-key or password\">\n                            </mat-input-container>\n                        </div>\n                    </div>\n\n\n                    <div class=\"divider divider-md\"></div>\n                    <div class=\"form-group row\">\n                        <div class=\"col-md-2\"></div>\n                        <div class=\"col-md-8\">\n                            <button mat-raised-button color=\"primary\" (click)=\"create(); heroForm.reset()\"\n                                    class=\"btn-w-md no-margin-left\">Add\n                            </button>\n                            <span class=\"space space-md\"></span>\n                            <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/projects']\">Cancel\n                            </button>\n                        </div>\n                    </div>\n                    <div class=\"form-group row\">\n                        <div class=\"col-md-2\"></div>\n                        <div class=\"col-md-10\">\n                            <div class=\"callout callout-info\">\n                                <p>Fx will launch a vBot for every project. The vBot will monitor the project repository's master branch for new commits and will sync back changed files.</p>\n                            </div>\n                        </div>\n                    </div>\n\n                </form>\n\n            </div>\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-new/projects-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/projects/projects-new/projects-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectsNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_org_service__ = __webpack_require__("../../../../../src/app/services/org.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__models_project_model__ = __webpack_require__("../../../../../src/app/models/project.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ProjectsNewComponent = (function () {
    function ProjectsNewComponent(projectService, orgService, route, router) {
        this.projectService = projectService;
        this.orgService = orgService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.project = new __WEBPACK_IMPORTED_MODULE_4__models_project_model__["a" /* Project */]('', '', '', '', 'GIT');
        this.projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab'];
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    ProjectsNewComponent.prototype.ngOnInit = function () {
        this.getOrgs();
    };
    ProjectsNewComponent.prototype.create = function () {
        var _this = this;
        console.log(this.project);
        this.showSpinner = true;
        this.projectService.create(this.project).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/projects']);
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    ProjectsNewComponent.prototype.getOrgs = function () {
        var _this = this;
        this.orgService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.orgs = results['data'];
        }, function (error) {
            console.log("Unable to fetch orgs");
        });
    };
    ProjectsNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-projects-new',
            template: __webpack_require__("../../../../../src/app/components/projects/projects-new/projects-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/projects/projects-new/projects-new.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_3__services_org_service__["a" /* OrgService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_3__services_org_service__["a" /* OrgService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], ProjectsNewComponent);
    return ProjectsNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/regions-list/regions-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n    <section class=\"hero\">\n        <div class=\"hero-content\">\n            <h1 class=\"hero-title\">Bot Hub</h1>\n        </div>\n        <p class=\"text-muted\">Fx Bot Regions</p>\n    </section>\n\n    <article class=\"article padding-lg-v1 article-dark article-bordered\">\n\n        <div class=\"container-fluid with-maxwidth chapter\">\n\n            <article class=\"article\">\n                <h2 class=\"article-title\">\n                    <!--[routerLink]=\"['/app/regions/new']\" -->\n                    <button mat-raised-button color=\"primary\" class=\"btn-w-md pull-right\">Launch Bots\n                    </button>\n                    <div class=\"divider divider-sm\"></div>\n                </h2>\n                <br/>\n\n                <div class=\"box box-default table-box mdl-shadow--2dp\">\n                    <table class=\"mdl-data-table\">\n                        <thead>\n                        <tr>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n                            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n                        </tr>\n                        </thead>\n                        <tbody>\n                        <tr *ngFor=\"let item of list\">\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n                            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n                        </tr>\n                        </tbody>\n                    </table>\n                </div>\n            </article>\n\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/regions-list/regions-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/regions-list/regions-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RegionsListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_regions_service__ = __webpack_require__("../../../../../src/app/services/regions.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RegionsListComponent = (function () {
    function RegionsListComponent(regionService) {
        this.regionService = regionService;
        this.title = "Bot Regions";
        this.showSpinner = false;
    }
    RegionsListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.regionService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.list = results['data'];
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    RegionsListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-regions-list',
            template: __webpack_require__("../../../../../src/app/components/regions-list/regions-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/regions-list/regions-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_1__services_regions_service__["a" /* RegionsService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_regions_service__["a" /* RegionsService */]])
    ], RegionsListComponent);
    return RegionsListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/run-detail/run-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth-lg1 no-breadcrumbs chapter\">\n    <article class=\"article\">\n        <h2 class=\"article-title\">\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects']\"> Projects </a>\n            / {{project.name}} /\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs']\"> Jobs </a>\n            /\n            <!--<a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs', jobId]\"> -->{{job.name}}<!--</a>-->\n            /\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs', jobId, 'runs']\"> Runs </a>\n            / {{run.runId}}\n        </h2>\n    </article>\n\n\n    <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <div class=\"item-card card__horizontal1\">\n            <div class=\"card__body card-white\">\n                <div class=\"card__title\">\n                    <h4>ID: {{run.runId}}</h4>\n                    <h4>Status: {{run.task.status}}</h4>\n                    <h4>Date: {{run.task.startTime | date:'short'}}</h4>\n                    <h4>Total Time: {{run.task.totalTime}}</h4>\n                </div>\n                <div class=\"divider divider-solid divider-lg\"></div>\n                <p class=\"card__desc\" style=\"white-space: pre;\">{{run.task.description}}</p>\n            </div>\n        </div>\n    </div>\n\n\n    <article class=\"article\">\n        <div class=\"box box-default table-box mdl-shadow--2dp\">\n            <table class=\"mdl-data-table\">\n                <thead>\n                <tr>\n                    <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Status</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Suite</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Total/Passed</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Total Size</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Total Time</th>\n                </tr>\n                </thead>\n                <tr *ngFor=\"let s of suites\">\n                    <td class=\"mdl-data-table__cell--non-numeric\" *ngIf=\"s.failed == 0\"><span class=\"text-success\"><i\n                            class=\"material-icons\">done</i> </span></td>\n                    <td class=\"mdl-data-table__cell--non-numeric\" *ngIf=\"s.failed == 0\"><span\n                            class=\"text-success\"> Passed</span></td>\n                    <td class=\"mdl-data-table__cell--non-numeric\" *ngIf=\"s.failed != 0\"><span class=\"text-danger\"><i\n                            class=\"material-icons\">clear</i> </span></td>\n                    <td class=\"mdl-data-table__cell--non-numeric\" *ngIf=\"s.failed != 0\"><span\n                            class=\"text-danger\"> Fail</span></td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{s.suiteName}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{s.tests}}/{{s.tests - s.failed}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{s.size}} B</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{s.time}} ms</td>\n                </tr>\n                <tfoot>\n                <tr class=\"bg-color-info1\">\n                    <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                    <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                    <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">{{total}}/{{total-failed}}</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">{{size}} B</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">{{time}} ms</th>\n                </tr>\n                </tfoot>\n            </table>\n        </div>\n    </article>\n\n    <a href=\"javascript:;\" (click)=\"getTestSuiteResponsesByRunId()\"> View Logs </a>\n\n    <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <div class=\"item-card card__horizontal1\" *ngFor=\"let item of list\">\n            <div class=\"card__body card-white\">\n                <div class=\"card__title\">\n                    <h4>{{item.testSuite}}</h4>\n                    <h6>Test-Suite</h6>\n                </div>\n                <div class=\"card__price\">\n                    <span>Pass: {{item.totalPassed}}</span>\n                    <span class=\"type--strikethrough\">Fail: {{item.totalFailed}}</span>\n                    <span>Time: {{item.requestTime}}</span>\n                </div>\n                <div class=\"divider divider-solid divider-lg\"></div>\n                <p class=\"card__desc\" style=\"white-space: pre;\">{{item.logs}}</p>\n            </div>\n        </div>\n    </div>\n</section>"

/***/ }),

/***/ "../../../../../src/app/components/run-detail/run-detail.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/run-detail/run-detail.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RunDetailComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_jobs_service__ = __webpack_require__("../../../../../src/app/services/jobs.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_run_service__ = __webpack_require__("../../../../../src/app/services/run.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__models_base_model__ = __webpack_require__("../../../../../src/app/models/base.model.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__models_run_model__ = __webpack_require__("../../../../../src/app/models/run.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var RunDetailComponent = (function () {
    function RunDetailComponent(jobsService, runService, projectService, route) {
        this.jobsService = jobsService;
        this.runService = runService;
        this.projectService = projectService;
        this.route = route;
        this.run = new __WEBPACK_IMPORTED_MODULE_6__models_run_model__["a" /* Run */]();
        this.projectId = "";
        this.jobId = "";
        this.total = 0;
        this.failed = 0;
        this.size = 0;
        this.time = 0;
        this.project = new __WEBPACK_IMPORTED_MODULE_5__models_base_model__["a" /* Base */]();
        this.job = new __WEBPACK_IMPORTED_MODULE_5__models_base_model__["a" /* Base */]();
        this.showSpinner = false;
    }
    RunDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.projectId = params['id'];
                _this.loadProject(_this.projectId);
            }
            if (params['jobId']) {
                _this.jobId = params['jobId'];
                _this.loadJob(_this.jobId);
            }
            if (params['runId']) {
                _this.id = params['runId'];
                _this.getRunById();
                _this.getSummary();
            }
        });
    };
    RunDetailComponent.prototype.loadProject = function (id) {
        var _this = this;
        this.projectService.getById(id).subscribe(function (results) {
            if (!results)
                return;
            _this.project = results['data'];
        });
    };
    RunDetailComponent.prototype.loadJob = function (id) {
        var _this = this;
        this.jobsService.getById(id).subscribe(function (results) {
            if (!results)
                return;
            _this.job = results['data'];
        });
    };
    RunDetailComponent.prototype.calSum = function () {
        for (var i = 0; i < this.suites.length; i++) {
            this.total += this.suites[i].tests;
            this.failed += this.suites[i].failed;
            this.size += this.suites[i].size;
            this.time += this.suites[i].time;
        }
    };
    RunDetailComponent.prototype.getRunById = function () {
        var _this = this;
        this.showSpinner = true;
        this.runService.getDetails(this.id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.run = results['data'];
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    RunDetailComponent.prototype.getTestSuiteResponsesByRunId = function () {
        var _this = this;
        this.showSpinner = true;
        this.runService.getTestSuiteResponses(this.id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.list = results['data'];
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    RunDetailComponent.prototype.getSummary = function () {
        var _this = this;
        this.showSpinner = true;
        this.runService.getSummary(this.id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.suites = results['data'];
            _this.calSum();
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    RunDetailComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-run-detail',
            template: __webpack_require__("../../../../../src/app/components/run-detail/run-detail.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/run-detail/run-detail.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]])
    ], RunDetailComponent);
    return RunDetailComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/run-list/run-list.component.html":
/***/ (function(module, exports) {

module.exports = "\n<section class=\"container-fluid with-maxwidth chapter\">\n\n    <article class=\"article\">\n        <h2 class=\"article-title\">\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects']\"> Projects </a>\n            / {{project.name}} /\n            <a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs']\"> Jobs </a>\n            /\n            <!--<a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs', jobId]\"> -->{{job.name}}<!--</a>--> /\n            Runs</h2>\n        <div class=\"box box-default table-box mdl-shadow--2dp\">\n            <table class=\"mdl-data-table\">\n                <thead>\n                <tr>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Region</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Status</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Total/Passed</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Status</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Time (ms)</th>\n                    <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n                </tr>\n                </thead>\n                <tbody>\n                <tr *ngFor=\"let item of list\">\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{item.job.regions}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{item.task.status}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{item.task.totalTests}}/{{item.task.totalTestCompleted}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">\n                        {{ ( (item.task.totalTestCompleted * 100 ) / (item.task.totalTestCompleted + item.task.failedTests) ).toFixed(0) }}%\n                    </td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">{{item.task.totalTime}}</td>\n                    <td class=\"mdl-data-table__cell--non-numeric\">\n                        <a href=\"javascript:;\" [routerLink]=\"['/app/projects', projectId, 'jobs', jobId, 'runs', item.id]\">#{{item.runId}}</a>\n                    </td>\n                </tr>\n                </tbody>\n            </table>\n        </div>\n    </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/run-list/run-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/run-list/run-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RunListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_jobs_service__ = __webpack_require__("../../../../../src/app/services/jobs.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_run_service__ = __webpack_require__("../../../../../src/app/services/run.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__services_project_service__ = __webpack_require__("../../../../../src/app/services/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__models_base_model__ = __webpack_require__("../../../../../src/app/models/base.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var RunListComponent = (function () {
    function RunListComponent(jobsService, runService, projectService, route) {
        this.jobsService = jobsService;
        this.runService = runService;
        this.projectService = projectService;
        this.route = route;
        this.projectId = "";
        this.jobId = "";
        this.title = "";
        this.project = new __WEBPACK_IMPORTED_MODULE_5__models_base_model__["a" /* Base */]();
        this.job = new __WEBPACK_IMPORTED_MODULE_5__models_base_model__["a" /* Base */]();
        this.showSpinner = false;
    }
    RunListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.projectId = params['id'];
                _this.loadProject(_this.projectId);
            }
            if (params['jobId']) {
                _this.jobId = params['jobId'];
                _this.loadJob(_this.jobId);
                _this.getRunByJob(_this.jobId);
            }
        });
    };
    RunListComponent.prototype.loadProject = function (id) {
        var _this = this;
        this.projectService.getById(id).subscribe(function (results) {
            if (!results)
                return;
            _this.project = results['data'];
        });
    };
    RunListComponent.prototype.loadJob = function (id) {
        var _this = this;
        this.jobsService.getById(id).subscribe(function (results) {
            if (!results)
                return;
            _this.job = results['data'];
        });
    };
    RunListComponent.prototype.getRunByJob = function (id) {
        var _this = this;
        this.showSpinner = true;
        this.runService.get(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.list = results['data'];
        }, function (error) {
            console.log("Unable to fetch regions");
        });
    };
    RunListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-run-list',
            template: __webpack_require__("../../../../../src/app/components/run-list/run-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/run-list/run-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_jobs_service__["a" /* JobsService */], __WEBPACK_IMPORTED_MODULE_3__services_run_service__["a" /* RunService */], __WEBPACK_IMPORTED_MODULE_4__services_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */]])
    ], RunListComponent);
    return RunListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Skills Hub</h1>\n    </div>\n    <p class=\"text-muted\">Develop and Register Analytics Skills</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsAnalyticsComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsAnalyticsComponent = (function () {
    function SkillsAnalyticsComponent() {
    }
    SkillsAnalyticsComponent.prototype.ngOnInit = function () {
    };
    SkillsAnalyticsComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-analytics',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsAnalyticsComponent);
    return SkillsAnalyticsComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Skills Hub</h1>\n    </div>\n    <p class=\"text-muted\">Develop and Register Bot Deployment across private/public cloud Skills</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsBotDeploymentComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsBotDeploymentComponent = (function () {
    function SkillsBotDeploymentComponent() {
    }
    SkillsBotDeploymentComponent.prototype.ngOnInit = function () {
    };
    SkillsBotDeploymentComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-bot-deployment',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsBotDeploymentComponent);
    return SkillsBotDeploymentComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-edit/skills-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  skills-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-edit/skills-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-edit/skills-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsEditComponent = (function () {
    function SkillsEditComponent() {
    }
    SkillsEditComponent.prototype.ngOnInit = function () {
    };
    SkillsEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-edit',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-edit/skills-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-edit/skills-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsEditComponent);
    return SkillsEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Skills Hub</h1>\n    </div>\n    <p class=\"text-muted\">Develop and Register Issue-Tracker Skills</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsIssueTrackerComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsIssueTrackerComponent = (function () {
    function SkillsIssueTrackerComponent() {
    }
    SkillsIssueTrackerComponent.prototype.ngOnInit = function () {
    };
    SkillsIssueTrackerComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-issue-tracker',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsIssueTrackerComponent);
    return SkillsIssueTrackerComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-list/skills-list.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  skills-list works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-list/skills-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-list/skills-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsListComponent = (function () {
    function SkillsListComponent() {
    }
    SkillsListComponent.prototype.ngOnInit = function () {
    };
    SkillsListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-list',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-list/skills-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-list/skills-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsListComponent);
    return SkillsListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-new/skills-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  skills-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-new/skills-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-new/skills-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsNewComponent = (function () {
    function SkillsNewComponent() {
    }
    SkillsNewComponent.prototype.ngOnInit = function () {
    };
    SkillsNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-new',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-new/skills-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-new/skills-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsNewComponent);
    return SkillsNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Skills Hub</h1>\n    </div>\n    <p class=\"text-muted\">Develop and Register Version-Control Skills</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillsVersionControlComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SkillsVersionControlComponent = (function () {
    function SkillsVersionControlComponent() {
    }
    SkillsVersionControlComponent.prototype.ngOnInit = function () {
    };
    SkillsVersionControlComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-skills-version-control',
            template: __webpack_require__("../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SkillsVersionControlComponent);
    return SkillsVersionControlComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/support/support-edit/support-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  support-edit works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/support/support-edit/support-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/support/support-edit/support-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SupportEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SupportEditComponent = (function () {
    function SupportEditComponent() {
    }
    SupportEditComponent.prototype.ngOnInit = function () {
    };
    SupportEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-support-edit',
            template: __webpack_require__("../../../../../src/app/components/support/support-edit/support-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/support/support-edit/support-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SupportEditComponent);
    return SupportEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/support/support-list/support-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Support Hub</h1>\n    </div>\n    <p class=\"text-muted\">FX AI Bot can help validate customers issues and file bugs.</p>\n  </section>\n\n  <article class=\"article padding-lg-v article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n      <div class=\"box box-default table-box mdl-shadow--2dp\">\n        <!--<table class=\"mdl-data-table\">\n          <thead>\n          <tr>\n            <th class=\"mdl-data-table__cell--non-numeric\">Name</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Location</th>\n            <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n          </tr>\n          </thead>\n          <tbody>\n          <tr *ngFor=\"let item of list\">\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.org.name}}/{{item.name}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.region}}</td>\n            <td class=\"mdl-data-table__cell--non-numeric\">{{item.visibility}}</td>\n          </tr>\n          </tbody>\n        </table>-->\n      </div>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/support/support-list/support-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/support/support-list/support-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SupportListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SupportListComponent = (function () {
    function SupportListComponent() {
    }
    SupportListComponent.prototype.ngOnInit = function () {
    };
    SupportListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-support-list',
            template: __webpack_require__("../../../../../src/app/components/support/support-list/support-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/support/support-list/support-list.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SupportListComponent);
    return SupportListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/support/support-new/support-new.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  support-new works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/components/support/support-new/support-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/support/support-new/support-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SupportNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SupportNewComponent = (function () {
    function SupportNewComponent() {
    }
    SupportNewComponent.prototype.ngOnInit = function () {
    };
    SupportNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-support-new',
            template: __webpack_require__("../../../../../src/app/components/support/support-new/support-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/support/support-new/support-new.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], SupportNewComponent);
    return SupportNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/vault/vault-edit/vault-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n\n    <h2 class=\"article-title\"><a href=\"javascript:;\" [routerLink]=\"['/app/vault']\"> Vault </a> / {{entry.key}}</h2>\n    <div class=\"box box-default\">\n      <div class=\"box-body padding-xl\">\n\n        <form role=\"form\" #heroForm=\"ngForm\">\n          <div class=\"form-group row\">\n            <label for=\"name\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"entry.key\" name=\"name\" id=\"name\"\n                       matInput placeholder=\"Entry key\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Access level\" [(ngModel)]=\"entry.visibility\" name=\"visibility\">\n                  <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                    {{visibility}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n          <div class=\"divider divider-md\"></div>\n          <div class=\"form-group row\">\n            <label for=\"secret\" class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"entry.val\" name=\"secret\" id=\"secret\"\n                       matInput placeholder=\"Secret\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <div class=\"col-md-2\"></div>\n            <div class=\"col-md-8\">\n              <button mat-raised-button color=\"primary\" (click)=\"update(); heroForm.reset()\"\n                      class=\"btn-w-md no-margin-left\">Save\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" color=\"warn\" class=\"btn-w-md\" (click)=\"delete(); heroForm.reset()\">Delete\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/vault']\">Cancel\n              </button>\n            </div>\n          </div>\n\n        </form>\n\n      </div>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-edit/vault-edit.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-edit/vault-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VaultEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_vault_service__ = __webpack_require__("../../../../../src/app/services/vault.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__models_vault_model__ = __webpack_require__("../../../../../src/app/models/vault.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var VaultEditComponent = (function () {
    function VaultEditComponent(vaultService, route, router) {
        this.vaultService = vaultService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.entry = new __WEBPACK_IMPORTED_MODULE_3__models_vault_model__["a" /* Vault */]();
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    VaultEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            if (params['id']) {
                _this.getById(params['id']);
            }
        });
    };
    VaultEditComponent.prototype.getById = function (id) {
        var _this = this;
        this.showSpinner = true;
        this.vaultService.getById(id).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.entry = results['data'];
            console.log(_this.entry);
        }, function (error) {
            console.log("Unable to fetch vault");
        });
    };
    VaultEditComponent.prototype.update = function () {
        var _this = this;
        console.log(this.entry);
        this.vaultService.update(this.entry).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/vault']);
        }, function (error) {
            console.log("Unable to update vault");
        });
    };
    VaultEditComponent.prototype.delete = function () {
        var _this = this;
        console.log(this.entry);
        this.vaultService.delete(this.entry).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/vault']);
        }, function (error) {
            console.log("Unable to delete entry");
        });
    };
    VaultEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-vault-edit',
            template: __webpack_require__("../../../../../src/app/components/vault/vault-edit/vault-edit.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/vault/vault-edit/vault-edit.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_vault_service__["a" /* VaultService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_vault_service__["a" /* VaultService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], VaultEditComponent);
    return VaultEditComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/vault/vault-list/vault-list.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n  <section class=\"hero\">\n    <div class=\"hero-content\">\n      <h1 class=\"hero-title\">Vault</h1>\n    </div>\n    <p class=\"text-muted\">Securely store & inject sensitive data at real-time in test-suites.</p>\n  </section>\n\n  <article class=\"article padding-lg-v1 article-dark article-bordered\">\n    <div class=\"container-fluid with-maxwidth chapter\">\n\n          <article class=\"article\">\n            <h2 class=\"article-title\">\n              <button mat-raised-button color=\"primary\" class=\"btn-w-md pull-right\" [routerLink]=\"['/app/vault/new']\">New Secret</button><div class=\"divider divider-sm\"></div>\n            </h2>\n            <br/>\n\n            <div class=\"box box-default table-box mdl-shadow--2dp\">\n              <table class=\"mdl-data-table\">\n                <thead>\n                <tr>\n                  <th class=\"mdl-data-table__cell--non-numeric\">Key</th>\n                  <th class=\"mdl-data-table__cell--non-numeric\">Visibility</th>\n                  <th class=\"mdl-data-table__cell--non-numeric\">Create-Date</th>\n                  <th class=\"mdl-data-table__cell--non-numeric\">Owner</th>\n                  <th class=\"mdl-data-table__cell--non-numeric\"></th>\n                </tr>\n                </thead>\n                <tbody>\n                <tr *ngFor=\"let key of keys\">\n                  <td class=\"mdl-data-table__cell--non-numeric bold\">{{key.org.name}}/{{key.key}}</td>\n                  <td class=\"mdl-data-table__cell--non-numeric\">{{key.visibility}}</td>\n                  <td class=\"mdl-data-table__cell--non-numeric\">{{key.createdDate | date:'shortDate'}}</td>\n                  <td class=\"mdl-data-table__cell--non-numeric\">{{key.createdBy}}</td>\n                  <td class=\"mdl-data-table__cell--non-numeric\"><a href=\"javascript:;\" [routerLink]=\"['/app/vault', key.id]\">Edit</a></td>\n                </tr>\n                </tbody>\n              </table>\n            </div>\n          </article>\n\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-list/vault-list.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-list/vault-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VaultListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_vault_service__ = __webpack_require__("../../../../../src/app/services/vault.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var VaultListComponent = (function () {
    function VaultListComponent(vaultService) {
        this.vaultService = vaultService;
        this.showSpinner = false;
    }
    VaultListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.showSpinner = true;
        this.vaultService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            _this.keys = results['data'];
        }, function (error) {
            console.log("Unable to fetch keys");
        });
    };
    VaultListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-vault-list',
            template: __webpack_require__("../../../../../src/app/components/vault/vault-list/vault-list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/vault/vault-list/vault-list.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_1__services_vault_service__["a" /* VaultService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_vault_service__["a" /* VaultService */]])
    ], VaultListComponent);
    return VaultListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/components/vault/vault-new/vault-new.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid with-maxwidth chapter\">\n\n  <article class=\"article\">\n\n    <h2 class=\"article-title\">New Secret</h2>\n    <div class=\"box box-default\">\n      <div class=\"box-body padding-xl\">\n\n        <form role=\"form\" #heroForm=\"ngForm\">\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Owner\" required [(ngModel)]=\"entry.org.id\" name=\"type\">\n                  <mat-option *ngFor=\"let org of orgs\" [value]=\"org.org.id\">\n                    {{org.org.name}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <label for=\"name\" class=\"col-md-2 control-label\"></label>\n            <div class=\"col-md-8\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"entry.key\" name=\"name\" id=\"name\"\n                       matInput placeholder=\"Entry key\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <label class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-8\">\n              <mat-form-field>\n                <mat-select placeholder=\"Access level\" [(ngModel)]=\"entry.visibility\" name=\"Access level\">\n                  <mat-option *ngFor=\"let visibility of visibilities\" [value]=\"visibility\">\n                    {{visibility}}\n                  </mat-option>\n                </mat-select>\n              </mat-form-field>\n            </div>\n          </div>\n          <div class=\"divider divider-dashed divider-lg pull-in\"></div>\n\n          <div class=\"form-group row\">\n            <label for=\"secret\" class=\"col-md-2 control-label\">\n            </label>\n            <div class=\"col-md-10\">\n              <mat-input-container class=\"full-width\">\n                <input required [(ngModel)]=\"entry.val\" name=\"secret\" id=\"secret\"\n                       matInput placeholder=\"Secret\">\n              </mat-input-container>\n            </div>\n          </div>\n\n          <div class=\"form-group row\">\n            <div class=\"col-md-2\"></div>\n            <div class=\"col-md-8\">\n              <button mat-raised-button color=\"primary\" (click)=\"create(); heroForm.reset()\"\n                      class=\"btn-w-md no-margin-left\">Add\n              </button>\n              <span class=\"space space-md\"></span>\n              <button mat-raised-button type=\"button\" class=\"btn-w-md\" [routerLink]=\"['/app/vault']\">Cancel\n              </button>\n            </div>\n          </div>\n\n        </form>\n\n      </div>\n    </div>\n  </article>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-new/vault-new.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/components/vault/vault-new/vault-new.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VaultNewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_vault_service__ = __webpack_require__("../../../../../src/app/services/vault.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_org_service__ = __webpack_require__("../../../../../src/app/services/org.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__models_vault_model__ = __webpack_require__("../../../../../src/app/models/vault.model.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var VaultNewComponent = (function () {
    function VaultNewComponent(vaultService, orgService, route, router) {
        this.vaultService = vaultService;
        this.orgService = orgService;
        this.route = route;
        this.router = router;
        this.showSpinner = false;
        this.entry = new __WEBPACK_IMPORTED_MODULE_4__models_vault_model__["a" /* Vault */]();
        this.visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    }
    VaultNewComponent.prototype.ngOnInit = function () {
        this.getOrgs();
    };
    VaultNewComponent.prototype.create = function () {
        var _this = this;
        console.log(this.entry);
        this.showSpinner = true;
        this.vaultService.create(this.entry).subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.router.navigate(['/app/vault']);
        }, function (error) {
            console.log("Unable to save vault entry");
        });
    };
    VaultNewComponent.prototype.getOrgs = function () {
        var _this = this;
        this.orgService.get().subscribe(function (results) {
            _this.showSpinner = false;
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            console.log(results);
            _this.orgs = results['data'];
        }, function (error) {
            console.log("Unable to fetch orgs");
        });
    };
    VaultNewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-vault-new',
            template: __webpack_require__("../../../../../src/app/components/vault/vault-new/vault-new.component.html"),
            styles: [__webpack_require__("../../../../../src/app/components/vault/vault-new/vault-new.component.scss")],
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_vault_service__["a" /* VaultService */], __WEBPACK_IMPORTED_MODULE_3__services_org_service__["a" /* OrgService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_vault_service__["a" /* VaultService */], __WEBPACK_IMPORTED_MODULE_3__services_org_service__["a" /* OrgService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], VaultNewComponent);
    return VaultNewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/config.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return APPCONFIG; });
function makeAppConfig() {
    var date = new Date();
    var year = date.getFullYear();
    var AppConfig = {
        brand: 'FxLabs, Inc',
        productName: 'Fx',
        user: 'User',
        year: year,
        layoutBoxed: false,
        navCollapsed: false,
        navBehind: true,
        fixedHeader: true,
        sidebarWidth: 'small',
        theme: 'light',
        colorOption: '32',
        AutoCloseMobileNav: true,
        productLink: 'https://fxlabs.io'
    };
    return AppConfig;
}
var APPCONFIG = makeAppConfig();


/***/ }),

/***/ "../../../../../src/app/dashboard/dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"chapter page-terms\">\n\n    <section class=\"hero\">\n        <div class=\"hero-content\">\n            <h1 class=\"hero-title\">Dashboard</h1>\n        </div>\n        <p class=\"text-muted\">Fx Control Plane</p>\n    </section>\n\n    <article class=\"article padding-lg-v article-dark article-bordered\">\n\n\n        <div class=\"container-fluid no-breadcrumbs page-dashboard\">\n\n            <!--\n           <div class=\"box box-default\">\n             <div class=\"box-body\">\n               <div myECharts [EChartsOptions]=\"trafficChart\" style=\"height: 450px;\"></div>\n             </div>\n           </div>\n            -->\n\n            <div class=\"row\">\n                <!-- Stats -->\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-info\">code</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Projects</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{projects}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-info\">devices</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Environments</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{envs}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-info\">alarm</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Jobs</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{jobs}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-info\">assignment</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Test-Suites</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{suites}} </span>\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <div class=\"row\">\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-warning\">directions_run</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Total Runs</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{runs}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-warning\">check</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Test Executions</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{tests}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-warning\">av_timer</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Total Time</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{time}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-warning\">data_usage</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Total Bytes</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{bytes}} </span>\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <div class=\"row\">\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-success\">content_copy</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>vBots</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{projects}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-success\">cloud_queue</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>eBots</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{eBots}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-success\">bug_report</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>iBots</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>{{iBots}} </span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"col-xl-3 col-sm-6\">\n                    <div class=\"box box-default\">\n                        <div class=\"box-top\">\n                            <i class=\"material-icons color-success\">attach_money</i>\n                        </div>\n                        <div class=\"box-info\">\n                            <span>Balance</span>\n                        </div>\n                        <div class=\"box-bottom\">\n                            <span>- </span>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <!--\n            <div class=\"box box-default\">\n              <div class=\"box-body padding-lg\">\n                <div class=\"row\">\n                  <div class=\"col-xl-4\">\n                    <div class=\"box box-transparent\">\n                      <div class=\"box-heading\">Platform</div>\n                      <div class=\"divider divider-md\"></div>\n                      <div class=\"box-body\">\n                        <div class=\"vprogressbar-container brand-success\">\n                          <ul class=\"vprogressbar clearfix\">\n                            <li><span class=\"vprogressbar-percent bg-color-info-alt\" style=\"height: 65%; opacity: 0.85;\"></span></li>\n                            <li><span class=\"vprogressbar-percent bg-color-success\" style=\"height: 50%; opacity: 0.85;\"></span></li>\n                            <li><span class=\"vprogressbar-percent bg-color-warning\" style=\"height: 40%; opacity: 0.85;\"></span></li>\n                            <li><span class=\"vprogressbar-percent bg-color-primary\" style=\"height: 80%; opacity: 0.85;\"></span></li>\n                          </ul>\n                          <ul class=\"vprogressbar-legend\">\n                            <li><span class=\"vpointer bg-color-info-alt\"></span> Direct</li>\n                            <li><span class=\"vpointer bg-color-success\"></span> Instagram</li>\n                            <li><span class=\"vpointer bg-color-warning\"></span> Twitter</li>\n                            <li><span class=\"vpointer bg-color-primary\"></span> Facebook</li>\n                          </ul>\n                          <div class=\"vprogressbar-info\">\n                            <span>Source of Acquisition</span>\n                          </div>\n                        </div>\n                      </div>\n                    </div>\n                  </div>\n                  <div class=\"col-xl-4\">\n                    <div class=\"box box-transparent\">\n                      <div class=\"box-heading\">Nationality</div>\n                      <div class=\"divider divider-md\"></div>\n                      <div class=\"box-body\">\n                        <div myECharts [EChartsOptions]=\"donutChart\" style=\"height: 300px;\"></div>\n                      </div>\n                    </div>\n                  </div>\n                  <div class=\"col-xl-4\">\n                    <section class=\"box box-transparent\">\n                      <div class=\"box-heading\">Budget vs Spending</div>\n                      <div class=\"divider divider-md\"></div>\n                      <div class=\"box-body\">\n                        <div myECharts [EChartsOptions]=\"radarChart\" style=\"height: 300px;\"></div>\n                      </div>\n                    </section>\n                  </div>\n                </div>\n              </div>\n            </div>\n            -->\n        </div>\n    </article>\n</section>"

/***/ }),

/***/ "../../../../../src/app/dashboard/dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DashboardComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__charts_charts_config__ = __webpack_require__("../../../../../src/app/charts/charts.config.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_dashboard_service__ = __webpack_require__("../../../../../src/app/services/dashboard.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var DashboardComponent = (function () {
    function DashboardComponent(dashboardService) {
        this.dashboardService = dashboardService;
        this.config = __WEBPACK_IMPORTED_MODULE_1__charts_charts_config__["a" /* CHARTCONFIG */];
        this.showSpinner = false;
        this.projects = "-";
        this.jobs = "-";
        this.envs = "-";
        this.runs = "-";
        this.tests = "-";
        this.time = "-";
        this.bytes = "-";
        this.iBots = "-";
        this.eBots = "-";
        this.suites = "-";
        this.getMonData = function () {
            var data = [];
            for (var i = 0; i < 13; i++) {
                data.push('Mon. ' + i);
            }
            return data;
        };
        this.monData = this.getMonData();
        //
        this.trafficChart = {
            legend: {
                show: true,
                x: 'right',
                y: 'top',
                textStyle: {
                    color: this.config.textColor
                },
                data: ['Trend', 'Search', 'Paid Ads', 'Virality']
            },
            grid: {
                x: 40,
                y: 60,
                x2: 40,
                y2: 30,
                borderWidth: 0
            },
            tooltip: {
                show: true,
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: this.config.gray
                    }
                }
            },
            xAxis: [
                {
                    type: 'category',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: this.config.textColor
                        }
                    },
                    splitLine: {
                        show: false,
                        lineStyle: {
                            color: this.config.splitLineColor
                        }
                    },
                    data: this.monData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: this.config.textColor
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: this.config.splitLineColor
                        }
                    }
                }
            ],
            series: [
                {
                    name: 'Trend',
                    type: 'line',
                    clickable: false,
                    lineStyle: {
                        normal: {
                            color: this.config.gray,
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: this.config.gray,
                        },
                        emphasis: {}
                    },
                    data: [41, 85, 27, 70, 50, 57, 41, 56, 69, 52, 48, 44, 35],
                    legendHoverLink: false,
                    z: 1
                },
                {
                    name: 'Search',
                    type: 'bar',
                    stack: 'traffic',
                    clickable: false,
                    itemStyle: {
                        normal: {
                            color: this.config.success,
                            barBorderRadius: 0
                        },
                        emphasis: {}
                    },
                    barCategoryGap: '60%',
                    data: [25, 45, 15, 39, 20, 26, 23, 26, 35, 27, 26, 25, 22],
                    legendHoverLink: false,
                    z: 2
                },
                {
                    name: 'Paid Ads',
                    type: 'bar',
                    stack: 'traffic',
                    smooth: true,
                    itemStyle: {
                        normal: {
                            color: this.config.primary,
                            barBorderRadius: 0
                        },
                        emphasis: {}
                    },
                    barCategoryGap: '60%',
                    data: [10, 25, 6, 19, 24, 25, 12, 15, 26, 13, 12, 8, 7],
                    symbol: 'none',
                    legendHoverLink: false,
                    z: 2
                },
                {
                    name: 'Virality',
                    type: 'bar',
                    stack: 'traffic',
                    smooth: true,
                    itemStyle: {
                        normal: {
                            color: this.config.info,
                            barBorderRadius: 0
                        },
                        emphasis: {}
                    },
                    barCategoryGap: '60%',
                    data: [6, 15, 6, 12, 6, 6, 6, 15, 8, 13, 10, 11, 6],
                    symbol: 'none',
                    legendHoverLink: false,
                    z: 2
                }
            ]
        };
        //
        this.donutChart = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                show: false,
                orient: 'vertical',
                x: 'right',
                data: ['Direct', 'Email', 'Affiliate', 'Video Ads', 'Search']
            },
            toolbox: {
                show: false,
                feature: {
                    restore: { show: true, title: 'restore' },
                    saveAsImage: { show: true, title: 'save as image' }
                }
            },
            calculable: true,
            series: [
                {
                    name: 'Traffic source',
                    type: 'pie',
                    radius: ['51%', '69%'],
                    itemStyle: {
                        normal: {
                            color: this.config.info
                        },
                        emphasis: {
                            label: {
                                show: true,
                                position: 'center',
                                textStyle: {
                                    fontSize: '20',
                                    fontWeight: 'normal'
                                }
                            }
                        }
                    },
                    data: [
                        {
                            value: 40,
                            name: 'United States',
                            itemStyle: {
                                normal: {
                                    color: this.config.success,
                                    label: {
                                        textStyle: {
                                            color: this.config.success
                                        }
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            color: this.config.success
                                        }
                                    }
                                }
                            }
                        },
                        {
                            value: 10,
                            name: 'United Kingdom',
                            itemStyle: {
                                normal: {
                                    color: this.config.primary,
                                    label: {
                                        textStyle: {
                                            color: this.config.primary
                                        }
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            color: this.config.primary
                                        }
                                    }
                                }
                            }
                        },
                        {
                            value: 20,
                            name: 'Canada',
                            itemStyle: {
                                normal: {
                                    color: this.config.infoAlt,
                                    label: {
                                        textStyle: {
                                            color: this.config.infoAlt
                                        }
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            color: this.config.infoAlt
                                        }
                                    }
                                }
                            }
                        },
                        {
                            value: 12,
                            name: 'Germany',
                            itemStyle: {
                                normal: {
                                    color: this.config.info,
                                    label: {
                                        textStyle: {
                                            color: this.config.info
                                        }
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            color: this.config.info
                                        }
                                    }
                                }
                            }
                        },
                        {
                            value: 18,
                            name: 'Japan',
                            itemStyle: {
                                normal: {
                                    color: this.config.warning,
                                    label: {
                                        textStyle: {
                                            color: this.config.warning
                                        }
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            color: this.config.warning
                                        }
                                    }
                                }
                            }
                        }
                    ]
                }
            ]
        };
        //
        this.radarChart = {
            tooltip: {},
            legend: {
                show: false,
                orient: 'vertical',
                x: 'right',
                y: 'bottom',
                data: ['Budget', 'Spending']
            },
            toolbox: {
                show: false
            },
            radar: [
                {
                    axisLine: {
                        show: true,
                        lineStyle: {
                            // for both indicator and axisLine, bad, better seperate them
                            color: '#b1b1b1'
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,.1)'
                        }
                    },
                    splitArea: {
                        show: true,
                        areaStyle: {
                            color: this.config.splitAreaColor
                        }
                    },
                    indicator: [
                        { name: 'sales', max: 6000 },
                        { name: 'dministration', max: 16000 },
                        { name: 'Information Techology', max: 30000 },
                        { name: 'Customer Support', max: 38000 },
                        { name: 'Development', max: 52000 },
                        { name: 'Marketing', max: 25000 }
                    ]
                }
            ],
            calculable: true,
            series: [
                {
                    type: 'radar',
                    data: [
                        {
                            name: 'Budget',
                            value: [4300, 10000, 28000, 35000, 50000, 19000],
                            itemStyle: {
                                normal: {
                                    color: this.config.primary
                                }
                            }
                        },
                        {
                            name: 'Spending',
                            value: [5000, 14000, 28000, 31000, 42000, 21000],
                            itemStyle: {
                                normal: {
                                    color: this.config.success
                                }
                            }
                        }
                    ]
                }
            ]
        };
    }
    DashboardComponent.prototype.ngOnInit = function () {
        this.get("count-projects", "projects");
        this.get("count-jobs", "jobs");
        this.get("count-envs", "envs");
        this.get("count-suites", "suites");
        this.get("count-runs", "runs");
        this.get("count-tests", "tests");
        this.get("count-time", "time");
        this.get("count-bytes", "bytes");
        this.get("count-ibots", "iBots");
        this.get("count-ebots", "eBots");
    };
    DashboardComponent.prototype.get = function (stat, _var) {
        var _this = this;
        this.dashboardService.getStat(stat).subscribe(function (results) {
            if (results['errors']) {
                // TODO - handle errors
                return;
            }
            var count = results['data'];
            if (_var === 'projects')
                _this.projects = count;
            else if (_var === 'jobs')
                _this.jobs = count;
            else if (_var === 'suites')
                _this.suites = count;
            else if (_var === 'envs')
                _this.envs = count;
            else if (_var === 'runs')
                _this.runs = count;
            else if (_var === 'tests')
                _this.tests = count;
            else if (_var === 'time')
                _this.time = count;
            else if (_var === 'bytes')
                _this.bytes = count;
            else if (_var === 'iBots')
                _this.iBots = count;
            else if (_var === 'eBots')
                _this.eBots = count;
        }, function (error) {
            console.log("Unable to fetch stat");
        });
    };
    DashboardComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-dashboard',
            template: __webpack_require__("../../../../../src/app/dashboard/dashboard.component.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_2__services_dashboard_service__["a" /* DashboardService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_dashboard_service__["a" /* DashboardService */]])
    ], DashboardComponent);
    return DashboardComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/customizer/customizer.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"quickview-wrapper customizer d-none d-lg-block d-xl-block\" id=\"quickview-customizer\">\n  <a class=\"customizer-close\" href=\"javascript:;\" myToggleQuickview=\"customizer\"><span class=\"material-icons\">close</span></a>\n  <a class=\"customizer-toggle\" href=\"javascript:;\" myToggleQuickview=\"customizer\"><span class=\"material-icons\">settings</span></a>\n\n  <div class=\"quickview-inner\" mySlimScroll>\n    <div>\n      <p class=\"customizer-header\">Account</p>\n      <p class=\"small no-margin\"></p>\n      <br/>\n      <br/>\n      <div class=\"divider divider-lg divider-solid\"></div>\n      <a href=\"/logout\"><span class=\"text-danger\"> Sign out</span> <i class=\"fas fa-sign-out-alt\"></i></a>\n\n      <br/>\n      <br/>\n      <br/>\n      <!--<h4 class=\"section-header\">Layout Options</h4>\n      <div class=\"divider divider-sm\"></div>\n      <mat-list>\n        <mat-list-item>\n          <p>Fixed Header</p>\n          <mat-slide-toggle class=\"mat-secondary\" [(ngModel)]=\"AppConfig.fixedHeader\"></mat-slide-toggle>\n        </mat-list-item>\n        <mat-list-item>\n          <p>Collapsed Sidebar</p>\n          <mat-slide-toggle class=\"mat-secondary\" [(ngModel)]=\"AppConfig.navCollapsed\" (change)=\"onLayoutChange()\"></mat-slide-toggle>\n        </mat-list-item>\n        <mat-list-item>\n          <p>Full Width Header</p>\n          <mat-slide-toggle class=\"mat-secondary\" [(ngModel)]=\"AppConfig.navBehind\"></mat-slide-toggle>\n        </mat-list-item>\n        <mat-list-item>\n          <p>Boxed Layout</p>\n          <mat-slide-toggle class=\"mat-secondary\" [(ngModel)]=\"AppConfig.layoutBoxed\" (change)=\"onLayoutChange()\"></mat-slide-toggle>\n        </mat-list-item>\n        <mat-list-item>\n          <p>Sidebar Width</p>\n          <mat-select class=\"no-margin\" style=\"width: 80px;\" [(ngModel)]=\"AppConfig.sidebarWidth\" (change)=\"onLayoutChange()\">\n            <mat-option value=\"small\">small</mat-option>\n            <mat-option value=\"middle\" >middle</mat-option>\n            <mat-option value=\"large\" >large</mat-option>\n          </mat-select>\n        </mat-list-item>\n      </mat-list>-->\n\n\n      <!--<div class=\"divider divider-lg divider-solid\"></div>\n      <h4 class=\"section-header\">Color Options</h4>\n      <p class=\"small no-margin\">Tip: Additionally, you can active \"Full Width Header\" above</p>\n      <div class=\"divider\"></div>\n      <div class=\"row\">\n        <div class=\"col-4\">\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"11\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-dark item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"12\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-primary item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"13\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-success item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"14\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-info item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"15\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-warning item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"16\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-danger item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n        </div>\n\n        <div class=\"col-4\">\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"21\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-light item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"22\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-primary item-header\"></span>\n              <span class=\"bg-color-primary item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"23\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-success item-header\"></span>\n              <span class=\"bg-color-success item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"24\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-info item-header\"></span>\n              <span class=\"bg-color-info item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"25\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-warning item-header\"></span>\n              <span class=\"bg-color-warning item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"26\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-danger item-header\"></span>\n              <span class=\"bg-color-danger item-header\"></span>\n              <span class=\"bg-color-dark\"></span>\n            </span>\n          </label>\n        </div>\n        <div class=\"col-4\">\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"31\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-dark item-header\"></span>\n              <span class=\"bg-color-dark item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"32\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-primary item-header\"></span>\n              <span class=\"bg-color-primary item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"33\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-success item-header\"></span>\n              <span class=\"bg-color-success item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"34\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-info item-header\"></span>\n              <span class=\"bg-color-info item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"35\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-warning item-header\"></span>\n              <span class=\"bg-color-warning item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n          <label class=\"color-option-check\">\n            <input type=\"radio\" name=\"color\" value=\"36\" [(ngModel)]=\"AppConfig.colorOption\">\n            <span class=\"color-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span class=\"bg-color-danger item-header\"></span>\n              <span class=\"bg-color-danger item-header\"></span>\n              <span class=\"bg-color-light\"></span>\n            </span>\n          </label>\n        </div>\n      </div>-->\n\n\n\n      <!--<div class=\"divider divider-lg divider-solid\"></div>\n      <h4 class=\"section-header\">Theme</h4>\n      <div class=\"divider\"></div>-->\n\n      <div class=\"row no-margin theme-options clearfix\">\n        <div class=\"col-4\">\n          <label class=\"theme-option-check\">\n            <input type=\"radio\" name=\"theme\" value=\"dark\" [(ngModel)]=\"AppConfig.theme\">\n            <span class=\"theme-option-item bg-color-dark\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span>Dark</span>\n            </span>\n          </label>\n        </div>\n        <!--<div class=\"col-4\">\n          <label class=\"theme-option-check\">\n            <input type=\"radio\" name=\"theme\" value=\"gray\" [(ngModel)]=\"AppConfig.theme\">\n            <span class=\"theme-option-item bg-color-gray\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span>Gray</span>\n            </span>\n          </label>\n        </div>-->\n        <div class=\"col-4\">\n          <label class=\"theme-option-check\">\n            <input type=\"radio\" name=\"theme\" value=\"light\" [(ngModel)]=\"AppConfig.theme\">\n            <span class=\"theme-option-item bg-color-page\">\n              <span class=\"overlay\"><span class=\"material-icons\">check</span></span>\n              <span>Standard</span>\n            </span>\n          </label>\n        </div>\n      </div>\n      <!--<div class=\"divider divider-lg divider-solid\"></div>\n      <div class=\"text-right\">\n        <a target=\"_blank\" [href]=\"AppConfig.productLink\">Download it Now</a>\n      </div>-->\n\n    </div>\n  </div>\n</section>"

/***/ }),

/***/ "../../../../../src/app/layout/customizer/customizer.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppCustomizerComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AppCustomizerComponent = (function () {
    function AppCustomizerComponent(layoutService) {
        var _this = this;
        this.layoutService = layoutService;
        this.onLayoutChange = function () {
            _this.layoutService.updateEChartsState(true);
        };
    }
    AppCustomizerComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    AppCustomizerComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-customizer',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/customizer/customizer.component.html"),
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__layout_service__["a" /* LayoutService */]])
    ], AppCustomizerComponent);
    return AppCustomizerComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/customizer/toggle-quickview.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ToggleQuickviewDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ToggleQuickviewDirective = (function () {
    function ToggleQuickviewDirective(el) {
        this.el = el;
    }
    ToggleQuickviewDirective.prototype.ngAfterViewInit = function () {
        var $el = $(this.el.nativeElement);
        var $body = $('#body');
        var target = this.myToggleQuickview;
        var qvClass = 'quickview-open';
        if (target) {
            qvClass = qvClass + '-' + target;
        }
        $el.on('click', function (e) {
            $body.toggleClass(qvClass);
            e.preventDefault();
        });
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", String)
    ], ToggleQuickviewDirective.prototype, "myToggleQuickview", void 0);
    ToggleQuickviewDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myToggleQuickview]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */]])
    ], ToggleQuickviewDirective);
    return ToggleQuickviewDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/footer/footer.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"app-footer\">\n  <div class=\"container-fluid\">\n    <span class=\"float-left\">\n      <span>Copyright © <a class=\"brand\" target=\"_blank\" [href]=\"AppConfig.productLink\">{{AppConfig.brand}}</a> {{AppConfig.year}}</span>\n    </span>\n    <span class=\"float-right\">\n      <!--<span>Built with Love <i class=\"material-icons\">favorite_border</i></span>-->\n    </span>\n  </div>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/layout/footer/footer.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppFooterComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var AppFooterComponent = (function () {
    function AppFooterComponent() {
    }
    AppFooterComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    AppFooterComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-footer',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/footer/footer.component.html")
        })
    ], AppFooterComponent);
    return AppFooterComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/header/header.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"app-header\">\n  <div class=\"app-header-inner\"\n     [ngClass]=\"{'bg-color-light': ['11','12','13','14','15','16','21'].indexOf(AppConfig.colorOption) >= 0,\n                 'bg-color-dark': AppConfig.colorOption === '31',\n                 'bg-color-primary': ['22','32'].indexOf(AppConfig.colorOption) >= 0,\n                 'bg-color-success': ['23','33'].indexOf(AppConfig.colorOption) >= 0,\n                 'bg-color-info': ['24','34'].indexOf(AppConfig.colorOption) >= 0,\n                 'bg-color-warning': ['25','35'].indexOf(AppConfig.colorOption) >= 0,\n                 'bg-color-danger': ['26','36'].indexOf(AppConfig.colorOption) >= 0}\">\n\n    <div class=\"d-lg-none d-xl-none float-left\">\n      <button mat-button myToggleOffcanvasNav class=\"md-button header-icon toggle-sidebar-btn\">\n        <i class=\"material-icons\">menu</i>\n      </button>\n    </div>\n\n\n    <div class=\"brand d-none d-lg-inline-block d-xl-inline-block\">\n      <!--<h2><a [routerLink]=\"['/']\">{{AppConfig.productName}}</a></h2>-->\n      <img src=\"/fx-white-100x100.png\" class=\"logo\" height=\"65\" [routerLink]=\"['/']\"/>\n    </div>\n\n    <div class=\"top-nav-left d-none d-lg-inline-block d-xl-inline-block\">\n      <ul class=\"list-unstyled list-inline\">\n        <li class=\"list-inline-item\"><button mat-button class=\"md-button header-btn\" myOpenSearchOverlay><i class=\"material-icons\">search</i></button></li>\n        <li class=\"list-inline-item\" [routerLink]=\"['/app/messages']\">\n          <button mat-button class=\"md-button header-btn\" >\n            <i class=\"material-icons\" >notifications_none</i>\n            <!--<span class=\"badge\">3</span>-->\n          </button>\n          <!--<mat-menu #appNotification=\"matMenu\" x-position=\"after\" y-position=\"below\" overlapTrigger=\"false\">\n            <mat-list dense>\n              <mat-list-item>\n                <mat-icon mat-list-avatar>mail_outline</mat-icon>\n                <h4 mat-line>New mail from David</h4>\n                <p mat-line class=\"text-muted\">5 minutes ago</p>\n              </mat-list-item>\n              <mat-list-item>\n                <mat-icon mat-list-avatar>chat_bubble_outline</mat-icon>\n                <h4 mat-line>Message from Jane</h4>\n                <p mat-line class=\"text-muted\">15 minutes ago</p>\n              </mat-list-item>\n              <mat-list-item>\n                <mat-icon mat-list-avatar>person_outline</mat-icon>\n                <h4 mat-line>New member John joined</h4>\n                <p mat-line class=\"text-muted\">1 hour ago</p>\n              </mat-list-item>\n            </mat-list>\n          </mat-menu>-->\n        </li>\n      </ul>\n    </div>\n\n    <!--<div class=\"top-nav-right\">\n      <ul class=\"list-unstyled float-right\">\n        <li>\n          <button mat-button class=\"md-button header-btn\" [routerLink]=\"['/app/page/about']\">\n            <img src=\"assets/images/g1.jpg\" alt=\"\" class=\"rounded img30_30\">\n          </button>\n        </li>\n        <li><button mat-button class=\"md-button header-btn d-lg-none d-xl-none\" myOpenSearchOverlay><i class=\"material-icons\">search</i></button></li>\n        <li><button mat-button class=\"md-button header-btn\"><i class=\"material-icons\">more_vert</i></button></li>\n      </ul>\n    </div>-->\n\n  </div>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/layout/header/header.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppHeaderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var AppHeaderComponent = (function () {
    function AppHeaderComponent() {
    }
    AppHeaderComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    AppHeaderComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-header',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/header/header.component.html")
        })
    ], AppHeaderComponent);
    return AppHeaderComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/layout-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LayoutRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__layout_component__ = __webpack_require__("../../../../../src/app/layout/layout.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dashboard_dashboard_component__ = __webpack_require__("../../../../../src/app/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_jobs_list_jobs_list_component__ = __webpack_require__("../../../../../src/app/components/jobs-list/jobs-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__components_run_list_run_list_component__ = __webpack_require__("../../../../../src/app/components/run-list/run-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__components_run_detail_run_detail_component__ = __webpack_require__("../../../../../src/app/components/run-detail/run-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__components_projects_projects_list_projects_list_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-list/projects-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__components_projects_projects_new_projects_new_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-new/projects-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_projects_projects_edit_projects_edit_component__ = __webpack_require__("../../../../../src/app/components/projects/projects-edit/projects-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_regions_list_regions_list_component__ = __webpack_require__("../../../../../src/app/components/regions-list/regions-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_message_list_message_list_component__ = __webpack_require__("../../../../../src/app/components/message-list/message-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__components_message_detail_message_detail_component__ = __webpack_require__("../../../../../src/app/components/message-detail/message-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_issues_issues_list_issues_list_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-list/issues-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__components_issues_issues_new_issues_new_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-new/issues-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__components_issues_issues_edit_issues_edit_component__ = __webpack_require__("../../../../../src/app/components/issues/issues-edit/issues-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__components_marketplace_marketplace_list_marketplace_list_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-list/marketplace-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__components_marketplace_marketplace_new_marketplace_new_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-new/marketplace-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__components_marketplace_marketplace_edit_marketplace_edit_component__ = __webpack_require__("../../../../../src/app/components/marketplace/marketplace-edit/marketplace-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__components_monitor_monitor_all_monitor_all_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-all/monitor-all.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__components_monitor_monitor_new_monitor_new_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-new/monitor-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__components_monitor_monitor_edit_monitor_edit_component__ = __webpack_require__("../../../../../src/app/components/monitor/monitor-edit/monitor-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__components_doc_doc_list_doc_list_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-list/doc-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__components_doc_doc_new_doc_new_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-new/doc-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_23__components_doc_doc_edit_doc_edit_component__ = __webpack_require__("../../../../../src/app/components/doc/doc-edit/doc-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_24__components_support_support_list_support_list_component__ = __webpack_require__("../../../../../src/app/components/support/support-list/support-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_25__components_support_support_new_support_new_component__ = __webpack_require__("../../../../../src/app/components/support/support-new/support-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_26__components_support_support_edit_support_edit_component__ = __webpack_require__("../../../../../src/app/components/support/support-edit/support-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_27__components_analytics_analytics_list_analytics_list_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-list/analytics-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_28__components_analytics_analytics_new_analytics_new_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-new/analytics-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_29__components_analytics_analytics_edit_analytics_edit_component__ = __webpack_require__("../../../../../src/app/components/analytics/analytics-edit/analytics-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_30__components_vault_vault_list_vault_list_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-list/vault-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_31__components_vault_vault_new_vault_new_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-new/vault-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_32__components_vault_vault_edit_vault_edit_component__ = __webpack_require__("../../../../../src/app/components/vault/vault-edit/vault-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_33__components_billing_billing_list_billing_list_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-list/billing-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_34__components_billing_billing_new_billing_new_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-new/billing-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_35__components_billing_billing_edit_billing_edit_component__ = __webpack_require__("../../../../../src/app/components/billing/billing-edit/billing-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_36__components_org_org_list_org_list_component__ = __webpack_require__("../../../../../src/app/components/org/org-list/org-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_37__components_org_org_new_org_new_component__ = __webpack_require__("../../../../../src/app/components/org/org-new/org-new.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_38__components_org_org_edit_org_edit_component__ = __webpack_require__("../../../../../src/app/components/org/org-edit/org-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_39__components_skills_skills_version_control_skills_version_control_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-version-control/skills-version-control.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_40__components_skills_skills_bot_deployment_skills_bot_deployment_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-bot-deployment/skills-bot-deployment.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_41__components_skills_skills_issue_tracker_skills_issue_tracker_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-issue-tracker/skills-issue-tracker.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_42__components_skills_skills_analytics_skills_analytics_component__ = __webpack_require__("../../../../../src/app/components/skills/skills-analytics/skills-analytics.component.ts");











































var routes = [
    {
        path: 'app',
        component: __WEBPACK_IMPORTED_MODULE_1__layout_component__["a" /* LayoutComponent */],
        children: [
            { path: '', redirectTo: '/app/projects', pathMatch: 'full' },
            { path: 'dashboard', component: __WEBPACK_IMPORTED_MODULE_2__dashboard_dashboard_component__["a" /* DashboardComponent */] },
            { path: 'projects', component: __WEBPACK_IMPORTED_MODULE_6__components_projects_projects_list_projects_list_component__["a" /* ProjectsListComponent */] },
            { path: 'projects/new', component: __WEBPACK_IMPORTED_MODULE_7__components_projects_projects_new_projects_new_component__["a" /* ProjectsNewComponent */] },
            { path: 'projects/:id', component: __WEBPACK_IMPORTED_MODULE_8__components_projects_projects_edit_projects_edit_component__["a" /* ProjectsEditComponent */] },
            { path: 'projects/:id/jobs', component: __WEBPACK_IMPORTED_MODULE_3__components_jobs_list_jobs_list_component__["a" /* JobslistComponent */] },
            { path: 'projects/:id/jobs/:jobId/runs', component: __WEBPACK_IMPORTED_MODULE_4__components_run_list_run_list_component__["a" /* RunListComponent */] },
            { path: 'projects/:id/jobs/:jobId/runs/:runId', component: __WEBPACK_IMPORTED_MODULE_5__components_run_detail_run_detail_component__["a" /* RunDetailComponent */] },
            { path: 'messages', component: __WEBPACK_IMPORTED_MODULE_10__components_message_list_message_list_component__["a" /* MessageListComponent */] },
            { path: 'message/:id', component: __WEBPACK_IMPORTED_MODULE_11__components_message_detail_message_detail_component__["a" /* MessageDetailComponent */] },
            { path: 'regions', component: __WEBPACK_IMPORTED_MODULE_9__components_regions_list_regions_list_component__["a" /* RegionsListComponent */] },
            { path: 'issues', component: __WEBPACK_IMPORTED_MODULE_12__components_issues_issues_list_issues_list_component__["a" /* IssuesListComponent */] },
            { path: 'issues/new', component: __WEBPACK_IMPORTED_MODULE_13__components_issues_issues_new_issues_new_component__["a" /* IssuesNewComponent */] },
            { path: 'issues/:id', component: __WEBPACK_IMPORTED_MODULE_14__components_issues_issues_edit_issues_edit_component__["a" /* IssuesEditComponent */] },
            { path: 'marketplace', component: __WEBPACK_IMPORTED_MODULE_15__components_marketplace_marketplace_list_marketplace_list_component__["a" /* MarketplaceListComponent */] },
            { path: 'marketplace/new', component: __WEBPACK_IMPORTED_MODULE_16__components_marketplace_marketplace_new_marketplace_new_component__["a" /* MarketplaceNewComponent */] },
            { path: 'marketplace/:id', component: __WEBPACK_IMPORTED_MODULE_17__components_marketplace_marketplace_edit_marketplace_edit_component__["a" /* MarketplaceEditComponent */] },
            { path: 'monitor', component: __WEBPACK_IMPORTED_MODULE_18__components_monitor_monitor_all_monitor_all_component__["a" /* MonitorAllComponent */] },
            { path: 'monitor/new', component: __WEBPACK_IMPORTED_MODULE_19__components_monitor_monitor_new_monitor_new_component__["a" /* MonitorNewComponent */] },
            { path: 'monitor/:id', component: __WEBPACK_IMPORTED_MODULE_20__components_monitor_monitor_edit_monitor_edit_component__["a" /* MonitorEditComponent */] },
            { path: 'doc', component: __WEBPACK_IMPORTED_MODULE_21__components_doc_doc_list_doc_list_component__["a" /* DocListComponent */] },
            { path: 'doc/new', component: __WEBPACK_IMPORTED_MODULE_22__components_doc_doc_new_doc_new_component__["a" /* DocNewComponent */] },
            { path: 'doc/:id', component: __WEBPACK_IMPORTED_MODULE_23__components_doc_doc_edit_doc_edit_component__["a" /* DocEditComponent */] },
            { path: 'support', component: __WEBPACK_IMPORTED_MODULE_24__components_support_support_list_support_list_component__["a" /* SupportListComponent */] },
            { path: 'support/new', component: __WEBPACK_IMPORTED_MODULE_25__components_support_support_new_support_new_component__["a" /* SupportNewComponent */] },
            { path: 'support/:id', component: __WEBPACK_IMPORTED_MODULE_26__components_support_support_edit_support_edit_component__["a" /* SupportEditComponent */] },
            { path: 'analytics', component: __WEBPACK_IMPORTED_MODULE_27__components_analytics_analytics_list_analytics_list_component__["a" /* AnalyticsListComponent */] },
            { path: 'analytics/new', component: __WEBPACK_IMPORTED_MODULE_28__components_analytics_analytics_new_analytics_new_component__["a" /* AnalyticsNewComponent */] },
            { path: 'analytics/:id', component: __WEBPACK_IMPORTED_MODULE_29__components_analytics_analytics_edit_analytics_edit_component__["a" /* AnalyticsEditComponent */] },
            { path: 'support', component: __WEBPACK_IMPORTED_MODULE_24__components_support_support_list_support_list_component__["a" /* SupportListComponent */] },
            { path: 'support/new', component: __WEBPACK_IMPORTED_MODULE_25__components_support_support_new_support_new_component__["a" /* SupportNewComponent */] },
            { path: 'support/:id', component: __WEBPACK_IMPORTED_MODULE_26__components_support_support_edit_support_edit_component__["a" /* SupportEditComponent */] },
            { path: 'vault', component: __WEBPACK_IMPORTED_MODULE_30__components_vault_vault_list_vault_list_component__["a" /* VaultListComponent */] },
            { path: 'vault/new', component: __WEBPACK_IMPORTED_MODULE_31__components_vault_vault_new_vault_new_component__["a" /* VaultNewComponent */] },
            { path: 'vault/:id', component: __WEBPACK_IMPORTED_MODULE_32__components_vault_vault_edit_vault_edit_component__["a" /* VaultEditComponent */] },
            { path: 'billing', component: __WEBPACK_IMPORTED_MODULE_33__components_billing_billing_list_billing_list_component__["a" /* BillingListComponent */] },
            { path: 'billing/new', component: __WEBPACK_IMPORTED_MODULE_34__components_billing_billing_new_billing_new_component__["a" /* BillingNewComponent */] },
            { path: 'billing/:id', component: __WEBPACK_IMPORTED_MODULE_35__components_billing_billing_edit_billing_edit_component__["a" /* BillingEditComponent */] },
            { path: 'org', component: __WEBPACK_IMPORTED_MODULE_36__components_org_org_list_org_list_component__["a" /* OrgListComponent */] },
            { path: 'org/new', component: __WEBPACK_IMPORTED_MODULE_37__components_org_org_new_org_new_component__["a" /* OrgNewComponent */] },
            { path: 'org/:id', component: __WEBPACK_IMPORTED_MODULE_38__components_org_org_edit_org_edit_component__["a" /* OrgEditComponent */] },
            { path: 'skills/version-control', component: __WEBPACK_IMPORTED_MODULE_39__components_skills_skills_version_control_skills_version_control_component__["a" /* SkillsVersionControlComponent */] },
            { path: 'skills/bot-deployment', component: __WEBPACK_IMPORTED_MODULE_40__components_skills_skills_bot_deployment_skills_bot_deployment_component__["a" /* SkillsBotDeploymentComponent */] },
            { path: 'skills/issue-tracker', component: __WEBPACK_IMPORTED_MODULE_41__components_skills_skills_issue_tracker_skills_issue_tracker_component__["a" /* SkillsIssueTrackerComponent */] },
            { path: 'skills/analytics', component: __WEBPACK_IMPORTED_MODULE_42__components_skills_skills_analytics_skills_analytics_component__["a" /* SkillsAnalyticsComponent */] },
        ]
    }
];
var LayoutRoutingModule = __WEBPACK_IMPORTED_MODULE_0__angular_router__["g" /* RouterModule */].forChild(routes);


/***/ }),

/***/ "../../../../../src/app/layout/layout.component.html":
/***/ (function(module, exports) {

module.exports = "\n<my-app-sidenav></my-app-sidenav>\n\n<section id=\"page-container\" class=\"app-page-container\">\n  <my-app-header></my-app-header>\n\n  <div class=\"app-content-wrapper\">\n    <div class=\"app-content\">\n      <div class=\"full-height\">\n        <router-outlet></router-outlet>\n      </div>\n    </div>\n\n    <my-app-footer></my-app-footer>\n  </div>\n</section>\n\n<my-app-customizer></my-app-customizer>\n\n<my-app-search-overlay></my-app-search-overlay>\n"

/***/ }),

/***/ "../../../../../src/app/layout/layout.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LayoutComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var LayoutComponent = (function () {
    function LayoutComponent() {
    }
    LayoutComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-layout',
            template: __webpack_require__("../../../../../src/app/layout/layout.component.html"),
        })
    ], LayoutComponent);
    return LayoutComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/layout.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LayoutModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__layout_routing_module__ = __webpack_require__("../../../../../src/app/layout/layout-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var LayoutModule = (function () {
    function LayoutModule() {
    }
    LayoutModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__layout_routing_module__["a" /* LayoutRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_2__shared_shared_module__["a" /* SharedModule */],
            ],
            declarations: []
        })
    ], LayoutModule);
    return LayoutModule;
}());



/***/ }),

/***/ "../../../../../src/app/layout/layout.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LayoutService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__ = __webpack_require__("../../../../rxjs/_esm5/Subject.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var LayoutService = (function () {
    function LayoutService() {
        this.preloaderStateSource = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__["a" /* Subject */]();
        this.preloaderState$ = this.preloaderStateSource.asObservable();
        this.searchOverlaySource = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__["a" /* Subject */]();
        this.searchOverlayState$ = this.searchOverlaySource.asObservable();
        this.echartsSource = new __WEBPACK_IMPORTED_MODULE_1_rxjs_Subject__["a" /* Subject */]();
        this.echartsState$ = this.echartsSource.asObservable();
    }
    LayoutService.prototype.updatePreloaderState = function (state) {
        // console.log('preloader state: ' + state)
        this.preloaderStateSource.next(state);
    };
    LayoutService.prototype.updateSearchOverlayState = function (state) {
        // console.log('overlay state: ' + state)
        this.searchOverlaySource.next(state);
    };
    LayoutService.prototype.updateEChartsState = function (state) {
        // console.log('echarts state: ' + state)
        this.echartsSource.next(state);
    };
    LayoutService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])()
    ], LayoutService);
    return LayoutService;
}());



/***/ }),

/***/ "../../../../../src/app/layout/preloader.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PreloaderDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var PreloaderDirective = (function () {
    function PreloaderDirective(el, router, layoutService) {
        var _this = this;
        this.el = el;
        this.router = router;
        this.layoutService = layoutService;
        router.events.subscribe(function (event) {
            _this.navigationInterceptor(event);
        });
        this.subscription = layoutService.preloaderState$.subscribe(function (state) {
            _this.updatePreloader(state);
        });
    }
    PreloaderDirective.prototype.ngAfterViewInit = function () {
        this.$el = $(this.el.nativeElement);
    };
    PreloaderDirective.prototype.active = function () {
        if (this.$el) {
            this.$el.removeClass('hide').addClass('active');
        }
    };
    PreloaderDirective.prototype.hide = function () {
        if (this.$el) {
            this.$el.addClass('hide').removeClass('active');
        }
    };
    // Shows and hides the loading spinner during RouterEvent changes
    PreloaderDirective.prototype.navigationInterceptor = function (event) {
        if (event instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["e" /* NavigationStart */]) {
            this.active();
        }
        if (event instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* NavigationEnd */]) {
            this.hide();
        }
        // Set loading state to false in both of the below events to hide the spinner in case a request fails
        if (event instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* NavigationCancel */]) {
            this.hide();
        }
        if (event instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* NavigationError */]) {
            this.hide();
        }
    };
    //
    PreloaderDirective.prototype.updatePreloader = function (state) {
        // console.log('change state')
        if (state === 'active') {
            this.active();
        }
        if (state === 'hide') {
            this.hide();
        }
    };
    PreloaderDirective.prototype.ngOnDestroy = function () {
        // prevent memory leak when component destroyed
        this.subscription.unsubscribe();
    };
    PreloaderDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({
            selector: '[myPreloader]',
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */], __WEBPACK_IMPORTED_MODULE_2__layout_service__["a" /* LayoutService */]])
    ], PreloaderDirective);
    return PreloaderDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/search-overlay/open-search-overlay.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OpenSearchOverlaylDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var OpenSearchOverlaylDirective = (function () {
    function OpenSearchOverlaylDirective(el, layoutService) {
        this.el = el;
        this.layoutService = layoutService;
    }
    OpenSearchOverlaylDirective.prototype.ngAfterViewInit = function () {
        var _this = this;
        var $el = $(this.el.nativeElement);
        var $body = $('#body');
        $el.on('click', function (e) {
            _this.openOverlay();
        });
    };
    OpenSearchOverlaylDirective.prototype.openOverlay = function () {
        this.layoutService.updateSearchOverlayState('open');
    };
    OpenSearchOverlaylDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myOpenSearchOverlay]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_1__layout_service__["a" /* LayoutService */]])
    ], OpenSearchOverlaylDirective);
    return OpenSearchOverlaylDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/search-overlay/search-overlay.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"app-overlay\" mySearchOverlay>\n  <div class=\"app-overlay-inner\">\n    <div class=\"overlay-header\">\n      <a href=\"javascript:;\" id=\"overlay-close\" class=\"overlay-close\">\n        <i class=\"material-icons\">close</i>\n      </a>\n    </div>\n    <div class=\"overlay-content\">\n      <input id=\"overlay-search-input\"\n           class=\"overlay-search-input\" \n           placeholder=\"Search...\"\n           >\n\n      <div class=\"text-small text-muted\">\n        <span>Press <em>Esc</em> key to dismiss</span>\n      </div>\n    </div>\n  </div>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/layout/search-overlay/search-overlay.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppSearchOverlayComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppSearchOverlayComponent = (function () {
    function AppSearchOverlayComponent() {
    }
    AppSearchOverlayComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-search-overlay',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/search-overlay/search-overlay.component.html")
        })
    ], AppSearchOverlayComponent);
    return AppSearchOverlayComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/search-overlay/search-overlay.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SearchOverlayDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var SearchOverlayDirective = (function () {
    function SearchOverlayDirective(el, layoutService) {
        var _this = this;
        this.el = el;
        this.layoutService = layoutService;
        this.subscription = layoutService.searchOverlayState$.subscribe(function (state) {
            _this.updateSearchOverlay(state);
        });
    }
    SearchOverlayDirective.prototype.ngAfterViewInit = function () {
        var _this = this;
        this.$el = $(this.el.nativeElement);
        this.$body = $('#body');
        this.$searchInput = this.$el.find('#overlay-search-input');
        this.$closeOverlayBtn = this.$el.find('#overlay-close');
        this.$el.on('keyup', function (e) {
            if (e.keyCode === 27) {
                _this.closeOverlay();
            }
        });
        this.$closeOverlayBtn.on('click', function (e) {
            _this.closeOverlay();
            e.preventDefault();
        });
    };
    SearchOverlayDirective.prototype.openOverlay = function () {
        var _this = this;
        this.$body.addClass('overlay-active');
        // [delay] should >= `visibility` transition duration in CSS, see _overlay.scss
        // otherwise auto-focus won't work since element is not there yet
        if (this.$searchInput) {
            setTimeout(function () {
                _this.$searchInput.focus();
            }, 301);
        }
    };
    SearchOverlayDirective.prototype.closeOverlay = function () {
        this.$body.removeClass('overlay-active');
        if (this.$searchInput) {
            this.$searchInput.val(function () {
                return this.defaultValue;
            });
        }
    };
    SearchOverlayDirective.prototype.updateSearchOverlay = function (state) {
        if (state === 'open') {
            this.openOverlay();
        }
    };
    SearchOverlayDirective.prototype.ngOnDestroy = function () {
        this.subscription.unsubscribe();
    };
    SearchOverlayDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[mySearchOverlay]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_1__layout_service__["a" /* LayoutService */]])
    ], SearchOverlayDirective);
    return SearchOverlayDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/auto-close-mobile-nav.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AutoCloseMobileNavDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



// Mobile only: automatically close sidebar on route change.
var AutoCloseMobileNavDirective = (function () {
    function AutoCloseMobileNavDirective(el, router) {
        this.el = el;
        this.router = router;
    }
    AutoCloseMobileNavDirective.prototype.ngOnInit = function () {
        var $body = $('#body');
        if (__WEBPACK_IMPORTED_MODULE_2__config__["a" /* APPCONFIG */].AutoCloseMobileNav) {
            this.router.events.subscribe(function (event) {
                if (event instanceof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* NavigationEnd */]) {
                    setTimeout(function () {
                        // console.log('NavigationEnd:', event);
                        $body.removeClass('sidebar-mobile-open');
                    }, 0);
                }
            });
        }
    };
    AutoCloseMobileNavDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myAutoCloseMobileNav]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["f" /* Router */]])
    ], AutoCloseMobileNavDirective);
    return AutoCloseMobileNavDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav-menu/accordion-nav.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AccordionNavDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AccordionNavDirective = (function () {
    function AccordionNavDirective(el) {
        this.el = el;
    }
    AccordionNavDirective.prototype.ngAfterViewInit = function () {
        // on click, open it's own ul, close sibling li opened ul & sub ul
        // on click, close it's own ul & sub ul
        var $nav = $(this.el.nativeElement);
        var slideTime = 250;
        var $lists = $nav.find('ul').parent('li');
        $lists.append('<i class="material-icons icon-has-ul">arrow_drop_down</i>');
        var $As = $lists.children('a');
        // Disable a link that has ul
        $As.on('click', function (event) {
            event.preventDefault();
        });
        // Accordion nav
        $nav.on('click', function (e) {
            var target = e.target;
            var $parentLi = $(target).closest('li'); // closest, insead of parent, so it still works when click on i icons
            if (!$parentLi.length) {
                return;
            } // return if doesn't click on li
            var $subUl = $parentLi.children('ul');
            // let depth = $subUl.parents().length; // but some li has no sub ul, so...
            var depth = $parentLi.parents().length + 1;
            // filter out all elements (except target) at current depth or greater
            var allAtDepth = $nav.find('ul').filter(function () {
                if ($(this).parents().length >= depth && this !== $subUl.get(0)) {
                    return true;
                }
            });
            allAtDepth.slideUp(slideTime).closest('li').removeClass('open');
            // Toggle target
            if ($parentLi.has('ul').length) {
                $parentLi.toggleClass('open');
            }
            $subUl.stop().slideToggle(slideTime);
        });
    };
    AccordionNavDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myAccordionNav]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */]])
    ], AccordionNavDirective);
    return AccordionNavDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav-menu/append-submenu-icon.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppendSubmenuIconDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AppendSubmenuIconDirective = (function () {
    function AppendSubmenuIconDirective(el) {
        this.el = el;
    }
    AppendSubmenuIconDirective.prototype.ngAfterViewInit = function () {
        var $el = $(this.el.nativeElement);
        $el.find('.prepend-icon').prepend('<i class="material-icons">keyboard_arrow_right</i>');
    };
    AppendSubmenuIconDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myAppendSubmenuIcon]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */]])
    ], AppendSubmenuIconDirective);
    return AppendSubmenuIconDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav-menu/highlight-active-items.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HighlightActiveItemsDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var HighlightActiveItemsDirective = (function () {
    function HighlightActiveItemsDirective(el, location, router) {
        this.el = el;
        this.location = location;
        this.router = router;
    }
    HighlightActiveItemsDirective.prototype.ngAfterViewInit = function () {
        var $el = $(this.el.nativeElement);
        var $links = $el.find('a');
        function highlightActive(links) {
            var path = location.hash;
            // console.log(path);
            links.each(function (i, link) {
                var $link = $(link);
                var $li = $link.parent('li');
                var href = $link.attr('href');
                // console.log(href);
                if ($li.hasClass('active')) {
                    $li.removeClass('active');
                }
                if (path.indexOf(href) === 0) {
                    $li.addClass('active');
                }
            });
        }
        highlightActive($links);
        this.router.events.subscribe(function (evt) {
            if (!(evt instanceof __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* NavigationEnd */])) {
                return;
            }
            highlightActive($links);
        });
    };
    HighlightActiveItemsDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myHighlightActiveItems]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_1__angular_common__["f" /* Location */], __WEBPACK_IMPORTED_MODULE_2__angular_router__["f" /* Router */]])
    ], HighlightActiveItemsDirective);
    return HighlightActiveItemsDirective;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav-menu/sidenav-menu.component.html":
/***/ (function(module, exports) {

module.exports = "<ul class=\"nav\" myAccordionNav myAppendSubmenuIcon mySlimScroll myHighlightActiveItems>\n  <!-- <li class=\"nav-header\"><span>Navigation</span></li> -->\n  <!-- <li><a mat-button [routerLink]=\"['/app/dashboard']\"><i class=\"nav-icon material-icons\">home</i><span class=\"nav-text\">Dashboard</span></a></li> -->\n  <!-- <li><a mat-button href=\"javascript:;\" myToggleQuickview=\"customizer\"><i class=\"nav-icon material-icons\">settings</i><span class=\"nav-text\">Customizer</span></a></li> -->\n\n  <br/>\n  <li><a mat-button [routerLink]=\"['/app/dashboard']\"><i class=\"nav-icon material-icons\">home</i><span class=\"nav-text\">Dashboard</span></a></li>\n  <li>\n    <a mat-button [routerLink]=\"['/app/projects']\"><i class=\"nav-icon material-icons\">code</i><span class=\"nav-text\">Projects</span></a>\n  </li>\n  <!--<li>\n    <a mat-button [routerLink]=\"['/app/jobs']\"><i class=\"nav-icon material-icons\">alarm</i><span class=\"nav-text\">Jobs</span></a>\n  </li>-->\n\n\n  <!-- <li>\n    <a mat-button href=\"#/app/ui\"><i class=\"nav-icon material-icons\">bubble_chart</i><span class=\"nav-text\">UI Kit</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/typography']\"><span>Typography</span></a></li>\n     <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/buttons']\"><span>Buttons</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/icons']\"><span>Icons</span><span class=\"badge badge-pill badge-info\">2</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/cards']\"><span>Cards</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/icon-boxes']\"><span>Icon Boxes</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/components']\"><span>Components</span><span class=\"badge badge-pill badge-danger\">12</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/hover']\"><span>Hover</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/feature-callouts']\"><span>Feature Callouts</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/lists']\"><span>Lists</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/sashes']\"><span>Sashes</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/testimonials']\"><span>Testimonials</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/pricing-tables']\"><span>Pricing Tables</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/call-to-actions']\"><span>Call to Actions</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/timeline']\"><span>Timeline</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/boxes']\"><span>Boxes</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/grids']\"><span>Grids</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ui/color']\"><span>Color</span></a></li>\n    </ul>\n  </li> -->\n  <!-- <li>\n    <a mat-button href=\"#/app/form\"><i class=\"nav-icon material-icons\">format_color_text</i><span class=\"nav-text\">Forms</span></a>\n    <ul> -->\n      <!-- <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/input']\"><span>Input</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/checkbox']\"><span>Checkbox</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/radio-button']\"><span>Radio Button</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/chips']\"><span>Chips</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/select']\"><span>Select</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/slide-toggle']\"><span>Slide Toggle</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/slider']\"><span>Slider</span></a></li> -->\n      <!-- <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/components']\"><span>Components</span><span class=\"badge badge-pill badge-info\">10</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/layouts']\"><span>Form Layouts</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/form/validation']\"><span>Form Validation</span></a></li>\n    </ul>\n  </li> -->\n  <!-- <li>\n    <a mat-button href=\"#/app/table\"><i class=\"nav-icon material-icons\">list</i><span class=\"nav-text\">Tables</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/table/static']\"><span>Static Tables</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/table/responsive']\"><span>Responsive Tables</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/table/data-table']\"><span>Data Tables</span></a></li>\n    </ul>\n  </li> -->\n  <!-- <li>\n    <a mat-button href=\"#/app/chart\"><i class=\"nav-icon material-icons\">equalizer</i><span class=\"nav-text\">Charts</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/line']\">Line &amp; Area</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/bar']\">Bar</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/pie']\">Pie</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/scatter']\">Scatter</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/radar']\">Radar</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/funnel']\">Funnel</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/chart/more']\">More</a></li>\n    </ul>\n  </li> -->\n  <li><a href=\"javascript:void(0);\"></a></li>\n  <li class=\"nav-divider\"></li>\n  <!--<li>\n    <a mat-button href=\"#/extra\"><i class=\"nav-icon material-icons\">apps</i><span class=\"nav-text\">Services</span></a>\n    <ul>-->\n      <li class=\"li-small\"><a mat-button href=\"javascript:;\"><i class=\"nav-icon material-icons text-muted\">apps</i><span class=\"nav-text text-muted\">Services</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/regions']\"><i class=\"nav-icon material-icons\">cloud_queue</i><span class=\"nav-text\">Bot Hub</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/issues']\"><i class=\"nav-icon material-icons\">report_problem</i><span class=\"nav-text\">Issue-Tracker Hub</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/marketplace']\"><i class=\"nav-icon material-icons\">shopping_cart</i><span class=\"nav-text\">Marketplace</span></a></li>\n      <!--<li><a mat-button [routerLink]=\"['/app/monitor']\"><i class=\"nav-icon material-icons\">favorite_border</i>&nbsp; &nbsp; <span class=\"nav-text\">  Monitoring Hub</span></a></li>\n      <li><a mat-button [routerLink]=\"['/app/doc']\"><i class=\"nav-icon material-icons\">folder_open</i>&nbsp; &nbsp; <span class=\"nav-text\">  Doc Hub</span></a></li>-->\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/support']\"><i class=\"nav-icon material-icons\">help_outline</i><span class=\"nav-text\">Support Hub</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/analytics']\"><i class=\"nav-icon material-icons\">dashboard</i><span class=\"nav-text\">Analytics Hub</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/vault']\"><i class=\"nav-icon material-icons\">security</i><span class=\"nav-text\">Vault</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/billing']\"><i class=\"nav-icon material-icons\">attach_money</i><span class=\"nav-text\">Billing</span></a></li>\n      <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/org']\"><i class=\"nav-icon material-icons\">account_circle</i><span class=\"nav-text\">Manage</span></a></li>\n\n    <!--</ul>\n  </li>-->\n\n    <li class=\"nav-divider\"></li>\n\n  <li>\n    <a mat-button href=\"#/code\"><i class=\"nav-icon material-icons\">code</i><span class=\"nav-text\">Developer Zone</span></a>\n    <ul>\n      <li><a mat-button [routerLink]=\"['/app/skills/version-control']\"><i class=\"nav-icon material-icons\">code</i>&nbsp; &nbsp; <span class=\"nav-text\">  Version-Control Skills</span></a></li>\n      <li><a mat-button [routerLink]=\"['/app/skills/bot-deployment']\"><i class=\"nav-icon material-icons\">code</i>&nbsp; &nbsp; <span class=\"nav-text\">  Bot Deployment Skills</span></a></li>\n      <li><a mat-button [routerLink]=\"['/app/skills/issue-tracker']\"><i class=\"nav-icon material-icons\">code</i>&nbsp; &nbsp; <span class=\"nav-text\">  Issue-Tracker Skills</span></a></li>\n      <li><a mat-button [routerLink]=\"['/app/skills/analytics']\"><i class=\"nav-icon material-icons\">code</i>&nbsp; &nbsp; <span class=\"nav-text\">  Analytics Skills</span></a></li>\n    </ul>\n  </li>\n\n\n  <!-- <li>\n    <a mat-button href=\"#/app/page\"><i class=\"nav-icon material-icons\">content_copy</i><span class=\"nav-text\">Pages</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/about']\">About</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/services']\">Services</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/contact']\">Contact</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/careers']\">Careers</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/blog']\">Blog</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/faqs']\">FAQs</a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/page/terms']\">Terms of Service</a></li>\n    </ul>\n  </li>\n  <li>\n    <a mat-button href=\"#/extra\"><i class=\"nav-icon material-icons\">more_horiz</i><span class=\"nav-text\">Extra Pages</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/login']\"><span>Login</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/sign-up']\"><span>Sign Up</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/forgot-password']\"><span>Forgot Password</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/confirm-email']\"><span>Confirm Email</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/lock-screen']\"><span>Lock Screen</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/404']\"><span>404 Error</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/500']\"><span>500 Error</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/extra/maintenance']\"><span>Maintenance</span></a></li>\n    </ul>\n  </li>\n  <li>\n    <a mat-button href=\"#/app/pglayout\"><i class=\"nav-icon material-icons\">view_array</i><span class=\"nav-text\">Page Layouts</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/pglayout/full-width']\"><span>Full Width</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/pglayout/centered']\"><span>Centered</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/fullscreen']\"><span>Fullscreen</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/pglayout/with-tabs']\"><span>With Tabs</span></a></li>\n    </ul>\n  </li> -->\n  <!-- <li>\n    <a mat-button href=\"#/app/ecommerce\"><i class=\"nav-icon material-icons\">shopping_cart</i><span class=\"nav-text\">eCommerce</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ecommerce/products']\"><span>Products</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ecommerce/horizontal-products']\"><span>Products (Horizontal)</span></a></li>\n      <li><a mat-button class=\"md-button prepend-icon\" [routerLink]=\"['/app/ecommerce/invoice']\"><span>Invoice</span></a></li>\n    </ul>\n  </li>\n  <li>\n    <a mat-button href=\"#/app/menu\"><i class=\"nav-icon material-icons\">sort</i><span class=\"nav-text\">Menu Levels</span></a>\n    <ul>\n      <li><a mat-button class=\"md-button prepend-icon\" href=\"javascript:;\"><span>Level 1</span></a></li>\n      <li>\n        <a mat-button class=\"md-button prepend-icon\" href=\"javascript:;\"><span>Level 1</span></a>\n        <ul>\n          <li><a mat-button class=\"md-button\" href=\"javascript:;\"><span>Level 2</span></a></li>\n          <li>\n            <a mat-button class=\"md-button\" href=\"javascript:;\"><span>Level 2</span></a>\n            <ul>\n              <li><a mat-button class=\"md-button\" href=\"javascript:;\"><span>Level 3</span></a></li>\n              <li><a mat-button class=\"md-button\" href=\"javascript:;\"><span>Level 3</span></a></li>\n            </ul>\n          </li>\n        </ul>\n      </li>\n    </ul>\n  </li>\n  <li class=\"nav-divider\"></li>\n  <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/form/components']\"><i class=\"nav-icon nav-dot material-icons color-info\">fiber_manual_record</i><span class=\"nav-text\">Form Components</span></a></li>\n  <li class=\"li-small\"><a mat-button [routerLink]=\"['/app/ui/components']\"><i class=\"nav-icon nav-dot material-icons color-success\">fiber_manual_record</i><span class=\"nav-text\">UI Components</span></a></li>\n  <li class=\"li-small\"><a mat-button href=\"http://iarouse.com/dist-angular-material/landing/\" target=\"_blank\"><i class=\"nav-icon nav-dot material-icons color-danger\">fiber_manual_record</i><span class=\"nav-text\">Material Landing</span></a></li> -->\n</ul>\n"

/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav-menu/sidenav-menu.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppSidenavMenuComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppSidenavMenuComponent = (function () {
    function AppSidenavMenuComponent() {
    }
    AppSidenavMenuComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-sidenav-menu',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/sidenav/sidenav-menu/sidenav-menu.component.html")
        })
    ], AppSidenavMenuComponent);
    return AppSidenavMenuComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav.component.html":
/***/ (function(module, exports) {

module.exports = "<nav class=\"app-sidebar\" myAutoCloseMobileNav\n   [ngClass]=\"{'bg-color-light': ['31','32','33','34','35','36'].indexOf(AppConfig.colorOption) >= 0,\n               'bg-color-dark': ['31','32','33','34','35','36'].indexOf(AppConfig.colorOption) < 0 }\">\n\n  <section class=\"sidebar-header\"\n       [ngClass]=\"{'bg-color-dark': ['11','31'].indexOf(AppConfig.colorOption) >= 0,\n                   'bg-color-light': AppConfig.colorOption === '21',\n                   'bg-color-primary': ['12','22','32'].indexOf(AppConfig.colorOption) >= 0,\n                   'bg-color-success': ['13','23','33'].indexOf(AppConfig.colorOption) >= 0,\n                   'bg-color-info': ['14','24','34'].indexOf(AppConfig.colorOption) >= 0,\n                   'bg-color-warning': ['15','25','35'].indexOf(AppConfig.colorOption) >= 0,\n                   'bg-color-danger': ['16','26','36'].indexOf(AppConfig.colorOption) >= 0}\">\n    <!-- <span class=\"logo-icon material-icons\">view_compact</span> -->\n    <svg class=\"logo-img\" version=\"1.1\" id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" viewBox=\"0 0 250 250\" style=\"enable-background:new 0 0 250 250;\" xml:space=\"preserve\">\n      <g>\n        <g>\n          <polygon class=\"st0\" points=\"125,153.4 100.3,153.4 88.6,182.6 88.6,182.6 66.9,182.6 66.8,182.6 125,52.1 125,52.2 125,52.2 \n            125,30 125,30 31.9,63.2 46.1,186.3 125,230 125,230 125,153.4    \"/>\n          <polygon class=\"st0\" points=\"108,135.4 125,135.4 125,135.4 125,94.5     \"/>\n        </g>\n        <g class=\"st1\">\n          <polygon class=\"st0\" points=\"125,153.4 149.7,153.4 161.4,182.6 161.4,182.6 183.1,182.6 183.2,182.6 125,52.1 125,52.2 125,52.2 \n            125,30 125,30 218.1,63.2 203.9,186.3 125,230 125,230 125,153.4    \"/>\n          <polygon class=\"st0\" points=\"142,135.4 125,135.4 125,135.4 125,94.5     \"/>\n        </g>\n      </g>\n    </svg>\n    <a [routerLink]=\"['/']\" class=\"brand\">{{AppConfig.productName}}</a>\n    <a href=\"javascript:;\" class=\"collapsednav-toggler\" (click)=\"toggleCollapsedNav()\">\n      <i class=\"material-icons\" *ngIf=\"this.AppConfig.navCollapsed\">radio_button_unchecked</i>\n      <i class=\"material-icons\" *ngIf=\"!this.AppConfig.navCollapsed\">radio_button_checked</i>\n    </a>\n  </section>\n\n  <section class=\"sidebar-content\">\n    <my-app-sidenav-menu></my-app-sidenav-menu>\n  </section>\n\n  <section class=\"sidebar-footer\">\n    <ul class=\"nav\">\n      <li><a mat-button aria-label=\"menu\" target=\"_blank\" [href]=\"AppConfig.productLink\" ><i class=\"nav-icon material-icons\">help</i><span class=\"nav-text\"><span>Help</span> & <span>Support</span></span></a></li>\n    </ul>\n  </section>\n</nav>\n"

/***/ }),

/***/ "../../../../../src/app/layout/sidenav/sidenav.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppSidenavComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var AppSidenavComponent = (function () {
    function AppSidenavComponent() {
    }
    AppSidenavComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    AppSidenavComponent.prototype.toggleCollapsedNav = function () {
        this.AppConfig.navCollapsed = !this.AppConfig.navCollapsed;
    };
    AppSidenavComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-app-sidenav',
            styles: [],
            template: __webpack_require__("../../../../../src/app/layout/sidenav/sidenav.component.html")
        })
    ], AppSidenavComponent);
    return AppSidenavComponent;
}());



/***/ }),

/***/ "../../../../../src/app/layout/sidenav/toggle-offcanvas-nav.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ToggleOffcanvasNavDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

// off-canvas sidebar for mobile, and this is the trigger
var ToggleOffcanvasNavDirective = (function () {
    function ToggleOffcanvasNavDirective(el) {
        this.el = el;
    }
    ToggleOffcanvasNavDirective.prototype.ngAfterViewInit = function () {
        var $navToggler = $(this.el.nativeElement);
        var $body = $('#body');
        $navToggler.on('click', function (e) {
            // _sidebar.scss, _page-container.scss
            $body.toggleClass('sidebar-mobile-open');
            e.preventDefault();
        });
    };
    ToggleOffcanvasNavDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myToggleOffcanvasNav]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */]])
    ], ToggleOffcanvasNavDirective);
    return ToggleOffcanvasNavDirective;
}());



/***/ }),

/***/ "../../../../../src/app/models/base.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Base; });
var Base = (function () {
    function Base() {
    }
    return Base;
}());



/***/ }),

/***/ "../../../../../src/app/models/message.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Message; });
var Message = (function () {
    function Message(subject, type, createdDate, taskType, status, message) {
        this.subject = subject;
        this.type = type;
        this.createdDate = createdDate;
        this.taskType = taskType;
        this.status = status;
        this.message = message;
    }
    return Message;
}());



/***/ }),

/***/ "../../../../../src/app/models/project.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Project; });
var Project = (function () {
    function Project(name, url, username, password, projectType) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.projectType = projectType;
    }
    return Project;
}());



/***/ }),

/***/ "../../../../../src/app/models/run.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Run; });
/* unused harmony export Task */
var Run = (function () {
    function Run() {
        this.task = new Task();
    }
    return Run;
}());

var Task = (function () {
    function Task() {
    }
    return Task;
}());



/***/ }),

/***/ "../../../../../src/app/models/subscription.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Subscription; });
/* unused harmony export Org */
var Subscription = (function () {
    function Subscription() {
        this.org = new Org();
    }
    return Subscription;
}());

var Org = (function () {
    function Org() {
    }
    return Org;
}());



/***/ }),

/***/ "../../../../../src/app/models/vault.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Vault; });
/* unused harmony export Org */
var Vault = (function () {
    function Vault() {
        this.org = new Org();
    }
    return Vault;
}());

var Org = (function () {
    function Org() {
    }
    return Org;
}());



/***/ }),

/***/ "../../../../../src/app/page-layouts/fullscreen/fullscreen.component.html":
/***/ (function(module, exports) {

module.exports = "<section class=\"container-fluid\">\n\n  <div class=\"article-title\">Blank</div>\n\n</section>"

/***/ }),

/***/ "../../../../../src/app/page-layouts/fullscreen/fullscreen.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageLayoutFullscreenComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

// Children of AppModule instead of AppMainModule
var PageLayoutFullscreenComponent = (function () {
    function PageLayoutFullscreenComponent() {
    }
    PageLayoutFullscreenComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-layout-fullscreen',
            styles: [],
            template: __webpack_require__("../../../../../src/app/page-layouts/fullscreen/fullscreen.component.html")
        })
    ], PageLayoutFullscreenComponent);
    return PageLayoutFullscreenComponent;
}());



/***/ }),

/***/ "../../../../../src/app/services/dashboard.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DashboardService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var DashboardService = (function () {
    function DashboardService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/dashboard';
    }
    DashboardService.prototype.getStat = function (name) {
        return this.http.get(this.serviceUrl + "/" + name);
    };
    DashboardService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], DashboardService);
    return DashboardService;
}());



/***/ }),

/***/ "../../../../../src/app/services/jobs.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return JobsService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var JobsService = (function () {
    function JobsService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/jobs';
    }
    JobsService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id);
    };
    /**
     * Get the jobs in observable from endpoint
     */
    JobsService.prototype.getJobs = function (id) {
        return this.http.get(this.serviceUrl + "/project-id/" + id);
    };
    JobsService.prototype.getCountJobs = function () {
        return this.http.get(this.serviceUrl + "/count");
    };
    JobsService.prototype.getCountTests = function () {
        return this.http.get(this.serviceUrl + "/count-tests");
    };
    JobsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], JobsService);
    return JobsService;
}());



/***/ }),

/***/ "../../../../../src/app/services/message.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MessageService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MessageService = (function () {
    function MessageService(http) {
        this.http = http;
        this.url = '/api/v1/alerts';
    }
    /**
     * Get the jobs in observable from endpoint
     */
    MessageService.prototype.get = function () {
        return this.http.get(this.url);
    };
    MessageService.prototype.getById = function (id) {
        return this.http.get(this.url + "/" + id);
    };
    MessageService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], MessageService);
    return MessageService;
}());



/***/ }),

/***/ "../../../../../src/app/services/org.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OrgService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var OrgService = (function () {
    function OrgService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/orgs';
    }
    OrgService.prototype.get = function () {
        return this.http.get(this.serviceUrl);
    };
    OrgService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], OrgService);
    return OrgService;
}());



/***/ }),

/***/ "../../../../../src/app/services/project.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ProjectService = (function () {
    function ProjectService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/projects';
    }
    /**
     * Get the jobs in observable from endpoint
     */
    ProjectService.prototype.getProjects = function () {
        return this.http.get(this.serviceUrl);
    };
    ProjectService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id + "/git-account");
    };
    ProjectService.prototype.create = function (project) {
        return this.http.post(this.serviceUrl + "/add", project);
    };
    ProjectService.prototype.update = function (project) {
        return this.http.put(this.serviceUrl + "/" + project['id'] + "/git-account", project);
    };
    ProjectService.prototype.delete = function (project) {
        return this.http.delete(this.serviceUrl + "/" + project['projectId']);
    };
    ProjectService.prototype.getCount = function () {
        return this.http.get(this.serviceUrl + "/count");
    };
    ProjectService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], ProjectService);
    return ProjectService;
}());



/***/ }),

/***/ "../../../../../src/app/services/regions.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RegionsService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RegionsService = (function () {
    function RegionsService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/clusters';
    }
    /**
     * Get the jobs in observable from endpoint
     */
    RegionsService.prototype.get = function () {
        return this.http.get(this.serviceUrl);
    };
    RegionsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], RegionsService);
    return RegionsService;
}());



/***/ }),

/***/ "../../../../../src/app/services/run.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RunService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RunService = (function () {
    function RunService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/runs';
    }
    RunService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id);
    };
    /**
     * Get the jobs in observable from endpoint
     */
    RunService.prototype.get = function (jobId) {
        return this.http.get(this.serviceUrl + "/job/" + jobId);
    };
    RunService.prototype.run = function (jobId) {
        //return this.http.post(
        console.log("JobId: " + jobId);
        return this.http.post(this.serviceUrl + "/job/" + jobId, null);
    };
    RunService.prototype.getDetails = function (runId) {
        return this.http.get(this.serviceUrl + "/" + runId);
    };
    RunService.prototype.getTestSuiteResponses = function (runId) {
        return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-responses");
    };
    RunService.prototype.getSummary = function (runId) {
        return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-summary");
    };
    RunService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], RunService);
    return RunService;
}());



/***/ }),

/***/ "../../../../../src/app/services/skill-subscription.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillSubscriptionService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var SkillSubscriptionService = (function () {
    function SkillSubscriptionService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/subscriptions';
    }
    /**
     * Get observable from endpoint
     */
    SkillSubscriptionService.prototype.get = function () {
        return this.http.get(this.serviceUrl);
    };
    SkillSubscriptionService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id);
    };
    SkillSubscriptionService.prototype.create = function (obj) {
        return this.http.post(this.serviceUrl, obj);
    };
    SkillSubscriptionService.prototype.update = function (obj) {
        return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
    };
    SkillSubscriptionService.prototype.delete = function (obj) {
        return this.http.delete(this.serviceUrl + "/" + obj['id']);
    };
    SkillSubscriptionService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], SkillSubscriptionService);
    return SkillSubscriptionService;
}());



/***/ }),

/***/ "../../../../../src/app/services/skill.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SkillService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var SkillService = (function () {
    function SkillService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/skills';
    }
    /**
     * Get observable from endpoint
     */
    SkillService.prototype.get = function () {
        return this.http.get(this.serviceUrl);
    };
    SkillService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id);
    };
    SkillService.prototype.create = function (obj) {
        return this.http.post(this.serviceUrl, obj);
    };
    SkillService.prototype.update = function (obj) {
        return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
    };
    SkillService.prototype.delete = function (obj) {
        return this.http.delete(this.serviceUrl + "/" + obj['id']);
    };
    SkillService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], SkillService);
    return SkillService;
}());



/***/ }),

/***/ "../../../../../src/app/services/vault.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VaultService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/toPromise.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var VaultService = (function () {
    function VaultService(http) {
        this.http = http;
        this.serviceUrl = '/api/v1/vault';
    }
    /**
     * Get observable from endpoint
     */
    VaultService.prototype.get = function () {
        return this.http.get(this.serviceUrl);
    };
    VaultService.prototype.getById = function (id) {
        return this.http.get(this.serviceUrl + "/" + id);
    };
    VaultService.prototype.create = function (obj) {
        return this.http.post(this.serviceUrl, obj);
    };
    VaultService.prototype.update = function (obj) {
        return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
    };
    VaultService.prototype.delete = function (obj) {
        return this.http.delete(this.serviceUrl + "/" + obj['id']);
    };
    VaultService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], VaultService);
    return VaultService;
}());



/***/ }),

/***/ "../../../../../src/app/shared/echarts.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return EChartsDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_echarts__ = __webpack_require__("../../../../echarts/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_echarts___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_echarts__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_echarts_theme_macarons__ = __webpack_require__("../../../../echarts/theme/macarons.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_echarts_theme_macarons___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_echarts_theme_macarons__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__layout_layout_service__ = __webpack_require__("../../../../../src/app/layout/layout.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var EChartsDirective = (function () {
    function EChartsDirective(el, layoutService) {
        var _this = this;
        this.layoutService = layoutService;
        this.resizeChart = function (state) {
            // console.log('state: ' + state)
            setTimeout(function () {
                if (_this.myChart) {
                    _this.myChart.resize();
                }
            }, 300);
        };
        this.el = el;
        this.subscription = layoutService.echartsState$.subscribe(function (state) {
            _this.resizeChart(state);
        });
    }
    EChartsDirective.prototype.ngAfterViewInit = function () {
        this.myChart = __WEBPACK_IMPORTED_MODULE_1_echarts___default.a.init(this.el.nativeElement, 'macarons');
        if (!this.EChartsOptions) {
            return;
        }
        this.myChart.setOption(this.EChartsOptions);
    };
    EChartsDirective.prototype.ngOnDestroy = function () {
        if (this.myChart) {
            this.myChart.dispose();
            this.myChart = null; // https://bitbucket.org/iarouse/angular-material/commits/5eec2667b5496edfa1cc0896333b83e188a35676
        }
    };
    EChartsDirective.prototype.onResize = function () {
        this.resizeChart(true);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", Object)
    ], EChartsDirective.prototype, "EChartsOptions", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* HostListener */])('window:resize'),
        __metadata("design:type", Function),
        __metadata("design:paramtypes", []),
        __metadata("design:returntype", void 0)
    ], EChartsDirective.prototype, "onResize", null);
    EChartsDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[myECharts]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */], __WEBPACK_IMPORTED_MODULE_3__layout_layout_service__["a" /* LayoutService */]])
    ], EChartsDirective);
    return EChartsDirective;
}());



/***/ }),

/***/ "../../../../../src/app/shared/shared.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SharedModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__echarts_directive__ = __webpack_require__("../../../../../src/app/shared/echarts.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__slim_scroll_directive__ = __webpack_require__("../../../../../src/app/shared/slim-scroll.directive.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var SharedModule = (function () {
    function SharedModule() {
    }
    SharedModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_1__echarts_directive__["a" /* EChartsDirective */],
                __WEBPACK_IMPORTED_MODULE_2__slim_scroll_directive__["a" /* SlimScrollDirective */],
            ],
            exports: [
                __WEBPACK_IMPORTED_MODULE_1__echarts_directive__["a" /* EChartsDirective */],
                __WEBPACK_IMPORTED_MODULE_2__slim_scroll_directive__["a" /* SlimScrollDirective */],
            ]
        })
    ], SharedModule);
    return SharedModule;
}());



/***/ }),

/***/ "../../../../../src/app/shared/slim-scroll.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SlimScrollDirective; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_jquery_slimscroll_jquery_slimscroll_min_js__ = __webpack_require__("../../../../jquery-slimscroll/jquery.slimscroll.min.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_jquery_slimscroll_jquery_slimscroll_min_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_jquery_slimscroll_jquery_slimscroll_min_js__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var SlimScrollDirective = (function () {
    function SlimScrollDirective(el) {
        this.el = el;
    }
    SlimScrollDirective.prototype.ngAfterViewInit = function () {
        var $el = $(this.el.nativeElement);
        $el.slimScroll({
            height: this.scrollHeight || '100%'
        });
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* Input */])(),
        __metadata("design:type", String)
    ], SlimScrollDirective.prototype, "scrollHeight", void 0);
    SlimScrollDirective = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["t" /* Directive */])({ selector: '[mySlimScroll]' }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ElementRef */]])
    ], SlimScrollDirective);
    return SlimScrollDirective;
}());



/***/ }),

/***/ "../../../../../src/assets/images/background/1.png":
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "1.b2afaf2b30718b2e6a25.png";

/***/ }),

/***/ "../../../../../src/assets/images/background/2.png":
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "2.469e003e44a67c7ef137.png";

/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: true,
    hmr: false
};


/***/ }),

/***/ "../../../../../src/hmr.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export hmrBootstrap */
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angularclass_hmr__ = __webpack_require__("../../../../@angularclass/hmr/dist/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angularclass_hmr___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__angularclass_hmr__);


var hmrBootstrap = function (module, bootstrap) {
    var ngModule;
    module.hot.accept();
    bootstrap().then(function (mod) { return ngModule = mod; });
    module.hot.dispose(function () {
        var appRef = ngModule.injector.get(__WEBPACK_IMPORTED_MODULE_0__angular_core__["g" /* ApplicationRef */]);
        var elements = appRef.components.map(function (c) { return c.location.nativeElement; });
        var makeVisible = Object(__WEBPACK_IMPORTED_MODULE_1__angularclass_hmr__["createNewHosts"])(elements);
        ngModule.destroy();
        makeVisible();
    });
};


/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__hmr__ = __webpack_require__("../../../../../src/hmr.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_hammerjs__ = __webpack_require__("../../../../hammerjs/hammer.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_hammerjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_hammerjs__);






if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* enableProdMode */])();
}
var bootstrap = function () { return Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]); };
if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].hmr) {
    if (false) {
        hmrBootstrap(module, bootstrap);
    }
    else {
        console.error('HMR is not enabled for webpack-dev-server!');
        console.log('Are you using the --hmr flag for ng serve?');
    }
}
else {
    bootstrap();
}


/***/ }),

/***/ "../../../../../src/styles/app.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/app.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./app.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./app.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../../src/styles/bootstrap.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/bootstrap.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./bootstrap.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./bootstrap.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../../src/styles/layout.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/layout.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./layout.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./layout.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../../src/styles/material2-theme.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/material2-theme.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./material2-theme.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./material2-theme.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../../src/styles/theme.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/theme.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./theme.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./theme.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../../src/styles/ui.scss":
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__("../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/ui.scss");
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {"hmr":true}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__("../../../../style-loader/lib/addStyles.js")(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./ui.scss", function() {
			var newContent = require("!!../../node_modules/css-loader/index.js??ref--8-1!../../node_modules/postcss-loader/lib/index.js??postcss!../../node_modules/sass-loader/lib/loader.js??ref--8-3!./ui.scss");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/app.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ":focus {\n  outline: none !important; }\n\nbutton[mat-fab], button[mat-mini-fab], button[mat-raised-button],\na[mat-fab],\na[mat-mini-fab],\na[mat-raised-button] {\n  text-decoration: none; }\n\nbutton[mat-raised-button].mat-warn, button[mat-fab].mat-warn, button[mat-mini-fab].mat-warn, button[mat-raised-button].mat-accent, button[mat-fab].mat-accent, button[mat-mini-fab].mat-accent,\na[mat-raised-button].mat-warn,\na[mat-fab].mat-warn,\na[mat-mini-fab].mat-warn,\na[mat-raised-button].mat-accent,\na[mat-fab].mat-accent,\na[mat-mini-fab].mat-accent {\n  color: #fff; }\n\nbutton[mat-fab], button[mat-mini-fab],\na[mat-fab],\na[mat-mini-fab] {\n  color: #fff; }\n\nbutton.btn-lg,\na.btn-lg {\n  padding: 0.5rem 1rem;\n  font-size: 1.25rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\nbutton.btn-sm,\na.btn-sm {\n  padding: 0.25rem 0.5rem;\n  font-size: 0.875rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\n.mat-menu-content a[mat-menu-item] {\n  text-decoration: none; }\n  .mat-menu-content a[mat-menu-item]:hover, .mat-menu-content a[mat-menu-item]:focus {\n    color: rgba(0, 0, 0, 0.87); }\n\n[mat-tab-nav-bar], .mat-tab-header {\n  border-bottom: 1px solid rgba(0, 0, 0, 0.05); }\n\n[mdInput] {\n  width: 100%; }\n\nmat-input-container.mat-icon-left {\n  position: relative;\n  padding-left: 36px; }\n  mat-input-container.mat-icon-left .mat-input-wrapper {\n    position: relative; }\n  mat-input-container.mat-icon-left .mat-input-infix > mat-icon {\n    position: absolute;\n    top: 0;\n    left: -32px;\n    right: auto; }\n\n.mat-input-container .mat-icon.material-icons {\n  font-size: 24px; }\n\n.mat-radio-group-spacing mat-radio-button {\n  margin-right: 20px; }\n  .mat-radio-group-spacing mat-radio-button:last-child {\n    margin-right: 0; }\n\nmat-input-container:not(.ng-pristine).ng-invalid .mat-input-underline {\n  border-color: #EF5350; }\n  mat-input-container:not(.ng-pristine).ng-invalid .mat-input-underline .mat-input-ripple {\n    background-color: #EF5350; }\n\nmat-input-container:not(.ng-pristine).ng-valid .mat-input-underline {\n  border-color: #66BB6A; }\n  mat-input-container:not(.ng-pristine).ng-valid .mat-input-underline .mat-input-ripple {\n    background-color: #66BB6A; }\n\n.app-sidebar [mat-button], .app-sidebar [mat-raised-button], .app-sidebar [mat-fab], .app-sidebar [mat-icon-button], .app-sidebar [mat-mini-fab] {\n  font-weight: normal;\n  min-width: inherit; }\n\n.quickview-inner .mat-list-item .mat-list-item-content {\n  padding: 0;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n  -webkit-box-pack: start;\n      -ms-flex-pack: start;\n          justify-content: flex-start; }\n  .quickview-inner .mat-list-item .mat-list-item-content > p {\n    -webkit-box-flex: 1;\n        -ms-flex: 1 1 auto;\n            flex: 1 1 auto;\n    margin: 0; }\n  .quickview-inner .mat-list-item .mat-list-item-content .mat-secondary {\n    display: -webkit-box;\n    display: -ms-flexbox;\n    display: flex;\n    -webkit-box-align: center;\n        -ms-flex-align: center;\n            align-items: center;\n    -ms-flex-negative: 0;\n        flex-shrink: 0;\n    margin-right: 0;\n    -webkit-box-pack: end;\n        -ms-flex-pack: end;\n            justify-content: flex-end; }\n\n.app-header .mat-button {\n  min-width: 60px; }\n\nbody .cdk-overlay-container {\n  z-index: 1001; }\n\n.page-with-tabs > .page-title {\n  background-color: rgba(0, 0, 0, 0.05);\n  padding: 30px 15px 30px 39px; }\n  .page-with-tabs > .page-title h2 {\n    margin: 0;\n    font-size: 20px;\n    line-height: 1;\n    font-weight: 500;\n    color: #2196F3; }\n\n.page-with-tabs > mat-tab-group > mat-tab-header {\n  background-color: rgba(0, 0, 0, 0.05);\n  border-bottom: 1px solid rgba(0, 0, 0, 0.05); }\n\n.typo-styles dt {\n  display: block;\n  float: left;\n  color: #fff;\n  background-color: rgba(0, 0, 0, 0.24);\n  width: 32px;\n  height: 32px;\n  border-radius: 16px;\n  line-height: 32px;\n  text-align: center;\n  font-weight: 500;\n  margin-top: 5px; }\n\n.typo-styles dd {\n  display: block;\n  margin-left: 80px;\n  margin-bottom: 20px; }\n\n.typo-styles .typo-styles__demo {\n  margin-bottom: 8px; }\n\n.page-icons .card .fa,\n.page-icons .card .material-icons,\n.page-icons .card .wi {\n  color: rgba(0, 0, 0, 0.5); }\n\n.page-icons .card .fa,\n.page-icons .card .wi {\n  font-size: 20px;\n  margin: 5px; }\n\n.theme-gray .page-icons .card .fa,\n.theme-gray .page-icons .card .material-icons,\n.theme-gray .page-icons .card .wi,\n.theme-dark .page-icons .card .fa,\n.theme-dark .page-icons .card .material-icons,\n.theme-dark .page-icons .card .wi {\n  color: rgba(255, 255, 255, 0.7);\n  opacity: .7; }\n\n.page-grids .grid-structure .row {\n  margin-top: .8rem; }\n  .page-grids .grid-structure .row .widget-container {\n    margin-top: 5px;\n    background: rgba(0, 0, 0, 0.1);\n    padding: 10px 15px 12px;\n    font-size: 0.875rem;\n    min-height: 0;\n    border-radius: 0.2rem; }\n\n.color-palette {\n  color: rgba(255, 255, 255, 0.87);\n  font-size: 14px;\n  font-weight: 500;\n  padding-bottom: 60px; }\n  .color-palette ul {\n    margin: 0;\n    padding: 0; }\n  .color-palette .dark {\n    color: rgba(0, 0, 0, 0.87); }\n  .color-palette .color-group {\n    padding-bottom: 40px; }\n  .color-palette .color-group:first-child,\n  .color-palette .color-group:nth-of-type(3n+1),\n  .color-palette .color-group:last-child:first-child,\n  .color-palette .color-group:last-child:nth-of-type(3n+1) {\n    clear: left;\n    margin-left: 0; }\n  .color-palette .color-group li.divide,\n  .color-palette .color-group:last-child li.divide {\n    border-top: 4px solid #fafafa; }\n  .color-palette .color-group li.color,\n  .color-palette .color-group:last-child li.color {\n    padding: 15px; }\n  .color-palette .color-group li,\n  .color-palette .color-group:last-child li {\n    list-style-type: none; }\n  .color-palette .color-group li.main-color,\n  .color-palette .color-group:last-child li.main-color {\n    border-bottom: 4px solid #fafafa; }\n  .color-palette .color-group li.main-color .name,\n  .color-palette .color-group:last-child li.main-color .name {\n    display: block;\n    margin-bottom: 60px; }\n  .color-palette .color-group li.color .hex,\n  .color-palette .color-group:last-child li.color .hex {\n    float: right;\n    text-transform: uppercase; }\n\n.page-auth {\n  background-color: #e9ecef;\n  min-height: 100%;\n  background: url(" + __webpack_require__("../../../../../src/assets/images/background/2.png") + ") no-repeat center center fixed;\n  background-size: cover;\n  padding: 0 10px; }\n  .page-auth .card {\n    box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);\n    padding: 40px 30px; }\n  .page-auth .logo {\n    font-size: 32px;\n    margin-bottom: 1.3em; }\n    .page-auth .logo.text-center {\n      padding: 0; }\n    .page-auth .logo a {\n      font-weight: normal;\n      text-decoration: none; }\n  .page-auth .main-body {\n    position: relative;\n    max-width: 480px;\n    margin: 0 auto;\n    padding: 50px 0 20px; }\n    @media (min-width: 768px) {\n      .page-auth .main-body {\n        padding-top: 150px; } }\n  .page-auth .additional-info {\n    color: #868e96;\n    background-color: rgba(0, 0, 0, 0.04);\n    padding: 10px 6px;\n    position: absolute;\n    bottom: 0;\n    left: 0;\n    right: 0;\n    text-align: center;\n    font-size: 0.875rem; }\n    .page-auth .additional-info a {\n      color: #636c72; }\n    .page-auth .additional-info .divider-h {\n      border-right: 1px solid rgba(0, 0, 0, 0.1);\n      margin: 0 15px; }\n  .page-auth.page-confirm-email .logo {\n    font-size: 24px;\n    margin-bottom: .8em; }\n  .page-auth .confirm-mail-icon {\n    text-align: center; }\n    .page-auth .confirm-mail-icon .material-icons {\n      color: #636c72;\n      font-size: 100px; }\n\n.page-err {\n  background-color: #343a40;\n  height: 100%;\n  position: relative; }\n  .page-err .err-container {\n    background-color: #343a40;\n    padding: 45px 10px 0; }\n    @media (min-width: 768px) {\n      .page-err .err-container {\n        padding: 100px 0 0; } }\n  .page-err .err {\n    color: #fafafa; }\n    .page-err .err h1 {\n      margin-bottom: 35px;\n      color: #fafafa;\n      color: rgba(255, 255, 255, 0.8);\n      font-size: 150px;\n      font-weight: 300;\n      text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); }\n      @media (min-width: 768px) {\n        .page-err .err h1 {\n          font-size: 180px; } }\n    .page-err .err h2 {\n      color: #fafafa;\n      color: rgba(255, 255, 255, 0.6);\n      margin: 0;\n      font-weight: 300;\n      font-size: 28px;\n      text-transform: uppercase; }\n      @media (min-width: 768px) {\n        .page-err .err h2 {\n          font-size: 36px; } }\n  .page-err .err-body {\n    padding: 20px 10px; }\n  .page-err .btn-goback {\n    color: #fff;\n    color: rgba(255, 255, 255, 0.8);\n    background-color: transparent;\n    border-color: #fff;\n    border-color: rgba(255, 255, 255, 0.8); }\n    .page-err .btn-goback:hover, .page-err .btn-goback:focus, .page-err .btn-goback:active, .page-err .btn-goback.active {\n      color: #fff;\n      background-color: rgba(255, 255, 255, 0.1); }\n    .open .page-err .btn-goback.dropdown-toggle {\n      color: #fff;\n      background-color: rgba(255, 255, 255, 0.1); }\n  .page-err .footer {\n    position: absolute;\n    bottom: 20px;\n    width: 100%; }\n\n.page-lock {\n  height: 100%;\n  background: url(" + __webpack_require__("../../../../../src/assets/images/background/2.png") + ") no-repeat center center fixed;\n  background-size: cover; }\n  .page-lock .lock-centered {\n    position: absolute;\n    top: 50%;\n    left: 0;\n    right: 0;\n    margin-top: -65px; }\n    @media screen and (min-width: 768px) {\n      .page-lock .lock-centered {\n        margin-top: -75px; } }\n  .page-lock .lock-container {\n    position: relative;\n    max-width: 420px;\n    margin: 0 auto; }\n  .page-lock .lock-box {\n    position: absolute;\n    left: 0;\n    right: 0; }\n    .page-lock .lock-box .lock-user {\n      background: #fff;\n      width: 50%;\n      float: left;\n      height: 50px;\n      line-height: 50px;\n      margin-top: 50px;\n      padding: 0 20px;\n      border-left-radius: 0.2rem 0 0 0.2rem;\n      color: #868e96; }\n    .page-lock .lock-box .lock-img img {\n      position: absolute;\n      border-radius: 50%;\n      left: 40%;\n      width: 80px;\n      height: 80px;\n      border: 6px solid #fff;\n      background: #fff; }\n      @media screen and (min-width: 768px) {\n        .page-lock .lock-box .lock-img img {\n          left: 33%;\n          width: 150px;\n          height: 150px;\n          border: 10px solid #fff; } }\n    .page-lock .lock-box .lock-pwd {\n      background: #fff;\n      width: 50%;\n      float: right;\n      height: 50px;\n      line-height: 50px;\n      padding: 0 0 0 50px;\n      margin-top: 50px;\n      border-right-radius: 0 0.2rem 0.2rem 0;\n      color: #2196F3; }\n      @media screen and (min-width: 768px) {\n        .page-lock .lock-box .lock-pwd {\n          padding: 0 0 0 80px; } }\n      .page-lock .lock-box .lock-pwd input {\n        width: 80%;\n        height: 50px;\n        color: #495057;\n        border: 0; }\n      .page-lock .lock-box .lock-pwd .btn-submit {\n        position: absolute;\n        top: 50%;\n        right: 20px;\n        color: rgba(0, 0, 0, 0.87); }\n        .page-lock .lock-box .lock-pwd .btn-submit .material-icons {\n          line-height: 50px;\n          height: 50px; }\n\n.page-profile .profile-header {\n  position: relative;\n  margin: 0 0 15px;\n  padding: 50px 30px 90px;\n  background: url(" + __webpack_require__("../../../../../src/assets/images/background/1.png") + ") no-repeat center center fixed;\n  background-size: cover; }\n\n.page-profile .profile-img {\n  display: inline-block;\n  margin-right: 20px; }\n  .page-profile .profile-img img {\n    max-width: 120px;\n    height: auto;\n    box-shadow: 0 0 0 5px rgba(255, 255, 255, 0.5), 0 0 10px rgba(0, 0, 0, 0.2); }\n\n.page-profile .profile-social {\n  display: inline-block; }\n  .page-profile .profile-social > a {\n    margin-right: 15px;\n    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2); }\n\n.page-profile .profile-info {\n  position: absolute;\n  left: 0;\n  right: 0;\n  bottom: 0;\n  background-color: rgba(0, 0, 0, 0.3);\n  line-height: 20px;\n  padding: 10px 30px;\n  color: #fafafa; }\n  .page-profile .profile-info ul {\n    margin: 0; }\n\n.page-profile img.media-object {\n  border-radius: 0.2rem; }\n\n.page-invoice {\n  color: rgba(0, 0, 0, 0.87); }\n  .page-invoice .invoice-wrapper {\n    padding: 0 0 30px;\n    background-color: #fff; }\n\n.invoice-inner {\n  padding: 15px 15px 30px;\n  background-color: #fff; }\n  .invoice-inner .invoice-sum li {\n    margin-bottom: 5px;\n    padding: 10px;\n    background-color: rgba(0, 0, 0, 0.05);\n    border-radius: 0.2rem; }\n  .invoice-inner .table.table-bordered {\n    border: 0; }\n  .invoice-inner .table .bg-color-dark > th {\n    border: 0; }\n\n.page-dashboard h3 {\n  font-size: 22px;\n  font-weight: normal;\n  line-height: 1;\n  margin: 0 0 30px; }\n\n.page-dashboard .box {\n  position: relative;\n  border-radius: 0.2rem; }\n  .page-dashboard .box .box-top,\n  .page-dashboard .box .box-bottom {\n    height: 100px;\n    padding: 32px 15px;\n    font-size: 40px;\n    line-height: 1;\n    text-align: center;\n    font-weight: 300; }\n    .page-dashboard .box .box-top .material-icons,\n    .page-dashboard .box .box-bottom .material-icons {\n      font-size: 40px; }\n  .page-dashboard .box .box-bottom {\n    border-top: 1px solid rgba(0, 0, 0, 0.15); }\n  .page-dashboard .box .box-info {\n    position: absolute;\n    width: 100%;\n    top: 50%;\n    margin-top: -12px;\n    text-align: center; }\n    .page-dashboard .box .box-info span {\n      height: 24px;\n      display: inline-block;\n      padding: 4px 10px;\n      text-transform: uppercase;\n      line-height: 14px;\n      background-color: #fff;\n      border: 1px solid rgba(0, 0, 0, 0.15);\n      font-size: 11px;\n      color: #868e96;\n      border-radius: 1em; }\n\n.theme-gray .page-dashboard .box .box-info span {\n  background-color: #444; }\n\n.theme-dark .page-dashboard .box .box-info span {\n  background-color: #3a4047; }\n\n.vprogressbar-container {\n  height: 250px;\n  margin-top: 25px;\n  border-bottom: rgba(221, 221, 221, 0.3) 2px solid;\n  position: relative; }\n  .vprogressbar-container.brand-info {\n    border-color: #00BCD4; }\n  .vprogressbar-container.brand-success {\n    border-color: #66BB6A; }\n  .vprogressbar-container .vprogressbar {\n    padding-left: 10px; }\n    .vprogressbar-container .vprogressbar li {\n      position: relative;\n      height: 248px;\n      width: 35px;\n      background: rgba(221, 221, 221, 0.3);\n      margin-right: 18px;\n      float: left;\n      list-style: none; }\n  .vprogressbar-container .vprogressbar-percent {\n    display: block;\n    position: absolute;\n    bottom: 0px;\n    left: 0px;\n    width: 100%; }\n  .vprogressbar-container .vprogressbar-legend {\n    position: absolute;\n    top: 0px;\n    right: 0px;\n    padding-left: 0;\n    padding: 5px 10px;\n    text-align: left;\n    border-radius: 0.2rem;\n    background: rgba(255, 255, 255, 0.15); }\n    .vprogressbar-container .vprogressbar-legend li {\n      display: block;\n      font-size: 11px;\n      margin-bottom: 5px;\n      list-style: none; }\n    .vprogressbar-container .vprogressbar-legend .vpointer {\n      height: 10px;\n      width: 10px;\n      display: inline-block;\n      position: relative;\n      top: 1px;\n      margin-right: 5px; }\n  .vprogressbar-container .vprogressbar-info {\n    color: #ccc; }\n\n.theme-gray .box-info .box-icon .material-icons,\n.theme-dark .box-info .box-icon .material-icons {\n  color: rgba(255, 255, 255, 0.54); }\n\n.theme-gray .box-info .box-num,\n.theme-dark .box-info .box-num {\n  color: rgba(255, 255, 255, 0.54); }\n\n.page-maintenance .top-header {\n  padding: 1em 0;\n  border-bottom: 1px solid rgba(0, 0, 0, 0.1);\n  font-size: 32px;\n  line-height: 1; }\n  .page-maintenance .top-header a.logo {\n    text-decoration: none;\n    color: rgba(0, 0, 0, 0.87); }\n\n.page-maintenance .content {\n  max-width: 1140px;\n  margin: 50px auto 0; }\n\n.page-maintenance .main-content {\n  margin-bottom: 80px; }\n  .page-maintenance .main-content h1 {\n    text-transform: uppercase;\n    font-size: 32px;\n    margin-bottom: 15px; }\n  .page-maintenance .main-content p {\n    font-size: 22px; }\n\n.theme-dark .page-maintenance .top-header a.logo,\n.theme-gray .page-maintenance .top-header a.logo {\n  color: #fff; }\n\n.page-about .hero.hero-bg-img {\n  background-size: cover;\n  padding: 0; }\n\n.page-about .hero .hero-inner {\n  background-color: rgba(0, 0, 0, 0.15);\n  padding: 90px 0; }\n\n.page-about .hero .hero-title,\n.page-about .hero .hero-tagline {\n  color: #fff; }\n\n.page-about .stat-container {\n  margin-bottom: 30px; }\n  .page-about .stat-container .stat-item {\n    margin-bottom: 20px;\n    border-bottom: 2px solid #f5f5f5; }\n  .page-about .stat-container .stat-num {\n    display: block;\n    color: #2196F3;\n    font-size: 72px;\n    font-weight: 300;\n    line-height: 66px; }\n  .page-about .stat-container .stat-desc {\n    display: inline-block;\n    margin-bottom: -2px;\n    padding-bottom: 20px;\n    border-bottom: 2px solid #2196F3;\n    font-size: 20px;\n    line-height: 22px;\n    font-weight: bold; }\n\n.page-about .article:nth-of-type(1) {\n  padding-top: 75px; }\n\n.page-about .space-bar {\n  padding: 3px;\n  border-radius: 0.2rem;\n  margin-right: 5px; }\n\n.page-terms h4 {\n  margin-top: 2em;\n  font-size: 16px;\n  font-weight: 500;\n  text-transform: uppercase; }\n\n.blog-item {\n  border-top: 1px solid rgba(0, 0, 0, 0.117647);\n  margin-top: 70px;\n  padding: 70px 0 10px; }\n  .blog-item:first-child {\n    border-top: 0;\n    padding-top: 0;\n    margin-top: 0; }\n  .blog-item h2 {\n    font-size: 35px;\n    line-height: 1; }\n    .blog-item h2 a {\n      color: rgba(0, 0, 0, 0.87);\n      font-weight: 300;\n      text-decoration: none; }\n  .blog-item .blog-info {\n    margin: 10px 0; }\n    .blog-item .blog-info > span {\n      margin-right: 8px; }\n    .blog-item .blog-info .avatar {\n      width: 30px;\n      height: auto;\n      border-radius: 50%; }\n    .blog-item .blog-info .date {\n      opacity: .7; }\n    .blog-item .blog-info .category {\n      display: inline-block;\n      text-transform: uppercase;\n      font-size: 12px;\n      padding: 2px 5px;\n      border-radius: 0.2rem;\n      background-color: rgba(0, 0, 0, 0.08); }\n      .blog-item .blog-info .category a {\n        color: rgba(0, 0, 0, 0.87);\n        text-decoration: none;\n        opacity: .7; }\n  .blog-item .desc {\n    font-size: 16px;\n    opacity: .7; }\n\n.theme-gray .blog-item h2 a,\n.theme-dark .blog-item h2 a {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .blog-item .blog-info .category a,\n.theme-dark .blog-item .blog-info .category a {\n  color: rgba(255, 255, 255, 0.7); }\n\n.spinner-center {\n  margin: 0 auto; }\n\n.spinner-center {\n  margin: 0 auto; }\n", ""]);

// exports


/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/bootstrap.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/*!\n * Bootstrap v4.0.0-beta.2 (https://getbootstrap.com)\n * Copyright 2011-2017 The Bootstrap Authors\n * Copyright 2011-2017 Twitter, Inc.\n * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)\n */\n:root {\n  --blue: #2196F3;\n  --indigo: #6610f2;\n  --purple: #7E57C2;\n  --pink: #e83e8c;\n  --red: #EF5350;\n  --orange: #FFCA28;\n  --yellow: #ffc107;\n  --green: #66BB6A;\n  --teal: #20c997;\n  --cyan: #00BCD4;\n  --white: #fff;\n  --gray: #868e96;\n  --gray-dark: #343a40;\n  --primary: #2196F3;\n  --secondary: #868e96;\n  --success: #66BB6A;\n  --info: #00BCD4;\n  --warning: #ffc107;\n  --danger: #EF5350;\n  --light: #f8f9fa;\n  --dark: #343a40;\n  --breakpoint-xs: 0;\n  --breakpoint-sm: 576px;\n  --breakpoint-md: 768px;\n  --breakpoint-lg: 992px;\n  --breakpoint-xl: 1200px;\n  --font-family-sans-serif: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  --font-family-monospace: \"SFMono-Regular\", Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace; }\n\n@media print {\n  *,\n  *::before,\n  *::after {\n    text-shadow: none !important;\n    box-shadow: none !important; }\n  a,\n  a:visited {\n    text-decoration: underline; }\n  abbr[title]::after {\n    content: \" (\" attr(title) \")\"; }\n  pre {\n    white-space: pre-wrap !important; }\n  pre,\n  blockquote {\n    border: 1px solid #999;\n    page-break-inside: avoid; }\n  thead {\n    display: table-header-group; }\n  tr,\n  img {\n    page-break-inside: avoid; }\n  p,\n  h2,\n  h3 {\n    orphans: 3;\n    widows: 3; }\n  h2,\n  h3 {\n    page-break-after: avoid; }\n  .navbar {\n    display: none; }\n  .badge {\n    border: 1px solid #000; }\n  .table {\n    border-collapse: collapse !important; }\n    .table td,\n    .table th {\n      background-color: #fff !important; }\n  .table-bordered th,\n  .table-bordered td {\n    border: 1px solid #ddd !important; } }\n\n*,\n*::before,\n*::after {\n  box-sizing: border-box; }\n\nhtml {\n  font-family: sans-serif;\n  line-height: 1.15;\n  -webkit-text-size-adjust: 100%;\n  -ms-text-size-adjust: 100%;\n  -ms-overflow-style: scrollbar;\n  -webkit-tap-highlight-color: transparent; }\n\n@-ms-viewport {\n  width: device-width; }\n\narticle, aside, dialog, figcaption, figure, footer, header, hgroup, main, nav, section {\n  display: block; }\n\nbody {\n  margin: 0;\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 1rem;\n  font-weight: 400;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.87);\n  text-align: left;\n  background-color: #e5e5e5; }\n\n[tabindex=\"-1\"]:focus {\n  outline: none !important; }\n\nhr {\n  box-sizing: content-box;\n  height: 0;\n  overflow: visible; }\n\nh1, h2, h3, h4, h5, h6 {\n  margin-top: 0;\n  margin-bottom: 0.5rem; }\n\np {\n  margin-top: 0;\n  margin-bottom: 1rem; }\n\nabbr[title],\nabbr[data-original-title] {\n  text-decoration: underline;\n  text-decoration: underline dotted;\n  cursor: help;\n  border-bottom: 0; }\n\naddress {\n  margin-bottom: 1rem;\n  font-style: normal;\n  line-height: inherit; }\n\nol,\nul,\ndl {\n  margin-top: 0;\n  margin-bottom: 1rem; }\n\nol ol,\nul ul,\nol ul,\nul ol {\n  margin-bottom: 0; }\n\ndt {\n  font-weight: 700; }\n\ndd {\n  margin-bottom: .5rem;\n  margin-left: 0; }\n\nblockquote {\n  margin: 0 0 1rem; }\n\ndfn {\n  font-style: italic; }\n\nb,\nstrong {\n  font-weight: bolder; }\n\nsmall {\n  font-size: 80%; }\n\nsub,\nsup {\n  position: relative;\n  font-size: 75%;\n  line-height: 0;\n  vertical-align: baseline; }\n\nsub {\n  bottom: -.25em; }\n\nsup {\n  top: -.5em; }\n\na {\n  color: #2196F3;\n  text-decoration: none;\n  background-color: transparent;\n  -webkit-text-decoration-skip: objects; }\n  a:hover {\n    color: #0a6ebd;\n    text-decoration: underline; }\n\na:not([href]):not([tabindex]) {\n  color: inherit;\n  text-decoration: none; }\n  a:not([href]):not([tabindex]):focus, a:not([href]):not([tabindex]):hover {\n    color: inherit;\n    text-decoration: none; }\n  a:not([href]):not([tabindex]):focus {\n    outline: 0; }\n\npre,\ncode,\nkbd,\nsamp {\n  font-family: monospace, monospace;\n  font-size: 1em; }\n\npre {\n  margin-top: 0;\n  margin-bottom: 1rem;\n  overflow: auto;\n  -ms-overflow-style: scrollbar; }\n\nfigure {\n  margin: 0 0 1rem; }\n\nimg {\n  vertical-align: middle;\n  border-style: none; }\n\nsvg:not(:root) {\n  overflow: hidden; }\n\na,\narea,\nbutton,\n[role=\"button\"],\ninput:not([type=\"range\"]),\nlabel,\nselect,\nsummary,\ntextarea {\n  -ms-touch-action: manipulation;\n      touch-action: manipulation; }\n\ntable {\n  border-collapse: collapse; }\n\ncaption {\n  padding-top: 0.75rem;\n  padding-bottom: 0.75rem;\n  color: #868e96;\n  text-align: left;\n  caption-side: bottom; }\n\nth {\n  text-align: inherit; }\n\nlabel {\n  display: inline-block;\n  margin-bottom: .5rem; }\n\nbutton {\n  border-radius: 0; }\n\nbutton:focus {\n  outline: 1px dotted;\n  outline: 5px auto -webkit-focus-ring-color; }\n\ninput,\nbutton,\nselect,\noptgroup,\ntextarea {\n  margin: 0;\n  font-family: inherit;\n  font-size: inherit;\n  line-height: inherit; }\n\nbutton,\ninput {\n  overflow: visible; }\n\nbutton,\nselect {\n  text-transform: none; }\n\nbutton,\nhtml [type=\"button\"],\n[type=\"reset\"],\n[type=\"submit\"] {\n  -webkit-appearance: button; }\n\nbutton::-moz-focus-inner,\n[type=\"button\"]::-moz-focus-inner,\n[type=\"reset\"]::-moz-focus-inner,\n[type=\"submit\"]::-moz-focus-inner {\n  padding: 0;\n  border-style: none; }\n\ninput[type=\"radio\"],\ninput[type=\"checkbox\"] {\n  box-sizing: border-box;\n  padding: 0; }\n\ninput[type=\"date\"],\ninput[type=\"time\"],\ninput[type=\"datetime-local\"],\ninput[type=\"month\"] {\n  -webkit-appearance: listbox; }\n\ntextarea {\n  overflow: auto;\n  resize: vertical; }\n\nfieldset {\n  min-width: 0;\n  padding: 0;\n  margin: 0;\n  border: 0; }\n\nlegend {\n  display: block;\n  width: 100%;\n  max-width: 100%;\n  padding: 0;\n  margin-bottom: .5rem;\n  font-size: 1.5rem;\n  line-height: inherit;\n  color: inherit;\n  white-space: normal; }\n\nprogress {\n  vertical-align: baseline; }\n\n[type=\"number\"]::-webkit-inner-spin-button,\n[type=\"number\"]::-webkit-outer-spin-button {\n  height: auto; }\n\n[type=\"search\"] {\n  outline-offset: -2px;\n  -webkit-appearance: none; }\n\n[type=\"search\"]::-webkit-search-cancel-button,\n[type=\"search\"]::-webkit-search-decoration {\n  -webkit-appearance: none; }\n\n::-webkit-file-upload-button {\n  font: inherit;\n  -webkit-appearance: button; }\n\noutput {\n  display: inline-block; }\n\nsummary {\n  display: list-item; }\n\ntemplate {\n  display: none; }\n\n[hidden] {\n  display: none !important; }\n\nh1, h2, h3, h4, h5, h6,\n.h1, .h2, .h3, .h4, .h5, .h6 {\n  margin-bottom: 0.5rem;\n  font-family: inherit;\n  font-weight: 500;\n  line-height: 1.2;\n  color: inherit; }\n\nh1, .h1 {\n  font-size: 2.5rem; }\n\nh2, .h2 {\n  font-size: 2rem; }\n\nh3, .h3 {\n  font-size: 1.75rem; }\n\nh4, .h4 {\n  font-size: 1.5rem; }\n\nh5, .h5 {\n  font-size: 1.25rem; }\n\nh6, .h6 {\n  font-size: 1rem; }\n\n.lead {\n  font-size: 1.25rem;\n  font-weight: 300; }\n\n.display-1 {\n  font-size: 6rem;\n  font-weight: 300;\n  line-height: 1.2; }\n\n.display-2 {\n  font-size: 5.5rem;\n  font-weight: 300;\n  line-height: 1.2; }\n\n.display-3 {\n  font-size: 4.5rem;\n  font-weight: 300;\n  line-height: 1.2; }\n\n.display-4 {\n  font-size: 3.5rem;\n  font-weight: 300;\n  line-height: 1.2; }\n\nhr {\n  margin-top: 1rem;\n  margin-bottom: 1rem;\n  border: 0;\n  border-top: 1px solid rgba(0, 0, 0, 0.1); }\n\nsmall,\n.small {\n  font-size: 80%;\n  font-weight: 400; }\n\nmark,\n.mark {\n  padding: 0.2em;\n  background-color: #fcf8e3; }\n\n.list-unstyled {\n  padding-left: 0;\n  list-style: none; }\n\n.list-inline {\n  padding-left: 0;\n  list-style: none; }\n\n.list-inline-item {\n  display: inline-block; }\n  .list-inline-item:not(:last-child) {\n    margin-right: 5px; }\n\n.initialism {\n  font-size: 90%;\n  text-transform: uppercase; }\n\n.blockquote {\n  margin-bottom: 1rem;\n  font-size: 1.25rem; }\n\n.blockquote-footer {\n  display: block;\n  font-size: 80%;\n  color: #868e96; }\n  .blockquote-footer::before {\n    content: \"\\2014   \\A0\"; }\n\n.img-fluid {\n  max-width: 100%;\n  height: auto; }\n\n.img-thumbnail {\n  padding: 0.25rem;\n  background-color: #e5e5e5;\n  border: 1px solid #ddd;\n  border-radius: 0.2rem;\n  transition: all 0.2s ease-in-out;\n  max-width: 100%;\n  height: auto; }\n\n.figure {\n  display: inline-block; }\n\n.figure-img {\n  margin-bottom: 0.5rem;\n  line-height: 1; }\n\n.figure-caption {\n  font-size: 90%;\n  color: #868e96; }\n\ncode,\nkbd,\npre,\nsamp {\n  font-family: \"SFMono-Regular\", Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace; }\n\ncode {\n  padding: 0.2rem 0.4rem;\n  font-size: 90%;\n  color: #bd4147;\n  background-color: #f8f9fa;\n  border-radius: 0.2rem; }\n  a > code {\n    padding: 0;\n    color: inherit;\n    background-color: inherit; }\n\nkbd {\n  padding: 0.2rem 0.4rem;\n  font-size: 90%;\n  color: #fff;\n  background-color: #212529;\n  border-radius: 0.2rem; }\n  kbd kbd {\n    padding: 0;\n    font-size: 100%;\n    font-weight: 700; }\n\npre {\n  display: block;\n  margin-top: 0;\n  margin-bottom: 1rem;\n  font-size: 90%;\n  color: #212529; }\n  pre code {\n    padding: 0;\n    font-size: inherit;\n    color: inherit;\n    background-color: transparent;\n    border-radius: 0; }\n\n.pre-scrollable {\n  max-height: 340px;\n  overflow-y: scroll; }\n\n.container {\n  width: 100%;\n  padding-right: 15px;\n  padding-left: 15px;\n  margin-right: auto;\n  margin-left: auto; }\n  @media (min-width: 576px) {\n    .container {\n      max-width: 540px; } }\n  @media (min-width: 768px) {\n    .container {\n      max-width: 720px; } }\n  @media (min-width: 992px) {\n    .container {\n      max-width: 960px; } }\n  @media (min-width: 1200px) {\n    .container {\n      max-width: 1140px; } }\n\n.container-fluid {\n  width: 100%;\n  padding-right: 15px;\n  padding-left: 15px;\n  margin-right: auto;\n  margin-left: auto; }\n\n.row {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  margin-right: -15px;\n  margin-left: -15px; }\n\n.no-gutters {\n  margin-right: 0;\n  margin-left: 0; }\n  .no-gutters > .col,\n  .no-gutters > [class*=\"col-\"] {\n    padding-right: 0;\n    padding-left: 0; }\n\n.col-1, .col-2, .col-3, .col-4, .col-5, .col-6, .col-7, .col-8, .col-9, .col-10, .col-11, .col-12, .col,\n.col-auto, .col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm,\n.col-sm-auto, .col-md-1, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-10, .col-md-11, .col-md-12, .col-md,\n.col-md-auto, .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg,\n.col-lg-auto, .col-xl-1, .col-xl-2, .col-xl-3, .col-xl-4, .col-xl-5, .col-xl-6, .col-xl-7, .col-xl-8, .col-xl-9, .col-xl-10, .col-xl-11, .col-xl-12, .col-xl,\n.col-xl-auto {\n  position: relative;\n  width: 100%;\n  min-height: 1px;\n  padding-right: 15px;\n  padding-left: 15px; }\n\n.col {\n  -ms-flex-preferred-size: 0;\n      flex-basis: 0;\n  -webkit-box-flex: 1;\n      -ms-flex-positive: 1;\n          flex-grow: 1;\n  max-width: 100%; }\n\n.col-auto {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 auto;\n          flex: 0 0 auto;\n  width: auto;\n  max-width: none; }\n\n.col-1 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 8.33333333%;\n          flex: 0 0 8.33333333%;\n  max-width: 8.33333333%; }\n\n.col-2 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 16.66666667%;\n          flex: 0 0 16.66666667%;\n  max-width: 16.66666667%; }\n\n.col-3 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 25%;\n          flex: 0 0 25%;\n  max-width: 25%; }\n\n.col-4 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 33.33333333%;\n          flex: 0 0 33.33333333%;\n  max-width: 33.33333333%; }\n\n.col-5 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 41.66666667%;\n          flex: 0 0 41.66666667%;\n  max-width: 41.66666667%; }\n\n.col-6 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 50%;\n          flex: 0 0 50%;\n  max-width: 50%; }\n\n.col-7 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 58.33333333%;\n          flex: 0 0 58.33333333%;\n  max-width: 58.33333333%; }\n\n.col-8 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 66.66666667%;\n          flex: 0 0 66.66666667%;\n  max-width: 66.66666667%; }\n\n.col-9 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 75%;\n          flex: 0 0 75%;\n  max-width: 75%; }\n\n.col-10 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 83.33333333%;\n          flex: 0 0 83.33333333%;\n  max-width: 83.33333333%; }\n\n.col-11 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 91.66666667%;\n          flex: 0 0 91.66666667%;\n  max-width: 91.66666667%; }\n\n.col-12 {\n  -webkit-box-flex: 0;\n      -ms-flex: 0 0 100%;\n          flex: 0 0 100%;\n  max-width: 100%; }\n\n.order-first {\n  -webkit-box-ordinal-group: 0;\n      -ms-flex-order: -1;\n          order: -1; }\n\n.order-1 {\n  -webkit-box-ordinal-group: 2;\n      -ms-flex-order: 1;\n          order: 1; }\n\n.order-2 {\n  -webkit-box-ordinal-group: 3;\n      -ms-flex-order: 2;\n          order: 2; }\n\n.order-3 {\n  -webkit-box-ordinal-group: 4;\n      -ms-flex-order: 3;\n          order: 3; }\n\n.order-4 {\n  -webkit-box-ordinal-group: 5;\n      -ms-flex-order: 4;\n          order: 4; }\n\n.order-5 {\n  -webkit-box-ordinal-group: 6;\n      -ms-flex-order: 5;\n          order: 5; }\n\n.order-6 {\n  -webkit-box-ordinal-group: 7;\n      -ms-flex-order: 6;\n          order: 6; }\n\n.order-7 {\n  -webkit-box-ordinal-group: 8;\n      -ms-flex-order: 7;\n          order: 7; }\n\n.order-8 {\n  -webkit-box-ordinal-group: 9;\n      -ms-flex-order: 8;\n          order: 8; }\n\n.order-9 {\n  -webkit-box-ordinal-group: 10;\n      -ms-flex-order: 9;\n          order: 9; }\n\n.order-10 {\n  -webkit-box-ordinal-group: 11;\n      -ms-flex-order: 10;\n          order: 10; }\n\n.order-11 {\n  -webkit-box-ordinal-group: 12;\n      -ms-flex-order: 11;\n          order: 11; }\n\n.order-12 {\n  -webkit-box-ordinal-group: 13;\n      -ms-flex-order: 12;\n          order: 12; }\n\n.offset-1 {\n  margin-left: 8.33333333%; }\n\n.offset-2 {\n  margin-left: 16.66666667%; }\n\n.offset-3 {\n  margin-left: 25%; }\n\n.offset-4 {\n  margin-left: 33.33333333%; }\n\n.offset-5 {\n  margin-left: 41.66666667%; }\n\n.offset-6 {\n  margin-left: 50%; }\n\n.offset-7 {\n  margin-left: 58.33333333%; }\n\n.offset-8 {\n  margin-left: 66.66666667%; }\n\n.offset-9 {\n  margin-left: 75%; }\n\n.offset-10 {\n  margin-left: 83.33333333%; }\n\n.offset-11 {\n  margin-left: 91.66666667%; }\n\n@media (min-width: 576px) {\n  .col-sm {\n    -ms-flex-preferred-size: 0;\n        flex-basis: 0;\n    -webkit-box-flex: 1;\n        -ms-flex-positive: 1;\n            flex-grow: 1;\n    max-width: 100%; }\n  .col-sm-auto {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 auto;\n            flex: 0 0 auto;\n    width: auto;\n    max-width: none; }\n  .col-sm-1 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 8.33333333%;\n            flex: 0 0 8.33333333%;\n    max-width: 8.33333333%; }\n  .col-sm-2 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 16.66666667%;\n            flex: 0 0 16.66666667%;\n    max-width: 16.66666667%; }\n  .col-sm-3 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 25%;\n            flex: 0 0 25%;\n    max-width: 25%; }\n  .col-sm-4 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 33.33333333%;\n            flex: 0 0 33.33333333%;\n    max-width: 33.33333333%; }\n  .col-sm-5 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 41.66666667%;\n            flex: 0 0 41.66666667%;\n    max-width: 41.66666667%; }\n  .col-sm-6 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 50%;\n            flex: 0 0 50%;\n    max-width: 50%; }\n  .col-sm-7 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 58.33333333%;\n            flex: 0 0 58.33333333%;\n    max-width: 58.33333333%; }\n  .col-sm-8 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 66.66666667%;\n            flex: 0 0 66.66666667%;\n    max-width: 66.66666667%; }\n  .col-sm-9 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 75%;\n            flex: 0 0 75%;\n    max-width: 75%; }\n  .col-sm-10 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 83.33333333%;\n            flex: 0 0 83.33333333%;\n    max-width: 83.33333333%; }\n  .col-sm-11 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 91.66666667%;\n            flex: 0 0 91.66666667%;\n    max-width: 91.66666667%; }\n  .col-sm-12 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 100%;\n            flex: 0 0 100%;\n    max-width: 100%; }\n  .order-sm-first {\n    -webkit-box-ordinal-group: 0;\n        -ms-flex-order: -1;\n            order: -1; }\n  .order-sm-1 {\n    -webkit-box-ordinal-group: 2;\n        -ms-flex-order: 1;\n            order: 1; }\n  .order-sm-2 {\n    -webkit-box-ordinal-group: 3;\n        -ms-flex-order: 2;\n            order: 2; }\n  .order-sm-3 {\n    -webkit-box-ordinal-group: 4;\n        -ms-flex-order: 3;\n            order: 3; }\n  .order-sm-4 {\n    -webkit-box-ordinal-group: 5;\n        -ms-flex-order: 4;\n            order: 4; }\n  .order-sm-5 {\n    -webkit-box-ordinal-group: 6;\n        -ms-flex-order: 5;\n            order: 5; }\n  .order-sm-6 {\n    -webkit-box-ordinal-group: 7;\n        -ms-flex-order: 6;\n            order: 6; }\n  .order-sm-7 {\n    -webkit-box-ordinal-group: 8;\n        -ms-flex-order: 7;\n            order: 7; }\n  .order-sm-8 {\n    -webkit-box-ordinal-group: 9;\n        -ms-flex-order: 8;\n            order: 8; }\n  .order-sm-9 {\n    -webkit-box-ordinal-group: 10;\n        -ms-flex-order: 9;\n            order: 9; }\n  .order-sm-10 {\n    -webkit-box-ordinal-group: 11;\n        -ms-flex-order: 10;\n            order: 10; }\n  .order-sm-11 {\n    -webkit-box-ordinal-group: 12;\n        -ms-flex-order: 11;\n            order: 11; }\n  .order-sm-12 {\n    -webkit-box-ordinal-group: 13;\n        -ms-flex-order: 12;\n            order: 12; }\n  .offset-sm-0 {\n    margin-left: 0; }\n  .offset-sm-1 {\n    margin-left: 8.33333333%; }\n  .offset-sm-2 {\n    margin-left: 16.66666667%; }\n  .offset-sm-3 {\n    margin-left: 25%; }\n  .offset-sm-4 {\n    margin-left: 33.33333333%; }\n  .offset-sm-5 {\n    margin-left: 41.66666667%; }\n  .offset-sm-6 {\n    margin-left: 50%; }\n  .offset-sm-7 {\n    margin-left: 58.33333333%; }\n  .offset-sm-8 {\n    margin-left: 66.66666667%; }\n  .offset-sm-9 {\n    margin-left: 75%; }\n  .offset-sm-10 {\n    margin-left: 83.33333333%; }\n  .offset-sm-11 {\n    margin-left: 91.66666667%; } }\n\n@media (min-width: 768px) {\n  .col-md {\n    -ms-flex-preferred-size: 0;\n        flex-basis: 0;\n    -webkit-box-flex: 1;\n        -ms-flex-positive: 1;\n            flex-grow: 1;\n    max-width: 100%; }\n  .col-md-auto {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 auto;\n            flex: 0 0 auto;\n    width: auto;\n    max-width: none; }\n  .col-md-1 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 8.33333333%;\n            flex: 0 0 8.33333333%;\n    max-width: 8.33333333%; }\n  .col-md-2 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 16.66666667%;\n            flex: 0 0 16.66666667%;\n    max-width: 16.66666667%; }\n  .col-md-3 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 25%;\n            flex: 0 0 25%;\n    max-width: 25%; }\n  .col-md-4 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 33.33333333%;\n            flex: 0 0 33.33333333%;\n    max-width: 33.33333333%; }\n  .col-md-5 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 41.66666667%;\n            flex: 0 0 41.66666667%;\n    max-width: 41.66666667%; }\n  .col-md-6 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 50%;\n            flex: 0 0 50%;\n    max-width: 50%; }\n  .col-md-7 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 58.33333333%;\n            flex: 0 0 58.33333333%;\n    max-width: 58.33333333%; }\n  .col-md-8 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 66.66666667%;\n            flex: 0 0 66.66666667%;\n    max-width: 66.66666667%; }\n  .col-md-9 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 75%;\n            flex: 0 0 75%;\n    max-width: 75%; }\n  .col-md-10 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 83.33333333%;\n            flex: 0 0 83.33333333%;\n    max-width: 83.33333333%; }\n  .col-md-11 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 91.66666667%;\n            flex: 0 0 91.66666667%;\n    max-width: 91.66666667%; }\n  .col-md-12 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 100%;\n            flex: 0 0 100%;\n    max-width: 100%; }\n  .order-md-first {\n    -webkit-box-ordinal-group: 0;\n        -ms-flex-order: -1;\n            order: -1; }\n  .order-md-1 {\n    -webkit-box-ordinal-group: 2;\n        -ms-flex-order: 1;\n            order: 1; }\n  .order-md-2 {\n    -webkit-box-ordinal-group: 3;\n        -ms-flex-order: 2;\n            order: 2; }\n  .order-md-3 {\n    -webkit-box-ordinal-group: 4;\n        -ms-flex-order: 3;\n            order: 3; }\n  .order-md-4 {\n    -webkit-box-ordinal-group: 5;\n        -ms-flex-order: 4;\n            order: 4; }\n  .order-md-5 {\n    -webkit-box-ordinal-group: 6;\n        -ms-flex-order: 5;\n            order: 5; }\n  .order-md-6 {\n    -webkit-box-ordinal-group: 7;\n        -ms-flex-order: 6;\n            order: 6; }\n  .order-md-7 {\n    -webkit-box-ordinal-group: 8;\n        -ms-flex-order: 7;\n            order: 7; }\n  .order-md-8 {\n    -webkit-box-ordinal-group: 9;\n        -ms-flex-order: 8;\n            order: 8; }\n  .order-md-9 {\n    -webkit-box-ordinal-group: 10;\n        -ms-flex-order: 9;\n            order: 9; }\n  .order-md-10 {\n    -webkit-box-ordinal-group: 11;\n        -ms-flex-order: 10;\n            order: 10; }\n  .order-md-11 {\n    -webkit-box-ordinal-group: 12;\n        -ms-flex-order: 11;\n            order: 11; }\n  .order-md-12 {\n    -webkit-box-ordinal-group: 13;\n        -ms-flex-order: 12;\n            order: 12; }\n  .offset-md-0 {\n    margin-left: 0; }\n  .offset-md-1 {\n    margin-left: 8.33333333%; }\n  .offset-md-2 {\n    margin-left: 16.66666667%; }\n  .offset-md-3 {\n    margin-left: 25%; }\n  .offset-md-4 {\n    margin-left: 33.33333333%; }\n  .offset-md-5 {\n    margin-left: 41.66666667%; }\n  .offset-md-6 {\n    margin-left: 50%; }\n  .offset-md-7 {\n    margin-left: 58.33333333%; }\n  .offset-md-8 {\n    margin-left: 66.66666667%; }\n  .offset-md-9 {\n    margin-left: 75%; }\n  .offset-md-10 {\n    margin-left: 83.33333333%; }\n  .offset-md-11 {\n    margin-left: 91.66666667%; } }\n\n@media (min-width: 992px) {\n  .col-lg {\n    -ms-flex-preferred-size: 0;\n        flex-basis: 0;\n    -webkit-box-flex: 1;\n        -ms-flex-positive: 1;\n            flex-grow: 1;\n    max-width: 100%; }\n  .col-lg-auto {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 auto;\n            flex: 0 0 auto;\n    width: auto;\n    max-width: none; }\n  .col-lg-1 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 8.33333333%;\n            flex: 0 0 8.33333333%;\n    max-width: 8.33333333%; }\n  .col-lg-2 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 16.66666667%;\n            flex: 0 0 16.66666667%;\n    max-width: 16.66666667%; }\n  .col-lg-3 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 25%;\n            flex: 0 0 25%;\n    max-width: 25%; }\n  .col-lg-4 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 33.33333333%;\n            flex: 0 0 33.33333333%;\n    max-width: 33.33333333%; }\n  .col-lg-5 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 41.66666667%;\n            flex: 0 0 41.66666667%;\n    max-width: 41.66666667%; }\n  .col-lg-6 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 50%;\n            flex: 0 0 50%;\n    max-width: 50%; }\n  .col-lg-7 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 58.33333333%;\n            flex: 0 0 58.33333333%;\n    max-width: 58.33333333%; }\n  .col-lg-8 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 66.66666667%;\n            flex: 0 0 66.66666667%;\n    max-width: 66.66666667%; }\n  .col-lg-9 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 75%;\n            flex: 0 0 75%;\n    max-width: 75%; }\n  .col-lg-10 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 83.33333333%;\n            flex: 0 0 83.33333333%;\n    max-width: 83.33333333%; }\n  .col-lg-11 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 91.66666667%;\n            flex: 0 0 91.66666667%;\n    max-width: 91.66666667%; }\n  .col-lg-12 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 100%;\n            flex: 0 0 100%;\n    max-width: 100%; }\n  .order-lg-first {\n    -webkit-box-ordinal-group: 0;\n        -ms-flex-order: -1;\n            order: -1; }\n  .order-lg-1 {\n    -webkit-box-ordinal-group: 2;\n        -ms-flex-order: 1;\n            order: 1; }\n  .order-lg-2 {\n    -webkit-box-ordinal-group: 3;\n        -ms-flex-order: 2;\n            order: 2; }\n  .order-lg-3 {\n    -webkit-box-ordinal-group: 4;\n        -ms-flex-order: 3;\n            order: 3; }\n  .order-lg-4 {\n    -webkit-box-ordinal-group: 5;\n        -ms-flex-order: 4;\n            order: 4; }\n  .order-lg-5 {\n    -webkit-box-ordinal-group: 6;\n        -ms-flex-order: 5;\n            order: 5; }\n  .order-lg-6 {\n    -webkit-box-ordinal-group: 7;\n        -ms-flex-order: 6;\n            order: 6; }\n  .order-lg-7 {\n    -webkit-box-ordinal-group: 8;\n        -ms-flex-order: 7;\n            order: 7; }\n  .order-lg-8 {\n    -webkit-box-ordinal-group: 9;\n        -ms-flex-order: 8;\n            order: 8; }\n  .order-lg-9 {\n    -webkit-box-ordinal-group: 10;\n        -ms-flex-order: 9;\n            order: 9; }\n  .order-lg-10 {\n    -webkit-box-ordinal-group: 11;\n        -ms-flex-order: 10;\n            order: 10; }\n  .order-lg-11 {\n    -webkit-box-ordinal-group: 12;\n        -ms-flex-order: 11;\n            order: 11; }\n  .order-lg-12 {\n    -webkit-box-ordinal-group: 13;\n        -ms-flex-order: 12;\n            order: 12; }\n  .offset-lg-0 {\n    margin-left: 0; }\n  .offset-lg-1 {\n    margin-left: 8.33333333%; }\n  .offset-lg-2 {\n    margin-left: 16.66666667%; }\n  .offset-lg-3 {\n    margin-left: 25%; }\n  .offset-lg-4 {\n    margin-left: 33.33333333%; }\n  .offset-lg-5 {\n    margin-left: 41.66666667%; }\n  .offset-lg-6 {\n    margin-left: 50%; }\n  .offset-lg-7 {\n    margin-left: 58.33333333%; }\n  .offset-lg-8 {\n    margin-left: 66.66666667%; }\n  .offset-lg-9 {\n    margin-left: 75%; }\n  .offset-lg-10 {\n    margin-left: 83.33333333%; }\n  .offset-lg-11 {\n    margin-left: 91.66666667%; } }\n\n@media (min-width: 1200px) {\n  .col-xl {\n    -ms-flex-preferred-size: 0;\n        flex-basis: 0;\n    -webkit-box-flex: 1;\n        -ms-flex-positive: 1;\n            flex-grow: 1;\n    max-width: 100%; }\n  .col-xl-auto {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 auto;\n            flex: 0 0 auto;\n    width: auto;\n    max-width: none; }\n  .col-xl-1 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 8.33333333%;\n            flex: 0 0 8.33333333%;\n    max-width: 8.33333333%; }\n  .col-xl-2 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 16.66666667%;\n            flex: 0 0 16.66666667%;\n    max-width: 16.66666667%; }\n  .col-xl-3 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 25%;\n            flex: 0 0 25%;\n    max-width: 25%; }\n  .col-xl-4 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 33.33333333%;\n            flex: 0 0 33.33333333%;\n    max-width: 33.33333333%; }\n  .col-xl-5 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 41.66666667%;\n            flex: 0 0 41.66666667%;\n    max-width: 41.66666667%; }\n  .col-xl-6 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 50%;\n            flex: 0 0 50%;\n    max-width: 50%; }\n  .col-xl-7 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 58.33333333%;\n            flex: 0 0 58.33333333%;\n    max-width: 58.33333333%; }\n  .col-xl-8 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 66.66666667%;\n            flex: 0 0 66.66666667%;\n    max-width: 66.66666667%; }\n  .col-xl-9 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 75%;\n            flex: 0 0 75%;\n    max-width: 75%; }\n  .col-xl-10 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 83.33333333%;\n            flex: 0 0 83.33333333%;\n    max-width: 83.33333333%; }\n  .col-xl-11 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 91.66666667%;\n            flex: 0 0 91.66666667%;\n    max-width: 91.66666667%; }\n  .col-xl-12 {\n    -webkit-box-flex: 0;\n        -ms-flex: 0 0 100%;\n            flex: 0 0 100%;\n    max-width: 100%; }\n  .order-xl-first {\n    -webkit-box-ordinal-group: 0;\n        -ms-flex-order: -1;\n            order: -1; }\n  .order-xl-1 {\n    -webkit-box-ordinal-group: 2;\n        -ms-flex-order: 1;\n            order: 1; }\n  .order-xl-2 {\n    -webkit-box-ordinal-group: 3;\n        -ms-flex-order: 2;\n            order: 2; }\n  .order-xl-3 {\n    -webkit-box-ordinal-group: 4;\n        -ms-flex-order: 3;\n            order: 3; }\n  .order-xl-4 {\n    -webkit-box-ordinal-group: 5;\n        -ms-flex-order: 4;\n            order: 4; }\n  .order-xl-5 {\n    -webkit-box-ordinal-group: 6;\n        -ms-flex-order: 5;\n            order: 5; }\n  .order-xl-6 {\n    -webkit-box-ordinal-group: 7;\n        -ms-flex-order: 6;\n            order: 6; }\n  .order-xl-7 {\n    -webkit-box-ordinal-group: 8;\n        -ms-flex-order: 7;\n            order: 7; }\n  .order-xl-8 {\n    -webkit-box-ordinal-group: 9;\n        -ms-flex-order: 8;\n            order: 8; }\n  .order-xl-9 {\n    -webkit-box-ordinal-group: 10;\n        -ms-flex-order: 9;\n            order: 9; }\n  .order-xl-10 {\n    -webkit-box-ordinal-group: 11;\n        -ms-flex-order: 10;\n            order: 10; }\n  .order-xl-11 {\n    -webkit-box-ordinal-group: 12;\n        -ms-flex-order: 11;\n            order: 11; }\n  .order-xl-12 {\n    -webkit-box-ordinal-group: 13;\n        -ms-flex-order: 12;\n            order: 12; }\n  .offset-xl-0 {\n    margin-left: 0; }\n  .offset-xl-1 {\n    margin-left: 8.33333333%; }\n  .offset-xl-2 {\n    margin-left: 16.66666667%; }\n  .offset-xl-3 {\n    margin-left: 25%; }\n  .offset-xl-4 {\n    margin-left: 33.33333333%; }\n  .offset-xl-5 {\n    margin-left: 41.66666667%; }\n  .offset-xl-6 {\n    margin-left: 50%; }\n  .offset-xl-7 {\n    margin-left: 58.33333333%; }\n  .offset-xl-8 {\n    margin-left: 66.66666667%; }\n  .offset-xl-9 {\n    margin-left: 75%; }\n  .offset-xl-10 {\n    margin-left: 83.33333333%; }\n  .offset-xl-11 {\n    margin-left: 91.66666667%; } }\n\n.table {\n  width: 100%;\n  max-width: 100%;\n  margin-bottom: 1rem;\n  background-color: transparent; }\n  .table th,\n  .table td {\n    padding: 0.75rem;\n    vertical-align: top;\n    border-top: 1px solid rgba(0, 0, 0, 0.1); }\n  .table thead th {\n    vertical-align: bottom;\n    border-bottom: 2px solid rgba(0, 0, 0, 0.1); }\n  .table tbody + tbody {\n    border-top: 2px solid rgba(0, 0, 0, 0.1); }\n  .table .table {\n    background-color: #e5e5e5; }\n\n.table-sm th,\n.table-sm td {\n  padding: 0.3rem; }\n\n.table-bordered {\n  border: 1px solid rgba(0, 0, 0, 0.1); }\n  .table-bordered th,\n  .table-bordered td {\n    border: 1px solid rgba(0, 0, 0, 0.1); }\n  .table-bordered thead th,\n  .table-bordered thead td {\n    border-bottom-width: 2px; }\n\n.table-striped tbody tr:nth-of-type(odd) {\n  background-color: rgba(0, 0, 0, 0.05); }\n\n.table-hover tbody tr:hover {\n  background-color: rgba(0, 0, 0, 0.075); }\n\n.table-primary,\n.table-primary > th,\n.table-primary > td {\n  background-color: #c1e2fc; }\n\n.table-hover .table-primary:hover {\n  background-color: #a9d7fb; }\n  .table-hover .table-primary:hover > td,\n  .table-hover .table-primary:hover > th {\n    background-color: #a9d7fb; }\n\n.table-secondary,\n.table-secondary > th,\n.table-secondary > td {\n  background-color: #dddfe2; }\n\n.table-hover .table-secondary:hover {\n  background-color: #cfd2d6; }\n  .table-hover .table-secondary:hover > td,\n  .table-hover .table-secondary:hover > th {\n    background-color: #cfd2d6; }\n\n.table-success,\n.table-success > th,\n.table-success > td {\n  background-color: #d4ecd5; }\n\n.table-hover .table-success:hover {\n  background-color: #c2e4c4; }\n  .table-hover .table-success:hover > td,\n  .table-hover .table-success:hover > th {\n    background-color: #c2e4c4; }\n\n.table-info,\n.table-info > th,\n.table-info > td {\n  background-color: #b8ecf3; }\n\n.table-hover .table-info:hover {\n  background-color: #a2e6ef; }\n  .table-hover .table-info:hover > td,\n  .table-hover .table-info:hover > th {\n    background-color: #a2e6ef; }\n\n.table-warning,\n.table-warning > th,\n.table-warning > td {\n  background-color: #ffeeba; }\n\n.table-hover .table-warning:hover {\n  background-color: #ffe8a1; }\n  .table-hover .table-warning:hover > td,\n  .table-hover .table-warning:hover > th {\n    background-color: #ffe8a1; }\n\n.table-danger,\n.table-danger > th,\n.table-danger > td {\n  background-color: #fbcfce; }\n\n.table-hover .table-danger:hover {\n  background-color: #f9b8b6; }\n  .table-hover .table-danger:hover > td,\n  .table-hover .table-danger:hover > th {\n    background-color: #f9b8b6; }\n\n.table-light,\n.table-light > th,\n.table-light > td {\n  background-color: #fdfdfe; }\n\n.table-hover .table-light:hover {\n  background-color: #ececf6; }\n  .table-hover .table-light:hover > td,\n  .table-hover .table-light:hover > th {\n    background-color: #ececf6; }\n\n.table-dark,\n.table-dark > th,\n.table-dark > td {\n  background-color: #c6c8ca; }\n\n.table-hover .table-dark:hover {\n  background-color: #b9bbbe; }\n  .table-hover .table-dark:hover > td,\n  .table-hover .table-dark:hover > th {\n    background-color: #b9bbbe; }\n\n.table-active,\n.table-active > th,\n.table-active > td {\n  background-color: rgba(0, 0, 0, 0.075); }\n\n.table-hover .table-active:hover {\n  background-color: rgba(0, 0, 0, 0.075); }\n  .table-hover .table-active:hover > td,\n  .table-hover .table-active:hover > th {\n    background-color: rgba(0, 0, 0, 0.075); }\n\n.table .thead-dark th {\n  color: #e5e5e5;\n  background-color: #212529;\n  border-color: #32383e; }\n\n.table .thead-light th {\n  color: #495057;\n  background-color: #e9ecef;\n  border-color: rgba(0, 0, 0, 0.1); }\n\n.table-dark {\n  color: #e5e5e5;\n  background-color: #212529; }\n  .table-dark th,\n  .table-dark td,\n  .table-dark thead th {\n    border-color: #32383e; }\n  .table-dark.table-bordered {\n    border: 0; }\n  .table-dark.table-striped tbody tr:nth-of-type(odd) {\n    background-color: rgba(255, 255, 255, 0.05); }\n  .table-dark.table-hover tbody tr:hover {\n    background-color: rgba(255, 255, 255, 0.075); }\n\n@media (max-width: 575px) {\n  .table-responsive-sm {\n    display: block;\n    width: 100%;\n    overflow-x: auto;\n    -webkit-overflow-scrolling: touch;\n    -ms-overflow-style: -ms-autohiding-scrollbar; }\n    .table-responsive-sm.table-bordered {\n      border: 0; } }\n\n@media (max-width: 767px) {\n  .table-responsive-md {\n    display: block;\n    width: 100%;\n    overflow-x: auto;\n    -webkit-overflow-scrolling: touch;\n    -ms-overflow-style: -ms-autohiding-scrollbar; }\n    .table-responsive-md.table-bordered {\n      border: 0; } }\n\n@media (max-width: 991px) {\n  .table-responsive-lg {\n    display: block;\n    width: 100%;\n    overflow-x: auto;\n    -webkit-overflow-scrolling: touch;\n    -ms-overflow-style: -ms-autohiding-scrollbar; }\n    .table-responsive-lg.table-bordered {\n      border: 0; } }\n\n@media (max-width: 1199px) {\n  .table-responsive-xl {\n    display: block;\n    width: 100%;\n    overflow-x: auto;\n    -webkit-overflow-scrolling: touch;\n    -ms-overflow-style: -ms-autohiding-scrollbar; }\n    .table-responsive-xl.table-bordered {\n      border: 0; } }\n\n.table-responsive {\n  display: block;\n  width: 100%;\n  overflow-x: auto;\n  -webkit-overflow-scrolling: touch;\n  -ms-overflow-style: -ms-autohiding-scrollbar; }\n  .table-responsive.table-bordered {\n    border: 0; }\n\n.form-control {\n  display: block;\n  width: 100%;\n  padding: 0.75rem 1.5rem;\n  font-size: 1rem;\n  line-height: 1.5;\n  color: #495057;\n  background-color: #fff;\n  background-image: none;\n  background-clip: padding-box;\n  border: 1px solid #ced4da;\n  border-radius: 0.2rem;\n  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s; }\n  .form-control::-ms-expand {\n    background-color: transparent;\n    border: 0; }\n  .form-control:focus {\n    color: #495057;\n    background-color: #fff;\n    border-color: #9acffa;\n    outline: none;\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.25); }\n  .form-control::-webkit-input-placeholder {\n    color: #868e96;\n    opacity: 1; }\n  .form-control:-ms-input-placeholder {\n    color: #868e96;\n    opacity: 1; }\n  .form-control::placeholder {\n    color: #868e96;\n    opacity: 1; }\n  .form-control:disabled, .form-control[readonly] {\n    background-color: #e9ecef;\n    opacity: 1; }\n\nselect.form-control:not([size]):not([multiple]) {\n  height: calc(3rem + 2px); }\n\nselect.form-control:focus::-ms-value {\n  color: #495057;\n  background-color: #fff; }\n\n.form-control-file,\n.form-control-range {\n  display: block; }\n\n.col-form-label {\n  padding-top: calc(0.75rem + 1px);\n  padding-bottom: calc(0.75rem + 1px);\n  margin-bottom: 0;\n  line-height: 1.5; }\n\n.col-form-label-lg {\n  padding-top: calc(0.5rem + 1px);\n  padding-bottom: calc(0.5rem + 1px);\n  font-size: 1.25rem;\n  line-height: 1.5; }\n\n.col-form-label-sm {\n  padding-top: calc(0.25rem + 1px);\n  padding-bottom: calc(0.25rem + 1px);\n  font-size: 0.875rem;\n  line-height: 1.5; }\n\n.col-form-legend {\n  padding-top: 0.75rem;\n  padding-bottom: 0.75rem;\n  margin-bottom: 0;\n  font-size: 1rem; }\n\n.form-control-plaintext {\n  padding-top: 0.75rem;\n  padding-bottom: 0.75rem;\n  margin-bottom: 0;\n  line-height: 1.5;\n  background-color: transparent;\n  border: solid transparent;\n  border-width: 1px 0; }\n  .form-control-plaintext.form-control-sm, .input-group-sm > .form-control-plaintext.form-control,\n  .input-group-sm > .form-control-plaintext.input-group-addon,\n  .input-group-sm > .input-group-btn > .form-control-plaintext.btn, .form-control-plaintext.form-control-lg, .input-group-lg > .form-control-plaintext.form-control,\n  .input-group-lg > .form-control-plaintext.input-group-addon,\n  .input-group-lg > .input-group-btn > .form-control-plaintext.btn {\n    padding-right: 0;\n    padding-left: 0; }\n\n.form-control-sm, .input-group-sm > .form-control,\n.input-group-sm > .input-group-addon,\n.input-group-sm > .input-group-btn > .btn {\n  padding: 0.25rem 0.5rem;\n  font-size: 0.875rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\nselect.form-control-sm:not([size]):not([multiple]), .input-group-sm > select.form-control:not([size]):not([multiple]),\n.input-group-sm > select.input-group-addon:not([size]):not([multiple]),\n.input-group-sm > .input-group-btn > select.btn:not([size]):not([multiple]) {\n  height: calc(1.8125rem + 2px); }\n\n.form-control-lg, .input-group-lg > .form-control,\n.input-group-lg > .input-group-addon,\n.input-group-lg > .input-group-btn > .btn {\n  padding: 0.5rem 1rem;\n  font-size: 1.25rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\nselect.form-control-lg:not([size]):not([multiple]), .input-group-lg > select.form-control:not([size]):not([multiple]),\n.input-group-lg > select.input-group-addon:not([size]):not([multiple]),\n.input-group-lg > .input-group-btn > select.btn:not([size]):not([multiple]) {\n  height: calc(2.875rem + 2px); }\n\n.form-group {\n  margin-bottom: 1rem; }\n\n.form-text {\n  display: block;\n  margin-top: 0.25rem; }\n\n.form-row {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  margin-right: -5px;\n  margin-left: -5px; }\n  .form-row > .col,\n  .form-row > [class*=\"col-\"] {\n    padding-right: 5px;\n    padding-left: 5px; }\n\n.form-check {\n  position: relative;\n  display: block;\n  margin-bottom: 0.5rem; }\n  .form-check.disabled .form-check-label {\n    color: #868e96; }\n\n.form-check-label {\n  padding-left: 1.25rem;\n  margin-bottom: 0; }\n\n.form-check-input {\n  position: absolute;\n  margin-top: 0.25rem;\n  margin-left: -1.25rem; }\n\n.form-check-inline {\n  display: inline-block;\n  margin-right: 0.75rem; }\n  .form-check-inline .form-check-label {\n    vertical-align: middle; }\n\n.valid-feedback {\n  display: none;\n  margin-top: .25rem;\n  font-size: .875rem;\n  color: #66BB6A; }\n\n.valid-tooltip {\n  position: absolute;\n  top: 100%;\n  z-index: 5;\n  display: none;\n  width: 250px;\n  padding: .5rem;\n  margin-top: .1rem;\n  font-size: .875rem;\n  line-height: 1;\n  color: #fff;\n  background-color: rgba(102, 187, 106, 0.8);\n  border-radius: .2rem; }\n\n.was-validated .form-control:valid, .form-control.is-valid, .was-validated\n.custom-select:valid,\n.custom-select.is-valid {\n  border-color: #66BB6A; }\n  .was-validated .form-control:valid:focus, .form-control.is-valid:focus, .was-validated\n  .custom-select:valid:focus,\n  .custom-select.is-valid:focus {\n    box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.25); }\n  .was-validated .form-control:valid ~ .valid-feedback,\n  .was-validated .form-control:valid ~ .valid-tooltip, .form-control.is-valid ~ .valid-feedback,\n  .form-control.is-valid ~ .valid-tooltip, .was-validated\n  .custom-select:valid ~ .valid-feedback,\n  .was-validated\n  .custom-select:valid ~ .valid-tooltip,\n  .custom-select.is-valid ~ .valid-feedback,\n  .custom-select.is-valid ~ .valid-tooltip {\n    display: block; }\n\n.was-validated .form-check-input:valid + .form-check-label, .form-check-input.is-valid + .form-check-label {\n  color: #66BB6A; }\n\n.was-validated .custom-control-input:valid ~ .custom-control-indicator, .custom-control-input.is-valid ~ .custom-control-indicator {\n  background-color: rgba(102, 187, 106, 0.25); }\n\n.was-validated .custom-control-input:valid ~ .custom-control-description, .custom-control-input.is-valid ~ .custom-control-description {\n  color: #66BB6A; }\n\n.was-validated .custom-file-input:valid ~ .custom-file-control, .custom-file-input.is-valid ~ .custom-file-control {\n  border-color: #66BB6A; }\n  .was-validated .custom-file-input:valid ~ .custom-file-control::before, .custom-file-input.is-valid ~ .custom-file-control::before {\n    border-color: inherit; }\n\n.was-validated .custom-file-input:valid:focus, .custom-file-input.is-valid:focus {\n  box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.25); }\n\n.invalid-feedback {\n  display: none;\n  margin-top: .25rem;\n  font-size: .875rem;\n  color: #EF5350; }\n\n.invalid-tooltip {\n  position: absolute;\n  top: 100%;\n  z-index: 5;\n  display: none;\n  width: 250px;\n  padding: .5rem;\n  margin-top: .1rem;\n  font-size: .875rem;\n  line-height: 1;\n  color: #fff;\n  background-color: rgba(239, 83, 80, 0.8);\n  border-radius: .2rem; }\n\n.was-validated .form-control:invalid, .form-control.is-invalid, .was-validated\n.custom-select:invalid,\n.custom-select.is-invalid {\n  border-color: #EF5350; }\n  .was-validated .form-control:invalid:focus, .form-control.is-invalid:focus, .was-validated\n  .custom-select:invalid:focus,\n  .custom-select.is-invalid:focus {\n    box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.25); }\n  .was-validated .form-control:invalid ~ .invalid-feedback,\n  .was-validated .form-control:invalid ~ .invalid-tooltip, .form-control.is-invalid ~ .invalid-feedback,\n  .form-control.is-invalid ~ .invalid-tooltip, .was-validated\n  .custom-select:invalid ~ .invalid-feedback,\n  .was-validated\n  .custom-select:invalid ~ .invalid-tooltip,\n  .custom-select.is-invalid ~ .invalid-feedback,\n  .custom-select.is-invalid ~ .invalid-tooltip {\n    display: block; }\n\n.was-validated .form-check-input:invalid + .form-check-label, .form-check-input.is-invalid + .form-check-label {\n  color: #EF5350; }\n\n.was-validated .custom-control-input:invalid ~ .custom-control-indicator, .custom-control-input.is-invalid ~ .custom-control-indicator {\n  background-color: rgba(239, 83, 80, 0.25); }\n\n.was-validated .custom-control-input:invalid ~ .custom-control-description, .custom-control-input.is-invalid ~ .custom-control-description {\n  color: #EF5350; }\n\n.was-validated .custom-file-input:invalid ~ .custom-file-control, .custom-file-input.is-invalid ~ .custom-file-control {\n  border-color: #EF5350; }\n  .was-validated .custom-file-input:invalid ~ .custom-file-control::before, .custom-file-input.is-invalid ~ .custom-file-control::before {\n    border-color: inherit; }\n\n.was-validated .custom-file-input:invalid:focus, .custom-file-input.is-invalid:focus {\n  box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.25); }\n\n.form-inline {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: horizontal;\n  -webkit-box-direction: normal;\n      -ms-flex-flow: row wrap;\n          flex-flow: row wrap;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center; }\n  .form-inline .form-check {\n    width: 100%; }\n  @media (min-width: 576px) {\n    .form-inline label {\n      display: -webkit-box;\n      display: -ms-flexbox;\n      display: flex;\n      -webkit-box-align: center;\n          -ms-flex-align: center;\n              align-items: center;\n      -webkit-box-pack: center;\n          -ms-flex-pack: center;\n              justify-content: center;\n      margin-bottom: 0; }\n    .form-inline .form-group {\n      display: -webkit-box;\n      display: -ms-flexbox;\n      display: flex;\n      -webkit-box-flex: 0;\n          -ms-flex: 0 0 auto;\n              flex: 0 0 auto;\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-flow: row wrap;\n              flex-flow: row wrap;\n      -webkit-box-align: center;\n          -ms-flex-align: center;\n              align-items: center;\n      margin-bottom: 0; }\n    .form-inline .form-control {\n      display: inline-block;\n      width: auto;\n      vertical-align: middle; }\n    .form-inline .form-control-plaintext {\n      display: inline-block; }\n    .form-inline .input-group {\n      width: auto; }\n    .form-inline .form-check {\n      display: -webkit-box;\n      display: -ms-flexbox;\n      display: flex;\n      -webkit-box-align: center;\n          -ms-flex-align: center;\n              align-items: center;\n      -webkit-box-pack: center;\n          -ms-flex-pack: center;\n              justify-content: center;\n      width: auto;\n      margin-top: 0;\n      margin-bottom: 0; }\n    .form-inline .form-check-label {\n      padding-left: 0; }\n    .form-inline .form-check-input {\n      position: relative;\n      margin-top: 0;\n      margin-right: 0.25rem;\n      margin-left: 0; }\n    .form-inline .custom-control {\n      display: -webkit-box;\n      display: -ms-flexbox;\n      display: flex;\n      -webkit-box-align: center;\n          -ms-flex-align: center;\n              align-items: center;\n      -webkit-box-pack: center;\n          -ms-flex-pack: center;\n              justify-content: center;\n      padding-left: 0; }\n    .form-inline .custom-control-indicator {\n      position: static;\n      display: inline-block;\n      margin-right: 0.25rem;\n      vertical-align: text-bottom; }\n    .form-inline .has-feedback .form-control-feedback {\n      top: 0; } }\n\n.btn {\n  display: inline-block;\n  font-weight: 400;\n  text-align: center;\n  white-space: nowrap;\n  vertical-align: middle;\n  -webkit-user-select: none;\n     -moz-user-select: none;\n      -ms-user-select: none;\n          user-select: none;\n  border: 1px solid transparent;\n  padding: 0.75rem 1.5rem;\n  font-size: 1rem;\n  line-height: 1.5;\n  border-radius: 0.2rem;\n  transition: background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out; }\n  .btn:focus, .btn:hover {\n    text-decoration: none; }\n  .btn:focus, .btn.focus {\n    outline: 0;\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.25); }\n  .btn.disabled, .btn:disabled {\n    opacity: .65; }\n  .btn:not([disabled]):not(.disabled):active, .btn:not([disabled]):not(.disabled).active {\n    background-image: none; }\n\na.btn.disabled,\nfieldset[disabled] a.btn {\n  pointer-events: none; }\n\n.btn-primary {\n  color: #fff;\n  background-color: #2196F3;\n  border-color: #2196F3; }\n  .btn-primary:hover {\n    color: #fff;\n    background-color: #0c83e2;\n    border-color: #0c7cd5; }\n  .btn-primary:focus, .btn-primary.focus {\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.5); }\n  .btn-primary.disabled, .btn-primary:disabled {\n    background-color: #2196F3;\n    border-color: #2196F3; }\n  .btn-primary:not([disabled]):not(.disabled):active, .btn-primary:not([disabled]):not(.disabled).active,\n  .show > .btn-primary.dropdown-toggle {\n    color: #fff;\n    background-color: #0c7cd5;\n    border-color: #0b75c9;\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.5); }\n\n.btn-secondary {\n  color: #fff;\n  background-color: #868e96;\n  border-color: #868e96; }\n  .btn-secondary:hover {\n    color: #fff;\n    background-color: #727b84;\n    border-color: #6c757d; }\n  .btn-secondary:focus, .btn-secondary.focus {\n    box-shadow: 0 0 0 0.2rem rgba(134, 142, 150, 0.5); }\n  .btn-secondary.disabled, .btn-secondary:disabled {\n    background-color: #868e96;\n    border-color: #868e96; }\n  .btn-secondary:not([disabled]):not(.disabled):active, .btn-secondary:not([disabled]):not(.disabled).active,\n  .show > .btn-secondary.dropdown-toggle {\n    color: #fff;\n    background-color: #6c757d;\n    border-color: #666e76;\n    box-shadow: 0 0 0 0.2rem rgba(134, 142, 150, 0.5); }\n\n.btn-success {\n  color: #111;\n  background-color: #66BB6A;\n  border-color: #66BB6A; }\n  .btn-success:hover {\n    color: #fff;\n    background-color: #4dae52;\n    border-color: #49a54e; }\n  .btn-success:focus, .btn-success.focus {\n    box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.5); }\n  .btn-success.disabled, .btn-success:disabled {\n    background-color: #66BB6A;\n    border-color: #66BB6A; }\n  .btn-success:not([disabled]):not(.disabled):active, .btn-success:not([disabled]):not(.disabled).active,\n  .show > .btn-success.dropdown-toggle {\n    color: #fff;\n    background-color: #49a54e;\n    border-color: #459c49;\n    box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.5); }\n\n.btn-info {\n  color: #fff;\n  background-color: #00BCD4;\n  border-color: #00BCD4; }\n  .btn-info:hover {\n    color: #fff;\n    background-color: #009aae;\n    border-color: #008fa1; }\n  .btn-info:focus, .btn-info.focus {\n    box-shadow: 0 0 0 0.2rem rgba(0, 188, 212, 0.5); }\n  .btn-info.disabled, .btn-info:disabled {\n    background-color: #00BCD4;\n    border-color: #00BCD4; }\n  .btn-info:not([disabled]):not(.disabled):active, .btn-info:not([disabled]):not(.disabled).active,\n  .show > .btn-info.dropdown-toggle {\n    color: #fff;\n    background-color: #008fa1;\n    border-color: #008394;\n    box-shadow: 0 0 0 0.2rem rgba(0, 188, 212, 0.5); }\n\n.btn-warning {\n  color: #111;\n  background-color: #ffc107;\n  border-color: #ffc107; }\n  .btn-warning:hover {\n    color: #111;\n    background-color: #e0a800;\n    border-color: #d39e00; }\n  .btn-warning:focus, .btn-warning.focus {\n    box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.5); }\n  .btn-warning.disabled, .btn-warning:disabled {\n    background-color: #ffc107;\n    border-color: #ffc107; }\n  .btn-warning:not([disabled]):not(.disabled):active, .btn-warning:not([disabled]):not(.disabled).active,\n  .show > .btn-warning.dropdown-toggle {\n    color: #111;\n    background-color: #d39e00;\n    border-color: #c69500;\n    box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.5); }\n\n.btn-danger {\n  color: #fff;\n  background-color: #EF5350;\n  border-color: #EF5350; }\n  .btn-danger:hover {\n    color: #fff;\n    background-color: #ec312d;\n    border-color: #eb2521; }\n  .btn-danger:focus, .btn-danger.focus {\n    box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.5); }\n  .btn-danger.disabled, .btn-danger:disabled {\n    background-color: #EF5350;\n    border-color: #EF5350; }\n  .btn-danger:not([disabled]):not(.disabled):active, .btn-danger:not([disabled]):not(.disabled).active,\n  .show > .btn-danger.dropdown-toggle {\n    color: #fff;\n    background-color: #eb2521;\n    border-color: #ea1a16;\n    box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.5); }\n\n.btn-light {\n  color: #111;\n  background-color: #f8f9fa;\n  border-color: #f8f9fa; }\n  .btn-light:hover {\n    color: #111;\n    background-color: #e2e6ea;\n    border-color: #dae0e5; }\n  .btn-light:focus, .btn-light.focus {\n    box-shadow: 0 0 0 0.2rem rgba(248, 249, 250, 0.5); }\n  .btn-light.disabled, .btn-light:disabled {\n    background-color: #f8f9fa;\n    border-color: #f8f9fa; }\n  .btn-light:not([disabled]):not(.disabled):active, .btn-light:not([disabled]):not(.disabled).active,\n  .show > .btn-light.dropdown-toggle {\n    color: #111;\n    background-color: #dae0e5;\n    border-color: #d3d9df;\n    box-shadow: 0 0 0 0.2rem rgba(248, 249, 250, 0.5); }\n\n.btn-dark {\n  color: #fff;\n  background-color: #343a40;\n  border-color: #343a40; }\n  .btn-dark:hover {\n    color: #fff;\n    background-color: #23272b;\n    border-color: #1d2124; }\n  .btn-dark:focus, .btn-dark.focus {\n    box-shadow: 0 0 0 0.2rem rgba(52, 58, 64, 0.5); }\n  .btn-dark.disabled, .btn-dark:disabled {\n    background-color: #343a40;\n    border-color: #343a40; }\n  .btn-dark:not([disabled]):not(.disabled):active, .btn-dark:not([disabled]):not(.disabled).active,\n  .show > .btn-dark.dropdown-toggle {\n    color: #fff;\n    background-color: #1d2124;\n    border-color: #171a1d;\n    box-shadow: 0 0 0 0.2rem rgba(52, 58, 64, 0.5); }\n\n.btn-outline-primary {\n  color: #2196F3;\n  background-color: transparent;\n  background-image: none;\n  border-color: #2196F3; }\n  .btn-outline-primary:hover {\n    color: #fff;\n    background-color: #2196F3;\n    border-color: #2196F3; }\n  .btn-outline-primary:focus, .btn-outline-primary.focus {\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.5); }\n  .btn-outline-primary.disabled, .btn-outline-primary:disabled {\n    color: #2196F3;\n    background-color: transparent; }\n  .btn-outline-primary:not([disabled]):not(.disabled):active, .btn-outline-primary:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-primary.dropdown-toggle {\n    color: #fff;\n    background-color: #2196F3;\n    border-color: #2196F3;\n    box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.5); }\n\n.btn-outline-secondary {\n  color: #868e96;\n  background-color: transparent;\n  background-image: none;\n  border-color: #868e96; }\n  .btn-outline-secondary:hover {\n    color: #fff;\n    background-color: #868e96;\n    border-color: #868e96; }\n  .btn-outline-secondary:focus, .btn-outline-secondary.focus {\n    box-shadow: 0 0 0 0.2rem rgba(134, 142, 150, 0.5); }\n  .btn-outline-secondary.disabled, .btn-outline-secondary:disabled {\n    color: #868e96;\n    background-color: transparent; }\n  .btn-outline-secondary:not([disabled]):not(.disabled):active, .btn-outline-secondary:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-secondary.dropdown-toggle {\n    color: #fff;\n    background-color: #868e96;\n    border-color: #868e96;\n    box-shadow: 0 0 0 0.2rem rgba(134, 142, 150, 0.5); }\n\n.btn-outline-success {\n  color: #66BB6A;\n  background-color: transparent;\n  background-image: none;\n  border-color: #66BB6A; }\n  .btn-outline-success:hover {\n    color: #fff;\n    background-color: #66BB6A;\n    border-color: #66BB6A; }\n  .btn-outline-success:focus, .btn-outline-success.focus {\n    box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.5); }\n  .btn-outline-success.disabled, .btn-outline-success:disabled {\n    color: #66BB6A;\n    background-color: transparent; }\n  .btn-outline-success:not([disabled]):not(.disabled):active, .btn-outline-success:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-success.dropdown-toggle {\n    color: #fff;\n    background-color: #66BB6A;\n    border-color: #66BB6A;\n    box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.5); }\n\n.btn-outline-info {\n  color: #00BCD4;\n  background-color: transparent;\n  background-image: none;\n  border-color: #00BCD4; }\n  .btn-outline-info:hover {\n    color: #fff;\n    background-color: #00BCD4;\n    border-color: #00BCD4; }\n  .btn-outline-info:focus, .btn-outline-info.focus {\n    box-shadow: 0 0 0 0.2rem rgba(0, 188, 212, 0.5); }\n  .btn-outline-info.disabled, .btn-outline-info:disabled {\n    color: #00BCD4;\n    background-color: transparent; }\n  .btn-outline-info:not([disabled]):not(.disabled):active, .btn-outline-info:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-info.dropdown-toggle {\n    color: #fff;\n    background-color: #00BCD4;\n    border-color: #00BCD4;\n    box-shadow: 0 0 0 0.2rem rgba(0, 188, 212, 0.5); }\n\n.btn-outline-warning {\n  color: #ffc107;\n  background-color: transparent;\n  background-image: none;\n  border-color: #ffc107; }\n  .btn-outline-warning:hover {\n    color: #fff;\n    background-color: #ffc107;\n    border-color: #ffc107; }\n  .btn-outline-warning:focus, .btn-outline-warning.focus {\n    box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.5); }\n  .btn-outline-warning.disabled, .btn-outline-warning:disabled {\n    color: #ffc107;\n    background-color: transparent; }\n  .btn-outline-warning:not([disabled]):not(.disabled):active, .btn-outline-warning:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-warning.dropdown-toggle {\n    color: #fff;\n    background-color: #ffc107;\n    border-color: #ffc107;\n    box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.5); }\n\n.btn-outline-danger {\n  color: #EF5350;\n  background-color: transparent;\n  background-image: none;\n  border-color: #EF5350; }\n  .btn-outline-danger:hover {\n    color: #fff;\n    background-color: #EF5350;\n    border-color: #EF5350; }\n  .btn-outline-danger:focus, .btn-outline-danger.focus {\n    box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.5); }\n  .btn-outline-danger.disabled, .btn-outline-danger:disabled {\n    color: #EF5350;\n    background-color: transparent; }\n  .btn-outline-danger:not([disabled]):not(.disabled):active, .btn-outline-danger:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-danger.dropdown-toggle {\n    color: #fff;\n    background-color: #EF5350;\n    border-color: #EF5350;\n    box-shadow: 0 0 0 0.2rem rgba(239, 83, 80, 0.5); }\n\n.btn-outline-light {\n  color: #f8f9fa;\n  background-color: transparent;\n  background-image: none;\n  border-color: #f8f9fa; }\n  .btn-outline-light:hover {\n    color: #212529;\n    background-color: #f8f9fa;\n    border-color: #f8f9fa; }\n  .btn-outline-light:focus, .btn-outline-light.focus {\n    box-shadow: 0 0 0 0.2rem rgba(248, 249, 250, 0.5); }\n  .btn-outline-light.disabled, .btn-outline-light:disabled {\n    color: #f8f9fa;\n    background-color: transparent; }\n  .btn-outline-light:not([disabled]):not(.disabled):active, .btn-outline-light:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-light.dropdown-toggle {\n    color: #212529;\n    background-color: #f8f9fa;\n    border-color: #f8f9fa;\n    box-shadow: 0 0 0 0.2rem rgba(248, 249, 250, 0.5); }\n\n.btn-outline-dark {\n  color: #343a40;\n  background-color: transparent;\n  background-image: none;\n  border-color: #343a40; }\n  .btn-outline-dark:hover {\n    color: #fff;\n    background-color: #343a40;\n    border-color: #343a40; }\n  .btn-outline-dark:focus, .btn-outline-dark.focus {\n    box-shadow: 0 0 0 0.2rem rgba(52, 58, 64, 0.5); }\n  .btn-outline-dark.disabled, .btn-outline-dark:disabled {\n    color: #343a40;\n    background-color: transparent; }\n  .btn-outline-dark:not([disabled]):not(.disabled):active, .btn-outline-dark:not([disabled]):not(.disabled).active,\n  .show > .btn-outline-dark.dropdown-toggle {\n    color: #fff;\n    background-color: #343a40;\n    border-color: #343a40;\n    box-shadow: 0 0 0 0.2rem rgba(52, 58, 64, 0.5); }\n\n.btn-link {\n  font-weight: 400;\n  color: #2196F3;\n  background-color: transparent; }\n  .btn-link:hover {\n    color: #0a6ebd;\n    text-decoration: underline;\n    background-color: transparent;\n    border-color: transparent; }\n  .btn-link:focus, .btn-link.focus {\n    border-color: transparent;\n    box-shadow: none; }\n  .btn-link:disabled, .btn-link.disabled {\n    color: #868e96; }\n\n.btn-lg, .btn-group-lg > .btn {\n  padding: 0.5rem 1rem;\n  font-size: 1.25rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\n.btn-sm, .btn-group-sm > .btn {\n  padding: 0.25rem 0.5rem;\n  font-size: 0.875rem;\n  line-height: 1.5;\n  border-radius: 0.2rem; }\n\n.btn-block {\n  display: block;\n  width: 100%; }\n\n.btn-block + .btn-block {\n  margin-top: 0.5rem; }\n\ninput[type=\"submit\"].btn-block,\ninput[type=\"reset\"].btn-block,\ninput[type=\"button\"].btn-block {\n  width: 100%; }\n\n.fade {\n  opacity: 0;\n  transition: opacity 0.15s linear; }\n  .fade.show {\n    opacity: 1; }\n\n.collapse {\n  display: none; }\n  .collapse.show {\n    display: block; }\n\ntr.collapse.show {\n  display: table-row; }\n\ntbody.collapse.show {\n  display: table-row-group; }\n\n.collapsing {\n  position: relative;\n  height: 0;\n  overflow: hidden;\n  transition: height 0.35s ease; }\n\n.dropup,\n.dropdown {\n  position: relative; }\n\n.dropdown-toggle::after {\n  display: inline-block;\n  width: 0;\n  height: 0;\n  margin-left: 0.255em;\n  vertical-align: 0.255em;\n  content: \"\";\n  border-top: 0.3em solid;\n  border-right: 0.3em solid transparent;\n  border-bottom: 0;\n  border-left: 0.3em solid transparent; }\n\n.dropdown-toggle:empty::after {\n  margin-left: 0; }\n\n.dropdown-menu {\n  position: absolute;\n  top: 100%;\n  left: 0;\n  z-index: 1000;\n  display: none;\n  float: left;\n  min-width: 10rem;\n  padding: 0.5rem 0;\n  margin: 0.125rem 0 0;\n  font-size: 1rem;\n  color: rgba(0, 0, 0, 0.87);\n  text-align: left;\n  list-style: none;\n  background-color: #fff;\n  background-clip: padding-box;\n  border: 1px solid rgba(0, 0, 0, 0.15);\n  border-radius: 0.2rem; }\n\n.dropup .dropdown-menu {\n  margin-top: 0;\n  margin-bottom: 0.125rem; }\n\n.dropup .dropdown-toggle::after {\n  display: inline-block;\n  width: 0;\n  height: 0;\n  margin-left: 0.255em;\n  vertical-align: 0.255em;\n  content: \"\";\n  border-top: 0;\n  border-right: 0.3em solid transparent;\n  border-bottom: 0.3em solid;\n  border-left: 0.3em solid transparent; }\n\n.dropup .dropdown-toggle:empty::after {\n  margin-left: 0; }\n\n.dropdown-divider {\n  height: 0;\n  margin: 0.5rem 0;\n  overflow: hidden;\n  border-top: 1px solid #e9ecef; }\n\n.dropdown-item {\n  display: block;\n  width: 100%;\n  padding: 0.25rem 1.5rem;\n  clear: both;\n  font-weight: 400;\n  color: #212529;\n  text-align: inherit;\n  white-space: nowrap;\n  background: none;\n  border: 0; }\n  .dropdown-item:focus, .dropdown-item:hover {\n    color: #16181b;\n    text-decoration: none;\n    background-color: #f8f9fa; }\n  .dropdown-item.active, .dropdown-item:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #2196F3; }\n  .dropdown-item.disabled, .dropdown-item:disabled {\n    color: #868e96;\n    background-color: transparent; }\n\n.dropdown-menu.show {\n  display: block; }\n\n.dropdown-header {\n  display: block;\n  padding: 0.5rem 1.5rem;\n  margin-bottom: 0;\n  font-size: 0.875rem;\n  color: #868e96;\n  white-space: nowrap; }\n\n.btn-group,\n.btn-group-vertical {\n  position: relative;\n  display: -webkit-inline-box;\n  display: -ms-inline-flexbox;\n  display: inline-flex;\n  vertical-align: middle; }\n  .btn-group > .btn,\n  .btn-group-vertical > .btn {\n    position: relative;\n    -webkit-box-flex: 0;\n        -ms-flex: 0 1 auto;\n            flex: 0 1 auto; }\n    .btn-group > .btn:hover,\n    .btn-group-vertical > .btn:hover {\n      z-index: 2; }\n    .btn-group > .btn:focus, .btn-group > .btn:active, .btn-group > .btn.active,\n    .btn-group-vertical > .btn:focus,\n    .btn-group-vertical > .btn:active,\n    .btn-group-vertical > .btn.active {\n      z-index: 2; }\n  .btn-group .btn + .btn,\n  .btn-group .btn + .btn-group,\n  .btn-group .btn-group + .btn,\n  .btn-group .btn-group + .btn-group,\n  .btn-group-vertical .btn + .btn,\n  .btn-group-vertical .btn + .btn-group,\n  .btn-group-vertical .btn-group + .btn,\n  .btn-group-vertical .btn-group + .btn-group {\n    margin-left: -1px; }\n\n.btn-toolbar {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  -webkit-box-pack: start;\n      -ms-flex-pack: start;\n          justify-content: flex-start; }\n  .btn-toolbar .input-group {\n    width: auto; }\n\n.btn-group > .btn:not(:first-child):not(:last-child):not(.dropdown-toggle) {\n  border-radius: 0; }\n\n.btn-group > .btn:first-child {\n  margin-left: 0; }\n  .btn-group > .btn:first-child:not(:last-child):not(.dropdown-toggle) {\n    border-top-right-radius: 0;\n    border-bottom-right-radius: 0; }\n\n.btn-group > .btn:last-child:not(:first-child),\n.btn-group > .dropdown-toggle:not(:first-child) {\n  border-top-left-radius: 0;\n  border-bottom-left-radius: 0; }\n\n.btn-group > .btn-group {\n  float: left; }\n\n.btn-group > .btn-group:not(:first-child):not(:last-child) > .btn {\n  border-radius: 0; }\n\n.btn-group > .btn-group:first-child:not(:last-child) > .btn:last-child,\n.btn-group > .btn-group:first-child:not(:last-child) > .dropdown-toggle {\n  border-top-right-radius: 0;\n  border-bottom-right-radius: 0; }\n\n.btn-group > .btn-group:last-child:not(:first-child) > .btn:first-child {\n  border-top-left-radius: 0;\n  border-bottom-left-radius: 0; }\n\n.btn + .dropdown-toggle-split {\n  padding-right: 1.125rem;\n  padding-left: 1.125rem; }\n  .btn + .dropdown-toggle-split::after {\n    margin-left: 0; }\n\n.btn-sm + .dropdown-toggle-split, .btn-group-sm > .btn + .dropdown-toggle-split {\n  padding-right: 0.375rem;\n  padding-left: 0.375rem; }\n\n.btn-lg + .dropdown-toggle-split, .btn-group-lg > .btn + .dropdown-toggle-split {\n  padding-right: 0.75rem;\n  padding-left: 0.75rem; }\n\n.btn-group-vertical {\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  -webkit-box-align: start;\n      -ms-flex-align: start;\n          align-items: flex-start;\n  -webkit-box-pack: center;\n      -ms-flex-pack: center;\n          justify-content: center; }\n  .btn-group-vertical .btn,\n  .btn-group-vertical .btn-group {\n    width: 100%; }\n  .btn-group-vertical > .btn + .btn,\n  .btn-group-vertical > .btn + .btn-group,\n  .btn-group-vertical > .btn-group + .btn,\n  .btn-group-vertical > .btn-group + .btn-group {\n    margin-top: -1px;\n    margin-left: 0; }\n  .btn-group-vertical > .btn:not(:first-child):not(:last-child) {\n    border-radius: 0; }\n  .btn-group-vertical > .btn:first-child:not(:last-child) {\n    border-bottom-right-radius: 0;\n    border-bottom-left-radius: 0; }\n  .btn-group-vertical > .btn:last-child:not(:first-child) {\n    border-top-left-radius: 0;\n    border-top-right-radius: 0; }\n  .btn-group-vertical > .btn-group:not(:first-child):not(:last-child) > .btn {\n    border-radius: 0; }\n  .btn-group-vertical > .btn-group:first-child:not(:last-child) > .btn:last-child,\n  .btn-group-vertical > .btn-group:first-child:not(:last-child) > .dropdown-toggle {\n    border-bottom-right-radius: 0;\n    border-bottom-left-radius: 0; }\n  .btn-group-vertical > .btn-group:last-child:not(:first-child) > .btn:first-child {\n    border-top-left-radius: 0;\n    border-top-right-radius: 0; }\n\n[data-toggle=\"buttons\"] > .btn input[type=\"radio\"],\n[data-toggle=\"buttons\"] > .btn input[type=\"checkbox\"],\n[data-toggle=\"buttons\"] > .btn-group > .btn input[type=\"radio\"],\n[data-toggle=\"buttons\"] > .btn-group > .btn input[type=\"checkbox\"] {\n  position: absolute;\n  clip: rect(0, 0, 0, 0);\n  pointer-events: none; }\n\n.input-group {\n  position: relative;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-align: stretch;\n      -ms-flex-align: stretch;\n          align-items: stretch;\n  width: 100%; }\n  .input-group .form-control {\n    position: relative;\n    z-index: 2;\n    -webkit-box-flex: 1;\n        -ms-flex: 1 1 auto;\n            flex: 1 1 auto;\n    width: 1%;\n    margin-bottom: 0; }\n    .input-group .form-control:focus, .input-group .form-control:active, .input-group .form-control:hover {\n      z-index: 3; }\n\n.input-group-addon,\n.input-group-btn,\n.input-group .form-control {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center; }\n  .input-group-addon:not(:first-child):not(:last-child),\n  .input-group-btn:not(:first-child):not(:last-child),\n  .input-group .form-control:not(:first-child):not(:last-child) {\n    border-radius: 0; }\n\n.input-group-addon,\n.input-group-btn {\n  white-space: nowrap; }\n\n.input-group-addon {\n  padding: 0.75rem 1.5rem;\n  margin-bottom: 0;\n  font-size: 1rem;\n  font-weight: 400;\n  line-height: 1.5;\n  color: #495057;\n  text-align: center;\n  background-color: #e9ecef;\n  border: 1px solid #ced4da;\n  border-radius: 0.2rem; }\n  .input-group-addon.form-control-sm,\n  .input-group-sm > .input-group-addon,\n  .input-group-sm > .input-group-btn > .input-group-addon.btn {\n    padding: 0.25rem 0.5rem;\n    font-size: 0.875rem;\n    border-radius: 0.2rem; }\n  .input-group-addon.form-control-lg,\n  .input-group-lg > .input-group-addon,\n  .input-group-lg > .input-group-btn > .input-group-addon.btn {\n    padding: 0.5rem 1rem;\n    font-size: 1.25rem;\n    border-radius: 0.2rem; }\n  .input-group-addon input[type=\"radio\"],\n  .input-group-addon input[type=\"checkbox\"] {\n    margin-top: 0; }\n\n.input-group .form-control:not(:last-child),\n.input-group-addon:not(:last-child),\n.input-group-btn:not(:last-child) > .btn,\n.input-group-btn:not(:last-child) > .btn-group > .btn,\n.input-group-btn:not(:last-child) > .dropdown-toggle,\n.input-group-btn:not(:first-child) > .btn:not(:last-child):not(.dropdown-toggle),\n.input-group-btn:not(:first-child) > .btn-group:not(:last-child) > .btn {\n  border-top-right-radius: 0;\n  border-bottom-right-radius: 0; }\n\n.input-group-addon:not(:last-child) {\n  border-right: 0; }\n\n.input-group .form-control:not(:first-child),\n.input-group-addon:not(:first-child),\n.input-group-btn:not(:first-child) > .btn,\n.input-group-btn:not(:first-child) > .btn-group > .btn,\n.input-group-btn:not(:first-child) > .dropdown-toggle,\n.input-group-btn:not(:last-child) > .btn:not(:first-child),\n.input-group-btn:not(:last-child) > .btn-group:not(:first-child) > .btn {\n  border-top-left-radius: 0;\n  border-bottom-left-radius: 0; }\n\n.form-control + .input-group-addon:not(:first-child) {\n  border-left: 0; }\n\n.input-group-btn {\n  position: relative;\n  -webkit-box-align: stretch;\n      -ms-flex-align: stretch;\n          align-items: stretch;\n  font-size: 0;\n  white-space: nowrap; }\n  .input-group-btn > .btn {\n    position: relative; }\n    .input-group-btn > .btn + .btn {\n      margin-left: -1px; }\n    .input-group-btn > .btn:focus, .input-group-btn > .btn:active, .input-group-btn > .btn:hover {\n      z-index: 3; }\n  .input-group-btn:first-child > .btn + .btn {\n    margin-left: 0; }\n  .input-group-btn:not(:last-child) > .btn,\n  .input-group-btn:not(:last-child) > .btn-group {\n    margin-right: -1px; }\n  .input-group-btn:not(:first-child) > .btn,\n  .input-group-btn:not(:first-child) > .btn-group {\n    z-index: 2;\n    margin-left: 0; }\n    .input-group-btn:not(:first-child) > .btn:first-child,\n    .input-group-btn:not(:first-child) > .btn-group:first-child {\n      margin-left: -1px; }\n    .input-group-btn:not(:first-child) > .btn:focus, .input-group-btn:not(:first-child) > .btn:active, .input-group-btn:not(:first-child) > .btn:hover,\n    .input-group-btn:not(:first-child) > .btn-group:focus,\n    .input-group-btn:not(:first-child) > .btn-group:active,\n    .input-group-btn:not(:first-child) > .btn-group:hover {\n      z-index: 3; }\n\n.custom-control {\n  position: relative;\n  display: -webkit-inline-box;\n  display: -ms-inline-flexbox;\n  display: inline-flex;\n  min-height: 1.5rem;\n  padding-left: 1.5rem;\n  margin-right: 1rem; }\n\n.custom-control-input {\n  position: absolute;\n  z-index: -1;\n  opacity: 0; }\n  .custom-control-input:checked ~ .custom-control-indicator {\n    color: #fff;\n    background-color: #2196F3; }\n  .custom-control-input:focus ~ .custom-control-indicator {\n    box-shadow: 0 0 0 1px #e5e5e5, 0 0 0 0.2rem rgba(33, 150, 243, 0.25); }\n  .custom-control-input:active ~ .custom-control-indicator {\n    color: #fff;\n    background-color: #cae6fc; }\n  .custom-control-input:disabled ~ .custom-control-indicator {\n    background-color: #e9ecef; }\n  .custom-control-input:disabled ~ .custom-control-description {\n    color: #868e96; }\n\n.custom-control-indicator {\n  position: absolute;\n  top: 0.25rem;\n  left: 0;\n  display: block;\n  width: 1rem;\n  height: 1rem;\n  pointer-events: none;\n  -webkit-user-select: none;\n     -moz-user-select: none;\n      -ms-user-select: none;\n          user-select: none;\n  background-color: #ddd;\n  background-repeat: no-repeat;\n  background-position: center center;\n  background-size: 50% 50%; }\n\n.custom-checkbox .custom-control-indicator {\n  border-radius: 0.2rem; }\n\n.custom-checkbox .custom-control-input:checked ~ .custom-control-indicator {\n  background-image: url(\"data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3E%3Cpath fill='%23fff' d='M6.564.75l-3.59 3.612-1.538-1.55L0 4.26 2.974 7.25 8 2.193z'/%3E%3C/svg%3E\"); }\n\n.custom-checkbox .custom-control-input:indeterminate ~ .custom-control-indicator {\n  background-color: #2196F3;\n  background-image: url(\"data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 4 4'%3E%3Cpath stroke='%23fff' d='M0 2h4'/%3E%3C/svg%3E\"); }\n\n.custom-radio .custom-control-indicator {\n  border-radius: 50%; }\n\n.custom-radio .custom-control-input:checked ~ .custom-control-indicator {\n  background-image: url(\"data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='-4 -4 8 8'%3E%3Ccircle r='3' fill='%23fff'/%3E%3C/svg%3E\"); }\n\n.custom-controls-stacked {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column; }\n  .custom-controls-stacked .custom-control {\n    margin-bottom: 0.25rem; }\n    .custom-controls-stacked .custom-control + .custom-control {\n      margin-left: 0; }\n\n.custom-select {\n  display: inline-block;\n  max-width: 100%;\n  height: calc(3rem + 2px);\n  padding: 0.375rem 1.75rem 0.375rem 0.75rem;\n  line-height: 1.5;\n  color: #495057;\n  vertical-align: middle;\n  background: #fff url(\"data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 4 5'%3E%3Cpath fill='%23333' d='M2 0L0 2h4zm0 5L0 3h4z'/%3E%3C/svg%3E\") no-repeat right 0.75rem center;\n  background-size: 8px 10px;\n  border: 1px solid #ced4da;\n  border-radius: 0.2rem;\n  -webkit-appearance: none;\n     -moz-appearance: none;\n          appearance: none; }\n  .custom-select:focus {\n    border-color: #9acffa;\n    outline: none; }\n    .custom-select:focus::-ms-value {\n      color: #495057;\n      background-color: #fff; }\n  .custom-select[multiple] {\n    height: auto;\n    background-image: none; }\n  .custom-select:disabled {\n    color: #868e96;\n    background-color: #e9ecef; }\n  .custom-select::-ms-expand {\n    opacity: 0; }\n\n.custom-select-sm {\n  height: calc(1.8125rem + 2px);\n  padding-top: 0.375rem;\n  padding-bottom: 0.375rem;\n  font-size: 75%; }\n\n.custom-file {\n  position: relative;\n  display: inline-block;\n  max-width: 100%;\n  height: calc(3rem + 2px);\n  margin-bottom: 0; }\n\n.custom-file-input {\n  min-width: 14rem;\n  max-width: 100%;\n  height: calc(3rem + 2px);\n  margin: 0;\n  opacity: 0; }\n  .custom-file-input:focus ~ .custom-file-control {\n    box-shadow: 0 0 0 0.075rem #fff, 0 0 0 0.2rem #2196F3; }\n\n.custom-file-control {\n  position: absolute;\n  top: 0;\n  right: 0;\n  left: 0;\n  z-index: 5;\n  height: calc(3rem + 2px);\n  padding: 0.75rem 1.5rem;\n  line-height: 1.5;\n  color: #495057;\n  pointer-events: none;\n  -webkit-user-select: none;\n     -moz-user-select: none;\n      -ms-user-select: none;\n          user-select: none;\n  background-color: #fff;\n  border: 1px solid #ced4da;\n  border-radius: 0.2rem; }\n  .custom-file-control:lang(en):empty::after {\n    content: \"Choose file...\"; }\n  .custom-file-control::before {\n    position: absolute;\n    top: -1px;\n    right: -1px;\n    bottom: -1px;\n    z-index: 6;\n    display: block;\n    height: calc(3rem + 2px);\n    padding: 0.75rem 1.5rem;\n    line-height: 1.5;\n    color: #495057;\n    background-color: #e9ecef;\n    border: 1px solid #ced4da;\n    border-radius: 0 0.2rem 0.2rem 0; }\n  .custom-file-control:lang(en)::before {\n    content: \"Browse\"; }\n\n.nav {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  padding-left: 0;\n  margin-bottom: 0;\n  list-style: none; }\n\n.nav-link {\n  display: block;\n  padding: 0.5rem 1rem; }\n  .nav-link:focus, .nav-link:hover {\n    text-decoration: none; }\n  .nav-link.disabled {\n    color: #868e96; }\n\n.nav-tabs {\n  border-bottom: 1px solid #ddd; }\n  .nav-tabs .nav-item {\n    margin-bottom: -1px; }\n  .nav-tabs .nav-link {\n    border: 1px solid transparent;\n    border-top-left-radius: 0.2rem;\n    border-top-right-radius: 0.2rem; }\n    .nav-tabs .nav-link:focus, .nav-tabs .nav-link:hover {\n      border-color: #e9ecef #e9ecef #ddd; }\n    .nav-tabs .nav-link.disabled {\n      color: #868e96;\n      background-color: transparent;\n      border-color: transparent; }\n  .nav-tabs .nav-link.active,\n  .nav-tabs .nav-item.show .nav-link {\n    color: #495057;\n    background-color: #e5e5e5;\n    border-color: #ddd #ddd #e5e5e5; }\n  .nav-tabs .dropdown-menu {\n    margin-top: -1px;\n    border-top-left-radius: 0;\n    border-top-right-radius: 0; }\n\n.nav-pills .nav-link {\n  border-radius: 0.2rem; }\n\n.nav-pills .nav-link.active,\n.nav-pills .show > .nav-link {\n  color: #fff;\n  background-color: #2196F3; }\n\n.nav-fill .nav-item {\n  -webkit-box-flex: 1;\n      -ms-flex: 1 1 auto;\n          flex: 1 1 auto;\n  text-align: center; }\n\n.nav-justified .nav-item {\n  -ms-flex-preferred-size: 0;\n      flex-basis: 0;\n  -webkit-box-flex: 1;\n      -ms-flex-positive: 1;\n          flex-grow: 1;\n  text-align: center; }\n\n.tab-content > .tab-pane {\n  display: none; }\n\n.tab-content > .active {\n  display: block; }\n\n.navbar {\n  position: relative;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n  -webkit-box-pack: justify;\n      -ms-flex-pack: justify;\n          justify-content: space-between;\n  padding: 0.5rem 1rem; }\n  .navbar > .container,\n  .navbar > .container-fluid {\n    display: -webkit-box;\n    display: -ms-flexbox;\n    display: flex;\n    -ms-flex-wrap: wrap;\n        flex-wrap: wrap;\n    -webkit-box-align: center;\n        -ms-flex-align: center;\n            align-items: center;\n    -webkit-box-pack: justify;\n        -ms-flex-pack: justify;\n            justify-content: space-between; }\n\n.navbar-brand {\n  display: inline-block;\n  padding-top: 0.3125rem;\n  padding-bottom: 0.3125rem;\n  margin-right: 1rem;\n  font-size: 1.25rem;\n  line-height: inherit;\n  white-space: nowrap; }\n  .navbar-brand:focus, .navbar-brand:hover {\n    text-decoration: none; }\n\n.navbar-nav {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  padding-left: 0;\n  margin-bottom: 0;\n  list-style: none; }\n  .navbar-nav .nav-link {\n    padding-right: 0;\n    padding-left: 0; }\n  .navbar-nav .dropdown-menu {\n    position: static;\n    float: none; }\n\n.navbar-text {\n  display: inline-block;\n  padding-top: 0.5rem;\n  padding-bottom: 0.5rem; }\n\n.navbar-collapse {\n  -ms-flex-preferred-size: 100%;\n      flex-basis: 100%;\n  -webkit-box-flex: 1;\n      -ms-flex-positive: 1;\n          flex-grow: 1;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center; }\n\n.navbar-toggler {\n  padding: 0.25rem 0.75rem;\n  font-size: 1.25rem;\n  line-height: 1;\n  background: transparent;\n  border: 1px solid transparent;\n  border-radius: 0.2rem; }\n  .navbar-toggler:focus, .navbar-toggler:hover {\n    text-decoration: none; }\n\n.navbar-toggler-icon {\n  display: inline-block;\n  width: 1.5em;\n  height: 1.5em;\n  vertical-align: middle;\n  content: \"\";\n  background: no-repeat center center;\n  background-size: 100% 100%; }\n\n@media (max-width: 575px) {\n  .navbar-expand-sm > .container,\n  .navbar-expand-sm > .container-fluid {\n    padding-right: 0;\n    padding-left: 0; } }\n\n@media (min-width: 576px) {\n  .navbar-expand-sm {\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-flow: row nowrap;\n            flex-flow: row nowrap;\n    -webkit-box-pack: start;\n        -ms-flex-pack: start;\n            justify-content: flex-start; }\n    .navbar-expand-sm .navbar-nav {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-direction: row;\n              flex-direction: row; }\n      .navbar-expand-sm .navbar-nav .dropdown-menu {\n        position: absolute; }\n      .navbar-expand-sm .navbar-nav .dropdown-menu-right {\n        right: 0;\n        left: auto; }\n      .navbar-expand-sm .navbar-nav .nav-link {\n        padding-right: .5rem;\n        padding-left: .5rem; }\n    .navbar-expand-sm > .container,\n    .navbar-expand-sm > .container-fluid {\n      -ms-flex-wrap: nowrap;\n          flex-wrap: nowrap; }\n    .navbar-expand-sm .navbar-collapse {\n      display: -webkit-box !important;\n      display: -ms-flexbox !important;\n      display: flex !important;\n      -ms-flex-preferred-size: auto;\n          flex-basis: auto; }\n    .navbar-expand-sm .navbar-toggler {\n      display: none; }\n    .navbar-expand-sm .dropup .dropdown-menu {\n      top: auto;\n      bottom: 100%; } }\n\n@media (max-width: 767px) {\n  .navbar-expand-md > .container,\n  .navbar-expand-md > .container-fluid {\n    padding-right: 0;\n    padding-left: 0; } }\n\n@media (min-width: 768px) {\n  .navbar-expand-md {\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-flow: row nowrap;\n            flex-flow: row nowrap;\n    -webkit-box-pack: start;\n        -ms-flex-pack: start;\n            justify-content: flex-start; }\n    .navbar-expand-md .navbar-nav {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-direction: row;\n              flex-direction: row; }\n      .navbar-expand-md .navbar-nav .dropdown-menu {\n        position: absolute; }\n      .navbar-expand-md .navbar-nav .dropdown-menu-right {\n        right: 0;\n        left: auto; }\n      .navbar-expand-md .navbar-nav .nav-link {\n        padding-right: .5rem;\n        padding-left: .5rem; }\n    .navbar-expand-md > .container,\n    .navbar-expand-md > .container-fluid {\n      -ms-flex-wrap: nowrap;\n          flex-wrap: nowrap; }\n    .navbar-expand-md .navbar-collapse {\n      display: -webkit-box !important;\n      display: -ms-flexbox !important;\n      display: flex !important;\n      -ms-flex-preferred-size: auto;\n          flex-basis: auto; }\n    .navbar-expand-md .navbar-toggler {\n      display: none; }\n    .navbar-expand-md .dropup .dropdown-menu {\n      top: auto;\n      bottom: 100%; } }\n\n@media (max-width: 991px) {\n  .navbar-expand-lg > .container,\n  .navbar-expand-lg > .container-fluid {\n    padding-right: 0;\n    padding-left: 0; } }\n\n@media (min-width: 992px) {\n  .navbar-expand-lg {\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-flow: row nowrap;\n            flex-flow: row nowrap;\n    -webkit-box-pack: start;\n        -ms-flex-pack: start;\n            justify-content: flex-start; }\n    .navbar-expand-lg .navbar-nav {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-direction: row;\n              flex-direction: row; }\n      .navbar-expand-lg .navbar-nav .dropdown-menu {\n        position: absolute; }\n      .navbar-expand-lg .navbar-nav .dropdown-menu-right {\n        right: 0;\n        left: auto; }\n      .navbar-expand-lg .navbar-nav .nav-link {\n        padding-right: .5rem;\n        padding-left: .5rem; }\n    .navbar-expand-lg > .container,\n    .navbar-expand-lg > .container-fluid {\n      -ms-flex-wrap: nowrap;\n          flex-wrap: nowrap; }\n    .navbar-expand-lg .navbar-collapse {\n      display: -webkit-box !important;\n      display: -ms-flexbox !important;\n      display: flex !important;\n      -ms-flex-preferred-size: auto;\n          flex-basis: auto; }\n    .navbar-expand-lg .navbar-toggler {\n      display: none; }\n    .navbar-expand-lg .dropup .dropdown-menu {\n      top: auto;\n      bottom: 100%; } }\n\n@media (max-width: 1199px) {\n  .navbar-expand-xl > .container,\n  .navbar-expand-xl > .container-fluid {\n    padding-right: 0;\n    padding-left: 0; } }\n\n@media (min-width: 1200px) {\n  .navbar-expand-xl {\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-flow: row nowrap;\n            flex-flow: row nowrap;\n    -webkit-box-pack: start;\n        -ms-flex-pack: start;\n            justify-content: flex-start; }\n    .navbar-expand-xl .navbar-nav {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-direction: row;\n              flex-direction: row; }\n      .navbar-expand-xl .navbar-nav .dropdown-menu {\n        position: absolute; }\n      .navbar-expand-xl .navbar-nav .dropdown-menu-right {\n        right: 0;\n        left: auto; }\n      .navbar-expand-xl .navbar-nav .nav-link {\n        padding-right: .5rem;\n        padding-left: .5rem; }\n    .navbar-expand-xl > .container,\n    .navbar-expand-xl > .container-fluid {\n      -ms-flex-wrap: nowrap;\n          flex-wrap: nowrap; }\n    .navbar-expand-xl .navbar-collapse {\n      display: -webkit-box !important;\n      display: -ms-flexbox !important;\n      display: flex !important;\n      -ms-flex-preferred-size: auto;\n          flex-basis: auto; }\n    .navbar-expand-xl .navbar-toggler {\n      display: none; }\n    .navbar-expand-xl .dropup .dropdown-menu {\n      top: auto;\n      bottom: 100%; } }\n\n.navbar-expand {\n  -webkit-box-orient: horizontal;\n  -webkit-box-direction: normal;\n      -ms-flex-flow: row nowrap;\n          flex-flow: row nowrap;\n  -webkit-box-pack: start;\n      -ms-flex-pack: start;\n          justify-content: flex-start; }\n  .navbar-expand > .container,\n  .navbar-expand > .container-fluid {\n    padding-right: 0;\n    padding-left: 0; }\n  .navbar-expand .navbar-nav {\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-direction: row;\n            flex-direction: row; }\n    .navbar-expand .navbar-nav .dropdown-menu {\n      position: absolute; }\n    .navbar-expand .navbar-nav .dropdown-menu-right {\n      right: 0;\n      left: auto; }\n    .navbar-expand .navbar-nav .nav-link {\n      padding-right: .5rem;\n      padding-left: .5rem; }\n  .navbar-expand > .container,\n  .navbar-expand > .container-fluid {\n    -ms-flex-wrap: nowrap;\n        flex-wrap: nowrap; }\n  .navbar-expand .navbar-collapse {\n    display: -webkit-box !important;\n    display: -ms-flexbox !important;\n    display: flex !important;\n    -ms-flex-preferred-size: auto;\n        flex-basis: auto; }\n  .navbar-expand .navbar-toggler {\n    display: none; }\n  .navbar-expand .dropup .dropdown-menu {\n    top: auto;\n    bottom: 100%; }\n\n.navbar-light .navbar-brand {\n  color: rgba(0, 0, 0, 0.9); }\n  .navbar-light .navbar-brand:focus, .navbar-light .navbar-brand:hover {\n    color: rgba(0, 0, 0, 0.9); }\n\n.navbar-light .navbar-nav .nav-link {\n  color: rgba(0, 0, 0, 0.5); }\n  .navbar-light .navbar-nav .nav-link:focus, .navbar-light .navbar-nav .nav-link:hover {\n    color: rgba(0, 0, 0, 0.7); }\n  .navbar-light .navbar-nav .nav-link.disabled {\n    color: rgba(0, 0, 0, 0.3); }\n\n.navbar-light .navbar-nav .show > .nav-link,\n.navbar-light .navbar-nav .active > .nav-link,\n.navbar-light .navbar-nav .nav-link.show,\n.navbar-light .navbar-nav .nav-link.active {\n  color: rgba(0, 0, 0, 0.9); }\n\n.navbar-light .navbar-toggler {\n  color: rgba(0, 0, 0, 0.5);\n  border-color: rgba(0, 0, 0, 0.1); }\n\n.navbar-light .navbar-toggler-icon {\n  background-image: url(\"data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 30 30' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='rgba(0, 0, 0, 0.5)' stroke-width='2' stroke-linecap='round' stroke-miterlimit='10' d='M4 7h22M4 15h22M4 23h22'/%3E%3C/svg%3E\"); }\n\n.navbar-light .navbar-text {\n  color: rgba(0, 0, 0, 0.5); }\n  .navbar-light .navbar-text a {\n    color: rgba(0, 0, 0, 0.9); }\n    .navbar-light .navbar-text a:focus, .navbar-light .navbar-text a:hover {\n      color: rgba(0, 0, 0, 0.9); }\n\n.navbar-dark .navbar-brand {\n  color: #fff; }\n  .navbar-dark .navbar-brand:focus, .navbar-dark .navbar-brand:hover {\n    color: #fff; }\n\n.navbar-dark .navbar-nav .nav-link {\n  color: rgba(255, 255, 255, 0.5); }\n  .navbar-dark .navbar-nav .nav-link:focus, .navbar-dark .navbar-nav .nav-link:hover {\n    color: rgba(255, 255, 255, 0.75); }\n  .navbar-dark .navbar-nav .nav-link.disabled {\n    color: rgba(255, 255, 255, 0.25); }\n\n.navbar-dark .navbar-nav .show > .nav-link,\n.navbar-dark .navbar-nav .active > .nav-link,\n.navbar-dark .navbar-nav .nav-link.show,\n.navbar-dark .navbar-nav .nav-link.active {\n  color: #fff; }\n\n.navbar-dark .navbar-toggler {\n  color: rgba(255, 255, 255, 0.5);\n  border-color: rgba(255, 255, 255, 0.1); }\n\n.navbar-dark .navbar-toggler-icon {\n  background-image: url(\"data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 30 30' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='rgba(255, 255, 255, 0.5)' stroke-width='2' stroke-linecap='round' stroke-miterlimit='10' d='M4 7h22M4 15h22M4 23h22'/%3E%3C/svg%3E\"); }\n\n.navbar-dark .navbar-text {\n  color: rgba(255, 255, 255, 0.5); }\n  .navbar-dark .navbar-text a {\n    color: #fff; }\n    .navbar-dark .navbar-text a:focus, .navbar-dark .navbar-text a:hover {\n      color: #fff; }\n\n.card {\n  position: relative;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  min-width: 0;\n  word-wrap: break-word;\n  background-color: #fff;\n  background-clip: border-box;\n  border: 1px solid rgba(0, 0, 0, 0.125);\n  border-radius: 0.2rem; }\n  .card > hr {\n    margin-right: 0;\n    margin-left: 0; }\n  .card > .list-group:first-child .list-group-item:first-child {\n    border-top-left-radius: 0.2rem;\n    border-top-right-radius: 0.2rem; }\n  .card > .list-group:last-child .list-group-item:last-child {\n    border-bottom-right-radius: 0.2rem;\n    border-bottom-left-radius: 0.2rem; }\n\n.card-body {\n  -webkit-box-flex: 1;\n      -ms-flex: 1 1 auto;\n          flex: 1 1 auto;\n  padding: 1.25rem; }\n\n.card-title {\n  margin-bottom: 0.75rem; }\n\n.card-subtitle {\n  margin-top: -0.375rem;\n  margin-bottom: 0; }\n\n.card-text:last-child {\n  margin-bottom: 0; }\n\n.card-link:hover {\n  text-decoration: none; }\n\n.card-link + .card-link {\n  margin-left: 1.25rem; }\n\n.card-header {\n  padding: 0.75rem 1.25rem;\n  margin-bottom: 0;\n  background-color: rgba(0, 0, 0, 0.03);\n  border-bottom: 1px solid rgba(0, 0, 0, 0.125); }\n  .card-header:first-child {\n    border-radius: calc(0.2rem - 1px) calc(0.2rem - 1px) 0 0; }\n  .card-header + .list-group .list-group-item:first-child {\n    border-top: 0; }\n\n.card-footer {\n  padding: 0.75rem 1.25rem;\n  background-color: rgba(0, 0, 0, 0.03);\n  border-top: 1px solid rgba(0, 0, 0, 0.125); }\n  .card-footer:last-child {\n    border-radius: 0 0 calc(0.2rem - 1px) calc(0.2rem - 1px); }\n\n.card-header-tabs {\n  margin-right: -0.625rem;\n  margin-bottom: -0.75rem;\n  margin-left: -0.625rem;\n  border-bottom: 0; }\n\n.card-header-pills {\n  margin-right: -0.625rem;\n  margin-left: -0.625rem; }\n\n.card-img-overlay {\n  position: absolute;\n  top: 0;\n  right: 0;\n  bottom: 0;\n  left: 0;\n  padding: 1.25rem; }\n\n.card-img {\n  width: 100%;\n  border-radius: calc(0.2rem - 1px); }\n\n.card-img-top {\n  width: 100%;\n  border-top-left-radius: calc(0.2rem - 1px);\n  border-top-right-radius: calc(0.2rem - 1px); }\n\n.card-img-bottom {\n  width: 100%;\n  border-bottom-right-radius: calc(0.2rem - 1px);\n  border-bottom-left-radius: calc(0.2rem - 1px); }\n\n.card-deck {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column; }\n  .card-deck .card {\n    margin-bottom: 15px; }\n  @media (min-width: 576px) {\n    .card-deck {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-flow: row wrap;\n              flex-flow: row wrap;\n      margin-right: -15px;\n      margin-left: -15px; }\n      .card-deck .card {\n        display: -webkit-box;\n        display: -ms-flexbox;\n        display: flex;\n        -webkit-box-flex: 1;\n            -ms-flex: 1 0 0%;\n                flex: 1 0 0%;\n        -webkit-box-orient: vertical;\n        -webkit-box-direction: normal;\n            -ms-flex-direction: column;\n                flex-direction: column;\n        margin-right: 15px;\n        margin-bottom: 0;\n        margin-left: 15px; } }\n\n.card-group {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column; }\n  .card-group .card {\n    margin-bottom: 15px; }\n  @media (min-width: 576px) {\n    .card-group {\n      -webkit-box-orient: horizontal;\n      -webkit-box-direction: normal;\n          -ms-flex-flow: row wrap;\n              flex-flow: row wrap; }\n      .card-group .card {\n        -webkit-box-flex: 1;\n            -ms-flex: 1 0 0%;\n                flex: 1 0 0%;\n        margin-bottom: 0; }\n        .card-group .card + .card {\n          margin-left: 0;\n          border-left: 0; }\n        .card-group .card:first-child {\n          border-top-right-radius: 0;\n          border-bottom-right-radius: 0; }\n          .card-group .card:first-child .card-img-top {\n            border-top-right-radius: 0; }\n          .card-group .card:first-child .card-img-bottom {\n            border-bottom-right-radius: 0; }\n        .card-group .card:last-child {\n          border-top-left-radius: 0;\n          border-bottom-left-radius: 0; }\n          .card-group .card:last-child .card-img-top {\n            border-top-left-radius: 0; }\n          .card-group .card:last-child .card-img-bottom {\n            border-bottom-left-radius: 0; }\n        .card-group .card:only-child {\n          border-radius: 0.2rem; }\n          .card-group .card:only-child .card-img-top {\n            border-top-left-radius: 0.2rem;\n            border-top-right-radius: 0.2rem; }\n          .card-group .card:only-child .card-img-bottom {\n            border-bottom-right-radius: 0.2rem;\n            border-bottom-left-radius: 0.2rem; }\n        .card-group .card:not(:first-child):not(:last-child):not(:only-child) {\n          border-radius: 0; }\n          .card-group .card:not(:first-child):not(:last-child):not(:only-child) .card-img-top,\n          .card-group .card:not(:first-child):not(:last-child):not(:only-child) .card-img-bottom {\n            border-radius: 0; } }\n\n.card-columns .card {\n  margin-bottom: 0.75rem; }\n\n@media (min-width: 576px) {\n  .card-columns {\n    -webkit-column-count: 3;\n            column-count: 3;\n    -webkit-column-gap: 1.25rem;\n            column-gap: 1.25rem; }\n    .card-columns .card {\n      display: inline-block;\n      width: 100%; } }\n\n.breadcrumb {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -ms-flex-wrap: wrap;\n      flex-wrap: wrap;\n  padding: 0.75rem 1rem;\n  margin-bottom: 1rem;\n  list-style: none;\n  background-color: #e9ecef;\n  border-radius: 0.2rem; }\n\n.breadcrumb-item + .breadcrumb-item::before {\n  display: inline-block;\n  padding-right: 0.5rem;\n  padding-left: 0.5rem;\n  color: #868e96;\n  content: \"/\"; }\n\n.breadcrumb-item + .breadcrumb-item:hover::before {\n  text-decoration: underline; }\n\n.breadcrumb-item + .breadcrumb-item:hover::before {\n  text-decoration: none; }\n\n.breadcrumb-item.active {\n  color: #868e96; }\n\n.pagination {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  padding-left: 0;\n  list-style: none;\n  border-radius: 0.2rem; }\n\n.page-item:first-child .page-link {\n  margin-left: 0;\n  border-top-left-radius: 0.2rem;\n  border-bottom-left-radius: 0.2rem; }\n\n.page-item:last-child .page-link {\n  border-top-right-radius: 0.2rem;\n  border-bottom-right-radius: 0.2rem; }\n\n.page-item.active .page-link {\n  z-index: 2;\n  color: #fff;\n  background-color: #2196F3;\n  border-color: #2196F3; }\n\n.page-item.disabled .page-link {\n  color: #868e96;\n  pointer-events: none;\n  background-color: #fff;\n  border-color: #ddd; }\n\n.page-link {\n  position: relative;\n  display: block;\n  padding: 0.5rem 0.75rem;\n  margin-left: -1px;\n  line-height: 1.25;\n  color: #2196F3;\n  background-color: #fff;\n  border: 1px solid #ddd; }\n  .page-link:focus, .page-link:hover {\n    color: #0a6ebd;\n    text-decoration: none;\n    background-color: #e9ecef;\n    border-color: #ddd; }\n\n.pagination-lg .page-link {\n  padding: 0.75rem 1.5rem;\n  font-size: 1.25rem;\n  line-height: 1.5; }\n\n.pagination-lg .page-item:first-child .page-link {\n  border-top-left-radius: 0.2rem;\n  border-bottom-left-radius: 0.2rem; }\n\n.pagination-lg .page-item:last-child .page-link {\n  border-top-right-radius: 0.2rem;\n  border-bottom-right-radius: 0.2rem; }\n\n.pagination-sm .page-link {\n  padding: 0.25rem 0.5rem;\n  font-size: 0.875rem;\n  line-height: 1.5; }\n\n.pagination-sm .page-item:first-child .page-link {\n  border-top-left-radius: 0.2rem;\n  border-bottom-left-radius: 0.2rem; }\n\n.pagination-sm .page-item:last-child .page-link {\n  border-top-right-radius: 0.2rem;\n  border-bottom-right-radius: 0.2rem; }\n\n.badge {\n  display: inline-block;\n  padding: 0.25em 0.4em;\n  font-size: 75%;\n  font-weight: 700;\n  line-height: 1;\n  text-align: center;\n  white-space: nowrap;\n  vertical-align: baseline;\n  border-radius: 0.2rem; }\n  .badge:empty {\n    display: none; }\n\n.btn .badge {\n  position: relative;\n  top: -1px; }\n\n.badge-pill {\n  padding-right: 0.6em;\n  padding-left: 0.6em;\n  border-radius: 10rem; }\n\n.badge-primary {\n  color: #fff;\n  background-color: #2196F3; }\n  .badge-primary[href]:focus, .badge-primary[href]:hover {\n    color: #fff;\n    text-decoration: none;\n    background-color: #0c7cd5; }\n\n.badge-secondary {\n  color: #fff;\n  background-color: #868e96; }\n  .badge-secondary[href]:focus, .badge-secondary[href]:hover {\n    color: #fff;\n    text-decoration: none;\n    background-color: #6c757d; }\n\n.badge-success {\n  color: #111;\n  background-color: #66BB6A; }\n  .badge-success[href]:focus, .badge-success[href]:hover {\n    color: #111;\n    text-decoration: none;\n    background-color: #49a54e; }\n\n.badge-info {\n  color: #fff;\n  background-color: #00BCD4; }\n  .badge-info[href]:focus, .badge-info[href]:hover {\n    color: #fff;\n    text-decoration: none;\n    background-color: #008fa1; }\n\n.badge-warning {\n  color: #111;\n  background-color: #ffc107; }\n  .badge-warning[href]:focus, .badge-warning[href]:hover {\n    color: #111;\n    text-decoration: none;\n    background-color: #d39e00; }\n\n.badge-danger {\n  color: #fff;\n  background-color: #EF5350; }\n  .badge-danger[href]:focus, .badge-danger[href]:hover {\n    color: #fff;\n    text-decoration: none;\n    background-color: #eb2521; }\n\n.badge-light {\n  color: #111;\n  background-color: #f8f9fa; }\n  .badge-light[href]:focus, .badge-light[href]:hover {\n    color: #111;\n    text-decoration: none;\n    background-color: #dae0e5; }\n\n.badge-dark {\n  color: #fff;\n  background-color: #343a40; }\n  .badge-dark[href]:focus, .badge-dark[href]:hover {\n    color: #fff;\n    text-decoration: none;\n    background-color: #1d2124; }\n\n.jumbotron {\n  padding: 2rem 1rem;\n  margin-bottom: 2rem;\n  background-color: #e9ecef;\n  border-radius: 0.2rem; }\n  @media (min-width: 576px) {\n    .jumbotron {\n      padding: 4rem 2rem; } }\n\n.jumbotron-fluid {\n  padding-right: 0;\n  padding-left: 0;\n  border-radius: 0; }\n\n.alert {\n  position: relative;\n  padding: 0.75rem 1.25rem;\n  margin-bottom: 1rem;\n  border: 1px solid transparent;\n  border-radius: 0.2rem; }\n\n.alert-heading {\n  color: inherit; }\n\n.alert-link {\n  font-weight: 700; }\n\n.alert-dismissible .close {\n  position: absolute;\n  top: 0;\n  right: 0;\n  padding: 0.75rem 1.25rem;\n  color: inherit; }\n\n.alert-primary {\n  color: #114e7e;\n  background-color: #d3eafd;\n  border-color: #c1e2fc; }\n  .alert-primary hr {\n    border-top-color: #a9d7fb; }\n  .alert-primary .alert-link {\n    color: #0b3251; }\n\n.alert-secondary {\n  color: #464a4e;\n  background-color: #e7e8ea;\n  border-color: #dddfe2; }\n  .alert-secondary hr {\n    border-top-color: #cfd2d6; }\n  .alert-secondary .alert-link {\n    color: #2e3133; }\n\n.alert-success {\n  color: #356137;\n  background-color: #e0f1e1;\n  border-color: #d4ecd5; }\n  .alert-success hr {\n    border-top-color: #c2e4c4; }\n  .alert-success .alert-link {\n    color: #234024; }\n\n.alert-info {\n  color: #00626e;\n  background-color: #ccf2f6;\n  border-color: #b8ecf3; }\n  .alert-info hr {\n    border-top-color: #a2e6ef; }\n  .alert-info .alert-link {\n    color: #00353b; }\n\n.alert-warning {\n  color: #856404;\n  background-color: #fff3cd;\n  border-color: #ffeeba; }\n  .alert-warning hr {\n    border-top-color: #ffe8a1; }\n  .alert-warning .alert-link {\n    color: #533f03; }\n\n.alert-danger {\n  color: #7c2b2a;\n  background-color: #fcdddc;\n  border-color: #fbcfce; }\n  .alert-danger hr {\n    border-top-color: #f9b8b6; }\n  .alert-danger .alert-link {\n    color: #561e1d; }\n\n.alert-light {\n  color: #818182;\n  background-color: #fefefe;\n  border-color: #fdfdfe; }\n  .alert-light hr {\n    border-top-color: #ececf6; }\n  .alert-light .alert-link {\n    color: #686868; }\n\n.alert-dark {\n  color: #1b1e21;\n  background-color: #d6d8d9;\n  border-color: #c6c8ca; }\n  .alert-dark hr {\n    border-top-color: #b9bbbe; }\n  .alert-dark .alert-link {\n    color: #040505; }\n\n@-webkit-keyframes progress-bar-stripes {\n  from {\n    background-position: 1rem 0; }\n  to {\n    background-position: 0 0; } }\n\n@keyframes progress-bar-stripes {\n  from {\n    background-position: 1rem 0; }\n  to {\n    background-position: 0 0; } }\n\n.progress {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  height: 1rem;\n  overflow: hidden;\n  font-size: 0.75rem;\n  background-color: #e9ecef;\n  border-radius: 0.2rem; }\n\n.progress-bar {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n  -webkit-box-pack: center;\n      -ms-flex-pack: center;\n          justify-content: center;\n  color: #fff;\n  background-color: #2196F3; }\n\n.progress-bar-striped {\n  background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);\n  background-size: 1rem 1rem; }\n\n.progress-bar-animated {\n  -webkit-animation: progress-bar-stripes 1s linear infinite;\n          animation: progress-bar-stripes 1s linear infinite; }\n\n.media {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-align: start;\n      -ms-flex-align: start;\n          align-items: flex-start; }\n\n.media-body {\n  -webkit-box-flex: 1;\n      -ms-flex: 1;\n          flex: 1; }\n\n.list-group {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  padding-left: 0;\n  margin-bottom: 0; }\n\n.list-group-item-action {\n  width: 100%;\n  color: #495057;\n  text-align: inherit; }\n  .list-group-item-action:focus, .list-group-item-action:hover {\n    color: #495057;\n    text-decoration: none;\n    background-color: #f8f9fa; }\n  .list-group-item-action:active {\n    color: rgba(0, 0, 0, 0.87);\n    background-color: #e9ecef; }\n\n.list-group-item {\n  position: relative;\n  display: block;\n  padding: 0.75rem 1.25rem;\n  margin-bottom: -1px;\n  background-color: #fff;\n  border: 1px solid rgba(0, 0, 0, 0.125); }\n  .list-group-item:first-child {\n    border-top-left-radius: 0.2rem;\n    border-top-right-radius: 0.2rem; }\n  .list-group-item:last-child {\n    margin-bottom: 0;\n    border-bottom-right-radius: 0.2rem;\n    border-bottom-left-radius: 0.2rem; }\n  .list-group-item:focus, .list-group-item:hover {\n    text-decoration: none; }\n  .list-group-item.disabled, .list-group-item:disabled {\n    color: #868e96;\n    background-color: #fff; }\n  .list-group-item.active {\n    z-index: 2;\n    color: #fff;\n    background-color: #2196F3;\n    border-color: #2196F3; }\n\n.list-group-flush .list-group-item {\n  border-right: 0;\n  border-left: 0;\n  border-radius: 0; }\n\n.list-group-flush:first-child .list-group-item:first-child {\n  border-top: 0; }\n\n.list-group-flush:last-child .list-group-item:last-child {\n  border-bottom: 0; }\n\n.list-group-item-primary {\n  color: #114e7e;\n  background-color: #c1e2fc; }\n\na.list-group-item-primary,\nbutton.list-group-item-primary {\n  color: #114e7e; }\n  a.list-group-item-primary:focus, a.list-group-item-primary:hover,\n  button.list-group-item-primary:focus,\n  button.list-group-item-primary:hover {\n    color: #114e7e;\n    background-color: #a9d7fb; }\n  a.list-group-item-primary.active,\n  button.list-group-item-primary.active {\n    color: #fff;\n    background-color: #114e7e;\n    border-color: #114e7e; }\n\n.list-group-item-secondary {\n  color: #464a4e;\n  background-color: #dddfe2; }\n\na.list-group-item-secondary,\nbutton.list-group-item-secondary {\n  color: #464a4e; }\n  a.list-group-item-secondary:focus, a.list-group-item-secondary:hover,\n  button.list-group-item-secondary:focus,\n  button.list-group-item-secondary:hover {\n    color: #464a4e;\n    background-color: #cfd2d6; }\n  a.list-group-item-secondary.active,\n  button.list-group-item-secondary.active {\n    color: #fff;\n    background-color: #464a4e;\n    border-color: #464a4e; }\n\n.list-group-item-success {\n  color: #356137;\n  background-color: #d4ecd5; }\n\na.list-group-item-success,\nbutton.list-group-item-success {\n  color: #356137; }\n  a.list-group-item-success:focus, a.list-group-item-success:hover,\n  button.list-group-item-success:focus,\n  button.list-group-item-success:hover {\n    color: #356137;\n    background-color: #c2e4c4; }\n  a.list-group-item-success.active,\n  button.list-group-item-success.active {\n    color: #fff;\n    background-color: #356137;\n    border-color: #356137; }\n\n.list-group-item-info {\n  color: #00626e;\n  background-color: #b8ecf3; }\n\na.list-group-item-info,\nbutton.list-group-item-info {\n  color: #00626e; }\n  a.list-group-item-info:focus, a.list-group-item-info:hover,\n  button.list-group-item-info:focus,\n  button.list-group-item-info:hover {\n    color: #00626e;\n    background-color: #a2e6ef; }\n  a.list-group-item-info.active,\n  button.list-group-item-info.active {\n    color: #fff;\n    background-color: #00626e;\n    border-color: #00626e; }\n\n.list-group-item-warning {\n  color: #856404;\n  background-color: #ffeeba; }\n\na.list-group-item-warning,\nbutton.list-group-item-warning {\n  color: #856404; }\n  a.list-group-item-warning:focus, a.list-group-item-warning:hover,\n  button.list-group-item-warning:focus,\n  button.list-group-item-warning:hover {\n    color: #856404;\n    background-color: #ffe8a1; }\n  a.list-group-item-warning.active,\n  button.list-group-item-warning.active {\n    color: #fff;\n    background-color: #856404;\n    border-color: #856404; }\n\n.list-group-item-danger {\n  color: #7c2b2a;\n  background-color: #fbcfce; }\n\na.list-group-item-danger,\nbutton.list-group-item-danger {\n  color: #7c2b2a; }\n  a.list-group-item-danger:focus, a.list-group-item-danger:hover,\n  button.list-group-item-danger:focus,\n  button.list-group-item-danger:hover {\n    color: #7c2b2a;\n    background-color: #f9b8b6; }\n  a.list-group-item-danger.active,\n  button.list-group-item-danger.active {\n    color: #fff;\n    background-color: #7c2b2a;\n    border-color: #7c2b2a; }\n\n.list-group-item-light {\n  color: #818182;\n  background-color: #fdfdfe; }\n\na.list-group-item-light,\nbutton.list-group-item-light {\n  color: #818182; }\n  a.list-group-item-light:focus, a.list-group-item-light:hover,\n  button.list-group-item-light:focus,\n  button.list-group-item-light:hover {\n    color: #818182;\n    background-color: #ececf6; }\n  a.list-group-item-light.active,\n  button.list-group-item-light.active {\n    color: #fff;\n    background-color: #818182;\n    border-color: #818182; }\n\n.list-group-item-dark {\n  color: #1b1e21;\n  background-color: #c6c8ca; }\n\na.list-group-item-dark,\nbutton.list-group-item-dark {\n  color: #1b1e21; }\n  a.list-group-item-dark:focus, a.list-group-item-dark:hover,\n  button.list-group-item-dark:focus,\n  button.list-group-item-dark:hover {\n    color: #1b1e21;\n    background-color: #b9bbbe; }\n  a.list-group-item-dark.active,\n  button.list-group-item-dark.active {\n    color: #fff;\n    background-color: #1b1e21;\n    border-color: #1b1e21; }\n\n.close {\n  float: right;\n  font-size: 1.5rem;\n  font-weight: 700;\n  line-height: 1;\n  color: #000;\n  text-shadow: 0 1px 0 #fff;\n  opacity: .5; }\n  .close:focus, .close:hover {\n    color: #000;\n    text-decoration: none;\n    opacity: .75; }\n\nbutton.close {\n  padding: 0;\n  background: transparent;\n  border: 0;\n  -webkit-appearance: none; }\n\n.align-baseline {\n  vertical-align: baseline !important; }\n\n.align-top {\n  vertical-align: top !important; }\n\n.align-middle {\n  vertical-align: middle !important; }\n\n.align-bottom {\n  vertical-align: bottom !important; }\n\n.align-text-bottom {\n  vertical-align: text-bottom !important; }\n\n.align-text-top {\n  vertical-align: text-top !important; }\n\n.bg-primary {\n  background-color: #2196F3 !important; }\n\na.bg-primary:focus, a.bg-primary:hover {\n  background-color: #0c7cd5 !important; }\n\n.bg-secondary {\n  background-color: #868e96 !important; }\n\na.bg-secondary:focus, a.bg-secondary:hover {\n  background-color: #6c757d !important; }\n\n.bg-success {\n  background-color: #66BB6A !important; }\n\na.bg-success:focus, a.bg-success:hover {\n  background-color: #49a54e !important; }\n\n.bg-info {\n  background-color: #00BCD4 !important; }\n\na.bg-info:focus, a.bg-info:hover {\n  background-color: #008fa1 !important; }\n\n.bg-warning {\n  background-color: #ffc107 !important; }\n\na.bg-warning:focus, a.bg-warning:hover {\n  background-color: #d39e00 !important; }\n\n.bg-danger {\n  background-color: #EF5350 !important; }\n\na.bg-danger:focus, a.bg-danger:hover {\n  background-color: #eb2521 !important; }\n\n.bg-light {\n  background-color: #f8f9fa !important; }\n\na.bg-light:focus, a.bg-light:hover {\n  background-color: #dae0e5 !important; }\n\n.bg-dark {\n  background-color: #343a40 !important; }\n\na.bg-dark:focus, a.bg-dark:hover {\n  background-color: #1d2124 !important; }\n\n.bg-white {\n  background-color: #fff !important; }\n\n.bg-transparent {\n  background-color: transparent !important; }\n\n.border {\n  border: 1px solid #e9ecef !important; }\n\n.border-0 {\n  border: 0 !important; }\n\n.border-top-0 {\n  border-top: 0 !important; }\n\n.border-right-0 {\n  border-right: 0 !important; }\n\n.border-bottom-0 {\n  border-bottom: 0 !important; }\n\n.border-left-0 {\n  border-left: 0 !important; }\n\n.border-primary {\n  border-color: #2196F3 !important; }\n\n.border-secondary {\n  border-color: #868e96 !important; }\n\n.border-success {\n  border-color: #66BB6A !important; }\n\n.border-info {\n  border-color: #00BCD4 !important; }\n\n.border-warning {\n  border-color: #ffc107 !important; }\n\n.border-danger {\n  border-color: #EF5350 !important; }\n\n.border-light {\n  border-color: #f8f9fa !important; }\n\n.border-dark {\n  border-color: #343a40 !important; }\n\n.border-white {\n  border-color: #fff !important; }\n\n.rounded {\n  border-radius: 0.2rem !important; }\n\n.rounded-top {\n  border-top-left-radius: 0.2rem !important;\n  border-top-right-radius: 0.2rem !important; }\n\n.rounded-right {\n  border-top-right-radius: 0.2rem !important;\n  border-bottom-right-radius: 0.2rem !important; }\n\n.rounded-bottom {\n  border-bottom-right-radius: 0.2rem !important;\n  border-bottom-left-radius: 0.2rem !important; }\n\n.rounded-left {\n  border-top-left-radius: 0.2rem !important;\n  border-bottom-left-radius: 0.2rem !important; }\n\n.rounded-circle {\n  border-radius: 50% !important; }\n\n.rounded-0 {\n  border-radius: 0 !important; }\n\n.clearfix::after {\n  display: block;\n  clear: both;\n  content: \"\"; }\n\n.d-none {\n  display: none !important; }\n\n.d-inline {\n  display: inline !important; }\n\n.d-inline-block {\n  display: inline-block !important; }\n\n.d-block {\n  display: block !important; }\n\n.d-table {\n  display: table !important; }\n\n.d-table-row {\n  display: table-row !important; }\n\n.d-table-cell {\n  display: table-cell !important; }\n\n.d-flex {\n  display: -webkit-box !important;\n  display: -ms-flexbox !important;\n  display: flex !important; }\n\n.d-inline-flex {\n  display: -webkit-inline-box !important;\n  display: -ms-inline-flexbox !important;\n  display: inline-flex !important; }\n\n@media (min-width: 576px) {\n  .d-sm-none {\n    display: none !important; }\n  .d-sm-inline {\n    display: inline !important; }\n  .d-sm-inline-block {\n    display: inline-block !important; }\n  .d-sm-block {\n    display: block !important; }\n  .d-sm-table {\n    display: table !important; }\n  .d-sm-table-row {\n    display: table-row !important; }\n  .d-sm-table-cell {\n    display: table-cell !important; }\n  .d-sm-flex {\n    display: -webkit-box !important;\n    display: -ms-flexbox !important;\n    display: flex !important; }\n  .d-sm-inline-flex {\n    display: -webkit-inline-box !important;\n    display: -ms-inline-flexbox !important;\n    display: inline-flex !important; } }\n\n@media (min-width: 768px) {\n  .d-md-none {\n    display: none !important; }\n  .d-md-inline {\n    display: inline !important; }\n  .d-md-inline-block {\n    display: inline-block !important; }\n  .d-md-block {\n    display: block !important; }\n  .d-md-table {\n    display: table !important; }\n  .d-md-table-row {\n    display: table-row !important; }\n  .d-md-table-cell {\n    display: table-cell !important; }\n  .d-md-flex {\n    display: -webkit-box !important;\n    display: -ms-flexbox !important;\n    display: flex !important; }\n  .d-md-inline-flex {\n    display: -webkit-inline-box !important;\n    display: -ms-inline-flexbox !important;\n    display: inline-flex !important; } }\n\n@media (min-width: 992px) {\n  .d-lg-none {\n    display: none !important; }\n  .d-lg-inline {\n    display: inline !important; }\n  .d-lg-inline-block {\n    display: inline-block !important; }\n  .d-lg-block {\n    display: block !important; }\n  .d-lg-table {\n    display: table !important; }\n  .d-lg-table-row {\n    display: table-row !important; }\n  .d-lg-table-cell {\n    display: table-cell !important; }\n  .d-lg-flex {\n    display: -webkit-box !important;\n    display: -ms-flexbox !important;\n    display: flex !important; }\n  .d-lg-inline-flex {\n    display: -webkit-inline-box !important;\n    display: -ms-inline-flexbox !important;\n    display: inline-flex !important; } }\n\n@media (min-width: 1200px) {\n  .d-xl-none {\n    display: none !important; }\n  .d-xl-inline {\n    display: inline !important; }\n  .d-xl-inline-block {\n    display: inline-block !important; }\n  .d-xl-block {\n    display: block !important; }\n  .d-xl-table {\n    display: table !important; }\n  .d-xl-table-row {\n    display: table-row !important; }\n  .d-xl-table-cell {\n    display: table-cell !important; }\n  .d-xl-flex {\n    display: -webkit-box !important;\n    display: -ms-flexbox !important;\n    display: flex !important; }\n  .d-xl-inline-flex {\n    display: -webkit-inline-box !important;\n    display: -ms-inline-flexbox !important;\n    display: inline-flex !important; } }\n\n.d-print-block {\n  display: none !important; }\n  @media print {\n    .d-print-block {\n      display: block !important; } }\n\n.d-print-inline {\n  display: none !important; }\n  @media print {\n    .d-print-inline {\n      display: inline !important; } }\n\n.d-print-inline-block {\n  display: none !important; }\n  @media print {\n    .d-print-inline-block {\n      display: inline-block !important; } }\n\n@media print {\n  .d-print-none {\n    display: none !important; } }\n\n.embed-responsive {\n  position: relative;\n  display: block;\n  width: 100%;\n  padding: 0;\n  overflow: hidden; }\n  .embed-responsive::before {\n    display: block;\n    content: \"\"; }\n  .embed-responsive .embed-responsive-item,\n  .embed-responsive iframe,\n  .embed-responsive embed,\n  .embed-responsive object,\n  .embed-responsive video {\n    position: absolute;\n    top: 0;\n    bottom: 0;\n    left: 0;\n    width: 100%;\n    height: 100%;\n    border: 0; }\n\n.embed-responsive-21by9::before {\n  padding-top: 42.85714286%; }\n\n.embed-responsive-16by9::before {\n  padding-top: 56.25%; }\n\n.embed-responsive-4by3::before {\n  padding-top: 75%; }\n\n.embed-responsive-1by1::before {\n  padding-top: 100%; }\n\n.flex-row {\n  -webkit-box-orient: horizontal !important;\n  -webkit-box-direction: normal !important;\n      -ms-flex-direction: row !important;\n          flex-direction: row !important; }\n\n.flex-column {\n  -webkit-box-orient: vertical !important;\n  -webkit-box-direction: normal !important;\n      -ms-flex-direction: column !important;\n          flex-direction: column !important; }\n\n.flex-row-reverse {\n  -webkit-box-orient: horizontal !important;\n  -webkit-box-direction: reverse !important;\n      -ms-flex-direction: row-reverse !important;\n          flex-direction: row-reverse !important; }\n\n.flex-column-reverse {\n  -webkit-box-orient: vertical !important;\n  -webkit-box-direction: reverse !important;\n      -ms-flex-direction: column-reverse !important;\n          flex-direction: column-reverse !important; }\n\n.flex-wrap {\n  -ms-flex-wrap: wrap !important;\n      flex-wrap: wrap !important; }\n\n.flex-nowrap {\n  -ms-flex-wrap: nowrap !important;\n      flex-wrap: nowrap !important; }\n\n.flex-wrap-reverse {\n  -ms-flex-wrap: wrap-reverse !important;\n      flex-wrap: wrap-reverse !important; }\n\n.justify-content-start {\n  -webkit-box-pack: start !important;\n      -ms-flex-pack: start !important;\n          justify-content: flex-start !important; }\n\n.justify-content-end {\n  -webkit-box-pack: end !important;\n      -ms-flex-pack: end !important;\n          justify-content: flex-end !important; }\n\n.justify-content-center {\n  -webkit-box-pack: center !important;\n      -ms-flex-pack: center !important;\n          justify-content: center !important; }\n\n.justify-content-between {\n  -webkit-box-pack: justify !important;\n      -ms-flex-pack: justify !important;\n          justify-content: space-between !important; }\n\n.justify-content-around {\n  -ms-flex-pack: distribute !important;\n      justify-content: space-around !important; }\n\n.align-items-start {\n  -webkit-box-align: start !important;\n      -ms-flex-align: start !important;\n          align-items: flex-start !important; }\n\n.align-items-end {\n  -webkit-box-align: end !important;\n      -ms-flex-align: end !important;\n          align-items: flex-end !important; }\n\n.align-items-center {\n  -webkit-box-align: center !important;\n      -ms-flex-align: center !important;\n          align-items: center !important; }\n\n.align-items-baseline {\n  -webkit-box-align: baseline !important;\n      -ms-flex-align: baseline !important;\n          align-items: baseline !important; }\n\n.align-items-stretch {\n  -webkit-box-align: stretch !important;\n      -ms-flex-align: stretch !important;\n          align-items: stretch !important; }\n\n.align-content-start {\n  -ms-flex-line-pack: start !important;\n      align-content: flex-start !important; }\n\n.align-content-end {\n  -ms-flex-line-pack: end !important;\n      align-content: flex-end !important; }\n\n.align-content-center {\n  -ms-flex-line-pack: center !important;\n      align-content: center !important; }\n\n.align-content-between {\n  -ms-flex-line-pack: justify !important;\n      align-content: space-between !important; }\n\n.align-content-around {\n  -ms-flex-line-pack: distribute !important;\n      align-content: space-around !important; }\n\n.align-content-stretch {\n  -ms-flex-line-pack: stretch !important;\n      align-content: stretch !important; }\n\n.align-self-auto {\n  -ms-flex-item-align: auto !important;\n      -ms-grid-row-align: auto !important;\n      align-self: auto !important; }\n\n.align-self-start {\n  -ms-flex-item-align: start !important;\n      align-self: flex-start !important; }\n\n.align-self-end {\n  -ms-flex-item-align: end !important;\n      align-self: flex-end !important; }\n\n.align-self-center {\n  -ms-flex-item-align: center !important;\n      -ms-grid-row-align: center !important;\n      align-self: center !important; }\n\n.align-self-baseline {\n  -ms-flex-item-align: baseline !important;\n      align-self: baseline !important; }\n\n.align-self-stretch {\n  -ms-flex-item-align: stretch !important;\n      -ms-grid-row-align: stretch !important;\n      align-self: stretch !important; }\n\n@media (min-width: 576px) {\n  .flex-sm-row {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: row !important;\n            flex-direction: row !important; }\n  .flex-sm-column {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: column !important;\n            flex-direction: column !important; }\n  .flex-sm-row-reverse {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: row-reverse !important;\n            flex-direction: row-reverse !important; }\n  .flex-sm-column-reverse {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: column-reverse !important;\n            flex-direction: column-reverse !important; }\n  .flex-sm-wrap {\n    -ms-flex-wrap: wrap !important;\n        flex-wrap: wrap !important; }\n  .flex-sm-nowrap {\n    -ms-flex-wrap: nowrap !important;\n        flex-wrap: nowrap !important; }\n  .flex-sm-wrap-reverse {\n    -ms-flex-wrap: wrap-reverse !important;\n        flex-wrap: wrap-reverse !important; }\n  .justify-content-sm-start {\n    -webkit-box-pack: start !important;\n        -ms-flex-pack: start !important;\n            justify-content: flex-start !important; }\n  .justify-content-sm-end {\n    -webkit-box-pack: end !important;\n        -ms-flex-pack: end !important;\n            justify-content: flex-end !important; }\n  .justify-content-sm-center {\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important; }\n  .justify-content-sm-between {\n    -webkit-box-pack: justify !important;\n        -ms-flex-pack: justify !important;\n            justify-content: space-between !important; }\n  .justify-content-sm-around {\n    -ms-flex-pack: distribute !important;\n        justify-content: space-around !important; }\n  .align-items-sm-start {\n    -webkit-box-align: start !important;\n        -ms-flex-align: start !important;\n            align-items: flex-start !important; }\n  .align-items-sm-end {\n    -webkit-box-align: end !important;\n        -ms-flex-align: end !important;\n            align-items: flex-end !important; }\n  .align-items-sm-center {\n    -webkit-box-align: center !important;\n        -ms-flex-align: center !important;\n            align-items: center !important; }\n  .align-items-sm-baseline {\n    -webkit-box-align: baseline !important;\n        -ms-flex-align: baseline !important;\n            align-items: baseline !important; }\n  .align-items-sm-stretch {\n    -webkit-box-align: stretch !important;\n        -ms-flex-align: stretch !important;\n            align-items: stretch !important; }\n  .align-content-sm-start {\n    -ms-flex-line-pack: start !important;\n        align-content: flex-start !important; }\n  .align-content-sm-end {\n    -ms-flex-line-pack: end !important;\n        align-content: flex-end !important; }\n  .align-content-sm-center {\n    -ms-flex-line-pack: center !important;\n        align-content: center !important; }\n  .align-content-sm-between {\n    -ms-flex-line-pack: justify !important;\n        align-content: space-between !important; }\n  .align-content-sm-around {\n    -ms-flex-line-pack: distribute !important;\n        align-content: space-around !important; }\n  .align-content-sm-stretch {\n    -ms-flex-line-pack: stretch !important;\n        align-content: stretch !important; }\n  .align-self-sm-auto {\n    -ms-flex-item-align: auto !important;\n        -ms-grid-row-align: auto !important;\n        align-self: auto !important; }\n  .align-self-sm-start {\n    -ms-flex-item-align: start !important;\n        align-self: flex-start !important; }\n  .align-self-sm-end {\n    -ms-flex-item-align: end !important;\n        align-self: flex-end !important; }\n  .align-self-sm-center {\n    -ms-flex-item-align: center !important;\n        -ms-grid-row-align: center !important;\n        align-self: center !important; }\n  .align-self-sm-baseline {\n    -ms-flex-item-align: baseline !important;\n        align-self: baseline !important; }\n  .align-self-sm-stretch {\n    -ms-flex-item-align: stretch !important;\n        -ms-grid-row-align: stretch !important;\n        align-self: stretch !important; } }\n\n@media (min-width: 768px) {\n  .flex-md-row {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: row !important;\n            flex-direction: row !important; }\n  .flex-md-column {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: column !important;\n            flex-direction: column !important; }\n  .flex-md-row-reverse {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: row-reverse !important;\n            flex-direction: row-reverse !important; }\n  .flex-md-column-reverse {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: column-reverse !important;\n            flex-direction: column-reverse !important; }\n  .flex-md-wrap {\n    -ms-flex-wrap: wrap !important;\n        flex-wrap: wrap !important; }\n  .flex-md-nowrap {\n    -ms-flex-wrap: nowrap !important;\n        flex-wrap: nowrap !important; }\n  .flex-md-wrap-reverse {\n    -ms-flex-wrap: wrap-reverse !important;\n        flex-wrap: wrap-reverse !important; }\n  .justify-content-md-start {\n    -webkit-box-pack: start !important;\n        -ms-flex-pack: start !important;\n            justify-content: flex-start !important; }\n  .justify-content-md-end {\n    -webkit-box-pack: end !important;\n        -ms-flex-pack: end !important;\n            justify-content: flex-end !important; }\n  .justify-content-md-center {\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important; }\n  .justify-content-md-between {\n    -webkit-box-pack: justify !important;\n        -ms-flex-pack: justify !important;\n            justify-content: space-between !important; }\n  .justify-content-md-around {\n    -ms-flex-pack: distribute !important;\n        justify-content: space-around !important; }\n  .align-items-md-start {\n    -webkit-box-align: start !important;\n        -ms-flex-align: start !important;\n            align-items: flex-start !important; }\n  .align-items-md-end {\n    -webkit-box-align: end !important;\n        -ms-flex-align: end !important;\n            align-items: flex-end !important; }\n  .align-items-md-center {\n    -webkit-box-align: center !important;\n        -ms-flex-align: center !important;\n            align-items: center !important; }\n  .align-items-md-baseline {\n    -webkit-box-align: baseline !important;\n        -ms-flex-align: baseline !important;\n            align-items: baseline !important; }\n  .align-items-md-stretch {\n    -webkit-box-align: stretch !important;\n        -ms-flex-align: stretch !important;\n            align-items: stretch !important; }\n  .align-content-md-start {\n    -ms-flex-line-pack: start !important;\n        align-content: flex-start !important; }\n  .align-content-md-end {\n    -ms-flex-line-pack: end !important;\n        align-content: flex-end !important; }\n  .align-content-md-center {\n    -ms-flex-line-pack: center !important;\n        align-content: center !important; }\n  .align-content-md-between {\n    -ms-flex-line-pack: justify !important;\n        align-content: space-between !important; }\n  .align-content-md-around {\n    -ms-flex-line-pack: distribute !important;\n        align-content: space-around !important; }\n  .align-content-md-stretch {\n    -ms-flex-line-pack: stretch !important;\n        align-content: stretch !important; }\n  .align-self-md-auto {\n    -ms-flex-item-align: auto !important;\n        -ms-grid-row-align: auto !important;\n        align-self: auto !important; }\n  .align-self-md-start {\n    -ms-flex-item-align: start !important;\n        align-self: flex-start !important; }\n  .align-self-md-end {\n    -ms-flex-item-align: end !important;\n        align-self: flex-end !important; }\n  .align-self-md-center {\n    -ms-flex-item-align: center !important;\n        -ms-grid-row-align: center !important;\n        align-self: center !important; }\n  .align-self-md-baseline {\n    -ms-flex-item-align: baseline !important;\n        align-self: baseline !important; }\n  .align-self-md-stretch {\n    -ms-flex-item-align: stretch !important;\n        -ms-grid-row-align: stretch !important;\n        align-self: stretch !important; } }\n\n@media (min-width: 992px) {\n  .flex-lg-row {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: row !important;\n            flex-direction: row !important; }\n  .flex-lg-column {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: column !important;\n            flex-direction: column !important; }\n  .flex-lg-row-reverse {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: row-reverse !important;\n            flex-direction: row-reverse !important; }\n  .flex-lg-column-reverse {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: column-reverse !important;\n            flex-direction: column-reverse !important; }\n  .flex-lg-wrap {\n    -ms-flex-wrap: wrap !important;\n        flex-wrap: wrap !important; }\n  .flex-lg-nowrap {\n    -ms-flex-wrap: nowrap !important;\n        flex-wrap: nowrap !important; }\n  .flex-lg-wrap-reverse {\n    -ms-flex-wrap: wrap-reverse !important;\n        flex-wrap: wrap-reverse !important; }\n  .justify-content-lg-start {\n    -webkit-box-pack: start !important;\n        -ms-flex-pack: start !important;\n            justify-content: flex-start !important; }\n  .justify-content-lg-end {\n    -webkit-box-pack: end !important;\n        -ms-flex-pack: end !important;\n            justify-content: flex-end !important; }\n  .justify-content-lg-center {\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important; }\n  .justify-content-lg-between {\n    -webkit-box-pack: justify !important;\n        -ms-flex-pack: justify !important;\n            justify-content: space-between !important; }\n  .justify-content-lg-around {\n    -ms-flex-pack: distribute !important;\n        justify-content: space-around !important; }\n  .align-items-lg-start {\n    -webkit-box-align: start !important;\n        -ms-flex-align: start !important;\n            align-items: flex-start !important; }\n  .align-items-lg-end {\n    -webkit-box-align: end !important;\n        -ms-flex-align: end !important;\n            align-items: flex-end !important; }\n  .align-items-lg-center {\n    -webkit-box-align: center !important;\n        -ms-flex-align: center !important;\n            align-items: center !important; }\n  .align-items-lg-baseline {\n    -webkit-box-align: baseline !important;\n        -ms-flex-align: baseline !important;\n            align-items: baseline !important; }\n  .align-items-lg-stretch {\n    -webkit-box-align: stretch !important;\n        -ms-flex-align: stretch !important;\n            align-items: stretch !important; }\n  .align-content-lg-start {\n    -ms-flex-line-pack: start !important;\n        align-content: flex-start !important; }\n  .align-content-lg-end {\n    -ms-flex-line-pack: end !important;\n        align-content: flex-end !important; }\n  .align-content-lg-center {\n    -ms-flex-line-pack: center !important;\n        align-content: center !important; }\n  .align-content-lg-between {\n    -ms-flex-line-pack: justify !important;\n        align-content: space-between !important; }\n  .align-content-lg-around {\n    -ms-flex-line-pack: distribute !important;\n        align-content: space-around !important; }\n  .align-content-lg-stretch {\n    -ms-flex-line-pack: stretch !important;\n        align-content: stretch !important; }\n  .align-self-lg-auto {\n    -ms-flex-item-align: auto !important;\n        -ms-grid-row-align: auto !important;\n        align-self: auto !important; }\n  .align-self-lg-start {\n    -ms-flex-item-align: start !important;\n        align-self: flex-start !important; }\n  .align-self-lg-end {\n    -ms-flex-item-align: end !important;\n        align-self: flex-end !important; }\n  .align-self-lg-center {\n    -ms-flex-item-align: center !important;\n        -ms-grid-row-align: center !important;\n        align-self: center !important; }\n  .align-self-lg-baseline {\n    -ms-flex-item-align: baseline !important;\n        align-self: baseline !important; }\n  .align-self-lg-stretch {\n    -ms-flex-item-align: stretch !important;\n        -ms-grid-row-align: stretch !important;\n        align-self: stretch !important; } }\n\n@media (min-width: 1200px) {\n  .flex-xl-row {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: row !important;\n            flex-direction: row !important; }\n  .flex-xl-column {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: normal !important;\n        -ms-flex-direction: column !important;\n            flex-direction: column !important; }\n  .flex-xl-row-reverse {\n    -webkit-box-orient: horizontal !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: row-reverse !important;\n            flex-direction: row-reverse !important; }\n  .flex-xl-column-reverse {\n    -webkit-box-orient: vertical !important;\n    -webkit-box-direction: reverse !important;\n        -ms-flex-direction: column-reverse !important;\n            flex-direction: column-reverse !important; }\n  .flex-xl-wrap {\n    -ms-flex-wrap: wrap !important;\n        flex-wrap: wrap !important; }\n  .flex-xl-nowrap {\n    -ms-flex-wrap: nowrap !important;\n        flex-wrap: nowrap !important; }\n  .flex-xl-wrap-reverse {\n    -ms-flex-wrap: wrap-reverse !important;\n        flex-wrap: wrap-reverse !important; }\n  .justify-content-xl-start {\n    -webkit-box-pack: start !important;\n        -ms-flex-pack: start !important;\n            justify-content: flex-start !important; }\n  .justify-content-xl-end {\n    -webkit-box-pack: end !important;\n        -ms-flex-pack: end !important;\n            justify-content: flex-end !important; }\n  .justify-content-xl-center {\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important; }\n  .justify-content-xl-between {\n    -webkit-box-pack: justify !important;\n        -ms-flex-pack: justify !important;\n            justify-content: space-between !important; }\n  .justify-content-xl-around {\n    -ms-flex-pack: distribute !important;\n        justify-content: space-around !important; }\n  .align-items-xl-start {\n    -webkit-box-align: start !important;\n        -ms-flex-align: start !important;\n            align-items: flex-start !important; }\n  .align-items-xl-end {\n    -webkit-box-align: end !important;\n        -ms-flex-align: end !important;\n            align-items: flex-end !important; }\n  .align-items-xl-center {\n    -webkit-box-align: center !important;\n        -ms-flex-align: center !important;\n            align-items: center !important; }\n  .align-items-xl-baseline {\n    -webkit-box-align: baseline !important;\n        -ms-flex-align: baseline !important;\n            align-items: baseline !important; }\n  .align-items-xl-stretch {\n    -webkit-box-align: stretch !important;\n        -ms-flex-align: stretch !important;\n            align-items: stretch !important; }\n  .align-content-xl-start {\n    -ms-flex-line-pack: start !important;\n        align-content: flex-start !important; }\n  .align-content-xl-end {\n    -ms-flex-line-pack: end !important;\n        align-content: flex-end !important; }\n  .align-content-xl-center {\n    -ms-flex-line-pack: center !important;\n        align-content: center !important; }\n  .align-content-xl-between {\n    -ms-flex-line-pack: justify !important;\n        align-content: space-between !important; }\n  .align-content-xl-around {\n    -ms-flex-line-pack: distribute !important;\n        align-content: space-around !important; }\n  .align-content-xl-stretch {\n    -ms-flex-line-pack: stretch !important;\n        align-content: stretch !important; }\n  .align-self-xl-auto {\n    -ms-flex-item-align: auto !important;\n        -ms-grid-row-align: auto !important;\n        align-self: auto !important; }\n  .align-self-xl-start {\n    -ms-flex-item-align: start !important;\n        align-self: flex-start !important; }\n  .align-self-xl-end {\n    -ms-flex-item-align: end !important;\n        align-self: flex-end !important; }\n  .align-self-xl-center {\n    -ms-flex-item-align: center !important;\n        -ms-grid-row-align: center !important;\n        align-self: center !important; }\n  .align-self-xl-baseline {\n    -ms-flex-item-align: baseline !important;\n        align-self: baseline !important; }\n  .align-self-xl-stretch {\n    -ms-flex-item-align: stretch !important;\n        -ms-grid-row-align: stretch !important;\n        align-self: stretch !important; } }\n\n.float-left {\n  float: left !important; }\n\n.float-right {\n  float: right !important; }\n\n.float-none {\n  float: none !important; }\n\n@media (min-width: 576px) {\n  .float-sm-left {\n    float: left !important; }\n  .float-sm-right {\n    float: right !important; }\n  .float-sm-none {\n    float: none !important; } }\n\n@media (min-width: 768px) {\n  .float-md-left {\n    float: left !important; }\n  .float-md-right {\n    float: right !important; }\n  .float-md-none {\n    float: none !important; } }\n\n@media (min-width: 992px) {\n  .float-lg-left {\n    float: left !important; }\n  .float-lg-right {\n    float: right !important; }\n  .float-lg-none {\n    float: none !important; } }\n\n@media (min-width: 1200px) {\n  .float-xl-left {\n    float: left !important; }\n  .float-xl-right {\n    float: right !important; }\n  .float-xl-none {\n    float: none !important; } }\n\n.position-static {\n  position: static !important; }\n\n.position-relative {\n  position: relative !important; }\n\n.position-absolute {\n  position: absolute !important; }\n\n.position-fixed {\n  position: fixed !important; }\n\n.position-sticky {\n  position: -webkit-sticky !important;\n  position: sticky !important; }\n\n.fixed-top {\n  position: fixed;\n  top: 0;\n  right: 0;\n  left: 0;\n  z-index: 1030; }\n\n.fixed-bottom {\n  position: fixed;\n  right: 0;\n  bottom: 0;\n  left: 0;\n  z-index: 1030; }\n\n@supports ((position: -webkit-sticky) or (position: sticky)) {\n  .sticky-top {\n    position: -webkit-sticky;\n    position: sticky;\n    top: 0;\n    z-index: 1020; } }\n\n.sr-only {\n  position: absolute;\n  width: 1px;\n  height: 1px;\n  padding: 0;\n  overflow: hidden;\n  clip: rect(0, 0, 0, 0);\n  white-space: nowrap;\n  -webkit-clip-path: inset(50%);\n          clip-path: inset(50%);\n  border: 0; }\n\n.sr-only-focusable:active, .sr-only-focusable:focus {\n  position: static;\n  width: auto;\n  height: auto;\n  overflow: visible;\n  clip: auto;\n  white-space: normal;\n  -webkit-clip-path: none;\n          clip-path: none; }\n\n.w-25 {\n  width: 25% !important; }\n\n.w-50 {\n  width: 50% !important; }\n\n.w-75 {\n  width: 75% !important; }\n\n.w-100 {\n  width: 100% !important; }\n\n.h-25 {\n  height: 25% !important; }\n\n.h-50 {\n  height: 50% !important; }\n\n.h-75 {\n  height: 75% !important; }\n\n.h-100 {\n  height: 100% !important; }\n\n.mw-100 {\n  max-width: 100% !important; }\n\n.mh-100 {\n  max-height: 100% !important; }\n\n.m-0 {\n  margin: 0 !important; }\n\n.mt-0,\n.my-0 {\n  margin-top: 0 !important; }\n\n.mr-0,\n.mx-0 {\n  margin-right: 0 !important; }\n\n.mb-0,\n.my-0 {\n  margin-bottom: 0 !important; }\n\n.ml-0,\n.mx-0 {\n  margin-left: 0 !important; }\n\n.m-1 {\n  margin: 0.25rem !important; }\n\n.mt-1,\n.my-1 {\n  margin-top: 0.25rem !important; }\n\n.mr-1,\n.mx-1 {\n  margin-right: 0.25rem !important; }\n\n.mb-1,\n.my-1 {\n  margin-bottom: 0.25rem !important; }\n\n.ml-1,\n.mx-1 {\n  margin-left: 0.25rem !important; }\n\n.m-2 {\n  margin: 0.5rem !important; }\n\n.mt-2,\n.my-2 {\n  margin-top: 0.5rem !important; }\n\n.mr-2,\n.mx-2 {\n  margin-right: 0.5rem !important; }\n\n.mb-2,\n.my-2 {\n  margin-bottom: 0.5rem !important; }\n\n.ml-2,\n.mx-2 {\n  margin-left: 0.5rem !important; }\n\n.m-3 {\n  margin: 1rem !important; }\n\n.mt-3,\n.my-3 {\n  margin-top: 1rem !important; }\n\n.mr-3,\n.mx-3 {\n  margin-right: 1rem !important; }\n\n.mb-3,\n.my-3 {\n  margin-bottom: 1rem !important; }\n\n.ml-3,\n.mx-3 {\n  margin-left: 1rem !important; }\n\n.m-4 {\n  margin: 1.5rem !important; }\n\n.mt-4,\n.my-4 {\n  margin-top: 1.5rem !important; }\n\n.mr-4,\n.mx-4 {\n  margin-right: 1.5rem !important; }\n\n.mb-4,\n.my-4 {\n  margin-bottom: 1.5rem !important; }\n\n.ml-4,\n.mx-4 {\n  margin-left: 1.5rem !important; }\n\n.m-5 {\n  margin: 3rem !important; }\n\n.mt-5,\n.my-5 {\n  margin-top: 3rem !important; }\n\n.mr-5,\n.mx-5 {\n  margin-right: 3rem !important; }\n\n.mb-5,\n.my-5 {\n  margin-bottom: 3rem !important; }\n\n.ml-5,\n.mx-5 {\n  margin-left: 3rem !important; }\n\n.p-0 {\n  padding: 0 !important; }\n\n.pt-0,\n.py-0 {\n  padding-top: 0 !important; }\n\n.pr-0,\n.px-0 {\n  padding-right: 0 !important; }\n\n.pb-0,\n.py-0 {\n  padding-bottom: 0 !important; }\n\n.pl-0,\n.px-0 {\n  padding-left: 0 !important; }\n\n.p-1 {\n  padding: 0.25rem !important; }\n\n.pt-1,\n.py-1 {\n  padding-top: 0.25rem !important; }\n\n.pr-1,\n.px-1 {\n  padding-right: 0.25rem !important; }\n\n.pb-1,\n.py-1 {\n  padding-bottom: 0.25rem !important; }\n\n.pl-1,\n.px-1 {\n  padding-left: 0.25rem !important; }\n\n.p-2 {\n  padding: 0.5rem !important; }\n\n.pt-2,\n.py-2 {\n  padding-top: 0.5rem !important; }\n\n.pr-2,\n.px-2 {\n  padding-right: 0.5rem !important; }\n\n.pb-2,\n.py-2 {\n  padding-bottom: 0.5rem !important; }\n\n.pl-2,\n.px-2 {\n  padding-left: 0.5rem !important; }\n\n.p-3 {\n  padding: 1rem !important; }\n\n.pt-3,\n.py-3 {\n  padding-top: 1rem !important; }\n\n.pr-3,\n.px-3 {\n  padding-right: 1rem !important; }\n\n.pb-3,\n.py-3 {\n  padding-bottom: 1rem !important; }\n\n.pl-3,\n.px-3 {\n  padding-left: 1rem !important; }\n\n.p-4 {\n  padding: 1.5rem !important; }\n\n.pt-4,\n.py-4 {\n  padding-top: 1.5rem !important; }\n\n.pr-4,\n.px-4 {\n  padding-right: 1.5rem !important; }\n\n.pb-4,\n.py-4 {\n  padding-bottom: 1.5rem !important; }\n\n.pl-4,\n.px-4 {\n  padding-left: 1.5rem !important; }\n\n.p-5 {\n  padding: 3rem !important; }\n\n.pt-5,\n.py-5 {\n  padding-top: 3rem !important; }\n\n.pr-5,\n.px-5 {\n  padding-right: 3rem !important; }\n\n.pb-5,\n.py-5 {\n  padding-bottom: 3rem !important; }\n\n.pl-5,\n.px-5 {\n  padding-left: 3rem !important; }\n\n.m-auto {\n  margin: auto !important; }\n\n.mt-auto,\n.my-auto {\n  margin-top: auto !important; }\n\n.mr-auto,\n.mx-auto {\n  margin-right: auto !important; }\n\n.mb-auto,\n.my-auto {\n  margin-bottom: auto !important; }\n\n.ml-auto,\n.mx-auto {\n  margin-left: auto !important; }\n\n@media (min-width: 576px) {\n  .m-sm-0 {\n    margin: 0 !important; }\n  .mt-sm-0,\n  .my-sm-0 {\n    margin-top: 0 !important; }\n  .mr-sm-0,\n  .mx-sm-0 {\n    margin-right: 0 !important; }\n  .mb-sm-0,\n  .my-sm-0 {\n    margin-bottom: 0 !important; }\n  .ml-sm-0,\n  .mx-sm-0 {\n    margin-left: 0 !important; }\n  .m-sm-1 {\n    margin: 0.25rem !important; }\n  .mt-sm-1,\n  .my-sm-1 {\n    margin-top: 0.25rem !important; }\n  .mr-sm-1,\n  .mx-sm-1 {\n    margin-right: 0.25rem !important; }\n  .mb-sm-1,\n  .my-sm-1 {\n    margin-bottom: 0.25rem !important; }\n  .ml-sm-1,\n  .mx-sm-1 {\n    margin-left: 0.25rem !important; }\n  .m-sm-2 {\n    margin: 0.5rem !important; }\n  .mt-sm-2,\n  .my-sm-2 {\n    margin-top: 0.5rem !important; }\n  .mr-sm-2,\n  .mx-sm-2 {\n    margin-right: 0.5rem !important; }\n  .mb-sm-2,\n  .my-sm-2 {\n    margin-bottom: 0.5rem !important; }\n  .ml-sm-2,\n  .mx-sm-2 {\n    margin-left: 0.5rem !important; }\n  .m-sm-3 {\n    margin: 1rem !important; }\n  .mt-sm-3,\n  .my-sm-3 {\n    margin-top: 1rem !important; }\n  .mr-sm-3,\n  .mx-sm-3 {\n    margin-right: 1rem !important; }\n  .mb-sm-3,\n  .my-sm-3 {\n    margin-bottom: 1rem !important; }\n  .ml-sm-3,\n  .mx-sm-3 {\n    margin-left: 1rem !important; }\n  .m-sm-4 {\n    margin: 1.5rem !important; }\n  .mt-sm-4,\n  .my-sm-4 {\n    margin-top: 1.5rem !important; }\n  .mr-sm-4,\n  .mx-sm-4 {\n    margin-right: 1.5rem !important; }\n  .mb-sm-4,\n  .my-sm-4 {\n    margin-bottom: 1.5rem !important; }\n  .ml-sm-4,\n  .mx-sm-4 {\n    margin-left: 1.5rem !important; }\n  .m-sm-5 {\n    margin: 3rem !important; }\n  .mt-sm-5,\n  .my-sm-5 {\n    margin-top: 3rem !important; }\n  .mr-sm-5,\n  .mx-sm-5 {\n    margin-right: 3rem !important; }\n  .mb-sm-5,\n  .my-sm-5 {\n    margin-bottom: 3rem !important; }\n  .ml-sm-5,\n  .mx-sm-5 {\n    margin-left: 3rem !important; }\n  .p-sm-0 {\n    padding: 0 !important; }\n  .pt-sm-0,\n  .py-sm-0 {\n    padding-top: 0 !important; }\n  .pr-sm-0,\n  .px-sm-0 {\n    padding-right: 0 !important; }\n  .pb-sm-0,\n  .py-sm-0 {\n    padding-bottom: 0 !important; }\n  .pl-sm-0,\n  .px-sm-0 {\n    padding-left: 0 !important; }\n  .p-sm-1 {\n    padding: 0.25rem !important; }\n  .pt-sm-1,\n  .py-sm-1 {\n    padding-top: 0.25rem !important; }\n  .pr-sm-1,\n  .px-sm-1 {\n    padding-right: 0.25rem !important; }\n  .pb-sm-1,\n  .py-sm-1 {\n    padding-bottom: 0.25rem !important; }\n  .pl-sm-1,\n  .px-sm-1 {\n    padding-left: 0.25rem !important; }\n  .p-sm-2 {\n    padding: 0.5rem !important; }\n  .pt-sm-2,\n  .py-sm-2 {\n    padding-top: 0.5rem !important; }\n  .pr-sm-2,\n  .px-sm-2 {\n    padding-right: 0.5rem !important; }\n  .pb-sm-2,\n  .py-sm-2 {\n    padding-bottom: 0.5rem !important; }\n  .pl-sm-2,\n  .px-sm-2 {\n    padding-left: 0.5rem !important; }\n  .p-sm-3 {\n    padding: 1rem !important; }\n  .pt-sm-3,\n  .py-sm-3 {\n    padding-top: 1rem !important; }\n  .pr-sm-3,\n  .px-sm-3 {\n    padding-right: 1rem !important; }\n  .pb-sm-3,\n  .py-sm-3 {\n    padding-bottom: 1rem !important; }\n  .pl-sm-3,\n  .px-sm-3 {\n    padding-left: 1rem !important; }\n  .p-sm-4 {\n    padding: 1.5rem !important; }\n  .pt-sm-4,\n  .py-sm-4 {\n    padding-top: 1.5rem !important; }\n  .pr-sm-4,\n  .px-sm-4 {\n    padding-right: 1.5rem !important; }\n  .pb-sm-4,\n  .py-sm-4 {\n    padding-bottom: 1.5rem !important; }\n  .pl-sm-4,\n  .px-sm-4 {\n    padding-left: 1.5rem !important; }\n  .p-sm-5 {\n    padding: 3rem !important; }\n  .pt-sm-5,\n  .py-sm-5 {\n    padding-top: 3rem !important; }\n  .pr-sm-5,\n  .px-sm-5 {\n    padding-right: 3rem !important; }\n  .pb-sm-5,\n  .py-sm-5 {\n    padding-bottom: 3rem !important; }\n  .pl-sm-5,\n  .px-sm-5 {\n    padding-left: 3rem !important; }\n  .m-sm-auto {\n    margin: auto !important; }\n  .mt-sm-auto,\n  .my-sm-auto {\n    margin-top: auto !important; }\n  .mr-sm-auto,\n  .mx-sm-auto {\n    margin-right: auto !important; }\n  .mb-sm-auto,\n  .my-sm-auto {\n    margin-bottom: auto !important; }\n  .ml-sm-auto,\n  .mx-sm-auto {\n    margin-left: auto !important; } }\n\n@media (min-width: 768px) {\n  .m-md-0 {\n    margin: 0 !important; }\n  .mt-md-0,\n  .my-md-0 {\n    margin-top: 0 !important; }\n  .mr-md-0,\n  .mx-md-0 {\n    margin-right: 0 !important; }\n  .mb-md-0,\n  .my-md-0 {\n    margin-bottom: 0 !important; }\n  .ml-md-0,\n  .mx-md-0 {\n    margin-left: 0 !important; }\n  .m-md-1 {\n    margin: 0.25rem !important; }\n  .mt-md-1,\n  .my-md-1 {\n    margin-top: 0.25rem !important; }\n  .mr-md-1,\n  .mx-md-1 {\n    margin-right: 0.25rem !important; }\n  .mb-md-1,\n  .my-md-1 {\n    margin-bottom: 0.25rem !important; }\n  .ml-md-1,\n  .mx-md-1 {\n    margin-left: 0.25rem !important; }\n  .m-md-2 {\n    margin: 0.5rem !important; }\n  .mt-md-2,\n  .my-md-2 {\n    margin-top: 0.5rem !important; }\n  .mr-md-2,\n  .mx-md-2 {\n    margin-right: 0.5rem !important; }\n  .mb-md-2,\n  .my-md-2 {\n    margin-bottom: 0.5rem !important; }\n  .ml-md-2,\n  .mx-md-2 {\n    margin-left: 0.5rem !important; }\n  .m-md-3 {\n    margin: 1rem !important; }\n  .mt-md-3,\n  .my-md-3 {\n    margin-top: 1rem !important; }\n  .mr-md-3,\n  .mx-md-3 {\n    margin-right: 1rem !important; }\n  .mb-md-3,\n  .my-md-3 {\n    margin-bottom: 1rem !important; }\n  .ml-md-3,\n  .mx-md-3 {\n    margin-left: 1rem !important; }\n  .m-md-4 {\n    margin: 1.5rem !important; }\n  .mt-md-4,\n  .my-md-4 {\n    margin-top: 1.5rem !important; }\n  .mr-md-4,\n  .mx-md-4 {\n    margin-right: 1.5rem !important; }\n  .mb-md-4,\n  .my-md-4 {\n    margin-bottom: 1.5rem !important; }\n  .ml-md-4,\n  .mx-md-4 {\n    margin-left: 1.5rem !important; }\n  .m-md-5 {\n    margin: 3rem !important; }\n  .mt-md-5,\n  .my-md-5 {\n    margin-top: 3rem !important; }\n  .mr-md-5,\n  .mx-md-5 {\n    margin-right: 3rem !important; }\n  .mb-md-5,\n  .my-md-5 {\n    margin-bottom: 3rem !important; }\n  .ml-md-5,\n  .mx-md-5 {\n    margin-left: 3rem !important; }\n  .p-md-0 {\n    padding: 0 !important; }\n  .pt-md-0,\n  .py-md-0 {\n    padding-top: 0 !important; }\n  .pr-md-0,\n  .px-md-0 {\n    padding-right: 0 !important; }\n  .pb-md-0,\n  .py-md-0 {\n    padding-bottom: 0 !important; }\n  .pl-md-0,\n  .px-md-0 {\n    padding-left: 0 !important; }\n  .p-md-1 {\n    padding: 0.25rem !important; }\n  .pt-md-1,\n  .py-md-1 {\n    padding-top: 0.25rem !important; }\n  .pr-md-1,\n  .px-md-1 {\n    padding-right: 0.25rem !important; }\n  .pb-md-1,\n  .py-md-1 {\n    padding-bottom: 0.25rem !important; }\n  .pl-md-1,\n  .px-md-1 {\n    padding-left: 0.25rem !important; }\n  .p-md-2 {\n    padding: 0.5rem !important; }\n  .pt-md-2,\n  .py-md-2 {\n    padding-top: 0.5rem !important; }\n  .pr-md-2,\n  .px-md-2 {\n    padding-right: 0.5rem !important; }\n  .pb-md-2,\n  .py-md-2 {\n    padding-bottom: 0.5rem !important; }\n  .pl-md-2,\n  .px-md-2 {\n    padding-left: 0.5rem !important; }\n  .p-md-3 {\n    padding: 1rem !important; }\n  .pt-md-3,\n  .py-md-3 {\n    padding-top: 1rem !important; }\n  .pr-md-3,\n  .px-md-3 {\n    padding-right: 1rem !important; }\n  .pb-md-3,\n  .py-md-3 {\n    padding-bottom: 1rem !important; }\n  .pl-md-3,\n  .px-md-3 {\n    padding-left: 1rem !important; }\n  .p-md-4 {\n    padding: 1.5rem !important; }\n  .pt-md-4,\n  .py-md-4 {\n    padding-top: 1.5rem !important; }\n  .pr-md-4,\n  .px-md-4 {\n    padding-right: 1.5rem !important; }\n  .pb-md-4,\n  .py-md-4 {\n    padding-bottom: 1.5rem !important; }\n  .pl-md-4,\n  .px-md-4 {\n    padding-left: 1.5rem !important; }\n  .p-md-5 {\n    padding: 3rem !important; }\n  .pt-md-5,\n  .py-md-5 {\n    padding-top: 3rem !important; }\n  .pr-md-5,\n  .px-md-5 {\n    padding-right: 3rem !important; }\n  .pb-md-5,\n  .py-md-5 {\n    padding-bottom: 3rem !important; }\n  .pl-md-5,\n  .px-md-5 {\n    padding-left: 3rem !important; }\n  .m-md-auto {\n    margin: auto !important; }\n  .mt-md-auto,\n  .my-md-auto {\n    margin-top: auto !important; }\n  .mr-md-auto,\n  .mx-md-auto {\n    margin-right: auto !important; }\n  .mb-md-auto,\n  .my-md-auto {\n    margin-bottom: auto !important; }\n  .ml-md-auto,\n  .mx-md-auto {\n    margin-left: auto !important; } }\n\n@media (min-width: 992px) {\n  .m-lg-0 {\n    margin: 0 !important; }\n  .mt-lg-0,\n  .my-lg-0 {\n    margin-top: 0 !important; }\n  .mr-lg-0,\n  .mx-lg-0 {\n    margin-right: 0 !important; }\n  .mb-lg-0,\n  .my-lg-0 {\n    margin-bottom: 0 !important; }\n  .ml-lg-0,\n  .mx-lg-0 {\n    margin-left: 0 !important; }\n  .m-lg-1 {\n    margin: 0.25rem !important; }\n  .mt-lg-1,\n  .my-lg-1 {\n    margin-top: 0.25rem !important; }\n  .mr-lg-1,\n  .mx-lg-1 {\n    margin-right: 0.25rem !important; }\n  .mb-lg-1,\n  .my-lg-1 {\n    margin-bottom: 0.25rem !important; }\n  .ml-lg-1,\n  .mx-lg-1 {\n    margin-left: 0.25rem !important; }\n  .m-lg-2 {\n    margin: 0.5rem !important; }\n  .mt-lg-2,\n  .my-lg-2 {\n    margin-top: 0.5rem !important; }\n  .mr-lg-2,\n  .mx-lg-2 {\n    margin-right: 0.5rem !important; }\n  .mb-lg-2,\n  .my-lg-2 {\n    margin-bottom: 0.5rem !important; }\n  .ml-lg-2,\n  .mx-lg-2 {\n    margin-left: 0.5rem !important; }\n  .m-lg-3 {\n    margin: 1rem !important; }\n  .mt-lg-3,\n  .my-lg-3 {\n    margin-top: 1rem !important; }\n  .mr-lg-3,\n  .mx-lg-3 {\n    margin-right: 1rem !important; }\n  .mb-lg-3,\n  .my-lg-3 {\n    margin-bottom: 1rem !important; }\n  .ml-lg-3,\n  .mx-lg-3 {\n    margin-left: 1rem !important; }\n  .m-lg-4 {\n    margin: 1.5rem !important; }\n  .mt-lg-4,\n  .my-lg-4 {\n    margin-top: 1.5rem !important; }\n  .mr-lg-4,\n  .mx-lg-4 {\n    margin-right: 1.5rem !important; }\n  .mb-lg-4,\n  .my-lg-4 {\n    margin-bottom: 1.5rem !important; }\n  .ml-lg-4,\n  .mx-lg-4 {\n    margin-left: 1.5rem !important; }\n  .m-lg-5 {\n    margin: 3rem !important; }\n  .mt-lg-5,\n  .my-lg-5 {\n    margin-top: 3rem !important; }\n  .mr-lg-5,\n  .mx-lg-5 {\n    margin-right: 3rem !important; }\n  .mb-lg-5,\n  .my-lg-5 {\n    margin-bottom: 3rem !important; }\n  .ml-lg-5,\n  .mx-lg-5 {\n    margin-left: 3rem !important; }\n  .p-lg-0 {\n    padding: 0 !important; }\n  .pt-lg-0,\n  .py-lg-0 {\n    padding-top: 0 !important; }\n  .pr-lg-0,\n  .px-lg-0 {\n    padding-right: 0 !important; }\n  .pb-lg-0,\n  .py-lg-0 {\n    padding-bottom: 0 !important; }\n  .pl-lg-0,\n  .px-lg-0 {\n    padding-left: 0 !important; }\n  .p-lg-1 {\n    padding: 0.25rem !important; }\n  .pt-lg-1,\n  .py-lg-1 {\n    padding-top: 0.25rem !important; }\n  .pr-lg-1,\n  .px-lg-1 {\n    padding-right: 0.25rem !important; }\n  .pb-lg-1,\n  .py-lg-1 {\n    padding-bottom: 0.25rem !important; }\n  .pl-lg-1,\n  .px-lg-1 {\n    padding-left: 0.25rem !important; }\n  .p-lg-2 {\n    padding: 0.5rem !important; }\n  .pt-lg-2,\n  .py-lg-2 {\n    padding-top: 0.5rem !important; }\n  .pr-lg-2,\n  .px-lg-2 {\n    padding-right: 0.5rem !important; }\n  .pb-lg-2,\n  .py-lg-2 {\n    padding-bottom: 0.5rem !important; }\n  .pl-lg-2,\n  .px-lg-2 {\n    padding-left: 0.5rem !important; }\n  .p-lg-3 {\n    padding: 1rem !important; }\n  .pt-lg-3,\n  .py-lg-3 {\n    padding-top: 1rem !important; }\n  .pr-lg-3,\n  .px-lg-3 {\n    padding-right: 1rem !important; }\n  .pb-lg-3,\n  .py-lg-3 {\n    padding-bottom: 1rem !important; }\n  .pl-lg-3,\n  .px-lg-3 {\n    padding-left: 1rem !important; }\n  .p-lg-4 {\n    padding: 1.5rem !important; }\n  .pt-lg-4,\n  .py-lg-4 {\n    padding-top: 1.5rem !important; }\n  .pr-lg-4,\n  .px-lg-4 {\n    padding-right: 1.5rem !important; }\n  .pb-lg-4,\n  .py-lg-4 {\n    padding-bottom: 1.5rem !important; }\n  .pl-lg-4,\n  .px-lg-4 {\n    padding-left: 1.5rem !important; }\n  .p-lg-5 {\n    padding: 3rem !important; }\n  .pt-lg-5,\n  .py-lg-5 {\n    padding-top: 3rem !important; }\n  .pr-lg-5,\n  .px-lg-5 {\n    padding-right: 3rem !important; }\n  .pb-lg-5,\n  .py-lg-5 {\n    padding-bottom: 3rem !important; }\n  .pl-lg-5,\n  .px-lg-5 {\n    padding-left: 3rem !important; }\n  .m-lg-auto {\n    margin: auto !important; }\n  .mt-lg-auto,\n  .my-lg-auto {\n    margin-top: auto !important; }\n  .mr-lg-auto,\n  .mx-lg-auto {\n    margin-right: auto !important; }\n  .mb-lg-auto,\n  .my-lg-auto {\n    margin-bottom: auto !important; }\n  .ml-lg-auto,\n  .mx-lg-auto {\n    margin-left: auto !important; } }\n\n@media (min-width: 1200px) {\n  .m-xl-0 {\n    margin: 0 !important; }\n  .mt-xl-0,\n  .my-xl-0 {\n    margin-top: 0 !important; }\n  .mr-xl-0,\n  .mx-xl-0 {\n    margin-right: 0 !important; }\n  .mb-xl-0,\n  .my-xl-0 {\n    margin-bottom: 0 !important; }\n  .ml-xl-0,\n  .mx-xl-0 {\n    margin-left: 0 !important; }\n  .m-xl-1 {\n    margin: 0.25rem !important; }\n  .mt-xl-1,\n  .my-xl-1 {\n    margin-top: 0.25rem !important; }\n  .mr-xl-1,\n  .mx-xl-1 {\n    margin-right: 0.25rem !important; }\n  .mb-xl-1,\n  .my-xl-1 {\n    margin-bottom: 0.25rem !important; }\n  .ml-xl-1,\n  .mx-xl-1 {\n    margin-left: 0.25rem !important; }\n  .m-xl-2 {\n    margin: 0.5rem !important; }\n  .mt-xl-2,\n  .my-xl-2 {\n    margin-top: 0.5rem !important; }\n  .mr-xl-2,\n  .mx-xl-2 {\n    margin-right: 0.5rem !important; }\n  .mb-xl-2,\n  .my-xl-2 {\n    margin-bottom: 0.5rem !important; }\n  .ml-xl-2,\n  .mx-xl-2 {\n    margin-left: 0.5rem !important; }\n  .m-xl-3 {\n    margin: 1rem !important; }\n  .mt-xl-3,\n  .my-xl-3 {\n    margin-top: 1rem !important; }\n  .mr-xl-3,\n  .mx-xl-3 {\n    margin-right: 1rem !important; }\n  .mb-xl-3,\n  .my-xl-3 {\n    margin-bottom: 1rem !important; }\n  .ml-xl-3,\n  .mx-xl-3 {\n    margin-left: 1rem !important; }\n  .m-xl-4 {\n    margin: 1.5rem !important; }\n  .mt-xl-4,\n  .my-xl-4 {\n    margin-top: 1.5rem !important; }\n  .mr-xl-4,\n  .mx-xl-4 {\n    margin-right: 1.5rem !important; }\n  .mb-xl-4,\n  .my-xl-4 {\n    margin-bottom: 1.5rem !important; }\n  .ml-xl-4,\n  .mx-xl-4 {\n    margin-left: 1.5rem !important; }\n  .m-xl-5 {\n    margin: 3rem !important; }\n  .mt-xl-5,\n  .my-xl-5 {\n    margin-top: 3rem !important; }\n  .mr-xl-5,\n  .mx-xl-5 {\n    margin-right: 3rem !important; }\n  .mb-xl-5,\n  .my-xl-5 {\n    margin-bottom: 3rem !important; }\n  .ml-xl-5,\n  .mx-xl-5 {\n    margin-left: 3rem !important; }\n  .p-xl-0 {\n    padding: 0 !important; }\n  .pt-xl-0,\n  .py-xl-0 {\n    padding-top: 0 !important; }\n  .pr-xl-0,\n  .px-xl-0 {\n    padding-right: 0 !important; }\n  .pb-xl-0,\n  .py-xl-0 {\n    padding-bottom: 0 !important; }\n  .pl-xl-0,\n  .px-xl-0 {\n    padding-left: 0 !important; }\n  .p-xl-1 {\n    padding: 0.25rem !important; }\n  .pt-xl-1,\n  .py-xl-1 {\n    padding-top: 0.25rem !important; }\n  .pr-xl-1,\n  .px-xl-1 {\n    padding-right: 0.25rem !important; }\n  .pb-xl-1,\n  .py-xl-1 {\n    padding-bottom: 0.25rem !important; }\n  .pl-xl-1,\n  .px-xl-1 {\n    padding-left: 0.25rem !important; }\n  .p-xl-2 {\n    padding: 0.5rem !important; }\n  .pt-xl-2,\n  .py-xl-2 {\n    padding-top: 0.5rem !important; }\n  .pr-xl-2,\n  .px-xl-2 {\n    padding-right: 0.5rem !important; }\n  .pb-xl-2,\n  .py-xl-2 {\n    padding-bottom: 0.5rem !important; }\n  .pl-xl-2,\n  .px-xl-2 {\n    padding-left: 0.5rem !important; }\n  .p-xl-3 {\n    padding: 1rem !important; }\n  .pt-xl-3,\n  .py-xl-3 {\n    padding-top: 1rem !important; }\n  .pr-xl-3,\n  .px-xl-3 {\n    padding-right: 1rem !important; }\n  .pb-xl-3,\n  .py-xl-3 {\n    padding-bottom: 1rem !important; }\n  .pl-xl-3,\n  .px-xl-3 {\n    padding-left: 1rem !important; }\n  .p-xl-4 {\n    padding: 1.5rem !important; }\n  .pt-xl-4,\n  .py-xl-4 {\n    padding-top: 1.5rem !important; }\n  .pr-xl-4,\n  .px-xl-4 {\n    padding-right: 1.5rem !important; }\n  .pb-xl-4,\n  .py-xl-4 {\n    padding-bottom: 1.5rem !important; }\n  .pl-xl-4,\n  .px-xl-4 {\n    padding-left: 1.5rem !important; }\n  .p-xl-5 {\n    padding: 3rem !important; }\n  .pt-xl-5,\n  .py-xl-5 {\n    padding-top: 3rem !important; }\n  .pr-xl-5,\n  .px-xl-5 {\n    padding-right: 3rem !important; }\n  .pb-xl-5,\n  .py-xl-5 {\n    padding-bottom: 3rem !important; }\n  .pl-xl-5,\n  .px-xl-5 {\n    padding-left: 3rem !important; }\n  .m-xl-auto {\n    margin: auto !important; }\n  .mt-xl-auto,\n  .my-xl-auto {\n    margin-top: auto !important; }\n  .mr-xl-auto,\n  .mx-xl-auto {\n    margin-right: auto !important; }\n  .mb-xl-auto,\n  .my-xl-auto {\n    margin-bottom: auto !important; }\n  .ml-xl-auto,\n  .mx-xl-auto {\n    margin-left: auto !important; } }\n\n.text-justify {\n  text-align: justify !important; }\n\n.text-nowrap {\n  white-space: nowrap !important; }\n\n.text-truncate {\n  overflow: hidden;\n  text-overflow: ellipsis;\n  white-space: nowrap; }\n\n.text-left {\n  text-align: left !important; }\n\n.text-right {\n  text-align: right !important; }\n\n.text-center {\n  text-align: center !important; }\n\n@media (min-width: 576px) {\n  .text-sm-left {\n    text-align: left !important; }\n  .text-sm-right {\n    text-align: right !important; }\n  .text-sm-center {\n    text-align: center !important; } }\n\n@media (min-width: 768px) {\n  .text-md-left {\n    text-align: left !important; }\n  .text-md-right {\n    text-align: right !important; }\n  .text-md-center {\n    text-align: center !important; } }\n\n@media (min-width: 992px) {\n  .text-lg-left {\n    text-align: left !important; }\n  .text-lg-right {\n    text-align: right !important; }\n  .text-lg-center {\n    text-align: center !important; } }\n\n@media (min-width: 1200px) {\n  .text-xl-left {\n    text-align: left !important; }\n  .text-xl-right {\n    text-align: right !important; }\n  .text-xl-center {\n    text-align: center !important; } }\n\n.text-lowercase {\n  text-transform: lowercase !important; }\n\n.text-uppercase {\n  text-transform: uppercase !important; }\n\n.text-capitalize {\n  text-transform: capitalize !important; }\n\n.font-weight-light {\n  font-weight: 300 !important; }\n\n.font-weight-normal {\n  font-weight: 400 !important; }\n\n.font-weight-bold {\n  font-weight: 700 !important; }\n\n.font-italic {\n  font-style: italic !important; }\n\n.text-white {\n  color: #fff !important; }\n\n.text-primary {\n  color: #2196F3 !important; }\n\na.text-primary:focus, a.text-primary:hover {\n  color: #0c7cd5 !important; }\n\n.text-secondary {\n  color: #868e96 !important; }\n\na.text-secondary:focus, a.text-secondary:hover {\n  color: #6c757d !important; }\n\n.text-success {\n  color: #66BB6A !important; }\n\na.text-success:focus, a.text-success:hover {\n  color: #49a54e !important; }\n\n.text-info {\n  color: #00BCD4 !important; }\n\na.text-info:focus, a.text-info:hover {\n  color: #008fa1 !important; }\n\n.text-warning {\n  color: #ffc107 !important; }\n\na.text-warning:focus, a.text-warning:hover {\n  color: #d39e00 !important; }\n\n.text-danger {\n  color: #EF5350 !important; }\n\na.text-danger:focus, a.text-danger:hover {\n  color: #eb2521 !important; }\n\n.text-light {\n  color: #f8f9fa !important; }\n\na.text-light:focus, a.text-light:hover {\n  color: #dae0e5 !important; }\n\n.text-dark {\n  color: #343a40 !important; }\n\na.text-dark:focus, a.text-dark:hover {\n  color: #1d2124 !important; }\n\n.text-muted {\n  color: #868e96 !important; }\n\n.text-hide {\n  font: 0/0 a;\n  color: transparent;\n  text-shadow: none;\n  background-color: transparent;\n  border: 0; }\n\n.visible {\n  visibility: visible !important; }\n\n.invisible {\n  visibility: hidden !important; }\n", ""]);

// exports


/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/layout.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "html {\n  height: 100%;\n  background-color: #e5e5e5; }\n\nbody {\n  height: 100%;\n  margin: 0;\n  padding: 0; }\n\n.full-height {\n  height: 100% !important; }\n\n.app-header {\n  display: block;\n  position: relative;\n  z-index: 1000;\n  height: 60px;\n  width: 100%;\n  background-color: #fff; }\n  .app-header .toggle-sidebar {\n    display: block; }\n\n.fixed-header .app-header {\n  position: fixed;\n  left: 0;\n  top: 0;\n  right: 0; }\n\n@media only screen and (min-width: 992px) {\n  .app-header .toggle-sidebar-btn {\n    display: none; }\n  .app-header .brand {\n    display: inline-block;\n    width: 250px;\n    padding: 0 0 0 64px;\n    transition: padding 0.25s cubic-bezier(0, 0, 0.2, 1); }\n    .app-header .brand a {\n      text-decoration: none;\n      font-weight: normal; }\n  .nav-behind .app-header .brand {\n    padding-left: 0; } }\n\n.app-sidebar {\n  display: block;\n  z-index: 99;\n  position: fixed;\n  left: 0;\n  bottom: 0;\n  top: 0;\n  height: 100vh;\n  width: 250px;\n  overflow: hidden;\n  background-color: #343a40; }\n  .app-sidebar .sidebar-header {\n    display: block;\n    position: relative;\n    height: 60px; }\n  .app-sidebar .sidebar-footer {\n    background-color: #343a40; }\n\n@media only screen and (min-width: 992px) {\n  .app-sidebar {\n    z-index: 1001;\n    overflow-x: hidden; } }\n\n.app-page-container {\n  width: 100%;\n  height: 100%; }\n  .app-page-container .app-content-wrapper {\n    min-height: 100%;\n    position: relative;\n    background-color: #fff; }\n    .app-page-container .app-content-wrapper .app-content {\n      z-index: 10;\n      padding-bottom: 44px;\n      min-height: 100%;\n      transition: all 0.3s ease; }\n      .app-page-container .app-content-wrapper .app-content.full-width {\n        width: 100%; }\n\n.fixed-header .app-page-container .app-content-wrapper .app-content {\n  padding-top: 60px; }\n\n.app-page-container.scroll-disabled {\n  overflow: hidden; }\n\n@media only screen and (max-width: 991px) {\n  .app-page-container {\n    position: relative;\n    z-index: 100;\n    padding-left: 0;\n    transition: -webkit-transform .25s ease;\n    transition: transform .25s ease;\n    transition: transform .25s ease, -webkit-transform .25s ease;\n    background-color: #fff; }\n    .app-page-container .app-content-wrapper .app-content {\n      overflow-x: hidden; }\n  .sidebar-mobile-open .app-page-container {\n    overflow: hidden;\n    position: fixed;\n    -webkit-transform: translateX(250px);\n            transform: translateX(250px); } }\n\n@media only screen and (min-width: 992px) {\n  .app-page-container .app-content-wrapper .app-content {\n    padding-left: 250px; }\n  .app-page-container .app-content-wrapper .app-footer {\n    left: 250px; }\n  .nav-collapsed .app-page-container .app-content-wrapper .app-content {\n    padding-left: 64px; }\n  .nav-collapsed .app-page-container .app-content-wrapper .app-footer {\n    left: 64px; } }\n\n.app-page-container .app-footer {\n  position: absolute;\n  left: 0;\n  right: 0;\n  bottom: 0;\n  transition: left 0.3s cubic-bezier(0, 0, 0.2, 1); }\n  .app-page-container .app-footer.fixed {\n    position: fixed; }\n\n.app-page-container .app-footer {\n  padding: 13px 0;\n  border-top: 1px solid rgba(0, 0, 0, 0.05);\n  font-size: 11px;\n  line-height: 17px;\n  color: #868e96; }\n  .app-page-container .app-footer .brand {\n    color: rgba(0, 0, 0, 0.87);\n    text-transform: uppercase;\n    letter-spacing: 0.02em; }\n  .app-page-container .app-footer .material-icons {\n    font-size: 0.875rem;\n    vertical-align: text-top; }\n\n.quickview-wrapper {\n  z-index: 1001;\n  position: fixed;\n  top: 0;\n  bottom: 0;\n  height: 100vh;\n  right: -300px;\n  width: 300px;\n  background: #fff;\n  transition: right 0.4s cubic-bezier(0.05, 0.74, 0.2, 0.99);\n  -webkit-backface-visibility: hidden;\n          backface-visibility: hidden; }\n\n.quickview-open .quickview-wrapper {\n  right: 0; }\n\n.quickview-wrapper {\n  border-left: 1px solid rgba(0, 0, 0, 0.05);\n  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1); }\n\n.app-overlay {\n  opacity: 0;\n  visibility: hidden;\n  transition: opacity .3s ease,  visibility .3s ease;\n  z-index: 1040;\n  position: fixed;\n  left: 0;\n  right: 0;\n  top: 0;\n  bottom: 0;\n  background: #fff;\n  overflow: hidden; }\n  .app-overlay .overlay-close {\n    position: absolute;\n    right: 20px;\n    top: 25px; }\n\n.overlay-active .app-overlay {\n  visibility: visible;\n  opacity: 1; }\n\n.app-main {\n  max-width: 100%;\n  margin: auto;\n  transition: max-width .35s ease; }\n  .app-main .app-sidebar {\n    left: auto; }\n  .app-main .app-header > .app-header-inner {\n    max-width: 100%;\n    margin: auto;\n    transition: max-width .35s ease; }\n\n@media only screen and (min-width: 992px) {\n  .layout-boxed.app-main {\n    max-width: 1200px;\n    box-shadow: 0 0 1px rgba(0, 0, 0, 0.2); }\n    .layout-boxed.app-main .app-header > .app-header-inner {\n      max-width: 1200px;\n      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2); }\n    .layout-boxed.app-main .app-overlay > .app-overlay-inner {\n      max-width: 1200px;\n      margin: auto; } }\n\n@media only screen and (max-width: 991px) {\n  .no-app-sidebar + .app-page-container .app-header .header-icon {\n    display: none; } }\n\n@media only screen and (min-width: 992px) {\n  .no-app-sidebar + .app-page-container .app-header .brand {\n    padding-left: 0; }\n  .no-app-sidebar + .app-page-container .app-content-wrapper .app-content {\n    padding-left: 0; }\n  .no-app-sidebar + .app-page-container .app-content-wrapper .app-footer {\n    left: 0; } }\n\n.app-sidebar ul.nav {\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  -ms-flex-wrap: nowrap;\n      flex-wrap: nowrap; }\n  .app-sidebar ul.nav ul {\n    display: none; }\n  .app-sidebar ul.nav li {\n    position: relative; }\n    .app-sidebar ul.nav li.open > .icon-has-ul {\n      -webkit-transform: rotate(-180deg);\n              transform: rotate(-180deg); }\n  .app-sidebar ul.nav .icon-has-ul {\n    position: absolute;\n    top: 12px;\n    right: 15px;\n    font-size: 18px;\n    line-height: 1;\n    color: #777;\n    transition: -webkit-transform .3s ease-in-out;\n    transition: transform .3s ease-in-out;\n    transition: transform .3s ease-in-out, -webkit-transform .3s ease-in-out; }\n  .app-sidebar ul.nav ul .icon-has-ul {\n    top: 11px; }\n  .app-sidebar ul.nav .nav-divider {\n    background-color: rgba(0, 0, 0, 0.15);\n    min-height: 1px;\n    margin: 10px 0;\n    overflow: hidden; }\n\n.app-sidebar .sidebar-content {\n  height: calc(100% - 104px); }\n\n.sidebar-header a.collapsednav-toggler {\n  display: inline-block;\n  position: absolute;\n  width: 20px;\n  height: 20px;\n  line-height: 20px;\n  text-align: center;\n  right: 18px;\n  top: 20px;\n  color: rgba(255, 255, 255, 0.55); }\n  .sidebar-header a.collapsednav-toggler .material-icons {\n    font-size: 12px; }\n\n@media only screen and (max-width: 991px) {\n  .app-sidebar .sidebar-header a.collapsednav-toggler {\n    display: none; } }\n\n.app-sidebar .sidebar-header {\n  text-align: left; }\n  .app-sidebar .sidebar-header .logo-icon {\n    margin-right: 11px; }\n  .app-sidebar .sidebar-header .logo-img {\n    margin-right: 12px; }\n  .app-sidebar .sidebar-header .brand {\n    display: inline; }\n  .app-sidebar .sidebar-header .collapsednav-toggler {\n    display: inline-block; }\n\n.app-sidebar .sidebar-content .nav-header {\n  display: block; }\n\n.app-sidebar .sidebar-content .nav-text {\n  display: inline; }\n\n.app-sidebar .sidebar-content .icon-has-ul {\n  display: inherit; }\n\n.app-sidebar .sidebar-content .badge {\n  display: inherit;\n  top: 15px;\n  right: 35px; }\n\n.app-sidebar .sidebar-content .nav > li > a {\n  padding: 10px 16px;\n  text-align: left; }\n  .app-sidebar .sidebar-content .nav > li > a .nav-icon {\n    transition: padding 0.35s cubic-bezier(0, 0, 0.2, 1), margin 0.35s cubic-bezier(0, 0, 0.2, 1);\n    margin-right: 18px; }\n\n.app-sidebar .sidebar-content .nav > li ul li > a {\n  text-align: left; }\n  .app-sidebar .sidebar-content .nav > li ul li > a > span {\n    display: inline; }\n  .app-sidebar .sidebar-content .nav > li ul li > a > .material-icons {\n    transition: margin 0.35s cubic-bezier(0, 0, 0.2, 1);\n    margin-right: 18px; }\n\n.app-sidebar .sidebar-footer .nav-text {\n  display: inline; }\n\n.app-sidebar .sidebar-footer .nav > li > a {\n  padding: 10px 16px;\n  text-align: left; }\n  .app-sidebar .sidebar-footer .nav > li > a .nav-icon {\n    margin-right: 15px; }\n\n@media only screen and (min-width: 992px) {\n  .app-sidebar {\n    transition: width 0.3s cubic-bezier(0, 0, 0.2, 1);\n    white-space: nowrap; }\n  .nav-collapsed .app-sidebar {\n    width: 64px; }\n    .nav-collapsed .app-sidebar .sidebar-header {\n      text-align: center; }\n      .nav-collapsed .app-sidebar .sidebar-header .logo-icon,\n      .nav-collapsed .app-sidebar .sidebar-header .logo-img {\n        margin-right: 0; }\n      .nav-collapsed .app-sidebar .sidebar-header .brand {\n        display: none; }\n      .nav-collapsed .app-sidebar .sidebar-header .collapsednav-toggler {\n        display: none; }\n    .nav-collapsed .app-sidebar .sidebar-content .nav-header,\n    .nav-collapsed .app-sidebar .sidebar-content .nav-text,\n    .nav-collapsed .app-sidebar .sidebar-content .icon-has-ul,\n    .nav-collapsed .app-sidebar .sidebar-content .nav ul a > span {\n      display: none; }\n    .nav-collapsed .app-sidebar .sidebar-content .badge {\n      top: 3px;\n      right: 5px; }\n    .nav-collapsed .app-sidebar .sidebar-content .nav > li > a {\n      padding: 12px 16px;\n      text-align: center; }\n    .nav-collapsed .app-sidebar .sidebar-content .nav > li .nav-icon {\n      margin-right: 0; }\n    .nav-collapsed .app-sidebar .sidebar-content .nav > li ul li > a {\n      text-align: center; }\n      .nav-collapsed .app-sidebar .sidebar-content .nav > li ul li > a > .material-icons {\n        margin-right: 0; }\n    .nav-collapsed .app-sidebar .sidebar-footer .nav-text {\n      display: none; }\n    .nav-collapsed .app-sidebar .sidebar-footer .nav > li > a {\n      text-align: center; }\n    .nav-collapsed .app-sidebar .sidebar-footer .nav > li .nav-icon {\n      margin-right: 0; }\n  .nav-collapsed .app-sidebar:hover {\n    width: 250px; }\n    .nav-collapsed .app-sidebar:hover > * {\n      width: 250px; }\n    .nav-collapsed .app-sidebar:hover .sidebar-header {\n      text-align: left; }\n      .nav-collapsed .app-sidebar:hover .sidebar-header .logo-icon {\n        margin-right: 11px; }\n      .nav-collapsed .app-sidebar:hover .sidebar-header .logo-img {\n        margin-right: 12px; }\n      .nav-collapsed .app-sidebar:hover .sidebar-header .brand {\n        display: inline; }\n      .nav-collapsed .app-sidebar:hover .sidebar-header .collapsednav-toggler {\n        display: inline-block; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .nav-header {\n      display: block; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .nav-text {\n      display: inline; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .icon-has-ul {\n      display: inherit; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .badge {\n      display: inherit;\n      top: 15px;\n      right: 35px; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .nav > li > a {\n      padding: 10px 16px;\n      text-align: left; }\n      .nav-collapsed .app-sidebar:hover .sidebar-content .nav > li > a .nav-icon {\n        transition: padding 0.35s cubic-bezier(0, 0, 0.2, 1), margin 0.35s cubic-bezier(0, 0, 0.2, 1);\n        margin-right: 18px; }\n    .nav-collapsed .app-sidebar:hover .sidebar-content .nav > li ul li > a {\n      text-align: left; }\n      .nav-collapsed .app-sidebar:hover .sidebar-content .nav > li ul li > a > span {\n        display: inline; }\n      .nav-collapsed .app-sidebar:hover .sidebar-content .nav > li ul li > a > .material-icons {\n        transition: margin 0.35s cubic-bezier(0, 0, 0.2, 1);\n        margin-right: 18px; }\n    .nav-collapsed .app-sidebar:hover .sidebar-footer .nav-text {\n      display: inline; }\n    .nav-collapsed .app-sidebar:hover .sidebar-footer .nav > li > a {\n      padding: 10px 16px;\n      text-align: left; }\n      .nav-collapsed .app-sidebar:hover .sidebar-footer .nav > li > a .nav-icon {\n        margin-right: 15px; } }\n\n@media only screen and (min-width: 992px) {\n  .nav-behind .app-sidebar {\n    z-index: 999; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-sm.nav-collapsed .app-page-container .app-content-wrapper .app-content,\n  .sidebar-lg.nav-collapsed .app-page-container .app-content-wrapper .app-content {\n    padding-left: 64px; }\n  .sidebar-sm.nav-collapsed .app-page-container .app-content-wrapper .app-footer,\n  .sidebar-lg.nav-collapsed .app-page-container .app-content-wrapper .app-footer {\n    left: 64px; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-sm.nav-collapsed .app-sidebar,\n  .sidebar-lg.nav-collapsed .app-sidebar {\n    width: 64px; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-sm .app-header .brand {\n    width: 220px; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-sm .app-page-container .app-content-wrapper .app-content {\n    padding-left: 220px; }\n  .sidebar-sm .app-page-container .app-content-wrapper .app-footer {\n    left: 220px; } }\n\n@media only screen and (max-width: 991px) {\n  .sidebar-sm.sidebar-mobile-open .app-page-container {\n    -webkit-transform: translateX(220px);\n            transform: translateX(220px); } }\n\n.sidebar-sm .app-sidebar {\n  width: 220px; }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-sm.nav-collapsed .app-sidebar:hover {\n    width: 220px; }\n    .sidebar-sm.nav-collapsed .app-sidebar:hover > * {\n      width: 220px; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-lg .app-header .brand {\n    width: 280px; } }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-lg .app-page-container .app-content-wrapper .app-content {\n    padding-left: 280px; }\n  .sidebar-lg .app-page-container .app-content-wrapper .app-footer {\n    left: 280px; } }\n\n@media only screen and (max-width: 991px) {\n  .sidebar-lg.sidebar-mobile-open .app-page-container {\n    -webkit-transform: translateX(280px);\n            transform: translateX(280px); } }\n\n.sidebar-lg .app-sidebar {\n  width: 280px; }\n\n@media only screen and (min-width: 992px) {\n  .sidebar-lg.nav-collapsed .app-sidebar:hover {\n    width: 280px; }\n    .sidebar-lg.nav-collapsed .app-sidebar:hover > * {\n      width: 280px; } }\n\n.app-header {\n  padding: 0;\n  border: 0;\n  text-align: center; }\n  .app-header .app-header-inner {\n    height: 60px;\n    box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12); }\n  @media only screen and (min-width: 992px) {\n    .app-header {\n      text-align: inherit; } }\n  .app-header.bg-transparent {\n    background-color: transparent !important; }\n  .app-header .brand {\n    display: inline-block;\n    text-align: center;\n    float: left; }\n    .app-header .brand h2 {\n      font-size: 30px;\n      margin: 0;\n      line-height: 60px; }\n  .app-header .header-icon {\n    display: inline-block;\n    height: 60px;\n    padding: 0 14px; }\n    @media only screen and (min-width: 992px) {\n      .app-header .header-icon {\n        padding-left: 18px;\n        padding-right: 18px; } }\n    .app-header .header-icon .material-icons {\n      font-size: 24px; }\n\n.top-nav-left > ul > .list-inline-item,\n.top-nav-right > ul > .list-inline-item {\n  margin: 0;\n  padding: 0; }\n\n.top-nav-left {\n  display: inline-block; }\n  .top-nav-left > ul {\n    display: inline;\n    margin: 0; }\n\n.top-nav-right {\n  display: inline-block;\n  font-size: 16px;\n  line-height: 24px;\n  float: right; }\n  .top-nav-right ul {\n    margin: 0; }\n  .top-nav-right li {\n    height: 60px;\n    float: left; }\n  .top-nav-right a:hover, .top-nav-right a:focus {\n    text-decoration: none; }\n\n.logo-img {\n  width: 24px;\n  height: 24px;\n  margin-bottom: -3px; }\n  .logo-img .st1 {\n    opacity: .9; }\n\n.bg-color-dark > .logo-img .st0,\n.bg-color-primary > .logo-img .st0,\n.bg-color-info > .logo-img .st0,\n.bg-color-danger > .logo-img .st0,\n.bg-color-success > .logo-img .st0 {\n  fill: #fff; }\n\n.bg-color-warning > .logo-img .st0,\n.bg-color-light > .logo-img .st0 {\n  fill: rgba(0, 0, 0, 0.87); }\n\n.app-sidebar {\n  box-shadow: 1px 0 2px rgba(0, 0, 0, 0.15); }\n  .app-sidebar .sidebar-header {\n    line-height: 60px;\n    padding: 0 18px;\n    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1); }\n    .app-sidebar .sidebar-header .logo-icon {\n      text-align: center;\n      font-size: 24px; }\n      .app-sidebar .sidebar-header .logo-icon.material-icons {\n        line-height: 60px;\n        height: 60px;\n        vertical-align: sub; }\n    .app-sidebar .sidebar-header a.brand {\n      display: inline-block;\n      font-weight: normal;\n      font-size: 23px;\n      line-height: 60px;\n      text-decoration: none; }\n  .app-sidebar .sidebar-footer {\n    position: absolute;\n    bottom: 0;\n    left: 0;\n    right: 0;\n    height: 44px;\n    border-top: 1px solid rgba(0, 0, 0, 0.1);\n    background-color: #343a40; }\n\n.app-sidebar .nav a {\n  display: block;\n  position: relative;\n  text-decoration: none; }\n  .app-sidebar .nav a:hover {\n    cursor: pointer; }\n\n.app-sidebar .nav li {\n  position: relative; }\n  .app-sidebar .nav li .badge {\n    position: absolute;\n    padding: 3px 6px; }\n\n.app-sidebar .nav .nav-header {\n  margin: 15px 15px 5px;\n  font-size: 0.875rem; }\n\n.app-sidebar .nav .nav-divider + .nav-header {\n  margin-top: 5px; }\n\n.app-sidebar .nav > li > a {\n  line-height: 24px; }\n\n.app-sidebar .nav .nav-icon {\n  display: inline-block; }\n  .app-sidebar .nav .nav-icon.material-icons {\n    width: 24px;\n    height: 24px;\n    font-size: 18px;\n    line-height: 24px;\n    text-align: center; }\n    .app-sidebar .nav .nav-icon.material-icons.nav-dot {\n      font-size: 16px; }\n\n.app-sidebar .nav ul {\n  list-style: none;\n  padding: 0; }\n  .app-sidebar .nav ul li > a {\n    padding: 10px 18px; }\n    .app-sidebar .nav ul li > a .material-icons {\n      width: 20px;\n      height: 20px;\n      font-size: 16px;\n      line-height: 1;\n      text-align: center;\n      vertical-align: middle;\n      margin-bottom: -2px; }\n\n.app-sidebar .nav ul ul li > a {\n  padding: 10px 15px 10px 56px; }\n\n.app-sidebar .nav ul ul ul li > a {\n  padding-left: 74px; }\n\n.app-sidebar {\n  background-color: #343a40; }\n  .app-sidebar .nav {\n    color: #a1a1a1; }\n    .app-sidebar .nav a {\n      color: #a1a1a1; }\n    .app-sidebar .nav .nav-header {\n      color: #868e96; }\n    .app-sidebar .nav .nav-divider {\n      background-color: rgba(0, 0, 0, 0.15); }\n    .app-sidebar .nav li > a:hover, .app-sidebar .nav li > a:focus {\n      background-color: transparent;\n      color: #fafafa; }\n    .app-sidebar .nav li.active > a,\n    .app-sidebar .nav li.active > a:hover,\n    .app-sidebar .nav li.active > a:focus {\n      background-color: transparent;\n      color: #fafafa; }\n    .app-sidebar .nav li.open > a,\n    .app-sidebar .nav li.open > a:hover,\n    .app-sidebar .nav li.open > a:focus {\n      background-color: rgba(0, 0, 0, 0.1);\n      color: #fafafa; }\n    .app-sidebar .nav ul {\n      background-color: rgba(0, 0, 0, 0.1); }\n      .app-sidebar .nav ul li.active > a,\n      .app-sidebar .nav ul li.active > a:hover,\n      .app-sidebar .nav ul li.active > a:focus, .app-sidebar .nav ul li.open > a,\n      .app-sidebar .nav ul li.open > a:hover,\n      .app-sidebar .nav ul li.open > a:focus {\n        background-color: rgba(0, 0, 0, 0.1); }\n    .app-sidebar .nav ul ul {\n      background-color: rgba(0, 0, 0, 0.1); }\n      .app-sidebar .nav ul ul > li.active > a,\n      .app-sidebar .nav ul ul > li.active > a:hover,\n      .app-sidebar .nav ul ul > li.active > a:focus, .app-sidebar .nav ul ul > li.open > a,\n      .app-sidebar .nav ul ul > li.open > a:hover,\n      .app-sidebar .nav ul ul > li.open > a:focus {\n        background-color: rgba(0, 0, 0, 0.1);\n        color: #fafafa; }\n    .app-sidebar .nav ul ul ul {\n      background-color: rgba(0, 0, 0, 0.1); }\n\n.app-overlay .app-overlay-inner {\n  max-width: 1090px;\n  margin: 0 auto;\n  padding: 20px 30px; }\n  @media only screen and (min-width: 768px) {\n    .app-overlay .app-overlay-inner {\n      padding: 20px 100px; } }\n\n.app-overlay input.overlay-search-input {\n  border: 0;\n  background-color: transparent;\n  font-size: 35px;\n  font-weight: normal;\n  width: 100%;\n  padding-left: 0;\n  line-height: 1; }\n  @media only screen and (min-width: 768px) {\n    .app-overlay input.overlay-search-input {\n      font-size: 70px; } }\n  .app-overlay input.overlay-search-input:focus {\n    outline: none; }\n\n.app-overlay .overlay-header {\n  position: relative;\n  padding: 60px 0 0; }\n  .app-overlay .overlay-header h2 {\n    font-size: 18px;\n    font-weight: normal;\n    margin: 0; }\n    @media only screen and (min-width: 768px) {\n      .app-overlay .overlay-header h2 {\n        font-size: 24px; } }\n\n.app-overlay a.overlay-close {\n  position: absolute;\n  top: 0;\n  right: 10px;\n  font-weight: 300; }\n  .app-overlay a.overlay-close .material-icons {\n    font-size: 32px; }\n\n.app-overlay .overlay-content {\n  margin: 12px 0 0; }\n\n.app-overlay {\n  background: rgba(255, 255, 255, 0.9); }\n  .app-overlay a.overlay-close {\n    color: rgba(0, 0, 0, 0.87); }\n\n.quickview-open-app #quickview-app {\n  right: 0; }\n\n.quickview-app .quickview-close {\n  display: inline-block;\n  z-index: 1;\n  position: absolute;\n  top: 11px;\n  right: 15px;\n  line-height: 24px;\n  color: rgba(0, 0, 0, 0.87);\n  opacity: .6; }\n  .quickview-app .quickview-close .material-icons {\n    font-size: 1rem;\n    vertical-align: middle; }\n  .quickview-app .quickview-close:hover {\n    opacity: 1; }\n\n#quickview-customizer {\n  width: 410px;\n  right: -410px; }\n\n.quickview-open-customizer #quickview-customizer {\n  right: 0; }\n\n.customizer {\n  padding: 0;\n  background-color: #fafafa; }\n  .customizer .quickview-inner {\n    padding: 15px 45px; }\n  .customizer .customizer-header {\n    text-transform: uppercase;\n    margin-bottom: 3px; }\n  .customizer h4.section-header {\n    margin: 12px 0 0;\n    font-size: 16px;\n    line-height: 1.35;\n    font-weight: normal; }\n  .customizer a {\n    position: relative;\n    display: block;\n    width: 100%;\n    color: rgba(0, 0, 0, 0.87); }\n  .customizer .customizer-close {\n    position: absolute;\n    right: 10px;\n    top: 10px;\n    padding: 7px;\n    width: auto;\n    z-index: 10; }\n    .customizer .customizer-close .material-icons {\n      font-size: 20px; }\n  .customizer a.customizer-toggle,\n  .customizer a.customizer-close {\n    color: rgba(0, 0, 0, 0.87); }\n    .customizer a.customizer-toggle:hover, .customizer a.customizer-toggle:focus,\n    .customizer a.customizer-close:hover,\n    .customizer a.customizer-close:focus {\n      color: rgba(0, 0, 0, 0.87); }\n  .customizer .customizer-toggle {\n    position: absolute;\n    top: 25%;\n    width: 54px;\n    height: 50px;\n    left: -48px;\n    text-align: center;\n    line-height: 50px;\n    cursor: pointer; }\n    .customizer .customizer-toggle .material-icons {\n      font-size: 16px;\n      line-height: 50px; }\n  .customizer:before {\n    position: absolute;\n    content: '';\n    top: 25%;\n    left: -47px;\n    width: 48px;\n    height: 50px;\n    background-color: #fafafa;\n    box-shadow: 0 0 9px rgba(0, 0, 0, 0.1);\n    border-left: 1px solid rgba(0, 0, 0, 0.1);\n    border-radius: 0 4px 4px 0; }\n  .customizer:after {\n    position: absolute;\n    top: 25%;\n    left: 0;\n    content: '';\n    width: 5px;\n    height: 50px;\n    background-color: #fafafa; }\n  .customizer md-list {\n    padding: 0; }\n  .customizer md-list-item, .customizer md-list-item ._md-list-item-inner {\n    min-height: 40px; }\n  .customizer md-list-item ._md-no-style, .customizer md-list-item._md-no-proxy {\n    padding: 0 5px; }\n\n.theme-gray .customizer a,\n.theme-dark .customizer a {\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-gray .customizer a:hover, .theme-gray .customizer a:focus,\n  .theme-dark .customizer a:hover,\n  .theme-dark .customizer a:focus {\n    color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .customizer {\n  background-color: #3f474e; }\n  .theme-dark .customizer:before {\n    background-color: #3f474e; }\n  .theme-dark .customizer:after {\n    background-color: #3f474e; }\n\n.theme-gray .customizer {\n  background-color: #484848; }\n  .theme-gray .customizer:before {\n    background-color: #484848; }\n  .theme-gray .customizer:after {\n    background-color: #484848; }\n\n.top-nav-left li .header-btn.md-button,\n.top-nav-right li .header-btn.md-button {\n  margin: 0;\n  line-height: 60px;\n  border-radius: 0;\n  min-width: 52px; }\n  @media only screen and (min-width: 992px) {\n    .top-nav-left li .header-btn.md-button,\n    .top-nav-right li .header-btn.md-button {\n      min-width: 60px; } }\n  .top-nav-left li .header-btn.md-button > .material-icons,\n  .top-nav-right li .header-btn.md-button > .material-icons {\n    vertical-align: middle; }\n  .top-nav-left li .header-btn.md-button .badge,\n  .top-nav-right li .header-btn.md-button .badge {\n    background-color: transparent;\n    position: absolute;\n    top: 6px;\n    right: 3px;\n    color: inherit; }\n\n.app-sidebar .md-button {\n  margin: 0;\n  text-align: left;\n  text-transform: none;\n  border-radius: 0;\n  font-weight: normal;\n  line-height: inherit;\n  min-height: inherit;\n  min-width: inherit; }\n\n.quickview-app md-tabs > md-tabs-wrapper {\n  background-color: #eee;\n  padding: 0 40px; }\n\n.quickview-app md-tabs md-pagination-wrapper {\n  width: 100% !important; }\n\nmd-backdrop.md-sidenav-backdrop,\n.md-sidenav-right {\n  z-index: 1001; }\n\n.md-sidenav-right .md-sidenav-inner {\n  height: 100%; }\n  .md-sidenav-right .md-sidenav-inner > md-tabs {\n    height: 100%; }\n\n.md-sidenav-right md-tabs-canvas > md-pagination-wrapper {\n  width: 100% !important; }\n  .md-sidenav-right md-tabs-canvas > md-pagination-wrapper > md-tab-item {\n    width: 50%; }\n  .md-sidenav-right md-tabs-canvas > md-pagination-wrapper md-ink-bar {\n    color: #2196F3;\n    background: #2196F3; }\n", ""]);

// exports


/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/material2-theme.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "/**\n * Applies styles for users in high contrast mode. Note that this only applies\n * to Microsoft browsers. Chrome can be included by checking for the `html[hc]`\n * attribute, however Chrome handles high contrast differently.\n */\n/* Theme for the ripple elements.*/\n/** The mixins below are shared between mat-menu and mat-select */\n/**\n * This mixin adds the correct panel transform styles based\n * on the direction that the menu panel opens.\n */\n/* stylelint-disable material/no-prefixes */\n/* stylelint-enable */\n/**\n * This mixin contains shared option styles between the select and\n * autocomplete components.\n */\n.mat-elevation-z0 {\n  box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0.2), 0px 0px 0px 0px rgba(0, 0, 0, 0.14), 0px 0px 0px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z1 {\n  box-shadow: 0px 2px 1px -1px rgba(0, 0, 0, 0.2), 0px 1px 1px 0px rgba(0, 0, 0, 0.14), 0px 1px 3px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z2 {\n  box-shadow: 0px 3px 1px -2px rgba(0, 0, 0, 0.2), 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 1px 5px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z3 {\n  box-shadow: 0px 3px 3px -2px rgba(0, 0, 0, 0.2), 0px 3px 4px 0px rgba(0, 0, 0, 0.14), 0px 1px 8px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z4 {\n  box-shadow: 0px 2px 4px -1px rgba(0, 0, 0, 0.2), 0px 4px 5px 0px rgba(0, 0, 0, 0.14), 0px 1px 10px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z5 {\n  box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2), 0px 5px 8px 0px rgba(0, 0, 0, 0.14), 0px 1px 14px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z6 {\n  box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2), 0px 6px 10px 0px rgba(0, 0, 0, 0.14), 0px 1px 18px 0px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z7 {\n  box-shadow: 0px 4px 5px -2px rgba(0, 0, 0, 0.2), 0px 7px 10px 1px rgba(0, 0, 0, 0.14), 0px 2px 16px 1px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z8 {\n  box-shadow: 0px 5px 5px -3px rgba(0, 0, 0, 0.2), 0px 8px 10px 1px rgba(0, 0, 0, 0.14), 0px 3px 14px 2px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z9 {\n  box-shadow: 0px 5px 6px -3px rgba(0, 0, 0, 0.2), 0px 9px 12px 1px rgba(0, 0, 0, 0.14), 0px 3px 16px 2px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z10 {\n  box-shadow: 0px 6px 6px -3px rgba(0, 0, 0, 0.2), 0px 10px 14px 1px rgba(0, 0, 0, 0.14), 0px 4px 18px 3px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z11 {\n  box-shadow: 0px 6px 7px -4px rgba(0, 0, 0, 0.2), 0px 11px 15px 1px rgba(0, 0, 0, 0.14), 0px 4px 20px 3px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z12 {\n  box-shadow: 0px 7px 8px -4px rgba(0, 0, 0, 0.2), 0px 12px 17px 2px rgba(0, 0, 0, 0.14), 0px 5px 22px 4px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z13 {\n  box-shadow: 0px 7px 8px -4px rgba(0, 0, 0, 0.2), 0px 13px 19px 2px rgba(0, 0, 0, 0.14), 0px 5px 24px 4px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z14 {\n  box-shadow: 0px 7px 9px -4px rgba(0, 0, 0, 0.2), 0px 14px 21px 2px rgba(0, 0, 0, 0.14), 0px 5px 26px 4px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z15 {\n  box-shadow: 0px 8px 9px -5px rgba(0, 0, 0, 0.2), 0px 15px 22px 2px rgba(0, 0, 0, 0.14), 0px 6px 28px 5px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z16 {\n  box-shadow: 0px 8px 10px -5px rgba(0, 0, 0, 0.2), 0px 16px 24px 2px rgba(0, 0, 0, 0.14), 0px 6px 30px 5px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z17 {\n  box-shadow: 0px 8px 11px -5px rgba(0, 0, 0, 0.2), 0px 17px 26px 2px rgba(0, 0, 0, 0.14), 0px 6px 32px 5px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z18 {\n  box-shadow: 0px 9px 11px -5px rgba(0, 0, 0, 0.2), 0px 18px 28px 2px rgba(0, 0, 0, 0.14), 0px 7px 34px 6px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z19 {\n  box-shadow: 0px 9px 12px -6px rgba(0, 0, 0, 0.2), 0px 19px 29px 2px rgba(0, 0, 0, 0.14), 0px 7px 36px 6px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z20 {\n  box-shadow: 0px 10px 13px -6px rgba(0, 0, 0, 0.2), 0px 20px 31px 3px rgba(0, 0, 0, 0.14), 0px 8px 38px 7px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z21 {\n  box-shadow: 0px 10px 13px -6px rgba(0, 0, 0, 0.2), 0px 21px 33px 3px rgba(0, 0, 0, 0.14), 0px 8px 40px 7px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z22 {\n  box-shadow: 0px 10px 14px -6px rgba(0, 0, 0, 0.2), 0px 22px 35px 3px rgba(0, 0, 0, 0.14), 0px 8px 42px 7px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z23 {\n  box-shadow: 0px 11px 14px -7px rgba(0, 0, 0, 0.2), 0px 23px 36px 3px rgba(0, 0, 0, 0.14), 0px 9px 44px 8px rgba(0, 0, 0, 0.12); }\n\n.mat-elevation-z24 {\n  box-shadow: 0px 11px 15px -7px rgba(0, 0, 0, 0.2), 0px 24px 38px 3px rgba(0, 0, 0, 0.14), 0px 9px 46px 8px rgba(0, 0, 0, 0.12); }\n\n.mat-h1, .mat-headline, .mat-typography h1 {\n  font: 400 24px/32px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 16px; }\n\n.mat-h2, .mat-title, .mat-typography h2 {\n  font: 500 20px/32px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 16px; }\n\n.mat-h3, .mat-subheading-2, .mat-typography h3 {\n  font: 400 16px/28px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 16px; }\n\n.mat-h4, .mat-subheading-1, .mat-typography h4 {\n  font: 400 15px/24px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 16px; }\n\n.mat-h5, .mat-typography h5 {\n  font-size: 11.62px;\n  font-weight: 400;\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  line-height: 20px;\n  margin: 0 0 12px; }\n\n.mat-h6, .mat-typography h6 {\n  font-size: 9.38px;\n  font-weight: 400;\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  line-height: 20px;\n  margin: 0 0 12px; }\n\n.mat-body-strong, .mat-body-2 {\n  font: 500 14px/24px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-body, .mat-body-1, .mat-typography {\n  font: 400 14px/20px Roboto, \"Helvetica Neue\", sans-serif; }\n  .mat-body p, .mat-body-1 p, .mat-typography p {\n    margin: 0 0 12px; }\n\n.mat-small, .mat-caption {\n  font: 400 12px/20px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-display-4, .mat-typography .mat-display-4 {\n  font: 300 112px/112px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 56px;\n  letter-spacing: -0.05em; }\n\n.mat-display-3, .mat-typography .mat-display-3 {\n  font: 400 56px/56px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 64px;\n  letter-spacing: -0.02em; }\n\n.mat-display-2, .mat-typography .mat-display-2 {\n  font: 400 45px/48px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 64px;\n  letter-spacing: -0.005em; }\n\n.mat-display-1, .mat-typography .mat-display-1 {\n  font: 400 34px/40px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0 0 64px; }\n\n.mat-button, .mat-raised-button, .mat-icon-button, .mat-fab, .mat-mini-fab {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 14px;\n  font-weight: 500; }\n\n.mat-button-toggle {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-card {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-card-title {\n  font-size: 24px;\n  font-weight: 400; }\n\n.mat-card-subtitle,\n.mat-card-content,\n.mat-card-header .mat-card-title {\n  font-size: 14px; }\n\n.mat-checkbox {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-checkbox-layout .mat-checkbox-label {\n  line-height: 24px; }\n\n.mat-chip {\n  font-size: 13px;\n  line-height: 18px; }\n  .mat-chip .mat-chip-remove.mat-icon {\n    font-size: 18px; }\n\n.mat-table {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-header-cell {\n  font-size: 12px;\n  font-weight: 500; }\n\n.mat-cell {\n  font-size: 14px; }\n\n.mat-calendar {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-calendar-body {\n  font-size: 13px; }\n\n.mat-calendar-body-label,\n.mat-calendar-period-button {\n  font-size: 14px;\n  font-weight: 500; }\n\n.mat-calendar-table-header th {\n  font-size: 11px;\n  font-weight: 400; }\n\n.mat-dialog-title {\n  font: 500 20px/32px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-expansion-panel-header {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 15px;\n  font-weight: 400; }\n\n.mat-expansion-panel-content {\n  font: 400 14px/20px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-form-field {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: inherit;\n  font-weight: 400;\n  line-height: 1.125; }\n\n.mat-form-field-wrapper {\n  padding-bottom: 1.25em; }\n\n.mat-form-field-prefix .mat-icon,\n.mat-form-field-suffix .mat-icon {\n  font-size: 150%;\n  line-height: 1.125; }\n\n.mat-form-field-prefix .mat-icon-button,\n.mat-form-field-suffix .mat-icon-button {\n  height: 1.5em;\n  width: 1.5em; }\n  .mat-form-field-prefix .mat-icon-button .mat-icon,\n  .mat-form-field-suffix .mat-icon-button .mat-icon {\n    height: 1.125em;\n    line-height: 1.125; }\n\n.mat-form-field-infix {\n  padding: 0.4375em 0;\n  border-top: 0.84375em solid transparent; }\n\n.mat-form-field-can-float.mat-form-field-should-float .mat-form-field-placeholder {\n  -webkit-transform: translateY(-1.28125em) scale(0.75) perspective(100px) translateZ(0.001px);\n          transform: translateY(-1.28125em) scale(0.75) perspective(100px) translateZ(0.001px);\n  -ms-transform: translateY(-1.28125em) scale(0.75);\n  width: 133.33333333%; }\n\n.mat-form-field-can-float .mat-form-field-autofill-control:-webkit-autofill + .mat-form-field-placeholder-wrapper\n.mat-form-field-placeholder {\n  -webkit-transform: translateY(-1.28125em) scale(0.75) perspective(100px) translateZ(0.001px);\n          transform: translateY(-1.28125em) scale(0.75) perspective(100px) translateZ(0.001px);\n  -ms-transform: translateY(-1.28125em) scale(0.75);\n  width: 133.33333333%; }\n\n.mat-form-field-placeholder-wrapper {\n  top: -0.84375em;\n  padding-top: 0.84375em; }\n\n.mat-form-field-placeholder {\n  top: 1.28125em; }\n\n.mat-form-field-underline {\n  bottom: 1.25em; }\n\n.mat-form-field-subscript-wrapper {\n  font-size: 75%;\n  margin-top: 0.54166667em;\n  top: calc(100% - 1.66666667em); }\n\n.mat-grid-tile-header,\n.mat-grid-tile-footer {\n  font-size: 14px; }\n  .mat-grid-tile-header .mat-line,\n  .mat-grid-tile-footer .mat-line {\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    display: block;\n    box-sizing: border-box; }\n    .mat-grid-tile-header .mat-line:nth-child(n+2),\n    .mat-grid-tile-footer .mat-line:nth-child(n+2) {\n      font-size: 12px; }\n\ninput.mat-input-element {\n  margin-top: -0.0625em; }\n\n.mat-menu-item {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 16px;\n  font-weight: 400; }\n\n.mat-paginator,\n.mat-paginator-page-size .mat-select-trigger {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 12px; }\n\n.mat-radio-button {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-select {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-select-trigger {\n  height: 1.125em; }\n\n.mat-slide-toggle-content {\n  font: 400 14px/20px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-slider-thumb-label-text {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 12px;\n  font-weight: 500; }\n\n.mat-stepper-vertical, .mat-stepper-horizontal {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-step-label {\n  font-size: 14px;\n  font-weight: 400; }\n\n.mat-step-label-selected {\n  font-size: 14px;\n  font-weight: 500; }\n\n.mat-tab-group {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-tab-label, .mat-tab-link {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 14px;\n  font-weight: 500; }\n\n.mat-toolbar,\n.mat-toolbar h1,\n.mat-toolbar h2,\n.mat-toolbar h3,\n.mat-toolbar h4,\n.mat-toolbar h5,\n.mat-toolbar h6 {\n  font: 500 20px/32px Roboto, \"Helvetica Neue\", sans-serif;\n  margin: 0; }\n\n.mat-tooltip {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 10px;\n  padding-top: 6px;\n  padding-bottom: 6px; }\n\n.mat-list-item {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-list-option {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-list .mat-list-item, .mat-nav-list .mat-list-item, .mat-selection-list .mat-list-item {\n  font-size: 16px; }\n  .mat-list .mat-list-item .mat-line, .mat-nav-list .mat-list-item .mat-line, .mat-selection-list .mat-list-item .mat-line {\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    display: block;\n    box-sizing: border-box; }\n    .mat-list .mat-list-item .mat-line:nth-child(n+2), .mat-nav-list .mat-list-item .mat-line:nth-child(n+2), .mat-selection-list .mat-list-item .mat-line:nth-child(n+2) {\n      font-size: 14px; }\n\n.mat-list .mat-list-option, .mat-nav-list .mat-list-option, .mat-selection-list .mat-list-option {\n  font-size: 16px; }\n  .mat-list .mat-list-option .mat-line, .mat-nav-list .mat-list-option .mat-line, .mat-selection-list .mat-list-option .mat-line {\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    display: block;\n    box-sizing: border-box; }\n    .mat-list .mat-list-option .mat-line:nth-child(n+2), .mat-nav-list .mat-list-option .mat-line:nth-child(n+2), .mat-selection-list .mat-list-option .mat-line:nth-child(n+2) {\n      font-size: 14px; }\n\n.mat-list .mat-subheader, .mat-nav-list .mat-subheader, .mat-selection-list .mat-subheader {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 14px;\n  font-weight: 500; }\n\n.mat-list[dense] .mat-list-item, .mat-nav-list[dense] .mat-list-item, .mat-selection-list[dense] .mat-list-item {\n  font-size: 12px; }\n  .mat-list[dense] .mat-list-item .mat-line, .mat-nav-list[dense] .mat-list-item .mat-line, .mat-selection-list[dense] .mat-list-item .mat-line {\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    display: block;\n    box-sizing: border-box; }\n    .mat-list[dense] .mat-list-item .mat-line:nth-child(n+2), .mat-nav-list[dense] .mat-list-item .mat-line:nth-child(n+2), .mat-selection-list[dense] .mat-list-item .mat-line:nth-child(n+2) {\n      font-size: 12px; }\n\n.mat-list[dense] .mat-list-option, .mat-nav-list[dense] .mat-list-option, .mat-selection-list[dense] .mat-list-option {\n  font-size: 12px; }\n  .mat-list[dense] .mat-list-option .mat-line, .mat-nav-list[dense] .mat-list-option .mat-line, .mat-selection-list[dense] .mat-list-option .mat-line {\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    display: block;\n    box-sizing: border-box; }\n    .mat-list[dense] .mat-list-option .mat-line:nth-child(n+2), .mat-nav-list[dense] .mat-list-option .mat-line:nth-child(n+2), .mat-selection-list[dense] .mat-list-option .mat-line:nth-child(n+2) {\n      font-size: 12px; }\n\n.mat-list[dense] .mat-subheader, .mat-nav-list[dense] .mat-subheader, .mat-selection-list[dense] .mat-subheader {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 12px;\n  font-weight: 500; }\n\n.mat-option {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 16px; }\n\n.mat-optgroup-label {\n  font: 500 14px/24px Roboto, \"Helvetica Neue\", sans-serif; }\n\n.mat-simple-snackbar {\n  font-family: Roboto, \"Helvetica Neue\", sans-serif;\n  font-size: 14px; }\n\n.mat-simple-snackbar-action {\n  line-height: 1;\n  font-family: inherit;\n  font-size: inherit;\n  font-weight: 500; }\n\n.mat-ripple {\n  overflow: hidden; }\n  @media screen and (-ms-high-contrast: active) {\n    .mat-ripple {\n      display: none; } }\n\n.mat-ripple.mat-ripple-unbounded {\n  overflow: visible; }\n\n.mat-ripple-element {\n  position: absolute;\n  border-radius: 50%;\n  pointer-events: none;\n  transition: opacity, -webkit-transform 0ms cubic-bezier(0, 0, 0.2, 1);\n  transition: opacity, transform 0ms cubic-bezier(0, 0, 0.2, 1);\n  transition: opacity, transform 0ms cubic-bezier(0, 0, 0.2, 1), -webkit-transform 0ms cubic-bezier(0, 0, 0.2, 1);\n  -webkit-transform: scale(0);\n          transform: scale(0); }\n\n.mat-option {\n  white-space: nowrap;\n  overflow: hidden;\n  text-overflow: ellipsis;\n  display: block;\n  line-height: 48px;\n  height: 48px;\n  padding: 0 16px;\n  text-align: left;\n  text-decoration: none;\n  position: relative;\n  cursor: pointer;\n  outline: none; }\n  .mat-option[disabled] {\n    cursor: default; }\n  [dir='rtl'] .mat-option {\n    text-align: right; }\n  .mat-option .mat-icon {\n    margin-right: 16px; }\n    [dir='rtl'] .mat-option .mat-icon {\n      margin-left: 16px;\n      margin-right: 0; }\n  .mat-option[aria-disabled='true'] {\n    -webkit-user-select: none;\n    -moz-user-select: none;\n    -ms-user-select: none;\n    user-select: none;\n    cursor: default; }\n  .mat-optgroup .mat-option:not(.mat-option-multiple) {\n    padding-left: 32px; }\n    [dir='rtl'] .mat-optgroup .mat-option:not(.mat-option-multiple) {\n      padding-left: 16px;\n      padding-right: 32px; }\n\n.mat-option-text {\n  display: inline-block; }\n\n.mat-option-ripple {\n  top: 0;\n  left: 0;\n  right: 0;\n  bottom: 0;\n  position: absolute;\n  pointer-events: none; }\n  @media screen and (-ms-high-contrast: active) {\n    .mat-option-ripple {\n      opacity: 0.5; } }\n\n.mat-option-pseudo-checkbox {\n  margin-right: 8px; }\n  [dir='rtl'] .mat-option-pseudo-checkbox {\n    margin-left: 8px;\n    margin-right: 0; }\n\n.mat-optgroup-label {\n  white-space: nowrap;\n  overflow: hidden;\n  text-overflow: ellipsis;\n  display: block;\n  line-height: 48px;\n  height: 48px;\n  padding: 0 16px;\n  text-align: left;\n  text-decoration: none;\n  -webkit-user-select: none;\n  -moz-user-select: none;\n  -ms-user-select: none;\n  user-select: none;\n  cursor: default; }\n  .mat-optgroup-label[disabled] {\n    cursor: default; }\n  [dir='rtl'] .mat-optgroup-label {\n    text-align: right; }\n  .mat-optgroup-label .mat-icon {\n    margin-right: 16px; }\n    [dir='rtl'] .mat-optgroup-label .mat-icon {\n      margin-left: 16px;\n      margin-right: 0; }\n\n.cdk-visually-hidden {\n  border: 0;\n  clip: rect(0 0 0 0);\n  height: 1px;\n  margin: -1px;\n  overflow: hidden;\n  padding: 0;\n  position: absolute;\n  width: 1px; }\n\n.cdk-overlay-container, .cdk-global-overlay-wrapper {\n  pointer-events: none;\n  top: 0;\n  left: 0;\n  height: 100%;\n  width: 100%; }\n\n.cdk-overlay-container {\n  position: fixed;\n  z-index: 1000; }\n\n.cdk-global-overlay-wrapper {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  position: absolute;\n  z-index: 1000; }\n\n.cdk-overlay-pane {\n  position: absolute;\n  pointer-events: auto;\n  box-sizing: border-box;\n  z-index: 1000; }\n\n.cdk-overlay-backdrop {\n  position: absolute;\n  top: 0;\n  bottom: 0;\n  left: 0;\n  right: 0;\n  z-index: 1000;\n  pointer-events: auto;\n  -webkit-tap-highlight-color: transparent;\n  transition: opacity 400ms cubic-bezier(0.25, 0.8, 0.25, 1);\n  opacity: 0; }\n  .cdk-overlay-backdrop.cdk-overlay-backdrop-showing {\n    opacity: 0.48; }\n\n.cdk-overlay-dark-backdrop {\n  background: rgba(0, 0, 0, 0.6); }\n\n.cdk-overlay-transparent-backdrop {\n  background: none; }\n\n.cdk-global-scrollblock {\n  position: fixed;\n  width: 100%;\n  overflow-y: scroll; }\n\n.mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.1); }\n\n.mat-option {\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-option:hover:not(.mat-option-disabled), .mat-option:focus:not(.mat-option-disabled) {\n    background: rgba(0, 0, 0, 0.04); }\n  .mat-primary .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #2196f3; }\n  .mat-accent .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #66bb6a; }\n  .mat-warn .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #ef5350; }\n  .mat-option.mat-selected:not(.mat-option-multiple):not(.mat-option-disabled) {\n    background: rgba(0, 0, 0, 0.04); }\n  .mat-option.mat-active {\n    background: rgba(0, 0, 0, 0.04);\n    color: rgba(0, 0, 0, 0.87); }\n  .mat-option.mat-option-disabled {\n    color: rgba(0, 0, 0, 0.38); }\n\n.mat-optgroup-label {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-optgroup-disabled .mat-optgroup-label {\n  color: rgba(0, 0, 0, 0.38); }\n\n.mat-pseudo-checkbox {\n  color: rgba(0, 0, 0, 0.54); }\n  .mat-pseudo-checkbox::after {\n    color: #fafafa; }\n\n.mat-pseudo-checkbox-checked,\n.mat-pseudo-checkbox-indeterminate,\n.mat-accent .mat-pseudo-checkbox-checked,\n.mat-accent .mat-pseudo-checkbox-indeterminate {\n  background: #66bb6a; }\n\n.mat-primary .mat-pseudo-checkbox-checked,\n.mat-primary .mat-pseudo-checkbox-indeterminate {\n  background: #2196f3; }\n\n.mat-warn .mat-pseudo-checkbox-checked,\n.mat-warn .mat-pseudo-checkbox-indeterminate {\n  background: #ef5350; }\n\n.mat-pseudo-checkbox-checked.mat-pseudo-checkbox-disabled,\n.mat-pseudo-checkbox-indeterminate.mat-pseudo-checkbox-disabled {\n  background: #b0b0b0; }\n\n.mat-app-background {\n  background-color: #fafafa; }\n\n.mat-theme-loaded-marker {\n  display: none; }\n\n.mat-autocomplete-panel {\n  background: white;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover) {\n    background: white; }\n    .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover):not(.mat-option-disabled) {\n      color: rgba(0, 0, 0, 0.87); }\n\n.mat-button, .mat-icon-button {\n  background: transparent; }\n  .mat-button.mat-primary .mat-button-focus-overlay, .mat-icon-button.mat-primary .mat-button-focus-overlay {\n    background-color: rgba(33, 150, 243, 0.12); }\n  .mat-button.mat-accent .mat-button-focus-overlay, .mat-icon-button.mat-accent .mat-button-focus-overlay {\n    background-color: rgba(102, 187, 106, 0.12); }\n  .mat-button.mat-warn .mat-button-focus-overlay, .mat-icon-button.mat-warn .mat-button-focus-overlay {\n    background-color: rgba(239, 83, 80, 0.12); }\n  .mat-button[disabled] .mat-button-focus-overlay, .mat-icon-button[disabled] .mat-button-focus-overlay {\n    background-color: transparent; }\n  .mat-button.mat-primary, .mat-icon-button.mat-primary {\n    color: #2196f3; }\n  .mat-button.mat-accent, .mat-icon-button.mat-accent {\n    color: #66bb6a; }\n  .mat-button.mat-warn, .mat-icon-button.mat-warn {\n    color: #ef5350; }\n  .mat-button.mat-primary[disabled], .mat-button.mat-accent[disabled], .mat-button.mat-warn[disabled], .mat-button[disabled][disabled], .mat-icon-button.mat-primary[disabled], .mat-icon-button.mat-accent[disabled], .mat-icon-button.mat-warn[disabled], .mat-icon-button[disabled][disabled] {\n    color: rgba(0, 0, 0, 0.38); }\n\n.mat-raised-button, .mat-fab, .mat-mini-fab {\n  color: rgba(0, 0, 0, 0.87);\n  background-color: white; }\n  .mat-raised-button.mat-primary, .mat-fab.mat-primary, .mat-mini-fab.mat-primary {\n    color: white; }\n  .mat-raised-button.mat-accent, .mat-fab.mat-accent, .mat-mini-fab.mat-accent {\n    color: rgba(0, 0, 0, 0.87); }\n  .mat-raised-button.mat-warn, .mat-fab.mat-warn, .mat-mini-fab.mat-warn {\n    color: rgba(0, 0, 0, 0.87); }\n  .mat-raised-button.mat-primary[disabled], .mat-raised-button.mat-accent[disabled], .mat-raised-button.mat-warn[disabled], .mat-raised-button[disabled][disabled], .mat-fab.mat-primary[disabled], .mat-fab.mat-accent[disabled], .mat-fab.mat-warn[disabled], .mat-fab[disabled][disabled], .mat-mini-fab.mat-primary[disabled], .mat-mini-fab.mat-accent[disabled], .mat-mini-fab.mat-warn[disabled], .mat-mini-fab[disabled][disabled] {\n    color: rgba(0, 0, 0, 0.38); }\n  .mat-raised-button.mat-primary, .mat-fab.mat-primary, .mat-mini-fab.mat-primary {\n    background-color: #2196f3; }\n  .mat-raised-button.mat-accent, .mat-fab.mat-accent, .mat-mini-fab.mat-accent {\n    background-color: #66bb6a; }\n  .mat-raised-button.mat-warn, .mat-fab.mat-warn, .mat-mini-fab.mat-warn {\n    background-color: #ef5350; }\n  .mat-raised-button.mat-primary[disabled], .mat-raised-button.mat-accent[disabled], .mat-raised-button.mat-warn[disabled], .mat-raised-button[disabled][disabled], .mat-fab.mat-primary[disabled], .mat-fab.mat-accent[disabled], .mat-fab.mat-warn[disabled], .mat-fab[disabled][disabled], .mat-mini-fab.mat-primary[disabled], .mat-mini-fab.mat-accent[disabled], .mat-mini-fab.mat-warn[disabled], .mat-mini-fab[disabled][disabled] {\n    background-color: rgba(0, 0, 0, 0.12); }\n  .mat-raised-button.mat-primary .mat-ripple-element, .mat-fab.mat-primary .mat-ripple-element, .mat-mini-fab.mat-primary .mat-ripple-element {\n    background-color: rgba(255, 255, 255, 0.2); }\n  .mat-raised-button.mat-accent .mat-ripple-element, .mat-fab.mat-accent .mat-ripple-element, .mat-mini-fab.mat-accent .mat-ripple-element {\n    background-color: rgba(0, 0, 0, 0.2); }\n  .mat-raised-button.mat-warn .mat-ripple-element, .mat-fab.mat-warn .mat-ripple-element, .mat-mini-fab.mat-warn .mat-ripple-element {\n    background-color: rgba(0, 0, 0, 0.2); }\n\n.mat-button.mat-primary .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.1); }\n\n.mat-button.mat-accent .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.1); }\n\n.mat-button.mat-warn .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.1); }\n\n.mat-icon-button.mat-primary .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.2); }\n\n.mat-icon-button.mat-accent .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.2); }\n\n.mat-icon-button.mat-warn .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.2); }\n\n.mat-button-toggle {\n  color: rgba(0, 0, 0, 0.38); }\n  .mat-button-toggle.cdk-focused .mat-button-toggle-focus-overlay {\n    background-color: rgba(0, 0, 0, 0.06); }\n\n.mat-button-toggle-checked {\n  background-color: #e0e0e0;\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-button-toggle-disabled {\n  background-color: #eeeeee;\n  color: rgba(0, 0, 0, 0.38); }\n  .mat-button-toggle-disabled.mat-button-toggle-checked {\n    background-color: #bdbdbd; }\n\n.mat-card {\n  background: white;\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-card-subtitle {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-checkbox-frame {\n  border-color: rgba(0, 0, 0, 0.54); }\n\n.mat-checkbox-checkmark {\n  fill: #fafafa; }\n\n.mat-checkbox-checkmark-path {\n  stroke: #fafafa !important; }\n\n.mat-checkbox-mixedmark {\n  background-color: #fafafa; }\n\n.mat-checkbox-indeterminate.mat-primary .mat-checkbox-background, .mat-checkbox-checked.mat-primary .mat-checkbox-background {\n  background-color: #2196f3; }\n\n.mat-checkbox-indeterminate.mat-accent .mat-checkbox-background, .mat-checkbox-checked.mat-accent .mat-checkbox-background {\n  background-color: #66bb6a; }\n\n.mat-checkbox-indeterminate.mat-warn .mat-checkbox-background, .mat-checkbox-checked.mat-warn .mat-checkbox-background {\n  background-color: #ef5350; }\n\n.mat-checkbox-disabled.mat-checkbox-checked .mat-checkbox-background, .mat-checkbox-disabled.mat-checkbox-indeterminate .mat-checkbox-background {\n  background-color: #b0b0b0; }\n\n.mat-checkbox-disabled:not(.mat-checkbox-checked) .mat-checkbox-frame {\n  border-color: #b0b0b0; }\n\n.mat-checkbox-disabled .mat-checkbox-label {\n  color: #b0b0b0; }\n\n.mat-checkbox:not(.mat-checkbox-disabled).mat-primary .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.26); }\n\n.mat-checkbox:not(.mat-checkbox-disabled).mat-accent .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.26); }\n\n.mat-checkbox:not(.mat-checkbox-disabled).mat-warn .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.26); }\n\n.mat-chip:not(.mat-basic-chip) {\n  background-color: #e0e0e0;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-chip:not(.mat-basic-chip) .mat-chip-remove {\n    color: rgba(0, 0, 0, 0.87);\n    opacity: 0.4; }\n  .mat-chip:not(.mat-basic-chip) .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.mat-chip.mat-chip-selected.mat-primary {\n  background-color: #2196f3;\n  color: white; }\n  .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove {\n    color: white;\n    opacity: 0.4; }\n  .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.mat-chip.mat-chip-selected.mat-warn {\n  background-color: #ef5350;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove {\n    color: rgba(0, 0, 0, 0.87);\n    opacity: 0.4; }\n  .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.mat-chip.mat-chip-selected.mat-accent {\n  background-color: #66bb6a;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove {\n    color: rgba(0, 0, 0, 0.87);\n    opacity: 0.4; }\n  .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.mat-table {\n  background: white; }\n\n.mat-row, .mat-header-row {\n  border-bottom-color: rgba(0, 0, 0, 0.12); }\n\n.mat-header-cell {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-cell {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-datepicker-content {\n  background-color: white;\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-calendar-arrow {\n  border-top-color: rgba(0, 0, 0, 0.54); }\n\n.mat-calendar-next-button,\n.mat-calendar-previous-button {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-calendar-table-header {\n  color: rgba(0, 0, 0, 0.38); }\n\n.mat-calendar-table-header-divider::after {\n  background: rgba(0, 0, 0, 0.12); }\n\n.mat-calendar-body-label {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-calendar-body-cell-content {\n  color: rgba(0, 0, 0, 0.87);\n  border-color: transparent; }\n  .mat-calendar-body-disabled > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected) {\n    color: rgba(0, 0, 0, 0.38); }\n\n:not(.mat-calendar-body-disabled):hover > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.cdk-keyboard-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.cdk-program-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected) {\n  background-color: rgba(0, 0, 0, 0.04); }\n\n.mat-calendar-body-selected {\n  background-color: #2196f3;\n  color: white; }\n\n.mat-calendar-body-disabled > .mat-calendar-body-selected {\n  background-color: rgba(33, 150, 243, 0.4); }\n\n.mat-calendar-body-today:not(.mat-calendar-body-selected) {\n  border-color: rgba(0, 0, 0, 0.38); }\n\n.mat-calendar-body-today.mat-calendar-body-selected {\n  box-shadow: inset 0 0 0 1px white; }\n\n.mat-calendar-body-disabled > .mat-calendar-body-today:not(.mat-calendar-body-selected) {\n  border-color: rgba(0, 0, 0, 0.18); }\n\n.mat-dialog-container {\n  background: white;\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-expansion-panel {\n  background: white;\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-action-row {\n  border-top-color: rgba(0, 0, 0, 0.12); }\n\n.mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-keyboard-focused, .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-program-focused, .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']):hover {\n  background: rgba(0, 0, 0, 0.04); }\n\n.mat-expansion-panel-header-title {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-expansion-panel-header-description,\n.mat-expansion-indicator::after {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-expansion-panel-header[aria-disabled='true'] {\n  color: rgba(0, 0, 0, 0.38); }\n  .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-title,\n  .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-description {\n    color: inherit; }\n\n.mat-form-field-placeholder {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-hint {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-focused .mat-form-field-placeholder {\n  color: #2196f3; }\n  .mat-focused .mat-form-field-placeholder.mat-accent {\n    color: #66bb6a; }\n  .mat-focused .mat-form-field-placeholder.mat-warn {\n    color: #ef5350; }\n\n.mat-focused .mat-form-field-required-marker {\n  color: #66bb6a; }\n\n.mat-form-field-underline {\n  background-color: rgba(0, 0, 0, 0.42); }\n\n.mat-form-field-disabled .mat-form-field-underline {\n  background-image: linear-gradient(to right, rgba(0, 0, 0, 0.42) 0%, rgba(0, 0, 0, 0.42) 33%, transparent 0%);\n  background-size: 4px 1px;\n  background-repeat: repeat-x; }\n\n.mat-form-field-ripple {\n  background-color: #2196f3; }\n  .mat-form-field-ripple.mat-accent {\n    background-color: #66bb6a; }\n  .mat-form-field-ripple.mat-warn {\n    background-color: #ef5350; }\n\n.mat-form-field-invalid .mat-form-field-placeholder {\n  color: #ef5350; }\n  .mat-form-field-invalid .mat-form-field-placeholder.mat-accent,\n  .mat-form-field-invalid .mat-form-field-placeholder .mat-form-field-required-marker {\n    color: #ef5350; }\n\n.mat-form-field-invalid .mat-form-field-ripple {\n  background-color: #ef5350; }\n\n.mat-error {\n  color: #ef5350; }\n\n.mat-icon.mat-primary {\n  color: #2196f3; }\n\n.mat-icon.mat-accent {\n  color: #66bb6a; }\n\n.mat-icon.mat-warn {\n  color: #ef5350; }\n\n.mat-input-element:disabled {\n  color: rgba(0, 0, 0, 0.42); }\n\n.mat-list .mat-list-item, .mat-nav-list .mat-list-item, .mat-selection-list .mat-list-item {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-list .mat-list-option, .mat-nav-list .mat-list-option, .mat-selection-list .mat-list-option {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-list .mat-subheader, .mat-nav-list .mat-subheader, .mat-selection-list .mat-subheader {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-list-item-disabled {\n  background-color: #eeeeee; }\n\n.mat-divider {\n  border-top-color: rgba(0, 0, 0, 0.12); }\n\n.mat-nav-list .mat-list-item {\n  outline: none; }\n  .mat-nav-list .mat-list-item:hover, .mat-nav-list .mat-list-item.mat-list-item-focus {\n    background: rgba(0, 0, 0, 0.04); }\n\n.mat-list-option {\n  outline: none; }\n  .mat-list-option:hover, .mat-list-option.mat-list-item-focus {\n    background: rgba(0, 0, 0, 0.04); }\n\n.mat-menu-panel {\n  background: white; }\n\n.mat-menu-item {\n  background: transparent;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-menu-item[disabled] {\n    color: rgba(0, 0, 0, 0.38); }\n\n.mat-menu-item .mat-icon,\n.mat-menu-item-submenu-trigger::after {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-menu-item:hover:not([disabled]),\n.mat-menu-item:focus:not([disabled]),\n.mat-menu-item-highlighted:not([disabled]) {\n  background: rgba(0, 0, 0, 0.04); }\n\n.mat-paginator {\n  background: white; }\n\n.mat-paginator,\n.mat-paginator-page-size .mat-select-trigger {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-paginator-increment,\n.mat-paginator-decrement {\n  border-top: 2px solid rgba(0, 0, 0, 0.54);\n  border-right: 2px solid rgba(0, 0, 0, 0.54); }\n\n.mat-icon-button[disabled] .mat-paginator-increment,\n.mat-icon-button[disabled] .mat-paginator-decrement {\n  border-color: rgba(0, 0, 0, 0.38); }\n\n.mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23bbdefb%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.mat-progress-bar-buffer {\n  background-color: #bbdefb; }\n\n.mat-progress-bar-fill::after {\n  background-color: #2196f3; }\n\n.mat-progress-bar.mat-accent .mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23c8e6c9%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.mat-progress-bar.mat-accent .mat-progress-bar-buffer {\n  background-color: #c8e6c9; }\n\n.mat-progress-bar.mat-accent .mat-progress-bar-fill::after {\n  background-color: #66bb6a; }\n\n.mat-progress-bar.mat-warn .mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23ffcdd2%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.mat-progress-bar.mat-warn .mat-progress-bar-buffer {\n  background-color: #ffcdd2; }\n\n.mat-progress-bar.mat-warn .mat-progress-bar-fill::after {\n  background-color: #ef5350; }\n\n.mat-progress-spinner circle, .mat-spinner circle {\n  stroke: #2196f3; }\n\n.mat-progress-spinner.mat-accent circle, .mat-spinner.mat-accent circle {\n  stroke: #66bb6a; }\n\n.mat-progress-spinner.mat-warn circle, .mat-spinner.mat-warn circle {\n  stroke: #ef5350; }\n\n.mat-radio-outer-circle {\n  border-color: rgba(0, 0, 0, 0.54); }\n\n.mat-radio-disabled .mat-radio-outer-circle {\n  border-color: rgba(0, 0, 0, 0.38); }\n\n.mat-radio-disabled .mat-radio-ripple .mat-ripple-element, .mat-radio-disabled .mat-radio-inner-circle {\n  background-color: rgba(0, 0, 0, 0.38); }\n\n.mat-radio-disabled .mat-radio-label-content {\n  color: rgba(0, 0, 0, 0.38); }\n\n.mat-radio-button.mat-primary.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #2196f3; }\n\n.mat-radio-button.mat-primary .mat-radio-inner-circle {\n  background-color: #2196f3; }\n\n.mat-radio-button.mat-primary .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.26); }\n\n.mat-radio-button.mat-accent.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #66bb6a; }\n\n.mat-radio-button.mat-accent .mat-radio-inner-circle {\n  background-color: #66bb6a; }\n\n.mat-radio-button.mat-accent .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.26); }\n\n.mat-radio-button.mat-warn.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #ef5350; }\n\n.mat-radio-button.mat-warn .mat-radio-inner-circle {\n  background-color: #ef5350; }\n\n.mat-radio-button.mat-warn .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.26); }\n\n.mat-select-disabled .mat-select-value,\n.mat-select-arrow {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-select-content, .mat-select-panel-done-animating {\n  background: white; }\n\n.mat-select-value {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-select-panel .mat-option.mat-selected:not(.mat-option-multiple) {\n  background: rgba(0, 0, 0, 0.12); }\n\n.mat-form-field.mat-focused.mat-primary .mat-select-arrow {\n  color: #2196f3; }\n\n.mat-form-field.mat-focused.mat-accent .mat-select-arrow {\n  color: #66bb6a; }\n\n.mat-form-field.mat-focused.mat-warn .mat-select-arrow {\n  color: #ef5350; }\n\n.mat-form-field .mat-select.mat-select-invalid .mat-select-arrow {\n  color: #ef5350; }\n\n.mat-form-field .mat-select.mat-select-disabled .mat-select-arrow {\n  color: rgba(0, 0, 0, 0.54); }\n\n.mat-select.mat-select-disabled .mat-select-arrow {\n  color: #ef5350; }\n\n.mat-drawer-container {\n  background-color: #fafafa;\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-drawer {\n  background-color: white;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-drawer.mat-drawer-push {\n    background-color: white; }\n\n.mat-drawer-backdrop.mat-drawer-shown {\n  background-color: rgba(0, 0, 0, 0.6); }\n\n.mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #4caf50; }\n\n.mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(76, 175, 80, 0.5); }\n\n.mat-slide-toggle:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.06); }\n\n.mat-slide-toggle .mat-ripple-element {\n  background-color: rgba(76, 175, 80, 0.12); }\n\n.mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #2196f3; }\n\n.mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(33, 150, 243, 0.5); }\n\n.mat-slide-toggle.mat-primary:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.06); }\n\n.mat-slide-toggle.mat-primary .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.12); }\n\n.mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #f44336; }\n\n.mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(244, 67, 54, 0.5); }\n\n.mat-slide-toggle.mat-warn:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.06); }\n\n.mat-slide-toggle.mat-warn .mat-ripple-element {\n  background-color: rgba(244, 67, 54, 0.12); }\n\n.mat-disabled .mat-slide-toggle-thumb {\n  background-color: #bdbdbd; }\n\n.mat-disabled .mat-slide-toggle-bar {\n  background-color: rgba(0, 0, 0, 0.1); }\n\n.mat-slide-toggle-thumb {\n  background-color: #fafafa; }\n\n.mat-slide-toggle-bar {\n  background-color: rgba(0, 0, 0, 0.38); }\n\n.mat-slider-track-background {\n  background-color: rgba(0, 0, 0, 0.26); }\n\n.mat-primary .mat-slider-track-fill,\n.mat-primary .mat-slider-thumb,\n.mat-primary .mat-slider-thumb-label {\n  background-color: #2196f3; }\n\n.mat-primary .mat-slider-thumb-label-text {\n  color: white; }\n\n.mat-accent .mat-slider-track-fill,\n.mat-accent .mat-slider-thumb,\n.mat-accent .mat-slider-thumb-label {\n  background-color: #66bb6a; }\n\n.mat-accent .mat-slider-thumb-label-text {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-warn .mat-slider-track-fill,\n.mat-warn .mat-slider-thumb,\n.mat-warn .mat-slider-thumb-label {\n  background-color: #ef5350; }\n\n.mat-warn .mat-slider-thumb-label-text {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-slider-focus-ring {\n  background-color: rgba(102, 187, 106, 0.2); }\n\n.mat-slider:hover .mat-slider-track-background,\n.cdk-focused .mat-slider-track-background {\n  background-color: rgba(0, 0, 0, 0.38); }\n\n.mat-slider-disabled .mat-slider-track-background,\n.mat-slider-disabled .mat-slider-track-fill,\n.mat-slider-disabled .mat-slider-thumb {\n  background-color: rgba(0, 0, 0, 0.26); }\n\n.mat-slider-disabled:hover .mat-slider-track-background {\n  background-color: rgba(0, 0, 0, 0.26); }\n\n.mat-slider-min-value .mat-slider-focus-ring {\n  background-color: rgba(0, 0, 0, 0.12); }\n\n.mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb,\n.mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb-label {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n.mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb,\n.mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb-label {\n  background-color: rgba(0, 0, 0, 0.26); }\n\n.mat-slider-min-value:not(.mat-slider-thumb-label-showing) .mat-slider-thumb {\n  border-color: rgba(0, 0, 0, 0.26);\n  background-color: transparent; }\n\n.mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover .mat-slider-thumb, .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused .mat-slider-thumb {\n  border-color: rgba(0, 0, 0, 0.38); }\n\n.mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover.mat-slider-disabled .mat-slider-thumb, .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused.mat-slider-disabled .mat-slider-thumb {\n  border-color: rgba(0, 0, 0, 0.26); }\n\n.mat-slider-has-ticks .mat-slider-wrapper::after {\n  border-color: rgba(0, 0, 0, 0.7); }\n\n.mat-slider-horizontal .mat-slider-ticks {\n  background-image: repeating-linear-gradient(to right, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7) 2px, transparent 0, transparent);\n  background-image: -moz-repeating-linear-gradient(0.0001deg, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7) 2px, transparent 0, transparent); }\n\n.mat-slider-vertical .mat-slider-ticks {\n  background-image: repeating-linear-gradient(to bottom, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7) 2px, transparent 0, transparent); }\n\n.mat-step-header.cdk-keyboard-focused, .mat-step-header.cdk-program-focused, .mat-step-header:hover {\n  background-color: rgba(0, 0, 0, 0.04); }\n\n.mat-step-header .mat-step-label,\n.mat-step-header .mat-step-optional {\n  color: rgba(0, 0, 0, 0.38); }\n\n.mat-step-header .mat-step-icon {\n  background-color: #2196f3;\n  color: white; }\n\n.mat-step-header .mat-step-icon-not-touched {\n  background-color: rgba(0, 0, 0, 0.38);\n  color: white; }\n\n.mat-step-header .mat-step-label.mat-step-label-active {\n  color: rgba(0, 0, 0, 0.87); }\n\n.mat-stepper-horizontal, .mat-stepper-vertical {\n  background-color: white; }\n\n.mat-stepper-vertical-line::before {\n  border-left-color: rgba(0, 0, 0, 0.12); }\n\n.mat-stepper-horizontal-line {\n  border-top-color: rgba(0, 0, 0, 0.12); }\n\n.mat-tab-nav-bar,\n.mat-tab-header {\n  border-bottom: 1px solid rgba(0, 0, 0, 0.12); }\n\n.mat-tab-group-inverted-header .mat-tab-nav-bar,\n.mat-tab-group-inverted-header .mat-tab-header {\n  border-top: 1px solid rgba(0, 0, 0, 0.12);\n  border-bottom: none; }\n\n.mat-tab-label, .mat-tab-link {\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-tab-label.mat-tab-disabled, .mat-tab-link.mat-tab-disabled {\n    color: rgba(0, 0, 0, 0.38); }\n\n.mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.38); }\n\n.mat-tab-group[class*='mat-background-'] .mat-tab-header,\n.mat-tab-nav-bar[class*='mat-background-'] {\n  border-bottom: none;\n  border-top: none; }\n\n.mat-tab-group.mat-primary .mat-tab-label:focus, .mat-tab-group.mat-primary .mat-tab-link:focus, .mat-tab-nav-bar.mat-primary .mat-tab-label:focus, .mat-tab-nav-bar.mat-primary .mat-tab-link:focus {\n  background-color: rgba(187, 222, 251, 0.3); }\n\n.mat-tab-group.mat-primary .mat-ink-bar, .mat-tab-nav-bar.mat-primary .mat-ink-bar {\n  background-color: #2196f3; }\n\n.mat-tab-group.mat-primary.mat-background-primary .mat-ink-bar, .mat-tab-nav-bar.mat-primary.mat-background-primary .mat-ink-bar {\n  background-color: white; }\n\n.mat-tab-group.mat-accent .mat-tab-label:focus, .mat-tab-group.mat-accent .mat-tab-link:focus, .mat-tab-nav-bar.mat-accent .mat-tab-label:focus, .mat-tab-nav-bar.mat-accent .mat-tab-link:focus {\n  background-color: rgba(200, 230, 201, 0.3); }\n\n.mat-tab-group.mat-accent .mat-ink-bar, .mat-tab-nav-bar.mat-accent .mat-ink-bar {\n  background-color: #66bb6a; }\n\n.mat-tab-group.mat-accent.mat-background-accent .mat-ink-bar, .mat-tab-nav-bar.mat-accent.mat-background-accent .mat-ink-bar {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n.mat-tab-group.mat-warn .mat-tab-label:focus, .mat-tab-group.mat-warn .mat-tab-link:focus, .mat-tab-nav-bar.mat-warn .mat-tab-label:focus, .mat-tab-nav-bar.mat-warn .mat-tab-link:focus {\n  background-color: rgba(255, 205, 210, 0.3); }\n\n.mat-tab-group.mat-warn .mat-ink-bar, .mat-tab-nav-bar.mat-warn .mat-ink-bar {\n  background-color: #ef5350; }\n\n.mat-tab-group.mat-warn.mat-background-warn .mat-ink-bar, .mat-tab-nav-bar.mat-warn.mat-background-warn .mat-ink-bar {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n.mat-tab-group.mat-background-primary .mat-tab-label:focus, .mat-tab-group.mat-background-primary .mat-tab-link:focus, .mat-tab-nav-bar.mat-background-primary .mat-tab-label:focus, .mat-tab-nav-bar.mat-background-primary .mat-tab-link:focus {\n  background-color: rgba(187, 222, 251, 0.3); }\n\n.mat-tab-group.mat-background-primary .mat-tab-header, .mat-tab-group.mat-background-primary .mat-tab-links, .mat-tab-nav-bar.mat-background-primary .mat-tab-header, .mat-tab-nav-bar.mat-background-primary .mat-tab-links {\n  background-color: #2196f3; }\n\n.mat-tab-group.mat-background-primary .mat-tab-label, .mat-tab-group.mat-background-primary .mat-tab-link, .mat-tab-nav-bar.mat-background-primary .mat-tab-label, .mat-tab-nav-bar.mat-background-primary .mat-tab-link {\n  color: white; }\n  .mat-tab-group.mat-background-primary .mat-tab-label.mat-tab-disabled, .mat-tab-group.mat-background-primary .mat-tab-link.mat-tab-disabled, .mat-tab-nav-bar.mat-background-primary .mat-tab-label.mat-tab-disabled, .mat-tab-nav-bar.mat-background-primary .mat-tab-link.mat-tab-disabled {\n    color: rgba(255, 255, 255, 0.4); }\n\n.mat-tab-group.mat-background-primary .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-chevron {\n  border-color: white; }\n\n.mat-tab-group.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(255, 255, 255, 0.4); }\n\n.mat-tab-group.mat-background-primary .mat-ripple-element, .mat-tab-nav-bar.mat-background-primary .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.mat-tab-group.mat-background-accent .mat-tab-label:focus, .mat-tab-group.mat-background-accent .mat-tab-link:focus, .mat-tab-nav-bar.mat-background-accent .mat-tab-label:focus, .mat-tab-nav-bar.mat-background-accent .mat-tab-link:focus {\n  background-color: rgba(200, 230, 201, 0.3); }\n\n.mat-tab-group.mat-background-accent .mat-tab-header, .mat-tab-group.mat-background-accent .mat-tab-links, .mat-tab-nav-bar.mat-background-accent .mat-tab-header, .mat-tab-nav-bar.mat-background-accent .mat-tab-links {\n  background-color: #66bb6a; }\n\n.mat-tab-group.mat-background-accent .mat-tab-label, .mat-tab-group.mat-background-accent .mat-tab-link, .mat-tab-nav-bar.mat-background-accent .mat-tab-label, .mat-tab-nav-bar.mat-background-accent .mat-tab-link {\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-tab-group.mat-background-accent .mat-tab-label.mat-tab-disabled, .mat-tab-group.mat-background-accent .mat-tab-link.mat-tab-disabled, .mat-tab-nav-bar.mat-background-accent .mat-tab-label.mat-tab-disabled, .mat-tab-nav-bar.mat-background-accent .mat-tab-link.mat-tab-disabled {\n    color: rgba(0, 0, 0, 0.4); }\n\n.mat-tab-group.mat-background-accent .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.mat-tab-group.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.4); }\n\n.mat-tab-group.mat-background-accent .mat-ripple-element, .mat-tab-nav-bar.mat-background-accent .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.12); }\n\n.mat-tab-group.mat-background-warn .mat-tab-label:focus, .mat-tab-group.mat-background-warn .mat-tab-link:focus, .mat-tab-nav-bar.mat-background-warn .mat-tab-label:focus, .mat-tab-nav-bar.mat-background-warn .mat-tab-link:focus {\n  background-color: rgba(255, 205, 210, 0.3); }\n\n.mat-tab-group.mat-background-warn .mat-tab-header, .mat-tab-group.mat-background-warn .mat-tab-links, .mat-tab-nav-bar.mat-background-warn .mat-tab-header, .mat-tab-nav-bar.mat-background-warn .mat-tab-links {\n  background-color: #ef5350; }\n\n.mat-tab-group.mat-background-warn .mat-tab-label, .mat-tab-group.mat-background-warn .mat-tab-link, .mat-tab-nav-bar.mat-background-warn .mat-tab-label, .mat-tab-nav-bar.mat-background-warn .mat-tab-link {\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-tab-group.mat-background-warn .mat-tab-label.mat-tab-disabled, .mat-tab-group.mat-background-warn .mat-tab-link.mat-tab-disabled, .mat-tab-nav-bar.mat-background-warn .mat-tab-label.mat-tab-disabled, .mat-tab-nav-bar.mat-background-warn .mat-tab-link.mat-tab-disabled {\n    color: rgba(0, 0, 0, 0.4); }\n\n.mat-tab-group.mat-background-warn .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.mat-tab-group.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.4); }\n\n.mat-tab-group.mat-background-warn .mat-ripple-element, .mat-tab-nav-bar.mat-background-warn .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.12); }\n\n.mat-toolbar {\n  background: whitesmoke;\n  color: rgba(0, 0, 0, 0.87); }\n  .mat-toolbar.mat-primary {\n    background: #2196f3;\n    color: white; }\n  .mat-toolbar.mat-accent {\n    background: #66bb6a;\n    color: rgba(0, 0, 0, 0.87); }\n  .mat-toolbar.mat-warn {\n    background: #ef5350;\n    color: rgba(0, 0, 0, 0.87); }\n\n.mat-tooltip {\n  background: rgba(97, 97, 97, 0.9); }\n\n.mat-snack-bar-container {\n  background: #323232;\n  color: white; }\n\n.mat-simple-snackbar-action {\n  color: #66bb6a; }\n\n.theme-dark .mat-ripple-element,\n.theme-gray .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.1); }\n\n.theme-dark .mat-option,\n.theme-gray .mat-option {\n  color: white; }\n  .theme-dark .mat-option:hover:not(.mat-option-disabled), .theme-dark .mat-option:focus:not(.mat-option-disabled),\n  .theme-gray .mat-option:hover:not(.mat-option-disabled),\n  .theme-gray .mat-option:focus:not(.mat-option-disabled) {\n    background: rgba(255, 255, 255, 0.04); }\n  .mat-primary .theme-dark .mat-option.mat-selected:not(.mat-option-disabled), .mat-primary\n  .theme-gray .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #2196f3; }\n  .mat-accent .theme-dark .mat-option.mat-selected:not(.mat-option-disabled), .mat-accent\n  .theme-gray .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #66bb6a; }\n  .mat-warn .theme-dark .mat-option.mat-selected:not(.mat-option-disabled), .mat-warn\n  .theme-gray .mat-option.mat-selected:not(.mat-option-disabled) {\n    color: #ef5350; }\n  .theme-dark .mat-option.mat-selected:not(.mat-option-multiple):not(.mat-option-disabled),\n  .theme-gray .mat-option.mat-selected:not(.mat-option-multiple):not(.mat-option-disabled) {\n    background: rgba(255, 255, 255, 0.04); }\n  .theme-dark .mat-option.mat-active,\n  .theme-gray .mat-option.mat-active {\n    background: rgba(255, 255, 255, 0.04);\n    color: white; }\n  .theme-dark .mat-option.mat-option-disabled,\n  .theme-gray .mat-option.mat-option-disabled {\n    color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-optgroup-label,\n.theme-gray .mat-optgroup-label {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-optgroup-disabled .mat-optgroup-label,\n.theme-gray .mat-optgroup-disabled .mat-optgroup-label {\n  color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-pseudo-checkbox,\n.theme-gray .mat-pseudo-checkbox {\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-dark .mat-pseudo-checkbox::after,\n  .theme-gray .mat-pseudo-checkbox::after {\n    color: #303030; }\n\n.theme-dark .mat-pseudo-checkbox-checked,\n.theme-dark .mat-pseudo-checkbox-indeterminate,\n.theme-dark .mat-accent .mat-pseudo-checkbox-checked,\n.theme-dark .mat-accent .mat-pseudo-checkbox-indeterminate,\n.theme-gray .mat-pseudo-checkbox-checked,\n.theme-gray .mat-pseudo-checkbox-indeterminate,\n.theme-gray .mat-accent .mat-pseudo-checkbox-checked,\n.theme-gray .mat-accent .mat-pseudo-checkbox-indeterminate {\n  background: #66bb6a; }\n\n.theme-dark .mat-primary .mat-pseudo-checkbox-checked,\n.theme-dark .mat-primary .mat-pseudo-checkbox-indeterminate,\n.theme-gray .mat-primary .mat-pseudo-checkbox-checked,\n.theme-gray .mat-primary .mat-pseudo-checkbox-indeterminate {\n  background: #2196f3; }\n\n.theme-dark .mat-warn .mat-pseudo-checkbox-checked,\n.theme-dark .mat-warn .mat-pseudo-checkbox-indeterminate,\n.theme-gray .mat-warn .mat-pseudo-checkbox-checked,\n.theme-gray .mat-warn .mat-pseudo-checkbox-indeterminate {\n  background: #ef5350; }\n\n.theme-dark .mat-pseudo-checkbox-checked.mat-pseudo-checkbox-disabled,\n.theme-dark .mat-pseudo-checkbox-indeterminate.mat-pseudo-checkbox-disabled,\n.theme-gray .mat-pseudo-checkbox-checked.mat-pseudo-checkbox-disabled,\n.theme-gray .mat-pseudo-checkbox-indeterminate.mat-pseudo-checkbox-disabled {\n  background: #686868; }\n\n.theme-dark .mat-app-background,\n.theme-gray .mat-app-background {\n  background-color: #303030; }\n\n.theme-dark .mat-theme-loaded-marker,\n.theme-gray .mat-theme-loaded-marker {\n  display: none; }\n\n.theme-dark .mat-autocomplete-panel,\n.theme-gray .mat-autocomplete-panel {\n  background: #424242;\n  color: white; }\n  .theme-dark .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover),\n  .theme-gray .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover) {\n    background: #424242; }\n    .theme-dark .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover):not(.mat-option-disabled),\n    .theme-gray .mat-autocomplete-panel .mat-option.mat-selected:not(.mat-active):not(:hover):not(.mat-option-disabled) {\n      color: white; }\n\n.theme-dark .mat-button, .theme-dark .mat-icon-button,\n.theme-gray .mat-button,\n.theme-gray .mat-icon-button {\n  background: transparent; }\n  .theme-dark .mat-button.mat-primary .mat-button-focus-overlay, .theme-dark .mat-icon-button.mat-primary .mat-button-focus-overlay,\n  .theme-gray .mat-button.mat-primary .mat-button-focus-overlay,\n  .theme-gray .mat-icon-button.mat-primary .mat-button-focus-overlay {\n    background-color: rgba(33, 150, 243, 0.12); }\n  .theme-dark .mat-button.mat-accent .mat-button-focus-overlay, .theme-dark .mat-icon-button.mat-accent .mat-button-focus-overlay,\n  .theme-gray .mat-button.mat-accent .mat-button-focus-overlay,\n  .theme-gray .mat-icon-button.mat-accent .mat-button-focus-overlay {\n    background-color: rgba(102, 187, 106, 0.12); }\n  .theme-dark .mat-button.mat-warn .mat-button-focus-overlay, .theme-dark .mat-icon-button.mat-warn .mat-button-focus-overlay,\n  .theme-gray .mat-button.mat-warn .mat-button-focus-overlay,\n  .theme-gray .mat-icon-button.mat-warn .mat-button-focus-overlay {\n    background-color: rgba(239, 83, 80, 0.12); }\n  .theme-dark .mat-button[disabled] .mat-button-focus-overlay, .theme-dark .mat-icon-button[disabled] .mat-button-focus-overlay,\n  .theme-gray .mat-button[disabled] .mat-button-focus-overlay,\n  .theme-gray .mat-icon-button[disabled] .mat-button-focus-overlay {\n    background-color: transparent; }\n  .theme-dark .mat-button.mat-primary, .theme-dark .mat-icon-button.mat-primary,\n  .theme-gray .mat-button.mat-primary,\n  .theme-gray .mat-icon-button.mat-primary {\n    color: #2196f3; }\n  .theme-dark .mat-button.mat-accent, .theme-dark .mat-icon-button.mat-accent,\n  .theme-gray .mat-button.mat-accent,\n  .theme-gray .mat-icon-button.mat-accent {\n    color: #66bb6a; }\n  .theme-dark .mat-button.mat-warn, .theme-dark .mat-icon-button.mat-warn,\n  .theme-gray .mat-button.mat-warn,\n  .theme-gray .mat-icon-button.mat-warn {\n    color: #ef5350; }\n  .theme-dark .mat-button.mat-primary[disabled], .theme-dark .mat-button.mat-accent[disabled], .theme-dark .mat-button.mat-warn[disabled], .theme-dark .mat-button[disabled][disabled], .theme-dark .mat-icon-button.mat-primary[disabled], .theme-dark .mat-icon-button.mat-accent[disabled], .theme-dark .mat-icon-button.mat-warn[disabled], .theme-dark .mat-icon-button[disabled][disabled],\n  .theme-gray .mat-button.mat-primary[disabled],\n  .theme-gray .mat-button.mat-accent[disabled],\n  .theme-gray .mat-button.mat-warn[disabled],\n  .theme-gray .mat-button[disabled][disabled],\n  .theme-gray .mat-icon-button.mat-primary[disabled],\n  .theme-gray .mat-icon-button.mat-accent[disabled],\n  .theme-gray .mat-icon-button.mat-warn[disabled],\n  .theme-gray .mat-icon-button[disabled][disabled] {\n    color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-raised-button, .theme-dark .mat-fab, .theme-dark .mat-mini-fab,\n.theme-gray .mat-raised-button,\n.theme-gray .mat-fab,\n.theme-gray .mat-mini-fab {\n  color: white;\n  background-color: #424242; }\n  .theme-dark .mat-raised-button.mat-primary, .theme-dark .mat-fab.mat-primary, .theme-dark .mat-mini-fab.mat-primary,\n  .theme-gray .mat-raised-button.mat-primary,\n  .theme-gray .mat-fab.mat-primary,\n  .theme-gray .mat-mini-fab.mat-primary {\n    color: white; }\n  .theme-dark .mat-raised-button.mat-accent, .theme-dark .mat-fab.mat-accent, .theme-dark .mat-mini-fab.mat-accent,\n  .theme-gray .mat-raised-button.mat-accent,\n  .theme-gray .mat-fab.mat-accent,\n  .theme-gray .mat-mini-fab.mat-accent {\n    color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-raised-button.mat-warn, .theme-dark .mat-fab.mat-warn, .theme-dark .mat-mini-fab.mat-warn,\n  .theme-gray .mat-raised-button.mat-warn,\n  .theme-gray .mat-fab.mat-warn,\n  .theme-gray .mat-mini-fab.mat-warn {\n    color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-raised-button.mat-primary[disabled], .theme-dark .mat-raised-button.mat-accent[disabled], .theme-dark .mat-raised-button.mat-warn[disabled], .theme-dark .mat-raised-button[disabled][disabled], .theme-dark .mat-fab.mat-primary[disabled], .theme-dark .mat-fab.mat-accent[disabled], .theme-dark .mat-fab.mat-warn[disabled], .theme-dark .mat-fab[disabled][disabled], .theme-dark .mat-mini-fab.mat-primary[disabled], .theme-dark .mat-mini-fab.mat-accent[disabled], .theme-dark .mat-mini-fab.mat-warn[disabled], .theme-dark .mat-mini-fab[disabled][disabled],\n  .theme-gray .mat-raised-button.mat-primary[disabled],\n  .theme-gray .mat-raised-button.mat-accent[disabled],\n  .theme-gray .mat-raised-button.mat-warn[disabled],\n  .theme-gray .mat-raised-button[disabled][disabled],\n  .theme-gray .mat-fab.mat-primary[disabled],\n  .theme-gray .mat-fab.mat-accent[disabled],\n  .theme-gray .mat-fab.mat-warn[disabled],\n  .theme-gray .mat-fab[disabled][disabled],\n  .theme-gray .mat-mini-fab.mat-primary[disabled],\n  .theme-gray .mat-mini-fab.mat-accent[disabled],\n  .theme-gray .mat-mini-fab.mat-warn[disabled],\n  .theme-gray .mat-mini-fab[disabled][disabled] {\n    color: rgba(255, 255, 255, 0.3); }\n  .theme-dark .mat-raised-button.mat-primary, .theme-dark .mat-fab.mat-primary, .theme-dark .mat-mini-fab.mat-primary,\n  .theme-gray .mat-raised-button.mat-primary,\n  .theme-gray .mat-fab.mat-primary,\n  .theme-gray .mat-mini-fab.mat-primary {\n    background-color: #2196f3; }\n  .theme-dark .mat-raised-button.mat-accent, .theme-dark .mat-fab.mat-accent, .theme-dark .mat-mini-fab.mat-accent,\n  .theme-gray .mat-raised-button.mat-accent,\n  .theme-gray .mat-fab.mat-accent,\n  .theme-gray .mat-mini-fab.mat-accent {\n    background-color: #66bb6a; }\n  .theme-dark .mat-raised-button.mat-warn, .theme-dark .mat-fab.mat-warn, .theme-dark .mat-mini-fab.mat-warn,\n  .theme-gray .mat-raised-button.mat-warn,\n  .theme-gray .mat-fab.mat-warn,\n  .theme-gray .mat-mini-fab.mat-warn {\n    background-color: #ef5350; }\n  .theme-dark .mat-raised-button.mat-primary[disabled], .theme-dark .mat-raised-button.mat-accent[disabled], .theme-dark .mat-raised-button.mat-warn[disabled], .theme-dark .mat-raised-button[disabled][disabled], .theme-dark .mat-fab.mat-primary[disabled], .theme-dark .mat-fab.mat-accent[disabled], .theme-dark .mat-fab.mat-warn[disabled], .theme-dark .mat-fab[disabled][disabled], .theme-dark .mat-mini-fab.mat-primary[disabled], .theme-dark .mat-mini-fab.mat-accent[disabled], .theme-dark .mat-mini-fab.mat-warn[disabled], .theme-dark .mat-mini-fab[disabled][disabled],\n  .theme-gray .mat-raised-button.mat-primary[disabled],\n  .theme-gray .mat-raised-button.mat-accent[disabled],\n  .theme-gray .mat-raised-button.mat-warn[disabled],\n  .theme-gray .mat-raised-button[disabled][disabled],\n  .theme-gray .mat-fab.mat-primary[disabled],\n  .theme-gray .mat-fab.mat-accent[disabled],\n  .theme-gray .mat-fab.mat-warn[disabled],\n  .theme-gray .mat-fab[disabled][disabled],\n  .theme-gray .mat-mini-fab.mat-primary[disabled],\n  .theme-gray .mat-mini-fab.mat-accent[disabled],\n  .theme-gray .mat-mini-fab.mat-warn[disabled],\n  .theme-gray .mat-mini-fab[disabled][disabled] {\n    background-color: rgba(255, 255, 255, 0.12); }\n  .theme-dark .mat-raised-button.mat-primary .mat-ripple-element, .theme-dark .mat-fab.mat-primary .mat-ripple-element, .theme-dark .mat-mini-fab.mat-primary .mat-ripple-element,\n  .theme-gray .mat-raised-button.mat-primary .mat-ripple-element,\n  .theme-gray .mat-fab.mat-primary .mat-ripple-element,\n  .theme-gray .mat-mini-fab.mat-primary .mat-ripple-element {\n    background-color: rgba(255, 255, 255, 0.2); }\n  .theme-dark .mat-raised-button.mat-accent .mat-ripple-element, .theme-dark .mat-fab.mat-accent .mat-ripple-element, .theme-dark .mat-mini-fab.mat-accent .mat-ripple-element,\n  .theme-gray .mat-raised-button.mat-accent .mat-ripple-element,\n  .theme-gray .mat-fab.mat-accent .mat-ripple-element,\n  .theme-gray .mat-mini-fab.mat-accent .mat-ripple-element {\n    background-color: rgba(0, 0, 0, 0.2); }\n  .theme-dark .mat-raised-button.mat-warn .mat-ripple-element, .theme-dark .mat-fab.mat-warn .mat-ripple-element, .theme-dark .mat-mini-fab.mat-warn .mat-ripple-element,\n  .theme-gray .mat-raised-button.mat-warn .mat-ripple-element,\n  .theme-gray .mat-fab.mat-warn .mat-ripple-element,\n  .theme-gray .mat-mini-fab.mat-warn .mat-ripple-element {\n    background-color: rgba(0, 0, 0, 0.2); }\n\n.theme-dark .mat-button.mat-primary .mat-ripple-element,\n.theme-gray .mat-button.mat-primary .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.1); }\n\n.theme-dark .mat-button.mat-accent .mat-ripple-element,\n.theme-gray .mat-button.mat-accent .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.1); }\n\n.theme-dark .mat-button.mat-warn .mat-ripple-element,\n.theme-gray .mat-button.mat-warn .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.1); }\n\n.theme-dark .mat-icon-button.mat-primary .mat-ripple-element,\n.theme-gray .mat-icon-button.mat-primary .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.2); }\n\n.theme-dark .mat-icon-button.mat-accent .mat-ripple-element,\n.theme-gray .mat-icon-button.mat-accent .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.2); }\n\n.theme-dark .mat-icon-button.mat-warn .mat-ripple-element,\n.theme-gray .mat-icon-button.mat-warn .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.2); }\n\n.theme-dark .mat-button-toggle,\n.theme-gray .mat-button-toggle {\n  color: rgba(255, 255, 255, 0.3); }\n  .theme-dark .mat-button-toggle.cdk-focused .mat-button-toggle-focus-overlay,\n  .theme-gray .mat-button-toggle.cdk-focused .mat-button-toggle-focus-overlay {\n    background-color: rgba(255, 255, 255, 0.06); }\n\n.theme-dark .mat-button-toggle-checked,\n.theme-gray .mat-button-toggle-checked {\n  background-color: #212121;\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-button-toggle-disabled,\n.theme-gray .mat-button-toggle-disabled {\n  background-color: black;\n  color: rgba(255, 255, 255, 0.3); }\n  .theme-dark .mat-button-toggle-disabled.mat-button-toggle-checked,\n  .theme-gray .mat-button-toggle-disabled.mat-button-toggle-checked {\n    background-color: #424242; }\n\n.theme-dark .mat-card,\n.theme-gray .mat-card {\n  background: #424242;\n  color: white; }\n\n.theme-dark .mat-card-subtitle,\n.theme-gray .mat-card-subtitle {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-checkbox-frame,\n.theme-gray .mat-checkbox-frame {\n  border-color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-checkbox-checkmark,\n.theme-gray .mat-checkbox-checkmark {\n  fill: #303030; }\n\n.theme-dark .mat-checkbox-checkmark-path,\n.theme-gray .mat-checkbox-checkmark-path {\n  stroke: #303030 !important; }\n\n.theme-dark .mat-checkbox-mixedmark,\n.theme-gray .mat-checkbox-mixedmark {\n  background-color: #303030; }\n\n.theme-dark .mat-checkbox-indeterminate.mat-primary .mat-checkbox-background, .theme-dark .mat-checkbox-checked.mat-primary .mat-checkbox-background,\n.theme-gray .mat-checkbox-indeterminate.mat-primary .mat-checkbox-background,\n.theme-gray .mat-checkbox-checked.mat-primary .mat-checkbox-background {\n  background-color: #2196f3; }\n\n.theme-dark .mat-checkbox-indeterminate.mat-accent .mat-checkbox-background, .theme-dark .mat-checkbox-checked.mat-accent .mat-checkbox-background,\n.theme-gray .mat-checkbox-indeterminate.mat-accent .mat-checkbox-background,\n.theme-gray .mat-checkbox-checked.mat-accent .mat-checkbox-background {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-checkbox-indeterminate.mat-warn .mat-checkbox-background, .theme-dark .mat-checkbox-checked.mat-warn .mat-checkbox-background,\n.theme-gray .mat-checkbox-indeterminate.mat-warn .mat-checkbox-background,\n.theme-gray .mat-checkbox-checked.mat-warn .mat-checkbox-background {\n  background-color: #ef5350; }\n\n.theme-dark .mat-checkbox-disabled.mat-checkbox-checked .mat-checkbox-background, .theme-dark .mat-checkbox-disabled.mat-checkbox-indeterminate .mat-checkbox-background,\n.theme-gray .mat-checkbox-disabled.mat-checkbox-checked .mat-checkbox-background,\n.theme-gray .mat-checkbox-disabled.mat-checkbox-indeterminate .mat-checkbox-background {\n  background-color: #686868; }\n\n.theme-dark .mat-checkbox-disabled:not(.mat-checkbox-checked) .mat-checkbox-frame,\n.theme-gray .mat-checkbox-disabled:not(.mat-checkbox-checked) .mat-checkbox-frame {\n  border-color: #686868; }\n\n.theme-dark .mat-checkbox-disabled .mat-checkbox-label,\n.theme-gray .mat-checkbox-disabled .mat-checkbox-label {\n  color: #686868; }\n\n.theme-dark .mat-checkbox:not(.mat-checkbox-disabled).mat-primary .mat-checkbox-ripple .mat-ripple-element,\n.theme-gray .mat-checkbox:not(.mat-checkbox-disabled).mat-primary .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.26); }\n\n.theme-dark .mat-checkbox:not(.mat-checkbox-disabled).mat-accent .mat-checkbox-ripple .mat-ripple-element,\n.theme-gray .mat-checkbox:not(.mat-checkbox-disabled).mat-accent .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.26); }\n\n.theme-dark .mat-checkbox:not(.mat-checkbox-disabled).mat-warn .mat-checkbox-ripple .mat-ripple-element,\n.theme-gray .mat-checkbox:not(.mat-checkbox-disabled).mat-warn .mat-checkbox-ripple .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.26); }\n\n.theme-dark .mat-chip:not(.mat-basic-chip),\n.theme-gray .mat-chip:not(.mat-basic-chip) {\n  background-color: #616161;\n  color: white; }\n  .theme-dark .mat-chip:not(.mat-basic-chip) .mat-chip-remove,\n  .theme-gray .mat-chip:not(.mat-basic-chip) .mat-chip-remove {\n    color: white;\n    opacity: 0.4; }\n  .theme-dark .mat-chip:not(.mat-basic-chip) .mat-chip-remove:hover,\n  .theme-gray .mat-chip:not(.mat-basic-chip) .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.theme-dark .mat-chip.mat-chip-selected.mat-primary,\n.theme-gray .mat-chip.mat-chip-selected.mat-primary {\n  background-color: #2196f3;\n  color: white; }\n  .theme-dark .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove,\n  .theme-gray .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove {\n    color: white;\n    opacity: 0.4; }\n  .theme-dark .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove:hover,\n  .theme-gray .mat-chip.mat-chip-selected.mat-primary .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.theme-dark .mat-chip.mat-chip-selected.mat-warn,\n.theme-gray .mat-chip.mat-chip-selected.mat-warn {\n  background-color: #ef5350;\n  color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove,\n  .theme-gray .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove {\n    color: rgba(0, 0, 0, 0.87);\n    opacity: 0.4; }\n  .theme-dark .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove:hover,\n  .theme-gray .mat-chip.mat-chip-selected.mat-warn .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.theme-dark .mat-chip.mat-chip-selected.mat-accent,\n.theme-gray .mat-chip.mat-chip-selected.mat-accent {\n  background-color: #66bb6a;\n  color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove,\n  .theme-gray .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove {\n    color: rgba(0, 0, 0, 0.87);\n    opacity: 0.4; }\n  .theme-dark .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove:hover,\n  .theme-gray .mat-chip.mat-chip-selected.mat-accent .mat-chip-remove:hover {\n    opacity: 0.54; }\n\n.theme-dark .mat-table,\n.theme-gray .mat-table {\n  background: #424242; }\n\n.theme-dark .mat-row, .theme-dark .mat-header-row,\n.theme-gray .mat-row,\n.theme-gray .mat-header-row {\n  border-bottom-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-header-cell,\n.theme-gray .mat-header-cell {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-cell,\n.theme-gray .mat-cell {\n  color: white; }\n\n.theme-dark .mat-datepicker-content,\n.theme-gray .mat-datepicker-content {\n  background-color: #424242;\n  color: white; }\n\n.theme-dark .mat-calendar-arrow,\n.theme-gray .mat-calendar-arrow {\n  border-top-color: white; }\n\n.theme-dark .mat-calendar-next-button,\n.theme-dark .mat-calendar-previous-button,\n.theme-gray .mat-calendar-next-button,\n.theme-gray .mat-calendar-previous-button {\n  color: white; }\n\n.theme-dark .mat-calendar-table-header,\n.theme-gray .mat-calendar-table-header {\n  color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-calendar-table-header-divider::after,\n.theme-gray .mat-calendar-table-header-divider::after {\n  background: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-calendar-body-label,\n.theme-gray .mat-calendar-body-label {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-calendar-body-cell-content,\n.theme-gray .mat-calendar-body-cell-content {\n  color: white;\n  border-color: transparent; }\n  .mat-calendar-body-disabled > .theme-dark .mat-calendar-body-cell-content:not(.mat-calendar-body-selected), .mat-calendar-body-disabled >\n  .theme-gray .mat-calendar-body-cell-content:not(.mat-calendar-body-selected) {\n    color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark :not(.mat-calendar-body-disabled):hover > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.theme-dark .cdk-keyboard-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.theme-dark .cdk-program-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.theme-gray :not(.mat-calendar-body-disabled):hover > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.theme-gray .cdk-keyboard-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected),\n.theme-gray .cdk-program-focused .mat-calendar-body-active > .mat-calendar-body-cell-content:not(.mat-calendar-body-selected) {\n  background-color: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-calendar-body-selected,\n.theme-gray .mat-calendar-body-selected {\n  background-color: #2196f3;\n  color: white; }\n\n.theme-dark .mat-calendar-body-disabled > .mat-calendar-body-selected,\n.theme-gray .mat-calendar-body-disabled > .mat-calendar-body-selected {\n  background-color: rgba(33, 150, 243, 0.4); }\n\n.theme-dark .mat-calendar-body-today:not(.mat-calendar-body-selected),\n.theme-gray .mat-calendar-body-today:not(.mat-calendar-body-selected) {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-calendar-body-today.mat-calendar-body-selected,\n.theme-gray .mat-calendar-body-today.mat-calendar-body-selected {\n  box-shadow: inset 0 0 0 1px white; }\n\n.theme-dark .mat-calendar-body-disabled > .mat-calendar-body-today:not(.mat-calendar-body-selected),\n.theme-gray .mat-calendar-body-disabled > .mat-calendar-body-today:not(.mat-calendar-body-selected) {\n  border-color: rgba(255, 255, 255, 0.1); }\n\n.theme-dark .mat-dialog-container,\n.theme-gray .mat-dialog-container {\n  background: #424242;\n  color: white; }\n\n.theme-dark .mat-expansion-panel,\n.theme-gray .mat-expansion-panel {\n  background: #424242;\n  color: white; }\n\n.theme-dark .mat-action-row,\n.theme-gray .mat-action-row {\n  border-top-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-keyboard-focused, .theme-dark .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-program-focused, .theme-dark .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']):hover,\n.theme-gray .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-keyboard-focused,\n.theme-gray .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']).cdk-program-focused,\n.theme-gray .mat-expansion-panel:not(.mat-expanded) .mat-expansion-panel-header:not([aria-disabled='true']):hover {\n  background: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-expansion-panel-header-title,\n.theme-gray .mat-expansion-panel-header-title {\n  color: white; }\n\n.theme-dark .mat-expansion-panel-header-description,\n.theme-dark .mat-expansion-indicator::after,\n.theme-gray .mat-expansion-panel-header-description,\n.theme-gray .mat-expansion-indicator::after {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-expansion-panel-header[aria-disabled='true'],\n.theme-gray .mat-expansion-panel-header[aria-disabled='true'] {\n  color: rgba(255, 255, 255, 0.3); }\n  .theme-dark .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-title,\n  .theme-dark .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-description,\n  .theme-gray .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-title,\n  .theme-gray .mat-expansion-panel-header[aria-disabled='true'] .mat-expansion-panel-header-description {\n    color: inherit; }\n\n.theme-dark .mat-form-field-placeholder,\n.theme-gray .mat-form-field-placeholder {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-hint,\n.theme-gray .mat-hint {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-focused .mat-form-field-placeholder,\n.theme-gray .mat-focused .mat-form-field-placeholder {\n  color: #2196f3; }\n  .theme-dark .mat-focused .mat-form-field-placeholder.mat-accent,\n  .theme-gray .mat-focused .mat-form-field-placeholder.mat-accent {\n    color: #66bb6a; }\n  .theme-dark .mat-focused .mat-form-field-placeholder.mat-warn,\n  .theme-gray .mat-focused .mat-form-field-placeholder.mat-warn {\n    color: #ef5350; }\n\n.theme-dark .mat-focused .mat-form-field-required-marker,\n.theme-gray .mat-focused .mat-form-field-required-marker {\n  color: #66bb6a; }\n\n.theme-dark .mat-form-field-underline,\n.theme-gray .mat-form-field-underline {\n  background-color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-form-field-disabled .mat-form-field-underline,\n.theme-gray .mat-form-field-disabled .mat-form-field-underline {\n  background-image: linear-gradient(to right, rgba(255, 255, 255, 0.7) 0%, rgba(255, 255, 255, 0.7) 33%, transparent 0%);\n  background-size: 4px 1px;\n  background-repeat: repeat-x; }\n\n.theme-dark .mat-form-field-ripple,\n.theme-gray .mat-form-field-ripple {\n  background-color: #2196f3; }\n  .theme-dark .mat-form-field-ripple.mat-accent,\n  .theme-gray .mat-form-field-ripple.mat-accent {\n    background-color: #66bb6a; }\n  .theme-dark .mat-form-field-ripple.mat-warn,\n  .theme-gray .mat-form-field-ripple.mat-warn {\n    background-color: #ef5350; }\n\n.theme-dark .mat-form-field-invalid .mat-form-field-placeholder,\n.theme-gray .mat-form-field-invalid .mat-form-field-placeholder {\n  color: #ef5350; }\n  .theme-dark .mat-form-field-invalid .mat-form-field-placeholder.mat-accent,\n  .theme-dark .mat-form-field-invalid .mat-form-field-placeholder .mat-form-field-required-marker,\n  .theme-gray .mat-form-field-invalid .mat-form-field-placeholder.mat-accent,\n  .theme-gray .mat-form-field-invalid .mat-form-field-placeholder .mat-form-field-required-marker {\n    color: #ef5350; }\n\n.theme-dark .mat-form-field-invalid .mat-form-field-ripple,\n.theme-gray .mat-form-field-invalid .mat-form-field-ripple {\n  background-color: #ef5350; }\n\n.theme-dark .mat-error,\n.theme-gray .mat-error {\n  color: #ef5350; }\n\n.theme-dark .mat-icon.mat-primary,\n.theme-gray .mat-icon.mat-primary {\n  color: #2196f3; }\n\n.theme-dark .mat-icon.mat-accent,\n.theme-gray .mat-icon.mat-accent {\n  color: #66bb6a; }\n\n.theme-dark .mat-icon.mat-warn,\n.theme-gray .mat-icon.mat-warn {\n  color: #ef5350; }\n\n.theme-dark .mat-input-element:disabled,\n.theme-gray .mat-input-element:disabled {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-list .mat-list-item, .theme-dark .mat-nav-list .mat-list-item, .theme-dark .mat-selection-list .mat-list-item,\n.theme-gray .mat-list .mat-list-item,\n.theme-gray .mat-nav-list .mat-list-item,\n.theme-gray .mat-selection-list .mat-list-item {\n  color: white; }\n\n.theme-dark .mat-list .mat-list-option, .theme-dark .mat-nav-list .mat-list-option, .theme-dark .mat-selection-list .mat-list-option,\n.theme-gray .mat-list .mat-list-option,\n.theme-gray .mat-nav-list .mat-list-option,\n.theme-gray .mat-selection-list .mat-list-option {\n  color: white; }\n\n.theme-dark .mat-list .mat-subheader, .theme-dark .mat-nav-list .mat-subheader, .theme-dark .mat-selection-list .mat-subheader,\n.theme-gray .mat-list .mat-subheader,\n.theme-gray .mat-nav-list .mat-subheader,\n.theme-gray .mat-selection-list .mat-subheader {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-list-item-disabled,\n.theme-gray .mat-list-item-disabled {\n  background-color: black; }\n\n.theme-dark .mat-divider,\n.theme-gray .mat-divider {\n  border-top-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-nav-list .mat-list-item,\n.theme-gray .mat-nav-list .mat-list-item {\n  outline: none; }\n  .theme-dark .mat-nav-list .mat-list-item:hover, .theme-dark .mat-nav-list .mat-list-item.mat-list-item-focus,\n  .theme-gray .mat-nav-list .mat-list-item:hover,\n  .theme-gray .mat-nav-list .mat-list-item.mat-list-item-focus {\n    background: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-list-option,\n.theme-gray .mat-list-option {\n  outline: none; }\n  .theme-dark .mat-list-option:hover, .theme-dark .mat-list-option.mat-list-item-focus,\n  .theme-gray .mat-list-option:hover,\n  .theme-gray .mat-list-option.mat-list-item-focus {\n    background: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-menu-panel,\n.theme-gray .mat-menu-panel {\n  background: #424242; }\n\n.theme-dark .mat-menu-item,\n.theme-gray .mat-menu-item {\n  background: transparent;\n  color: white; }\n  .theme-dark .mat-menu-item[disabled],\n  .theme-gray .mat-menu-item[disabled] {\n    color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-menu-item .mat-icon,\n.theme-dark .mat-menu-item-submenu-trigger::after,\n.theme-gray .mat-menu-item .mat-icon,\n.theme-gray .mat-menu-item-submenu-trigger::after {\n  color: white; }\n\n.theme-dark .mat-menu-item:hover:not([disabled]),\n.theme-dark .mat-menu-item:focus:not([disabled]),\n.theme-dark .mat-menu-item-highlighted:not([disabled]),\n.theme-gray .mat-menu-item:hover:not([disabled]),\n.theme-gray .mat-menu-item:focus:not([disabled]),\n.theme-gray .mat-menu-item-highlighted:not([disabled]) {\n  background: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-paginator,\n.theme-gray .mat-paginator {\n  background: #424242; }\n\n.theme-dark .mat-paginator,\n.theme-dark .mat-paginator-page-size .mat-select-trigger,\n.theme-gray .mat-paginator,\n.theme-gray .mat-paginator-page-size .mat-select-trigger {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-paginator-increment,\n.theme-dark .mat-paginator-decrement,\n.theme-gray .mat-paginator-increment,\n.theme-gray .mat-paginator-decrement {\n  border-top: 2px solid white;\n  border-right: 2px solid white; }\n\n.theme-dark .mat-icon-button[disabled] .mat-paginator-increment,\n.theme-dark .mat-icon-button[disabled] .mat-paginator-decrement,\n.theme-gray .mat-icon-button[disabled] .mat-paginator-increment,\n.theme-gray .mat-icon-button[disabled] .mat-paginator-decrement {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-progress-bar-background,\n.theme-gray .mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23bbdefb%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.theme-dark .mat-progress-bar-buffer,\n.theme-gray .mat-progress-bar-buffer {\n  background-color: #bbdefb; }\n\n.theme-dark .mat-progress-bar-fill::after,\n.theme-gray .mat-progress-bar-fill::after {\n  background-color: #2196f3; }\n\n.theme-dark .mat-progress-bar.mat-accent .mat-progress-bar-background,\n.theme-gray .mat-progress-bar.mat-accent .mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23c8e6c9%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.theme-dark .mat-progress-bar.mat-accent .mat-progress-bar-buffer,\n.theme-gray .mat-progress-bar.mat-accent .mat-progress-bar-buffer {\n  background-color: #c8e6c9; }\n\n.theme-dark .mat-progress-bar.mat-accent .mat-progress-bar-fill::after,\n.theme-gray .mat-progress-bar.mat-accent .mat-progress-bar-fill::after {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-progress-bar.mat-warn .mat-progress-bar-background,\n.theme-gray .mat-progress-bar.mat-warn .mat-progress-bar-background {\n  background-image: url(\"data:image/svg+xml;charset=UTF-8,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20xmlns%3Axlink%3D%27http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink%27%20x%3D%270px%27%20y%3D%270px%27%20enable-background%3D%27new%200%200%205%202%27%20xml%3Aspace%3D%27preserve%27%20viewBox%3D%270%200%205%202%27%20preserveAspectRatio%3D%27none%20slice%27%3E%3Ccircle%20cx%3D%271%27%20cy%3D%271%27%20r%3D%271%27%20fill%3D%27%23ffcdd2%27%2F%3E%3C%2Fsvg%3E\"); }\n\n.theme-dark .mat-progress-bar.mat-warn .mat-progress-bar-buffer,\n.theme-gray .mat-progress-bar.mat-warn .mat-progress-bar-buffer {\n  background-color: #ffcdd2; }\n\n.theme-dark .mat-progress-bar.mat-warn .mat-progress-bar-fill::after,\n.theme-gray .mat-progress-bar.mat-warn .mat-progress-bar-fill::after {\n  background-color: #ef5350; }\n\n.theme-dark .mat-progress-spinner circle, .theme-dark .mat-spinner circle,\n.theme-gray .mat-progress-spinner circle,\n.theme-gray .mat-spinner circle {\n  stroke: #2196f3; }\n\n.theme-dark .mat-progress-spinner.mat-accent circle, .theme-dark .mat-spinner.mat-accent circle,\n.theme-gray .mat-progress-spinner.mat-accent circle,\n.theme-gray .mat-spinner.mat-accent circle {\n  stroke: #66bb6a; }\n\n.theme-dark .mat-progress-spinner.mat-warn circle, .theme-dark .mat-spinner.mat-warn circle,\n.theme-gray .mat-progress-spinner.mat-warn circle,\n.theme-gray .mat-spinner.mat-warn circle {\n  stroke: #ef5350; }\n\n.theme-dark .mat-radio-outer-circle,\n.theme-gray .mat-radio-outer-circle {\n  border-color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-radio-disabled .mat-radio-outer-circle,\n.theme-gray .mat-radio-disabled .mat-radio-outer-circle {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-radio-disabled .mat-radio-ripple .mat-ripple-element, .theme-dark .mat-radio-disabled .mat-radio-inner-circle,\n.theme-gray .mat-radio-disabled .mat-radio-ripple .mat-ripple-element,\n.theme-gray .mat-radio-disabled .mat-radio-inner-circle {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-radio-disabled .mat-radio-label-content,\n.theme-gray .mat-radio-disabled .mat-radio-label-content {\n  color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-radio-button.mat-primary.mat-radio-checked .mat-radio-outer-circle,\n.theme-gray .mat-radio-button.mat-primary.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #2196f3; }\n\n.theme-dark .mat-radio-button.mat-primary .mat-radio-inner-circle,\n.theme-gray .mat-radio-button.mat-primary .mat-radio-inner-circle {\n  background-color: #2196f3; }\n\n.theme-dark .mat-radio-button.mat-primary .mat-radio-ripple .mat-ripple-element,\n.theme-gray .mat-radio-button.mat-primary .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(33, 150, 243, 0.26); }\n\n.theme-dark .mat-radio-button.mat-accent.mat-radio-checked .mat-radio-outer-circle,\n.theme-gray .mat-radio-button.mat-accent.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #66bb6a; }\n\n.theme-dark .mat-radio-button.mat-accent .mat-radio-inner-circle,\n.theme-gray .mat-radio-button.mat-accent .mat-radio-inner-circle {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-radio-button.mat-accent .mat-radio-ripple .mat-ripple-element,\n.theme-gray .mat-radio-button.mat-accent .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(102, 187, 106, 0.26); }\n\n.theme-dark .mat-radio-button.mat-warn.mat-radio-checked .mat-radio-outer-circle,\n.theme-gray .mat-radio-button.mat-warn.mat-radio-checked .mat-radio-outer-circle {\n  border-color: #ef5350; }\n\n.theme-dark .mat-radio-button.mat-warn .mat-radio-inner-circle,\n.theme-gray .mat-radio-button.mat-warn .mat-radio-inner-circle {\n  background-color: #ef5350; }\n\n.theme-dark .mat-radio-button.mat-warn .mat-radio-ripple .mat-ripple-element,\n.theme-gray .mat-radio-button.mat-warn .mat-radio-ripple .mat-ripple-element {\n  background-color: rgba(239, 83, 80, 0.26); }\n\n.theme-dark .mat-select-disabled .mat-select-value,\n.theme-dark .mat-select-arrow,\n.theme-gray .mat-select-disabled .mat-select-value,\n.theme-gray .mat-select-arrow {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-select-content, .theme-dark .mat-select-panel-done-animating,\n.theme-gray .mat-select-content,\n.theme-gray .mat-select-panel-done-animating {\n  background: #424242; }\n\n.theme-dark .mat-select-value,\n.theme-gray .mat-select-value {\n  color: white; }\n\n.theme-dark .mat-select-panel .mat-option.mat-selected:not(.mat-option-multiple),\n.theme-gray .mat-select-panel .mat-option.mat-selected:not(.mat-option-multiple) {\n  background: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-form-field.mat-focused.mat-primary .mat-select-arrow,\n.theme-gray .mat-form-field.mat-focused.mat-primary .mat-select-arrow {\n  color: #2196f3; }\n\n.theme-dark .mat-form-field.mat-focused.mat-accent .mat-select-arrow,\n.theme-gray .mat-form-field.mat-focused.mat-accent .mat-select-arrow {\n  color: #66bb6a; }\n\n.theme-dark .mat-form-field.mat-focused.mat-warn .mat-select-arrow,\n.theme-gray .mat-form-field.mat-focused.mat-warn .mat-select-arrow {\n  color: #ef5350; }\n\n.theme-dark .mat-form-field .mat-select.mat-select-invalid .mat-select-arrow,\n.theme-gray .mat-form-field .mat-select.mat-select-invalid .mat-select-arrow {\n  color: #ef5350; }\n\n.theme-dark .mat-form-field .mat-select.mat-select-disabled .mat-select-arrow,\n.theme-gray .mat-form-field .mat-select.mat-select-disabled .mat-select-arrow {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-select.mat-select-disabled .mat-select-arrow,\n.theme-gray .mat-select.mat-select-disabled .mat-select-arrow {\n  color: #ef5350; }\n\n.theme-dark .mat-drawer-container,\n.theme-gray .mat-drawer-container {\n  background-color: #303030;\n  color: white; }\n\n.theme-dark .mat-drawer,\n.theme-gray .mat-drawer {\n  background-color: #424242;\n  color: white; }\n  .theme-dark .mat-drawer.mat-drawer-push,\n  .theme-gray .mat-drawer.mat-drawer-push {\n    background-color: #424242; }\n\n.theme-dark .mat-drawer-backdrop.mat-drawer-shown,\n.theme-gray .mat-drawer-backdrop.mat-drawer-shown {\n  background-color: rgba(189, 189, 189, 0.6); }\n\n.theme-dark .mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb,\n.theme-gray .mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #a5d6a7; }\n\n.theme-dark .mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar,\n.theme-gray .mat-slide-toggle.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(165, 214, 167, 0.5); }\n\n.theme-dark .mat-slide-toggle:not(.mat-checked) .mat-ripple-element,\n.theme-gray .mat-slide-toggle:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-slide-toggle .mat-ripple-element,\n.theme-gray .mat-slide-toggle .mat-ripple-element {\n  background-color: rgba(165, 214, 167, 0.12); }\n\n.theme-dark .mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb,\n.theme-gray .mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #90caf9; }\n\n.theme-dark .mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar,\n.theme-gray .mat-slide-toggle.mat-primary.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(144, 202, 249, 0.5); }\n\n.theme-dark .mat-slide-toggle.mat-primary:not(.mat-checked) .mat-ripple-element,\n.theme-gray .mat-slide-toggle.mat-primary:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-slide-toggle.mat-primary .mat-ripple-element,\n.theme-gray .mat-slide-toggle.mat-primary .mat-ripple-element {\n  background-color: rgba(144, 202, 249, 0.12); }\n\n.theme-dark .mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb,\n.theme-gray .mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-thumb {\n  background-color: #ef9a9a; }\n\n.theme-dark .mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar,\n.theme-gray .mat-slide-toggle.mat-warn.mat-checked:not(.mat-disabled) .mat-slide-toggle-bar {\n  background-color: rgba(239, 154, 154, 0.5); }\n\n.theme-dark .mat-slide-toggle.mat-warn:not(.mat-checked) .mat-ripple-element,\n.theme-gray .mat-slide-toggle.mat-warn:not(.mat-checked) .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-slide-toggle.mat-warn .mat-ripple-element,\n.theme-gray .mat-slide-toggle.mat-warn .mat-ripple-element {\n  background-color: rgba(239, 154, 154, 0.12); }\n\n.theme-dark .mat-disabled .mat-slide-toggle-thumb,\n.theme-gray .mat-disabled .mat-slide-toggle-thumb {\n  background-color: #424242; }\n\n.theme-dark .mat-disabled .mat-slide-toggle-bar,\n.theme-gray .mat-disabled .mat-slide-toggle-bar {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-slide-toggle-thumb,\n.theme-gray .mat-slide-toggle-thumb {\n  background-color: #bdbdbd; }\n\n.theme-dark .mat-slide-toggle-bar,\n.theme-gray .mat-slide-toggle-bar {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-track-background,\n.theme-gray .mat-slider-track-background {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-primary .mat-slider-track-fill,\n.theme-dark .mat-primary .mat-slider-thumb,\n.theme-dark .mat-primary .mat-slider-thumb-label,\n.theme-gray .mat-primary .mat-slider-track-fill,\n.theme-gray .mat-primary .mat-slider-thumb,\n.theme-gray .mat-primary .mat-slider-thumb-label {\n  background-color: #2196f3; }\n\n.theme-dark .mat-primary .mat-slider-thumb-label-text,\n.theme-gray .mat-primary .mat-slider-thumb-label-text {\n  color: white; }\n\n.theme-dark .mat-accent .mat-slider-track-fill,\n.theme-dark .mat-accent .mat-slider-thumb,\n.theme-dark .mat-accent .mat-slider-thumb-label,\n.theme-gray .mat-accent .mat-slider-track-fill,\n.theme-gray .mat-accent .mat-slider-thumb,\n.theme-gray .mat-accent .mat-slider-thumb-label {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-accent .mat-slider-thumb-label-text,\n.theme-gray .mat-accent .mat-slider-thumb-label-text {\n  color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-warn .mat-slider-track-fill,\n.theme-dark .mat-warn .mat-slider-thumb,\n.theme-dark .mat-warn .mat-slider-thumb-label,\n.theme-gray .mat-warn .mat-slider-track-fill,\n.theme-gray .mat-warn .mat-slider-thumb,\n.theme-gray .mat-warn .mat-slider-thumb-label {\n  background-color: #ef5350; }\n\n.theme-dark .mat-warn .mat-slider-thumb-label-text,\n.theme-gray .mat-warn .mat-slider-thumb-label-text {\n  color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-slider-focus-ring,\n.theme-gray .mat-slider-focus-ring {\n  background-color: rgba(102, 187, 106, 0.2); }\n\n.theme-dark .mat-slider:hover .mat-slider-track-background,\n.theme-dark .cdk-focused .mat-slider-track-background,\n.theme-gray .mat-slider:hover .mat-slider-track-background,\n.theme-gray .cdk-focused .mat-slider-track-background {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-disabled .mat-slider-track-background,\n.theme-dark .mat-slider-disabled .mat-slider-track-fill,\n.theme-dark .mat-slider-disabled .mat-slider-thumb,\n.theme-gray .mat-slider-disabled .mat-slider-track-background,\n.theme-gray .mat-slider-disabled .mat-slider-track-fill,\n.theme-gray .mat-slider-disabled .mat-slider-thumb {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-disabled:hover .mat-slider-track-background,\n.theme-gray .mat-slider-disabled:hover .mat-slider-track-background {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-min-value .mat-slider-focus-ring,\n.theme-gray .mat-slider-min-value .mat-slider-focus-ring {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb,\n.theme-dark .mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb-label,\n.theme-gray .mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb,\n.theme-gray .mat-slider-min-value.mat-slider-thumb-label-showing .mat-slider-thumb-label {\n  background-color: white; }\n\n.theme-dark .mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb,\n.theme-dark .mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb-label,\n.theme-gray .mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb,\n.theme-gray .mat-slider-min-value.mat-slider-thumb-label-showing.cdk-focused .mat-slider-thumb-label {\n  background-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-min-value:not(.mat-slider-thumb-label-showing) .mat-slider-thumb,\n.theme-gray .mat-slider-min-value:not(.mat-slider-thumb-label-showing) .mat-slider-thumb {\n  border-color: rgba(255, 255, 255, 0.3);\n  background-color: transparent; }\n\n.theme-dark .mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover .mat-slider-thumb, .theme-dark .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused .mat-slider-thumb,\n.theme-gray .mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover .mat-slider-thumb,\n.theme-gray .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused .mat-slider-thumb {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover.mat-slider-disabled .mat-slider-thumb, .theme-dark .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused.mat-slider-disabled .mat-slider-thumb,\n.theme-gray .mat-slider-min-value:not(.mat-slider-thumb-label-showing):hover.mat-slider-disabled .mat-slider-thumb,\n.theme-gray .mat-slider-min-value:not(.mat-slider-thumb-label-showing).cdk-focused.mat-slider-disabled .mat-slider-thumb {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-slider-has-ticks .mat-slider-wrapper::after,\n.theme-gray .mat-slider-has-ticks .mat-slider-wrapper::after {\n  border-color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .mat-slider-horizontal .mat-slider-ticks,\n.theme-gray .mat-slider-horizontal .mat-slider-ticks {\n  background-image: repeating-linear-gradient(to right, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0.7) 2px, transparent 0, transparent);\n  background-image: -moz-repeating-linear-gradient(0.0001deg, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0.7) 2px, transparent 0, transparent); }\n\n.theme-dark .mat-slider-vertical .mat-slider-ticks,\n.theme-gray .mat-slider-vertical .mat-slider-ticks {\n  background-image: repeating-linear-gradient(to bottom, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0.7) 2px, transparent 0, transparent); }\n\n.theme-dark .mat-step-header.cdk-keyboard-focused, .theme-dark .mat-step-header.cdk-program-focused, .theme-dark .mat-step-header:hover,\n.theme-gray .mat-step-header.cdk-keyboard-focused,\n.theme-gray .mat-step-header.cdk-program-focused,\n.theme-gray .mat-step-header:hover {\n  background-color: rgba(255, 255, 255, 0.04); }\n\n.theme-dark .mat-step-header .mat-step-label,\n.theme-dark .mat-step-header .mat-step-optional,\n.theme-gray .mat-step-header .mat-step-label,\n.theme-gray .mat-step-header .mat-step-optional {\n  color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-step-header .mat-step-icon,\n.theme-gray .mat-step-header .mat-step-icon {\n  background-color: #2196f3;\n  color: white; }\n\n.theme-dark .mat-step-header .mat-step-icon-not-touched,\n.theme-gray .mat-step-header .mat-step-icon-not-touched {\n  background-color: rgba(255, 255, 255, 0.3);\n  color: white; }\n\n.theme-dark .mat-step-header .mat-step-label.mat-step-label-active,\n.theme-gray .mat-step-header .mat-step-label.mat-step-label-active {\n  color: white; }\n\n.theme-dark .mat-stepper-horizontal, .theme-dark .mat-stepper-vertical,\n.theme-gray .mat-stepper-horizontal,\n.theme-gray .mat-stepper-vertical {\n  background-color: #424242; }\n\n.theme-dark .mat-stepper-vertical-line::before,\n.theme-gray .mat-stepper-vertical-line::before {\n  border-left-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-stepper-horizontal-line,\n.theme-gray .mat-stepper-horizontal-line {\n  border-top-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-tab-nav-bar,\n.theme-dark .mat-tab-header,\n.theme-gray .mat-tab-nav-bar,\n.theme-gray .mat-tab-header {\n  border-bottom: 1px solid rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-tab-group-inverted-header .mat-tab-nav-bar,\n.theme-dark .mat-tab-group-inverted-header .mat-tab-header,\n.theme-gray .mat-tab-group-inverted-header .mat-tab-nav-bar,\n.theme-gray .mat-tab-group-inverted-header .mat-tab-header {\n  border-top: 1px solid rgba(255, 255, 255, 0.12);\n  border-bottom: none; }\n\n.theme-dark .mat-tab-label, .theme-dark .mat-tab-link,\n.theme-gray .mat-tab-label,\n.theme-gray .mat-tab-link {\n  color: white; }\n  .theme-dark .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-link.mat-tab-disabled {\n    color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-header-pagination-chevron {\n  border-color: white; }\n\n.theme-dark .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(255, 255, 255, 0.3); }\n\n.theme-dark .mat-tab-group[class*='mat-background-'] .mat-tab-header,\n.theme-dark .mat-tab-nav-bar[class*='mat-background-'],\n.theme-gray .mat-tab-group[class*='mat-background-'] .mat-tab-header,\n.theme-gray .mat-tab-nav-bar[class*='mat-background-'] {\n  border-bottom: none;\n  border-top: none; }\n\n.theme-dark .mat-tab-group.mat-primary .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-primary .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-primary .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-primary .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-primary .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-primary .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-primary .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-primary .mat-tab-link:focus {\n  background-color: rgba(187, 222, 251, 0.3); }\n\n.theme-dark .mat-tab-group.mat-primary .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-primary .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-primary .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-primary .mat-ink-bar {\n  background-color: #2196f3; }\n\n.theme-dark .mat-tab-group.mat-primary.mat-background-primary .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-primary.mat-background-primary .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-primary.mat-background-primary .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-primary.mat-background-primary .mat-ink-bar {\n  background-color: white; }\n\n.theme-dark .mat-tab-group.mat-accent .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-accent .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-accent .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-accent .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-accent .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-accent .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-accent .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-accent .mat-tab-link:focus {\n  background-color: rgba(200, 230, 201, 0.3); }\n\n.theme-dark .mat-tab-group.mat-accent .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-accent .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-accent .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-accent .mat-ink-bar {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-tab-group.mat-accent.mat-background-accent .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-accent.mat-background-accent .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-accent.mat-background-accent .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-accent.mat-background-accent .mat-ink-bar {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-tab-group.mat-warn .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-warn .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-warn .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-warn .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-warn .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-warn .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-warn .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-warn .mat-tab-link:focus {\n  background-color: rgba(255, 205, 210, 0.3); }\n\n.theme-dark .mat-tab-group.mat-warn .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-warn .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-warn .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-warn .mat-ink-bar {\n  background-color: #ef5350; }\n\n.theme-dark .mat-tab-group.mat-warn.mat-background-warn .mat-ink-bar, .theme-dark .mat-tab-nav-bar.mat-warn.mat-background-warn .mat-ink-bar,\n.theme-gray .mat-tab-group.mat-warn.mat-background-warn .mat-ink-bar,\n.theme-gray .mat-tab-nav-bar.mat-warn.mat-background-warn .mat-ink-bar {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-background-primary .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-link:focus {\n  background-color: rgba(187, 222, 251, 0.3); }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-tab-header, .theme-dark .mat-tab-group.mat-background-primary .mat-tab-links, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-header, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-links,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-header,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-links,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-header,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-links {\n  background-color: #2196f3; }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-tab-label, .theme-dark .mat-tab-group.mat-background-primary .mat-tab-link, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-label, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-link,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-label,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-link,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-label,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-link {\n  color: white; }\n  .theme-dark .mat-tab-group.mat-background-primary .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-group.mat-background-primary .mat-tab-link.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-primary .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-primary .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-link.mat-tab-disabled {\n    color: rgba(255, 255, 255, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-chevron {\n  border-color: white; }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(255, 255, 255, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-primary .mat-ripple-element, .theme-dark .mat-tab-nav-bar.mat-background-primary .mat-ripple-element,\n.theme-gray .mat-tab-group.mat-background-primary .mat-ripple-element,\n.theme-gray .mat-tab-nav-bar.mat-background-primary .mat-ripple-element {\n  background-color: rgba(255, 255, 255, 0.12); }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-background-accent .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-link:focus {\n  background-color: rgba(200, 230, 201, 0.3); }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-tab-header, .theme-dark .mat-tab-group.mat-background-accent .mat-tab-links, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-header, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-links,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-header,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-links,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-header,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-links {\n  background-color: #66bb6a; }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-tab-label, .theme-dark .mat-tab-group.mat-background-accent .mat-tab-link, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-label, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-link,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-label,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-link,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-label,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-link {\n  color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-tab-group.mat-background-accent .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-group.mat-background-accent .mat-tab-link.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-accent .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-accent .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-link.mat-tab-disabled {\n    color: rgba(0, 0, 0, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-accent .mat-ripple-element, .theme-dark .mat-tab-nav-bar.mat-background-accent .mat-ripple-element,\n.theme-gray .mat-tab-group.mat-background-accent .mat-ripple-element,\n.theme-gray .mat-tab-nav-bar.mat-background-accent .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.12); }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-tab-label:focus, .theme-dark .mat-tab-group.mat-background-warn .mat-tab-link:focus, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-label:focus, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-link:focus,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-label:focus,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-link:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-label:focus,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-link:focus {\n  background-color: rgba(255, 205, 210, 0.3); }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-tab-header, .theme-dark .mat-tab-group.mat-background-warn .mat-tab-links, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-header, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-links,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-header,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-links,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-header,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-links {\n  background-color: #ef5350; }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-tab-label, .theme-dark .mat-tab-group.mat-background-warn .mat-tab-link, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-label, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-link,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-label,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-link,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-label,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-link {\n  color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-tab-group.mat-background-warn .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-group.mat-background-warn .mat-tab-link.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-label.mat-tab-disabled, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-warn .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-group.mat-background-warn .mat-tab-link.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-label.mat-tab-disabled,\n  .theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-link.mat-tab-disabled {\n    color: rgba(0, 0, 0, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-group.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-tab-header-pagination-disabled .mat-tab-header-pagination-chevron {\n  border-color: rgba(0, 0, 0, 0.4); }\n\n.theme-dark .mat-tab-group.mat-background-warn .mat-ripple-element, .theme-dark .mat-tab-nav-bar.mat-background-warn .mat-ripple-element,\n.theme-gray .mat-tab-group.mat-background-warn .mat-ripple-element,\n.theme-gray .mat-tab-nav-bar.mat-background-warn .mat-ripple-element {\n  background-color: rgba(0, 0, 0, 0.12); }\n\n.theme-dark .mat-toolbar,\n.theme-gray .mat-toolbar {\n  background: #212121;\n  color: white; }\n  .theme-dark .mat-toolbar.mat-primary,\n  .theme-gray .mat-toolbar.mat-primary {\n    background: #2196f3;\n    color: white; }\n  .theme-dark .mat-toolbar.mat-accent,\n  .theme-gray .mat-toolbar.mat-accent {\n    background: #66bb6a;\n    color: rgba(0, 0, 0, 0.87); }\n  .theme-dark .mat-toolbar.mat-warn,\n  .theme-gray .mat-toolbar.mat-warn {\n    background: #ef5350;\n    color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-tooltip,\n.theme-gray .mat-tooltip {\n  background: rgba(97, 97, 97, 0.9); }\n\n.theme-dark .mat-snack-bar-container,\n.theme-gray .mat-snack-bar-container {\n  background: #fafafa;\n  color: rgba(0, 0, 0, 0.87); }\n\n.theme-dark .mat-simple-snackbar-action,\n.theme-gray .mat-simple-snackbar-action {\n  color: inherit; }\n", ""]);

// exports


/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/theme.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".bg-color-dark .bg-color-light a {\n  color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-body {\n  background-color: #e5e5e5;\n  color: rgba(0, 0, 0, 0.87); }\n  .bg-color-body:hover {\n    background-color: #e5e5e5;\n    color: rgba(0, 0, 0, 0.87); }\n  .bg-color-body a {\n    color: rgba(0, 0, 0, 0.87); }\n    .bg-color-body a:hover {\n      color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-page {\n  background-color: #f5f5f5;\n  color: rgba(0, 0, 0, 0.87); }\n  .bg-color-page:hover {\n    background-color: #f5f5f5;\n    color: rgba(0, 0, 0, 0.87); }\n  .bg-color-page a {\n    color: rgba(0, 0, 0, 0.87); }\n    .bg-color-page a:hover {\n      color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-light {\n  background-color: #fff;\n  color: rgba(0, 0, 0, 0.87); }\n  .bg-color-light:hover {\n    background-color: #fff;\n    color: rgba(0, 0, 0, 0.87); }\n  .bg-color-light a {\n    color: rgba(0, 0, 0, 0.87); }\n    .bg-color-light a:hover {\n      color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-white {\n  background-color: #fff;\n  color: rgba(0, 0, 0, 0.87); }\n  .bg-color-white:hover {\n    background-color: #fff;\n    color: rgba(0, 0, 0, 0.87); }\n  .bg-color-white a {\n    color: rgba(0, 0, 0, 0.87); }\n    .bg-color-white a:hover {\n      color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-gray {\n  background-color: #636c72;\n  color: #fff; }\n  .bg-color-gray:hover {\n    background-color: #636c72;\n    color: #fff; }\n  .bg-color-gray a {\n    color: #fff; }\n    .bg-color-gray a:hover {\n      color: #fff; }\n\n.bg-color-dark {\n  background-color: #343a40;\n  color: #fff; }\n  .bg-color-dark:hover {\n    background-color: #343a40;\n    color: #fff; }\n  .bg-color-dark a {\n    color: #fff; }\n    .bg-color-dark a:hover {\n      color: #fff; }\n\n.bg-color-primary {\n  background-color: #2196F3;\n  color: #fff; }\n  .bg-color-primary:hover {\n    background-color: #2196F3;\n    color: #fff; }\n  .bg-color-primary a {\n    color: #fff; }\n    .bg-color-primary a:hover {\n      color: #fff; }\n\n.bg-color-success {\n  background-color: #66BB6A;\n  color: #fff; }\n  .bg-color-success:hover {\n    background-color: #66BB6A;\n    color: #fff; }\n  .bg-color-success a {\n    color: #fff; }\n    .bg-color-success a:hover {\n      color: #fff; }\n\n.bg-color-info {\n  background-color: #00BCD4;\n  color: #fff; }\n  .bg-color-info:hover {\n    background-color: #00BCD4;\n    color: #fff; }\n  .bg-color-info a {\n    color: #fff; }\n    .bg-color-info a:hover {\n      color: #fff; }\n\n.bg-color-warning {\n  background-color: #ffc107;\n  color: rgba(0, 0, 0, 0.87); }\n  .bg-color-warning:hover {\n    background-color: #ffc107;\n    color: rgba(0, 0, 0, 0.87); }\n  .bg-color-warning a {\n    color: rgba(0, 0, 0, 0.87); }\n    .bg-color-warning a:hover {\n      color: rgba(0, 0, 0, 0.87); }\n\n.bg-color-danger {\n  background-color: #EF5350;\n  color: #fff; }\n  .bg-color-danger:hover {\n    background-color: #EF5350;\n    color: #fff; }\n  .bg-color-danger a {\n    color: #fff; }\n    .bg-color-danger a:hover {\n      color: #fff; }\n\n.mdl-data-table tbody .bg-color-dark {\n  background-color: #343a40;\n  color: #fff; }\n  .mdl-data-table tbody .bg-color-dark:hover {\n    background-color: #343a40;\n    color: #fff; }\n  .mdl-data-table tbody .bg-color-dark a {\n    color: #fff; }\n    .mdl-data-table tbody .bg-color-dark a:hover {\n      color: #fff; }\n\n.mdl-data-table tbody .bg-color-primary {\n  background-color: #2196F3;\n  color: #fff; }\n  .mdl-data-table tbody .bg-color-primary:hover {\n    background-color: #2196F3;\n    color: #fff; }\n  .mdl-data-table tbody .bg-color-primary a {\n    color: #fff; }\n    .mdl-data-table tbody .bg-color-primary a:hover {\n      color: #fff; }\n\n.mdl-data-table tbody .bg-color-success {\n  background-color: #66BB6A;\n  color: #fff; }\n  .mdl-data-table tbody .bg-color-success:hover {\n    background-color: #66BB6A;\n    color: #fff; }\n  .mdl-data-table tbody .bg-color-success a {\n    color: #fff; }\n    .mdl-data-table tbody .bg-color-success a:hover {\n      color: #fff; }\n\n.mdl-data-table tbody .bg-color-info {\n  background-color: #00BCD4;\n  color: #fff; }\n  .mdl-data-table tbody .bg-color-info:hover {\n    background-color: #00BCD4;\n    color: #fff; }\n  .mdl-data-table tbody .bg-color-info a {\n    color: #fff; }\n    .mdl-data-table tbody .bg-color-info a:hover {\n      color: #fff; }\n\n.mdl-data-table tbody .bg-color-danger {\n  background-color: #EF5350;\n  color: #fff; }\n  .mdl-data-table tbody .bg-color-danger:hover {\n    background-color: #EF5350;\n    color: #fff; }\n  .mdl-data-table tbody .bg-color-danger a {\n    color: #fff; }\n    .mdl-data-table tbody .bg-color-danger a:hover {\n      color: #fff; }\n\n.color-option-check {\n  position: relative;\n  display: block; }\n  .color-option-check input[type=\"radio\"] {\n    display: none; }\n  .color-option-check input[type=\"radio\"] + span:hover {\n    cursor: pointer; }\n  .color-option-check input[type=\"radio\"] + span {\n    position: relative; }\n    .color-option-check input[type=\"radio\"] + span > .overlay {\n      display: none;\n      position: absolute;\n      top: 0;\n      bottom: 0;\n      right: 0;\n      left: 0;\n      width: 100%;\n      height: 100%;\n      background-color: rgba(0, 0, 0, 0.3);\n      text-align: center;\n      line-height: 30px;\n      color: #fff; }\n  .color-option-check input[type=\"radio\"]:checked + span > .overlay {\n    display: block; }\n  .color-option-check .color-option-item {\n    overflow: hidden;\n    display: block;\n    box-shadow: 0 0 2px rgba(0, 0, 0, 0.1);\n    margin-bottom: 15px; }\n    .color-option-check .color-option-item > span {\n      display: block;\n      float: left;\n      width: 50%;\n      height: 20px; }\n    .color-option-check .color-option-item .item-header {\n      height: 10px; }\n\n.color-option-check .bg-color-page {\n  background-color: #f1f1f1; }\n\n.theme-options > div {\n  padding: 0; }\n\n.theme-option-check {\n  position: relative;\n  display: block;\n  margin: 0;\n  font-weight: normal; }\n  .theme-option-check input[type=\"radio\"] {\n    display: none; }\n  .theme-option-check input[type=\"radio\"] + span:hover {\n    cursor: pointer; }\n  .theme-option-check input[type=\"radio\"] + span {\n    position: relative; }\n    .theme-option-check input[type=\"radio\"] + span > .overlay {\n      display: none;\n      position: absolute;\n      top: 0;\n      bottom: 0;\n      right: 0;\n      left: 0;\n      width: 100%;\n      height: 100%;\n      text-align: center;\n      line-height: 60px;\n      color: #fff; }\n      .theme-option-check input[type=\"radio\"] + span > .overlay .material-icons {\n        vertical-align: bottom;\n        color: #66BB6A; }\n  .theme-option-check input[type=\"radio\"]:checked + span > .overlay {\n    display: block; }\n  .theme-option-check .theme-option-item {\n    overflow: hidden;\n    display: block; }\n    .theme-option-check .theme-option-item > span {\n      display: block;\n      text-align: center;\n      height: 60px;\n      line-height: 60px;\n      text-transform: uppercase; }\n\n.app-sidebar.bg-color-light .nav {\n  color: rgba(0, 0, 0, 0.87); }\n  .app-sidebar.bg-color-light .nav a {\n    color: rgba(0, 0, 0, 0.87); }\n  .app-sidebar.bg-color-light .nav .nav-header {\n    color: #868e96; }\n  .app-sidebar.bg-color-light .nav li > a:hover, .app-sidebar.bg-color-light .nav li > a:focus {\n    background-color: transparent;\n    color: #2196F3; }\n  .app-sidebar.bg-color-light .nav li.active > a,\n  .app-sidebar.bg-color-light .nav li.active > a:hover,\n  .app-sidebar.bg-color-light .nav li.active > a:focus {\n    background-color: transparent;\n    color: #2196F3; }\n  .app-sidebar.bg-color-light .nav li.open > a,\n  .app-sidebar.bg-color-light .nav li.open > a:hover,\n  .app-sidebar.bg-color-light .nav li.open > a:focus {\n    background-color: rgba(0, 0, 0, 0.05);\n    color: #2196F3; }\n  .app-sidebar.bg-color-light .nav li.open > .icon-has-ul {\n    color: #2196F3; }\n  .app-sidebar.bg-color-light .nav li > a:focus {\n    background-color: transparent; }\n  .app-sidebar.bg-color-light .nav ul {\n    background-color: rgba(0, 0, 0, 0.05); }\n    .app-sidebar.bg-color-light .nav ul li.active > a,\n    .app-sidebar.bg-color-light .nav ul li.active > a:hover,\n    .app-sidebar.bg-color-light .nav ul li.active > a:focus, .app-sidebar.bg-color-light .nav ul li.open > a,\n    .app-sidebar.bg-color-light .nav ul li.open > a:hover,\n    .app-sidebar.bg-color-light .nav ul li.open > a:focus {\n      background-color: rgba(0, 0, 0, 0.05);\n      color: #2196F3; }\n  .app-sidebar.bg-color-light .nav ul ul {\n    background-color: rgba(0, 0, 0, 0.05); }\n    .app-sidebar.bg-color-light .nav ul ul > li.active > a,\n    .app-sidebar.bg-color-light .nav ul ul > li.active > a:hover,\n    .app-sidebar.bg-color-light .nav ul ul > li.active > a:focus, .app-sidebar.bg-color-light .nav ul ul > li.open > a,\n    .app-sidebar.bg-color-light .nav ul ul > li.open > a:hover,\n    .app-sidebar.bg-color-light .nav ul ul > li.open > a:focus {\n      background-color: rgba(0, 0, 0, 0.05);\n      color: #2196F3; }\n  .app-sidebar.bg-color-light .nav ul ul ul {\n    background-color: rgba(0, 0, 0, 0.05); }\n\n.app-sidebar.bg-color-light .sidebar-footer {\n  background-color: #fafafa; }\n\n.app-sidebar .sidebar-header.bg-color-light a.collapsednav-toggler, .app-sidebar .sidebar-header.bg-color-warning a.collapsednav-toggler {\n  color: rgba(0, 0, 0, 0.5); }\n\nhtml,\nbody,\n.app-header {\n  background-color: #e5e5e5; }\n\n.app-page-container .app-content-wrapper {\n  background-color: #f5f5f5; }\n\n@media only screen and (max-width: 991px) {\n  .app-page-container {\n    background-color: #f5f5f5; } }\n\n.theme-gray,\n.theme-dark {\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-gray a:hover, .theme-gray a:focus,\n  .theme-dark a:hover,\n  .theme-dark a:focus {\n    color: #2196F3; }\n  .theme-gray .app-sidebar .sidebar-header,\n  .theme-dark .app-sidebar .sidebar-header {\n    color: #fff; }\n    .theme-gray .app-sidebar .sidebar-header a,\n    .theme-dark .app-sidebar .sidebar-header a {\n      color: #fff; }\n    .theme-gray .app-sidebar .sidebar-header a.collapsednav-toggler,\n    .theme-dark .app-sidebar .sidebar-header a.collapsednav-toggler {\n      color: #fff;\n      opacity: .5; }\n  .theme-gray .app-overlay,\n  .theme-dark .app-overlay {\n    background: rgba(0, 0, 0, 0.7);\n    color: rgba(255, 255, 255, 0.7); }\n    .theme-gray .app-overlay input,\n    .theme-dark .app-overlay input {\n      color: rgba(255, 255, 255, 0.7); }\n    .theme-gray .app-overlay a.overlay-close,\n    .theme-dark .app-overlay a.overlay-close {\n      color: #868e96; }\n  .theme-gray .app-sidebar .sidebar-header,\n  .theme-dark .app-sidebar .sidebar-header {\n    color: #a1a1a1; }\n    .theme-gray .app-sidebar .sidebar-header a,\n    .theme-dark .app-sidebar .sidebar-header a {\n      color: #a1a1a1 !important; }\n  .theme-gray .app-footer .brand,\n  .theme-dark .app-footer .brand {\n    color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .app-sidebar {\n  background-color: #3c3c3c; }\n  .theme-gray .app-sidebar .sidebar-header {\n    background-color: #3c3c3c; }\n  .theme-gray .app-sidebar .sidebar-footer {\n    background-color: #3c3c3c; }\n  .theme-gray .app-sidebar .nav {\n    color: #a1a1a1; }\n    .theme-gray .app-sidebar .nav a {\n      color: #a1a1a1; }\n\n.theme-gray .app-page-container .app-content-wrapper {\n  background-color: #444; }\n\n@media only screen and (max-width: 991px) {\n  .theme-gray .app-page-container {\n    background-color: #444; } }\n\n.theme-gray .app-header-inner.bg-color-light, .theme-gray .app-header-inner.bg-color-dark, .theme-gray .app-header-inner.bg-color-primary, .theme-gray .app-header-inner.bg-color-success, .theme-gray .app-header-inner.bg-color-info, .theme-gray .app-header-inner.bg-color-warning, .theme-gray .app-header-inner.bg-color-danger {\n  background-color: #4c4c4c;\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-gray .app-header-inner.bg-color-light a, .theme-gray .app-header-inner.bg-color-dark a, .theme-gray .app-header-inner.bg-color-primary a, .theme-gray .app-header-inner.bg-color-success a, .theme-gray .app-header-inner.bg-color-info a, .theme-gray .app-header-inner.bg-color-warning a, .theme-gray .app-header-inner.bg-color-danger a {\n    color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark .app-sidebar {\n  background-color: #343a40; }\n  .theme-dark .app-sidebar .sidebar-header {\n    background-color: #343a40; }\n  .theme-dark .app-sidebar .sidebar-footer {\n    background-color: #343a40; }\n  .theme-dark .app-sidebar .nav {\n    color: #a1a1a1; }\n    .theme-dark .app-sidebar .nav a {\n      color: #a1a1a1; }\n\n.theme-dark .app-page-container .app-content-wrapper {\n  background-color: #3a4047; }\n\n@media only screen and (max-width: 991px) {\n  .theme-dark .app-page-container {\n    background-color: #3a4047; } }\n\n.theme-dark .app-header-inner.bg-color-light, .theme-dark .app-header-inner.bg-color-dark, .theme-dark .app-header-inner.bg-color-primary, .theme-dark .app-header-inner.bg-color-success, .theme-dark .app-header-inner.bg-color-info, .theme-dark .app-header-inner.bg-color-warning, .theme-dark .app-header-inner.bg-color-danger {\n  background-color: #424951;\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-dark .app-header-inner.bg-color-light a, .theme-dark .app-header-inner.bg-color-dark a, .theme-dark .app-header-inner.bg-color-primary a, .theme-dark .app-header-inner.bg-color-success a, .theme-dark .app-header-inner.bg-color-info a, .theme-dark .app-header-inner.bg-color-warning a, .theme-dark .app-header-inner.bg-color-danger a {\n    color: rgba(255, 255, 255, 0.7); }\n\n.theme-dark {\n  background-color: #3a4047; }\n\n.theme-gray {\n  background-color: #444; }\n", ""]);

// exports


/***/ }),

/***/ "../../../../css-loader/index.js?{\"sourceMap\":false,\"importLoaders\":1}!../../../../postcss-loader/lib/index.js?{\"ident\":\"postcss\"}!../../../../sass-loader/lib/loader.js?{\"sourceMap\":false,\"precision\":8,\"includePaths\":[]}!../../../../../src/styles/ui.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/* Material Design Lite */\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/* Typography */\n/* Shadows */\n/* Animations */\n/* Dialog */\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/* Typography */\n/* Shadows */\n/* Animations */\n/* Dialog */\nhtml, body {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20px; }\n\nh1, h2, h3, h4, h5, h6, p {\n  margin: 0;\n  padding: 0; }\n\n/**\n  * Styles for HTML elements\n  */\nh1 small, h2 small, h3 small, h4 small, h5 small, h6 small {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 56px;\n  font-weight: 400;\n  line-height: 1.35;\n  letter-spacing: -0.02em;\n  opacity: 0.54;\n  font-size: 0.6em; }\n\nh1 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 56px;\n  font-weight: 400;\n  line-height: 1.35;\n  letter-spacing: -0.02em;\n  margin-top: 24px;\n  margin-bottom: 24px; }\n\nh2 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 45px;\n  font-weight: 400;\n  line-height: 48px;\n  margin-top: 24px;\n  margin-bottom: 24px; }\n\nh3 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 34px;\n  font-weight: 400;\n  line-height: 40px;\n  margin-top: 24px;\n  margin-bottom: 24px; }\n\nh4 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 24px;\n  font-weight: 400;\n  line-height: 32px;\n  -moz-osx-font-smoothing: grayscale;\n  margin-top: 24px;\n  margin-bottom: 16px; }\n\nh5 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 20px;\n  font-weight: 500;\n  line-height: 1;\n  letter-spacing: 0.02em;\n  margin-top: 24px;\n  margin-bottom: 16px; }\n\nh6 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 16px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0.04em;\n  margin-top: 24px;\n  margin-bottom: 16px; }\n\np {\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0;\n  margin-bottom: 16px; }\n\na {\n  color: #2196F3;\n  font-weight: 500; }\n\nblockquote {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  position: relative;\n  font-size: 24px;\n  font-weight: 300;\n  font-style: italic;\n  line-height: 1.35;\n  letter-spacing: 0.08em; }\n  blockquote:before {\n    position: absolute;\n    left: -0.5em;\n    content: '\\201C'; }\n  blockquote:after {\n    content: '\\201D';\n    margin-left: -0.05em; }\n\nmark {\n  background-color: #f4ff81; }\n\ndt {\n  font-weight: 700; }\n\naddress {\n  font-size: 12px;\n  font-weight: 400;\n  line-height: 1;\n  letter-spacing: 0;\n  font-style: normal; }\n\nul, ol {\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0; }\n\n/**\n * Class Name Styles\n */\n.mdl-typography--display-4 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 112px;\n  font-weight: 300;\n  line-height: 1;\n  letter-spacing: -0.04em; }\n\n.mdl-typography--display-4-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 112px;\n  font-weight: 300;\n  line-height: 1;\n  letter-spacing: -0.04em;\n  opacity: 0.54; }\n\n.mdl-typography--display-3 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 56px;\n  font-weight: 400;\n  line-height: 1.35;\n  letter-spacing: -0.02em; }\n\n.mdl-typography--display-3-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 56px;\n  font-weight: 400;\n  line-height: 1.35;\n  letter-spacing: -0.02em;\n  opacity: 0.54; }\n\n.mdl-typography--display-2 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 45px;\n  font-weight: 400;\n  line-height: 48px; }\n\n.mdl-typography--display-2-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 45px;\n  font-weight: 400;\n  line-height: 48px;\n  opacity: 0.54; }\n\n.mdl-typography--display-1 {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 34px;\n  font-weight: 400;\n  line-height: 40px; }\n\n.mdl-typography--display-1-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 34px;\n  font-weight: 400;\n  line-height: 40px;\n  opacity: 0.54; }\n\n.mdl-typography--headline {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 24px;\n  font-weight: 400;\n  line-height: 32px;\n  -moz-osx-font-smoothing: grayscale; }\n\n.mdl-typography--headline-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 24px;\n  font-weight: 400;\n  line-height: 32px;\n  -moz-osx-font-smoothing: grayscale;\n  opacity: 0.87; }\n\n.mdl-typography--title {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 20px;\n  font-weight: 500;\n  line-height: 1;\n  letter-spacing: 0.02em; }\n\n.mdl-typography--title-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 20px;\n  font-weight: 500;\n  line-height: 1;\n  letter-spacing: 0.02em;\n  opacity: 0.87; }\n\n.mdl-typography--subhead {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 16px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0.04em; }\n\n.mdl-typography--subhead-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 16px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0.04em;\n  opacity: 0.87; }\n\n.mdl-typography--body-2 {\n  font-size: 14px;\n  font-weight: bold;\n  line-height: 24px;\n  letter-spacing: 0; }\n\n.mdl-typography--body-2-color-contrast {\n  font-size: 14px;\n  font-weight: bold;\n  line-height: 24px;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--body-1 {\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0; }\n\n.mdl-typography--body-1-color-contrast {\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--body-2-force-preferred-font {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  line-height: 24px;\n  letter-spacing: 0; }\n\n.mdl-typography--body-2-force-preferred-font-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  line-height: 24px;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--body-1-force-preferred-font {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0; }\n\n.mdl-typography--body-1-force-preferred-font-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 24px;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--caption {\n  font-size: 12px;\n  font-weight: 400;\n  line-height: 1;\n  letter-spacing: 0; }\n\n.mdl-typography--caption-force-preferred-font {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 12px;\n  font-weight: 400;\n  line-height: 1;\n  letter-spacing: 0; }\n\n.mdl-typography--caption-color-contrast {\n  font-size: 12px;\n  font-weight: 400;\n  line-height: 1;\n  letter-spacing: 0;\n  opacity: 0.54; }\n\n.mdl-typography--caption-force-preferred-font-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 12px;\n  font-weight: 400;\n  line-height: 1;\n  letter-spacing: 0;\n  opacity: 0.54; }\n\n.mdl-typography--menu {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  line-height: 1;\n  letter-spacing: 0; }\n\n.mdl-typography--menu-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  line-height: 1;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--button {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  text-transform: uppercase;\n  line-height: 1;\n  letter-spacing: 0; }\n\n.mdl-typography--button-color-contrast {\n  font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-weight: 500;\n  text-transform: uppercase;\n  line-height: 1;\n  letter-spacing: 0;\n  opacity: 0.87; }\n\n.mdl-typography--text-left {\n  text-align: left; }\n\n.mdl-typography--text-right {\n  text-align: right; }\n\n.mdl-typography--text-center {\n  text-align: center; }\n\n.mdl-typography--text-justify {\n  text-align: justify; }\n\n.mdl-typography--text-nowrap {\n  white-space: nowrap; }\n\n.mdl-typography--text-lowercase {\n  text-transform: lowercase; }\n\n.mdl-typography--text-uppercase {\n  text-transform: uppercase; }\n\n.mdl-typography--text-capitalize {\n  text-transform: capitalize; }\n\n.mdl-typography--font-thin {\n  font-weight: 200 !important; }\n\n.mdl-typography--font-light {\n  font-weight: 300 !important; }\n\n.mdl-typography--font-regular {\n  font-weight: 400 !important; }\n\n.mdl-typography--font-medium {\n  font-weight: 500 !important; }\n\n.mdl-typography--font-bold {\n  font-weight: 700 !important; }\n\n.mdl-typography--font-black {\n  font-weight: 900 !important; }\n\n.material-icons {\n  font-family: 'Material Icons';\n  font-weight: normal;\n  font-style: normal;\n  font-size: 24px;\n  line-height: 1;\n  letter-spacing: normal;\n  text-transform: none;\n  display: inline-block;\n  word-wrap: normal;\n  font-feature-settings: 'liga';\n  -webkit-font-feature-settings: 'liga';\n  -webkit-font-smoothing: antialiased; }\n\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n.mdl-badge {\n  position: relative;\n  white-space: nowrap;\n  margin-right: 24px; }\n  .mdl-badge:not([data-badge]) {\n    margin-right: auto; }\n  .mdl-badge[data-badge]:after {\n    content: attr(data-badge);\n    display: -webkit-box;\n    display: -ms-flexbox;\n    display: flex;\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n        -ms-flex-direction: row;\n            flex-direction: row;\n    -ms-flex-wrap: wrap;\n        flex-wrap: wrap;\n    -webkit-box-pack: center;\n        -ms-flex-pack: center;\n            justify-content: center;\n    -ms-flex-line-pack: center;\n        align-content: center;\n    -webkit-box-align: center;\n        -ms-flex-align: center;\n            align-items: center;\n    position: absolute;\n    top: -11px;\n    right: -24px;\n    font-family: \"Roboto\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n    font-weight: 600;\n    font-size: 75%;\n    width: 22px;\n    height: 22px;\n    border-radius: 50%;\n    background: #00BCD4;\n    color: #333; }\n    .mdl-button .mdl-badge[data-badge]:after {\n      top: -10px;\n      right: -5px; }\n  .mdl-badge.mdl-badge--no-background[data-badge]:after {\n    color: #00BCD4;\n    background: #999;\n    box-shadow: 0 0 1px gray; }\n  .mdl-badge.mdl-badge--overlap {\n    margin-right: 10px; }\n    .mdl-badge.mdl-badge--overlap:after {\n      right: -10px; }\n\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n.mdl-card {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n      -ms-flex-direction: column;\n          flex-direction: column;\n  font-size: 16px;\n  font-weight: 400;\n  min-height: 200px;\n  overflow: hidden;\n  width: 330px;\n  z-index: 1;\n  position: relative;\n  background: rgb(255,255,255);\n  border-radius: 2px;\n  box-sizing: border-box; }\n\n.mdl-card__media {\n  background-color: rgb(#66BB6A);\n  background-repeat: repeat;\n  background-position: 50% 50%;\n  background-size: cover;\n  background-origin: padding-box;\n  background-attachment: scroll;\n  box-sizing: border-box; }\n\n.mdl-card__title {\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n  color: rgb(0,0,0);\n  display: block;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-pack: stretch;\n      -ms-flex-pack: stretch;\n          justify-content: stretch;\n  line-height: normal;\n  padding: 16px 16px;\n  -webkit-perspective-origin: 165px 56px;\n          perspective-origin: 165px 56px;\n  -webkit-transform-origin: 165px 56px;\n          transform-origin: 165px 56px;\n  box-sizing: border-box; }\n  .mdl-card__title.mdl-card--border {\n    border-bottom: 1px solid rgba(0, 0, 0, 0.125); }\n\n.mdl-card__title-text {\n  -ms-flex-item-align: end;\n      align-self: flex-end;\n  color: inherit;\n  display: block;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  font-size: 24px;\n  font-weight: 300;\n  line-height: normal;\n  overflow: hidden;\n  -webkit-transform-origin: 149px 48px;\n          transform-origin: 149px 48px;\n  margin: 0; }\n\n.mdl-card__subtitle-text {\n  font-size: 14px;\n  color: rgba(0,0,0, 0.54);\n  margin: 0; }\n\n.mdl-card__supporting-text {\n  color: rgba(0,0,0, 0.54);\n  font-size: 14px;\n  line-height: 18px;\n  overflow: hidden;\n  padding: 16px 16px;\n  width: 90%; }\n  .mdl-card__supporting-text.mdl-card--border {\n    border-bottom: 1px solid rgba(0, 0, 0, 0.125); }\n\n.mdl-card__actions {\n  font-size: 16px;\n  line-height: normal;\n  width: 100%;\n  background-color: transparent;\n  padding: 8px;\n  box-sizing: border-box; }\n  .mdl-card__actions.mdl-card--border {\n    border-top: 1px solid rgba(0, 0, 0, 0.125); }\n\n.mdl-card--expand {\n  -webkit-box-flex: 1;\n      -ms-flex-positive: 1;\n          flex-grow: 1; }\n\n.mdl-card__menu {\n  position: absolute;\n  right: 16px;\n  top: 16px; }\n\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/* Typography */\n/* Shadows */\n/* Animations */\n/* Dialog */\n.mdl-data-table {\n  position: relative;\n  border: 1px solid rgba(0, 0, 0, 0.1);\n  border-collapse: collapse;\n  white-space: nowrap;\n  font-size: 13px;\n  background-color: rgb(255,255,255); }\n  .mdl-data-table thead {\n    padding-bottom: 3px; }\n    .mdl-data-table thead .mdl-data-table__select {\n      margin-top: 0; }\n  .mdl-data-table tbody tr {\n    position: relative;\n    height: 48px;\n    transition-duration: 0.28s;\n    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);\n    transition-property: background-color; }\n    .mdl-data-table tbody tr.is-selected {\n      background-color: #e0e0e0; }\n    .mdl-data-table tbody tr:hover {\n      background-color: rgba(0, 0, 0, 0.08); }\n  .mdl-data-table td, .mdl-data-table th {\n    padding: 0 18px 12px 18px;\n    text-align: right; }\n    .mdl-data-table td:first-of-type, .mdl-data-table th:first-of-type {\n      padding-left: 24px; }\n    .mdl-data-table td:last-of-type, .mdl-data-table th:last-of-type {\n      padding-right: 24px; }\n  .mdl-data-table td {\n    position: relative;\n    vertical-align: middle;\n    height: 48px;\n    border-top: 1px solid rgba(0, 0, 0, 0.1);\n    border-bottom: 1px solid rgba(0, 0, 0, 0.1);\n    padding-top: 12px;\n    box-sizing: border-box; }\n    .mdl-data-table td .mdl-data-table__select {\n      vertical-align: middle; }\n  .mdl-data-table th {\n    position: relative;\n    vertical-align: bottom;\n    text-overflow: ellipsis;\n    font-size: 14px;\n    font-weight: bold;\n    line-height: 24px;\n    letter-spacing: 0;\n    height: 48px;\n    font-size: 12px;\n    color: rgba(0, 0, 0, 0.54);\n    padding-bottom: 8px;\n    box-sizing: border-box; }\n    .mdl-data-table th.mdl-data-table__header--sorted-ascending, .mdl-data-table th.mdl-data-table__header--sorted-descending {\n      color: rgba(0, 0, 0, 0.87); }\n      .mdl-data-table th.mdl-data-table__header--sorted-ascending:before, .mdl-data-table th.mdl-data-table__header--sorted-descending:before {\n        font-family: 'Material Icons';\n        font-weight: normal;\n        font-style: normal;\n        font-size: 24px;\n        line-height: 1;\n        letter-spacing: normal;\n        text-transform: none;\n        display: inline-block;\n        word-wrap: normal;\n        font-feature-settings: 'liga';\n        -webkit-font-feature-settings: 'liga';\n        -webkit-font-smoothing: antialiased;\n        font-size: 16px;\n        content: \"\\E5D8\";\n        margin-right: 5px;\n        vertical-align: sub; }\n      .mdl-data-table th.mdl-data-table__header--sorted-ascending:hover, .mdl-data-table th.mdl-data-table__header--sorted-descending:hover {\n        cursor: pointer; }\n        .mdl-data-table th.mdl-data-table__header--sorted-ascending:hover:before, .mdl-data-table th.mdl-data-table__header--sorted-descending:hover:before {\n          color: rgba(0, 0, 0, 0.26); }\n    .mdl-data-table th.mdl-data-table__header--sorted-descending:before {\n      content: \"\\E5DB\"; }\n\n.mdl-data-table__select {\n  width: 16px; }\n\n.mdl-data-table__cell--non-numeric.mdl-data-table__cell--non-numeric {\n  text-align: left; }\n\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/**\n * Copyright 2015 Google Inc. All Rights Reserved.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n *      http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n/* Typography */\n/* Shadows */\n/* Animations */\n/* Dialog */\n.mdl-shadow--2dp, .card-panel, .card {\n  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12); }\n\n.mdl-shadow--3dp {\n  box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.14), 0 3px 3px -2px rgba(0, 0, 0, 0.2), 0 1px 8px 0 rgba(0, 0, 0, 0.12); }\n\n.mdl-shadow--4dp {\n  box-shadow: 0 4px 5px 0 rgba(0, 0, 0, 0.14), 0 1px 10px 0 rgba(0, 0, 0, 0.12), 0 2px 4px -1px rgba(0, 0, 0, 0.2); }\n\n.mdl-shadow--6dp {\n  box-shadow: 0 6px 10px 0 rgba(0, 0, 0, 0.14), 0 1px 18px 0 rgba(0, 0, 0, 0.12), 0 3px 5px -1px rgba(0, 0, 0, 0.2); }\n\n.mdl-shadow--8dp {\n  box-shadow: 0 8px 10px 1px rgba(0, 0, 0, 0.14), 0 3px 14px 2px rgba(0, 0, 0, 0.12), 0 5px 5px -3px rgba(0, 0, 0, 0.2); }\n\n.mdl-shadow--16dp {\n  box-shadow: 0 16px 24px 2px rgba(0, 0, 0, 0.14), 0 6px 30px 5px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 0, 0, 0.2); }\n\n.mdl-shadow--24dp {\n  box-shadow: 0 9px 46px 8px rgba(0, 0, 0, 0.14), 0 11px 15px -7px rgba(0, 0, 0, 0.12), 0 24px 38px 3px rgba(0, 0, 0, 0.2); }\n\naddress {\n  line-height: 1.5; }\n\nh1, h2, h3, h4, h5, h6 {\n  line-height: 1.2; }\n\n.divider {\n  display: block;\n  border: 0;\n  border-top: 1px solid transparent;\n  margin-top: 10px;\n  margin-bottom: 10px; }\n  .divider.divider-solid {\n    border-style: solid;\n    border-color: rgba(0, 0, 0, 0.1); }\n  .divider.divider-dashed {\n    border-style: dashed;\n    border-color: rgba(0, 0, 0, 0.1); }\n  .divider.divider-dotted {\n    border-style: dotted;\n    border-color: rgba(0, 0, 0, 0.1); }\n\n.divider-xs {\n  margin-top: 3px;\n  margin-bottom: 3px; }\n\n.divider-sm {\n  margin-top: 5px;\n  margin-bottom: 5px; }\n\n.divider-md {\n  margin-top: 15px;\n  margin-bottom: 15px; }\n\n.divider-lg {\n  margin-top: 20px;\n  margin-bottom: 20px; }\n\n.divider-xl {\n  margin-top: 30px;\n  margin-bottom: 30px; }\n\n.divider-xxl {\n  margin-top: 50px;\n  margin-bottom: 50px; }\n\n.space {\n  display: inline;\n  padding: 6px; }\n\n.space-md {\n  padding: 15px; }\n\n.space-lg {\n  padding: 25px; }\n\nspan.block {\n  display: block; }\n\n.no-margin {\n  margin: 0 !important; }\n\n.no-margin-h {\n  margin-left: 0 !important;\n  margin-right: 0 !important; }\n\n.no-margin-top {\n  margin-top: 0 !important; }\n\n.no-margin-bottom {\n  margin-bottom: 0 !important; }\n\n.no-margin-left {\n  margin-left: 0 !important; }\n\n.no-margin-right {\n  margin-right: 0 !important; }\n\n.no-border {\n  border: 0 !important; }\n\n.no-border-radius {\n  border-radius: 0; }\n\n.no-padding {\n  padding: 0 !important; }\n\n.no-padding-h {\n  padding-left: 0 !important;\n  padding-right: 0 !important; }\n\n.no-shadow {\n  box-shadow: 0; }\n\n.full-width {\n  width: 100%; }\n\n.pull-in {\n  margin-left: -15px;\n  margin-right: -15px; }\n\n.margin-b-lg {\n  margin-bottom: 30px; }\n\n.article-title,\n.article-title-style {\n  font-size: 24px;\n  font-weight: normal;\n  line-height: 32px;\n  margin: .6em 0;\n  color: #2196F3; }\n  .article-title.color-dark,\n  .article-title-style.color-dark {\n    color: rgba(0, 0, 0, 0.87); }\n\n.chapter .article {\n  padding-bottom: 30px; }\n  .chapter .article:nth-of-type(1) .article-title {\n    margin: 0 0 30px;\n    border-top: none; }\n  .chapter .article.article-dark {\n    background-color: rgba(0, 0, 0, 0.015); }\n  .chapter .article.article-bordered {\n    border-top: 1px solid rgba(0, 0, 0, 0.117647);\n    border-bottom: 1px solid rgba(0, 0, 0, 0.117647); }\n    .chapter .article.article-bordered:last-child {\n      border-bottom: 0; }\n  .chapter .article.padding-lg-v {\n    padding: 75px 0; }\n\n.chapter .article-title {\n  font-size: 24px;\n  padding-top: 80px;\n  font-weight: normal;\n  line-height: 32px;\n  margin: 48px 0 30px;\n  border-top: 1px solid rgba(0, 0, 0, 0.117647);\n  color: #2196F3; }\n\n.hero {\n  padding: 30px 0 35px;\n  text-align: center; }\n\n.hero-title {\n  -webkit-font-smoothing: antialiased; }\n\n.hero-title {\n  color: rgba(0, 0, 0, 0.87);\n  font-size: 45px;\n  font-weight: 300;\n  line-height: 50px;\n  margin-bottom: 10px; }\n\n.hero-tagline {\n  margin: 10px auto 30px;\n  max-width: 700px;\n  color: rgba(0, 0, 0, 0.87);\n  font-weight: 300;\n  font-size: 24px;\n  line-height: 32px; }\n\n.theme-gray .hero-title,\n.theme-gray .hero-tagline,\n.theme-dark .hero-title,\n.theme-dark .hero-tagline {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .article-title.color-dark,\n.theme-dark .article-title.color-dark {\n  color: rgba(255, 255, 255, 0.7); }\n\n.container-fluid {\n  padding-left: 30px;\n  padding-right: 30px; }\n  .container-fluid.padding-lg-h {\n    padding-left: 30px;\n    padding-right: 30px; }\n  .container-fluid.with-maxwidth {\n    max-width: 1400px; }\n  .container-fluid.with-maxwidth-lg {\n    max-width: 960px; }\n  .container-fluid.with-maxwidth-md {\n    max-width: 960px; }\n  .container-fluid.with-maxwidth-sm {\n    max-width: 720px; }\n  .container-fluid.no-breadcrumbs {\n    padding-top: 2em;\n    padding-bottom: 1em; }\n\n.gradient-text {\n  font-size: 150px;\n  font-weight: 300;\n  color: #2196F3;\n  background: -webkit-linear-gradient(92deg, #fb83fa, #00aced);\n  -webkit-background-clip: text;\n  -webkit-text-fill-color: transparent; }\n\n.text-small {\n  font-size: 12px; }\n\n.text-normal {\n  font-size: 1rem; }\n\n.text-huge {\n  font-size: 72px; }\n\n.text-large {\n  font-size: 50px; }\n\n.text-thin {\n  font-weight: 300; }\n\n.text-ultralight {\n  font-weight: 100; }\n\n.bold {\n  font-weight: 500; }\n\n.color-primary,\na.color-primary {\n  color: #2196F3; }\n  .color-primary:hover,\n  a.color-primary:hover {\n    color: #2196F3; }\n\n.color-success,\na.color-success {\n  color: #66BB6A; }\n  .color-success:hover,\n  a.color-success:hover {\n    color: #66BB6A; }\n\n.color-info,\na.color-info {\n  color: #00BCD4; }\n  .color-info:hover,\n  a.color-info:hover {\n    color: #00BCD4; }\n\n.color-warning,\na.color-warning {\n  color: #ffc107; }\n  .color-warning:hover,\n  a.color-warning:hover {\n    color: #ffc107; }\n\n.color-danger,\na.color-danger {\n  color: #EF5350; }\n  .color-danger:hover,\n  a.color-danger:hover {\n    color: #EF5350; }\n\n.color-dark,\na.color-dark {\n  color: #343a40; }\n  .color-dark:hover,\n  a.color-dark:hover {\n    color: #343a40; }\n\n.color-white,\na.color-white {\n  color: #fff; }\n  .color-white:hover,\n  a.color-white:hover {\n    color: #fff; }\n\n.color-gray-darker,\na.color-gray-darker {\n  color: #e9ecef; }\n  .color-gray-darker:hover,\n  a.color-gray-darker:hover {\n    color: #e9ecef; }\n\n.color-gray-dark,\na.color-gray-dark {\n  color: #343a40; }\n  .color-gray-dark:hover,\n  a.color-gray-dark:hover {\n    color: #343a40; }\n\n.color-gray,\na.color-gray {\n  color: #495057; }\n  .color-gray:hover,\n  a.color-gray:hover {\n    color: #495057; }\n\n.color-gray-light,\na.color-gray-light {\n  color: #636c72; }\n  .color-gray-light:hover,\n  a.color-gray-light:hover {\n    color: #636c72; }\n\n.color-gray-lighter,\na.color-gray-lighter {\n  color: #e9ecef; }\n  .color-gray-lighter:hover,\n  a.color-gray-lighter:hover {\n    color: #e9ecef; }\n\n.dropcap,\n.dropcap-square,\n.dropcap-circle {\n  display: block;\n  float: left;\n  font-weight: normal;\n  line-height: 36px;\n  margin-right: 6px;\n  text-shadow: none; }\n\n.dropcap {\n  font-size: 3.1em; }\n\n.dropcap-square,\n.dropcap-circle {\n  background-color: #e9ecef;\n  color: rgba(0, 0, 0, 0.87);\n  width: 36px;\n  text-align: center; }\n\n.dropcap-square {\n  border-radius: 0.2rem;\n  font-size: 2.3em; }\n\n.dropcap-circle {\n  border-radius: 50%;\n  font-size: 1.78em; }\n\n.dropcap.colored {\n  color: #2196F3; }\n\n.dropcap-square.colored,\n.dropcap-circle.colored {\n  background-color: #2196F3;\n  color: #fff; }\n\n.ui-highlight {\n  background-color: #343a40;\n  color: #fff;\n  border-radius: 0.2rem;\n  padding: 2px 5px; }\n  .ui-highlight.colored {\n    background-color: #2196F3; }\n\na.btn-w-xs,\nbutton.btn-w-xs {\n  min-width: 80px; }\n\na.btn-w-sm,\nbutton.btn-w-sm {\n  min-width: 100px; }\n\na.btn-w-md,\nbutton.btn-w-md {\n  min-width: 135px; }\n\na.btn-w-lg,\nbutton.btn-w-lg {\n  min-width: 160px; }\n\na.btn-round,\nbutton.btn-round {\n  border-radius: 2em; }\n\na.btn-gap,\nbutton.btn-gap {\n  margin: 5px; }\n\na.btn-gap-h,\nbutton.btn-gap-h {\n  margin: 0 5px; }\n\na.btn-gap-v,\nbutton.btn-gap-v {\n  margin: 0 0 5px; }\n\n.btn-icon {\n  padding: 0;\n  display: inline-block;\n  text-align: center;\n  border-radius: 0.2rem;\n  height: 35px;\n  width: 35px;\n  line-height: 35px; }\n  .btn-icon i {\n    line-height: 35px; }\n  .btn-icon.btn-icon-lined {\n    line-height: 31px; }\n    .btn-icon.btn-icon-lined i {\n      line-height: 31px; }\n    .btn-icon.btn-icon-lined.btn-icon-thin {\n      line-height: 33px; }\n      .btn-icon.btn-icon-lined.btn-icon-thin i {\n        line-height: 33px; }\n\n.btn-icon-round {\n  border-radius: 50%; }\n\n.btn-icon-sm {\n  height: 30px;\n  width: 30px;\n  line-height: 30px; }\n  .btn-icon-sm i {\n    line-height: 30px; }\n  .btn-icon-sm.btn-icon-lined {\n    line-height: 26px; }\n    .btn-icon-sm.btn-icon-lined i {\n      line-height: 26px; }\n    .btn-icon-sm.btn-icon-lined.btn-icon-thin {\n      line-height: 28px; }\n      .btn-icon-sm.btn-icon-lined.btn-icon-thin i {\n        line-height: 28px; }\n\n.btn-icon-md {\n  height: 45px;\n  width: 45px;\n  line-height: 45px;\n  font-size: 18px; }\n  .btn-icon-md i {\n    line-height: 45px; }\n  .btn-icon-md.btn-icon-lined {\n    line-height: 41px; }\n    .btn-icon-md.btn-icon-lined i {\n      line-height: 41px; }\n    .btn-icon-md.btn-icon-lined.btn-icon-thin {\n      line-height: 43px; }\n      .btn-icon-md.btn-icon-lined.btn-icon-thin i {\n        line-height: 43px; }\n\n.btn-icon-lg {\n  height: 65px;\n  width: 65px;\n  line-height: 65px;\n  font-size: 28px; }\n  .btn-icon-lg i {\n    line-height: 65px; }\n  .btn-icon-lg.btn-icon-lined {\n    line-height: 61px; }\n    .btn-icon-lg.btn-icon-lined i {\n      line-height: 61px; }\n    .btn-icon-lg.btn-icon-lined.btn-icon-thin {\n      line-height: 63px; }\n      .btn-icon-lg.btn-icon-lined.btn-icon-thin i {\n        line-height: 63px; }\n\n.btn-icon-lg-alt {\n  height: 70px;\n  width: 70px;\n  line-height: 70px; }\n  .btn-icon-lg-alt i {\n    line-height: 70px; }\n  .btn-icon-lg-alt.btn-icon-lined {\n    line-height: 66px; }\n    .btn-icon-lg-alt.btn-icon-lined i {\n      line-height: 66px; }\n    .btn-icon-lg-alt.btn-icon-lined.btn-icon-thin {\n      line-height: 68px; }\n      .btn-icon-lg-alt.btn-icon-lined.btn-icon-thin i {\n        line-height: 68px; }\n\n.btn-icon-xl {\n  height: 80px;\n  width: 80px;\n  line-height: 80px; }\n  .btn-icon-xl i {\n    line-height: 80px; }\n  .btn-icon-xl.btn-icon-lined {\n    line-height: 76px; }\n    .btn-icon-xl.btn-icon-lined i {\n      line-height: 76px; }\n    .btn-icon-xl.btn-icon-lined.btn-icon-thin {\n      line-height: 78px; }\n      .btn-icon-xl.btn-icon-lined.btn-icon-thin i {\n        line-height: 78px; }\n\n.btn-twitter {\n  color: #fff;\n  background-color: #00c7f7;\n  border-color: #00c7f7; }\n  .btn-twitter:hover, .btn-twitter:focus, .btn-twitter:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #00a6ce;\n    border-color: #0096ba; }\n\n.btn-facebook {\n  color: #fff;\n  background-color: #335397;\n  border-color: #335397; }\n  .btn-facebook:hover, .btn-facebook:focus, .btn-facebook:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #294279;\n    border-color: #243a69; }\n\n.btn-gplus,\n.btn-google-plus {\n  color: #fff;\n  background-color: #dd4a38;\n  border-color: #dd4a38; }\n  .btn-gplus:hover, .btn-gplus:focus, .btn-gplus:active,\n  .btn-google-plus:hover,\n  .btn-google-plus:focus,\n  .btn-google-plus:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #ca3522;\n    border-color: #b8301f; }\n\n.btn-instagram {\n  color: #fff;\n  background-color: #82685A;\n  border-color: #82685A; }\n  .btn-instagram:hover, .btn-instagram:focus, .btn-instagram:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #6a5549;\n    border-color: #5e4b41; }\n\n.btn-vimeo {\n  color: #fff;\n  background-color: #63879C;\n  border-color: #63879C; }\n  .btn-vimeo:hover, .btn-vimeo:focus, .btn-vimeo:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #537183;\n    border-color: #4b6777; }\n\n.btn-flickr {\n  color: #fff;\n  background-color: #0061DB;\n  border-color: #0061DB; }\n  .btn-flickr:hover, .btn-flickr:focus, .btn-flickr:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #004fb2;\n    border-color: #00469e; }\n\n.btn-github {\n  color: #fff;\n  background-color: #3B3B3B;\n  border-color: #3B3B3B; }\n  .btn-github:hover, .btn-github:focus, .btn-github:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #272727;\n    border-color: #1c1c1c; }\n\n.btn-pinterest {\n  color: #fff;\n  background-color: #D73532;\n  border-color: #D73532; }\n  .btn-pinterest:hover, .btn-pinterest:focus, .btn-pinterest:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #bc2725;\n    border-color: #ab2421; }\n\n.btn-tumblr {\n  color: #fff;\n  background-color: #586980;\n  border-color: #586980; }\n  .btn-tumblr:hover, .btn-tumblr:focus, .btn-tumblr:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #475568;\n    border-color: #3f4b5c; }\n\n.btn-linkedin {\n  color: #fff;\n  background-color: #018FAF;\n  border-color: #018FAF; }\n  .btn-linkedin:hover, .btn-linkedin:focus, .btn-linkedin:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #016e86;\n    border-color: #015d72; }\n\n.btn-dribbble {\n  color: #fff;\n  background-color: #EA73A0;\n  border-color: #EA73A0; }\n  .btn-dribbble:hover, .btn-dribbble:focus, .btn-dribbble:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #e55088;\n    border-color: #e23e7c; }\n\n.btn-stumbleupon {\n  color: #fff;\n  background-color: #EA4B24;\n  border-color: #EA4B24; }\n  .btn-stumbleupon:hover, .btn-stumbleupon:focus, .btn-stumbleupon:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #d13914;\n    border-color: #bf3412; }\n\n.btn-lastfm {\n  color: #fff;\n  background-color: #B80638;\n  border-color: #B80638; }\n  .btn-lastfm:hover, .btn-lastfm:focus, .btn-lastfm:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #90052c;\n    border-color: #7d0426; }\n\n.btn-evernote {\n  color: #fff;\n  background-color: #3BAB27;\n  border-color: #3BAB27; }\n  .btn-evernote:hover, .btn-evernote:focus, .btn-evernote:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #308a1f;\n    border-color: #2a791c; }\n\n.btn-skype {\n  color: #fff;\n  background-color: #00B0F6;\n  border-color: #00B0F6; }\n  .btn-skype:hover, .btn-skype:focus, .btn-skype:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #0093cd;\n    border-color: #0084b9; }\n\n.btn-soundcloud {\n  color: #fff;\n  background-color: #0066FF;\n  border-color: #0066FF; }\n  .btn-soundcloud:hover, .btn-soundcloud:focus, .btn-soundcloud:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #0056d6;\n    border-color: #004ec2; }\n\n.btn-behance {\n  color: #fff;\n  background-color: #B80638;\n  border-color: #B80638; }\n  .btn-behance:hover, .btn-behance:focus, .btn-behance:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #90052c;\n    border-color: #7d0426; }\n\n.btn-rss {\n  color: #fff;\n  background-color: #F79638;\n  border-color: #F79638; }\n  .btn-rss:hover, .btn-rss:focus, .btn-rss:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #f58111;\n    border-color: #e87709; }\n\n.btn-youtube {\n  color: #fff;\n  background-color: #CC181E;\n  border-color: #CC181E; }\n  .btn-youtube:hover, .btn-youtube:focus, .btn-youtube:active {\n    color: #fff;\n    text-decoration: none;\n    background-color: #a71419;\n    border-color: #951216; }\n\n.theme-gray .btn-default,\n.theme-gray .btn-secondary,\n.theme-dark .btn-default,\n.theme-dark .btn-secondary {\n  background-color: rgba(255, 255, 255, 0.05);\n  border-color: rgba(0, 0, 0, 0.15);\n  color: rgba(255, 255, 255, 0.7); }\n\n@media only screen and (max-width: 800px) {\n  .table-flip-scroll {\n    /* sort out borders */ }\n    .table-flip-scroll .cf:after {\n      visibility: hidden;\n      display: block;\n      font-size: 0;\n      content: \" \";\n      clear: both;\n      height: 0; }\n    .table-flip-scroll * html .cf {\n      zoom: 1; }\n    .table-flip-scroll *:first-child + html .cf {\n      zoom: 1; }\n    .table-flip-scroll table {\n      width: 100%;\n      border-collapse: collapse;\n      border-spacing: 0; }\n    .table-flip-scroll th,\n    .table-flip-scroll td {\n      margin: 0;\n      vertical-align: top; }\n    .table-flip-scroll th {\n      text-align: left; }\n    .table-flip-scroll table {\n      display: block;\n      position: relative;\n      width: 100%; }\n    .table-flip-scroll thead {\n      display: block;\n      float: left; }\n    .table-flip-scroll tbody {\n      display: block;\n      width: auto;\n      position: relative;\n      overflow-x: auto;\n      white-space: nowrap; }\n    .table-flip-scroll thead tr {\n      display: block; }\n    .table-flip-scroll .table > thead > tr > th:first-child {\n      border-top: 1px solid #ddd; }\n    .table-flip-scroll th {\n      display: block;\n      text-align: right; }\n    .table-flip-scroll tbody tr {\n      display: inline-block;\n      vertical-align: top; }\n    .table-flip-scroll td {\n      display: block;\n      min-height: 1.25em;\n      text-align: left; }\n    .table-flip-scroll th {\n      border-bottom: 0;\n      border-left: 0; }\n    .table-flip-scroll td {\n      border-left: 0;\n      border-right: 0;\n      border-bottom: 0; }\n    .table-flip-scroll tbody tr {\n      border-left: 1px solid #babcbf; }\n    .table-flip-scroll th:last-child,\n    .table-flip-scroll td:last-child {\n      border-bottom: 1px solid #babcbf; }\n    .table-flip-scroll .mdl-data-table tbody tr {\n      height: auto; } }\n\n.theme-gray .form-control,\n.theme-gray .input-group-addon,\n.theme-dark .form-control,\n.theme-dark .input-group-addon {\n  background-color: rgba(255, 255, 255, 0.05);\n  border-color: rgba(0, 0, 0, 0.15);\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .help-block,\n.theme-dark .help-block {\n  color: #636c72; }\n\ninput:-webkit-autofill,\ninput:-webkit-autofill:hover,\ninput:-webkit-autofill:focus,\ninput:-webkit-autofill:active {\n  transition-delay: 9999s; }\n\n.input-round {\n  border-radius: 2em; }\n\ninput.input-primary {\n  border-color: #2196F3; }\n  input.input-primary:focus {\n    border-color: #2196F3; }\n\ninput.input-info {\n  border-color: #00BCD4; }\n  input.input-info:focus {\n    border-color: #00BCD4; }\n\ninput.input-success {\n  border-color: #66BB6A; }\n  input.input-success:focus {\n    border-color: #66BB6A; }\n\ninput.input-warning {\n  border-color: #ffc107; }\n  input.input-warning:focus {\n    border-color: #ffc107; }\n\ninput.input-danger {\n  border-color: #EF5350; }\n  input.input-danger:focus {\n    border-color: #EF5350; }\n\n.box {\n  position: relative;\n  margin-bottom: 1.5rem;\n  border: 0;\n  border-radius: 0.2rem;\n  box-shadow: 0 0 2px rgba(0, 0, 0, 0.15); }\n  .box.box-transparent {\n    box-shadow: none; }\n  .box .box-dark {\n    background-color: rgba(0, 0, 0, 0.03); }\n\n.box-default {\n  background-color: #fff; }\n\n.box-header,\n.box-heading {\n  padding: 0.75rem 1.25rem;\n  border-bottom: 1px solid transparent;\n  border-radius: -0.8rem -0.8rem 0 0;\n  font-weight: 500; }\n\n.box-toolbar {\n  position: absolute;\n  top: 1.25rem;\n  right: 1.25rem; }\n  .box-toolbar a {\n    color: #868e96; }\n    .box-toolbar a .material-icons {\n      font-size: 1rem; }\n\n.box-divider {\n  height: 1px;\n  background: rgba(0, 0, 0, 0.08);\n  margin: 0 1.25rem; }\n\n.box-body {\n  padding: 1.25rem; }\n  .box-body::after {\n    display: block;\n    clear: both;\n    content: \"\"; }\n  .box-body.padding-lg {\n    padding: 30px 15px; }\n  .box-body.padding-xl {\n    padding: 60px 15px; }\n  .box-body.padding-lg-h {\n    padding: 15px 30px; }\n  .box-body.padding-lg-v {\n    padding: 30px 15px; }\n\n.theme-gray .box-default {\n  background-color: #4c4c4c; }\n\n.theme-dark .box-default {\n  background-color: #424951; }\n\n.breadcrumb {\n  display: block;\n  margin: 0;\n  border: 0;\n  border-radius: 0;\n  box-shadow: none;\n  background-color: transparent;\n  padding: 15px 0;\n  text-transform: uppercase;\n  color: #ccc;\n  text-shadow: none;\n  font-size: 11px; }\n  .breadcrumb a {\n    color: #ccc;\n    text-decoration: none; }\n    .breadcrumb a:hover {\n      color: #2196F3; }\n  .breadcrumb li {\n    padding-left: 0; }\n\n.call-to-action {\n  position: relative;\n  text-align: center; }\n  .call-to-action .cta-inner {\n    padding: 2em 0; }\n  .call-to-action .cta-text {\n    font-size: 24px;\n    line-height: 30px; }\n  .call-to-action .cta-btn {\n    margin: 30px 0 10px; }\n  .call-to-action .cta-muted {\n    opacity: .7; }\n  .call-to-action.cta-inline .cta-text {\n    font-size: 20px;\n    font-weight: 300; }\n  .call-to-action.cta-inline .cta-btn {\n    display: inline-block;\n    margin: 0 0 0 30px; }\n  .call-to-action.cta-full-width .cta-inner {\n    padding: 6em 1em; }\n  .call-to-action.cta-bg-img {\n    background-size: cover;\n    background-position: center;\n    color: rgba(255, 255, 255, 0.8); }\n    .call-to-action.cta-bg-img .cta-inner {\n      background-color: rgba(0, 0, 0, 0.35);\n      padding: 7em 1em; }\n\n.callout {\n  margin: 20px 0;\n  padding: 20px;\n  border-left: 3px solid #eee; }\n  .callout h4 {\n    margin-top: 0;\n    margin-bottom: 5px;\n    font-size: 1.25rem;\n    line-height: 1.5; }\n  .callout p:last-child {\n    margin-bottom: 0; }\n\n.callout-success {\n  background-color: rgba(102, 187, 106, 0.08);\n  border-color: #66BB6A; }\n  .callout-success h4 {\n    color: #66BB6A; }\n\n.callout-info {\n  background-color: rgba(0, 188, 212, 0.05);\n  border-color: #00BCD4; }\n  .callout-info h4 {\n    color: #00BCD4; }\n\n.callout-warning {\n  background-color: rgba(255, 193, 7, 0.08);\n  border-color: #ffc107; }\n  .callout-warning h4 {\n    color: #ffc107; }\n\n.callout-danger {\n  background-color: rgba(239, 83, 80, 0.05);\n  border-color: #EF5350; }\n  .callout-danger h4 {\n    color: #EF5350; }\n\n.card-white {\n  color: rgba(0, 0, 0, 0.87);\n  background-color: #fff; }\n\na.item-card {\n  display: block;\n  text-decoration: none; }\n  a.item-card:hover {\n    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); }\n\n.item-card {\n  position: relative;\n  transition: 0.2s linear; }\n  .item-card img {\n    max-width: 100%;\n    max-height: 100%; }\n  .item-card .card__image {\n    padding: 30px 15px;\n    height: 300px;\n    background-color: rgba(0, 0, 0, 0.035);\n    text-align: center; }\n  .item-card h1, .item-card h2, .item-card h3, .item-card h4, .item-card h5, .item-card h6 {\n    margin: 0;\n    color: rgba(0, 0, 0, 0.87);\n    line-height: 1.5; }\n  .item-card h6 {\n    font-size: 10px;\n    opacity: .5; }\n  .item-card h4 {\n    font-size: 16px; }\n  .item-card .card__body {\n    position: relative;\n    padding: 30px 15px;\n    margin-bottom: 30px; }\n  .item-card .card__price {\n    position: absolute;\n    top: 30px;\n    right: 15px; }\n    .item-card .card__price span {\n      display: block;\n      color: #636c72; }\n      .item-card .card__price span.type--strikethrough {\n        opacity: .5;\n        text-decoration: line-through; }\n  .item-card.card__horizontal {\n    position: relative;\n    margin-bottom: 30px; }\n    .item-card.card__horizontal .card__image {\n      width: 50%; }\n    .item-card.card__horizontal .card__body {\n      width: 50%;\n      height: 100%;\n      position: absolute;\n      top: 0;\n      right: 0;\n      padding: 40px 30px; }\n    .item-card.card__horizontal .card__price {\n      top: 30px;\n      right: 30px; }\n    .item-card.card__horizontal .card__desc {\n      margin: 0 25% 3em 0;\n      opacity: .7; }\n\n.theme-gray .card-white,\n.theme-dark .card-white {\n  color: rgba(255, 255, 255, 0.7); }\n  .theme-gray .card-white h1, .theme-gray .card-white h2, .theme-gray .card-white h3, .theme-gray .card-white h4, .theme-gray .card-white h5, .theme-gray .card-white h6,\n  .theme-dark .card-white h1,\n  .theme-dark .card-white h2,\n  .theme-dark .card-white h3,\n  .theme-dark .card-white h4,\n  .theme-dark .card-white h5,\n  .theme-dark .card-white h6 {\n    color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .card-white {\n  background-color: #4c4c4c; }\n\n.theme-dark .card-white {\n  background-color: #424951; }\n\n.feature-callout {\n  position: relative;\n  clear: both; }\n  @media (min-width: 768px) {\n    .feature-callout.feature-content-left .callout-feature-content {\n      padding-right: 2em; } }\n  @media (min-width: 1200px) {\n    .feature-callout.feature-content-left .callout-feature-content {\n      padding-right: 3em; } }\n  @media (min-width: 768px) {\n    .feature-callout.feature-content-right .callout-feature-content {\n      padding-left: 2em; } }\n  @media (min-width: 1200px) {\n    .feature-callout.feature-content-right .callout-feature-content {\n      padding-left: 3em; } }\n  .feature-callout.feature-content-center .callout-feature-content {\n    text-align: center;\n    max-width: 75%;\n    margin: 0 auto; }\n  .feature-callout .md-button {\n    padding: 5px 0;\n    min-width: 150px; }\n  @media (min-width: 768px) {\n    .feature-callout .offset-md-6 {\n      margin-left: 50%; } }\n\n.feature-callout-cover,\n.feature-callout-image-pull {\n  background-size: cover;\n  background-position: center; }\n\n.feature-callout-image-pull {\n  min-height: 200px; }\n  @media (min-width: 768px) {\n    .feature-callout-image-pull {\n      position: absolute;\n      top: 0;\n      bottom: 0; } }\n\n.callout-feature-content {\n  padding: 3em 0; }\n  @media (min-width: 768px) {\n    .callout-feature-content {\n      padding-top: 6em;\n      padding-bottom: 6em; } }\n  @media (min-width: 992px) {\n    .callout-feature-content {\n      padding-top: 8em;\n      padding-bottom: 8em; } }\n  @media (min-width: 1200px) {\n    .callout-feature-content {\n      padding-top: 12em;\n      padding-bottom: 12em; } }\n  .callout-feature-content h2 {\n    font-size: 24px;\n    margin-top: 0; }\n    @media (min-width: 768px) {\n      .callout-feature-content h2 {\n        font-size: 30px;\n        font-weight: 400;\n        line-height: 1.9;\n        margin: 0; } }\n  .callout-feature-content p {\n    line-height: 1.75;\n    -webkit-hyphens: none;\n        -ms-hyphens: none;\n            hyphens: none; }\n    .callout-feature-content p:last-child {\n      margin-bottom: 0; }\n    @media (min-width: 768px) {\n      .callout-feature-content p {\n        line-height: 2;\n        margin: 1.5em 0; } }\n\n.feature-callout-cover.has-overlay {\n  color: #fff; }\n  .feature-callout-cover.has-overlay:after {\n    content: ' ';\n    position: absolute;\n    right: 0;\n    bottom: 0;\n    top: 0;\n    width: 100%;\n    background: rgba(0, 0, 0, 0.25); }\n\n.feature-callout-cover .with-maxwidth {\n  position: relative;\n  z-index: 2; }\n\n.feature-callout-cover h2 {\n  font-size: 30px;\n  font-weight: bold;\n  letter-spacing: .1em; }\n  @media (min-width: 768px) {\n    .feature-callout-cover h2 {\n      font-size: 48px; } }\n\n.ih-item {\n  position: relative;\n  border-radius: 0.2rem;\n  transition: all 0.35s ease-in-out; }\n  .ih-item a {\n    color: #333; }\n    .ih-item a:hover {\n      text-decoration: none; }\n  .ih-item img {\n    border-radius: 0.2rem;\n    width: 100%;\n    height: 100%; }\n\n.ih-item.square {\n  position: relative;\n  margin-bottom: 30px; }\n  .ih-item.square .info {\n    position: absolute;\n    top: 0;\n    bottom: 0;\n    left: 0;\n    right: 0;\n    text-align: center;\n    -webkit-backface-visibility: hidden;\n            backface-visibility: hidden; }\n\n.ih-item.ih-material {\n  overflow: hidden;\n  position: relative;\n  width: 100%;\n  height: 100%; }\n  .ih-item.ih-material .info-mask {\n    content: '';\n    position: absolute;\n    top: -25%;\n    left: 0;\n    right: 0;\n    padding-bottom: 100%;\n    border-radius: 50%;\n    opacity: 0;\n    -webkit-transform: scale(0);\n            transform: scale(0);\n    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1); }\n  .ih-item.ih-material .info-content {\n    opacity: 0;\n    position: absolute;\n    top: 0;\n    bottom: 0;\n    left: 0;\n    right: 0;\n    -webkit-backface-visibility: hidden;\n            backface-visibility: hidden;\n    -webkit-transform: scale(0.5);\n            transform: scale(0.5);\n    transition: all .3s ease; }\n    .ih-item.ih-material .info-content:before {\n      content: '';\n      display: inline-block;\n      vertical-align: middle;\n      height: 100%;\n      margin-left: -0.5em; }\n    .ih-item.ih-material .info-content .info-inner {\n      display: inline-block;\n      width: 100%;\n      vertical-align: middle;\n      text-align: center; }\n      .ih-item.ih-material .info-content .info-inner h3 {\n        margin: 0 0 10px;\n        text-transform: uppercase;\n        color: #fff;\n        font-size: 18px;\n        line-height: 1; }\n      .ih-item.ih-material .info-content .info-inner p {\n        font-style: italic;\n        font-size: 12px;\n        color: #e9ecef; }\n  .ih-item.ih-material .bg-color-white + .info-content .info-inner h3,\n  .ih-item.ih-material .bg-color-warning + .info-content .info-inner h3 {\n    color: rgba(0, 0, 0, 0.87); }\n  .ih-item.ih-material .bg-color-white + .info-content .info-inner p,\n  .ih-item.ih-material .bg-color-warning + .info-content .info-inner p {\n    color: #999; }\n  .ih-item.ih-material a:hover .info-content {\n    opacity: 1;\n    -webkit-transform: scale(1);\n            transform: scale(1); }\n  .ih-item.ih-material a:hover .info-mask {\n    opacity: .8;\n    -webkit-transform: scale(1.21);\n            transform: scale(1.21); }\n\n.ih-item.square.effect3 {\n  overflow: hidden; }\n  .ih-item.square.effect3 .info {\n    height: 65px;\n    background-color: rgba(0, 0, 0, 0.8);\n    opacity: 0;\n    transition: all .35s ease-in-out; }\n    .ih-item.square.effect3 .info h3 {\n      text-transform: uppercase;\n      color: #fff;\n      text-align: center;\n      font-size: 17px;\n      line-height: 1;\n      padding: 10px 10px 0 4px;\n      margin: 4px 0 0 0; }\n    .ih-item.square.effect3 .info p {\n      margin: 0;\n      font-style: italic;\n      font-size: 12px;\n      line-height: 20px;\n      position: relative;\n      color: rgba(255, 255, 255, 0.7);\n      padding: 5px;\n      text-align: center; }\n    .ih-item.square.effect3 .info.bg-color-primary {\n      background-color: rgba(33, 150, 243, 0.8); }\n    .ih-item.square.effect3 .info.bg-color-info {\n      background-color: rgba(0, 188, 212, 0.8); }\n  .ih-item.square.effect3 a:hover .info {\n    visibility: visible;\n    opacity: 1; }\n\n.ih-item.square.effect3.bottom_to_top .info {\n  top: auto;\n  -webkit-transform: translateY(100%);\n          transform: translateY(100%); }\n\n.ih-item.square.effect3.bottom_to_top a:hover .info {\n  -webkit-transform: translateY(0);\n          transform: translateY(0); }\n\n.ih-item.square.effect3.top_to_bottom .info {\n  bottom: auto;\n  -webkit-transform: translateY(-100%);\n          transform: translateY(-100%); }\n\n.ih-item.square.effect3.top_to_bottom a:hover .info {\n  -webkit-transform: translateY(0);\n          transform: translateY(0); }\n\n.icon-box {\n  margin-top: 30px;\n  position: relative;\n  padding: 0 0 0 80px; }\n  .icon-box .ibox-icon {\n    display: block;\n    position: absolute;\n    width: 64px;\n    height: 64px;\n    top: 0;\n    left: 0; }\n    .icon-box .ibox-icon a {\n      text-decoration: none; }\n    .icon-box .ibox-icon a,\n    .icon-box .ibox-icon i,\n    .icon-box .ibox-icon img {\n      display: block;\n      position: relative;\n      width: 100%;\n      height: 100%;\n      color: #FFF; }\n    .icon-box .ibox-icon i,\n    .icon-box .ibox-icon img {\n      border-radius: 50%;\n      background-color: #2196F3; }\n    .icon-box .ibox-icon i {\n      font-style: normal;\n      font-size: 28px;\n      text-align: center;\n      line-height: 64px; }\n  .icon-box h3 {\n    font-size: 16px;\n    font-weight: bold;\n    text-transform: uppercase;\n    letter-spacing: 1px;\n    margin: 0;\n    color: rgba(0, 0, 0, 0.87); }\n  .icon-box p {\n    margin: 8px 0 0 0;\n    color: #999; }\n  .icon-box .before-heading {\n    font-size: 14px; }\n\n/* Icon Box - Icon Large\n-----------------------------------------------------------------*/\n.icon-box.ibox-lg {\n  padding: 0 0 0 115px; }\n  .icon-box.ibox-lg .ibox-icon {\n    width: 96px;\n    height: 96px; }\n    .icon-box.ibox-lg .ibox-icon i {\n      font-size: 42px;\n      line-height: 96px; }\n\n/* Icon Box - Icon Outline\n-----------------------------------------------------------------*/\n.icon-box.ibox-outline .ibox-icon {\n  border: 1px solid #2196F3;\n  border-radius: 50%;\n  padding: 3px; }\n  .icon-box.ibox-outline .ibox-icon i {\n    line-height: 56px; }\n\n/* Icon Box - Icon Outline Large\n-----------------------------------------------------------------*/\n.icon-box.ibox-outline.ibox-lg .ibox-icon {\n  padding: 4px; }\n  .icon-box.ibox-outline.ibox-lg .ibox-icon i {\n    line-height: 86px; }\n\n/* Icon Box - Icon Rounded\n-----------------------------------------------------------------*/\n.icon-box.ibox-rounded .ibox-icon {\n  border-radius: 0.2rem !important; }\n  .icon-box.ibox-rounded .ibox-icon i,\n  .icon-box.ibox-rounded .ibox-icon img {\n    border-radius: 0.2rem !important; }\n\n/* Icon Box - Icon Rounded & Large\n-----------------------------------------------------------------*/\n.icon-box.ibox-rounded.ibox-lg .ibox-icon {\n  border-radius: 0.2rem !important; }\n  .icon-box.ibox-rounded.ibox-lg .ibox-icon i,\n  .icon-box.ibox-rounded.ibox-lg .ibox-icon img {\n    border-radius: 0.2rem !important; }\n\n/* Icon Box - Light Background\n-----------------------------------------------------------------*/\n.icon-box.ibox-light i,\n.icon-box.ibox-light img {\n  background-color: #e5e5e5;\n  color: rgba(0, 0, 0, 0.87); }\n\n.icon-box.ibox-light.ibox-outline .ibox-icon {\n  border-color: #E5E5E5; }\n  .icon-box.ibox-light.ibox-outline .ibox-icon i {\n    line-height: 54px; }\n\n.icon-box.ibox-light.ibox-outline i,\n.icon-box.ibox-light.ibox-outline img {\n  border: 1px solid #E5E5E5;\n  background-color: #e5e5e5;\n  color: rgba(0, 0, 0, 0.87); }\n\n.icon-box.ibox-light.ibox-outline.ibox-lg .ibox-icon i {\n  line-height: 84px; }\n\n.icon-box.ibox-light .ibox-icon i {\n  line-height: 62px; }\n\n.icon-box.ibox-lg .ibox-icon i {\n  line-height: 96px; }\n\n/* Icon Box - Dark Background\n-----------------------------------------------------------------*/\n.icon-box.ibox-dark.ibox-outline .ibox-icon {\n  border-color: rgba(0, 0, 0, 0.87); }\n\n.icon-box.ibox-dark .ibox-icon i,\n.icon-box.ibox-dark .ibox-icon img {\n  background-color: rgba(0, 0, 0, 0.87); }\n\n/* Icon Box - Border\n-----------------------------------------------------------------*/\n.icon-box.ibox-border .ibox-icon {\n  border: 1px solid #2196F3;\n  border-radius: 50%; }\n  .icon-box.ibox-border .ibox-icon i,\n  .icon-box.ibox-border .ibox-icon img {\n    border: none;\n    background-color: transparent !important;\n    color: #2196F3; }\n\n.icon-box.ibox-border .ibox-icon {\n  padding: 0; }\n  .icon-box.ibox-border .ibox-icon i {\n    line-height: 62px !important; }\n\n.icon-box.ibox-border.ibox-lg .ibox-icon i {\n  line-height: 94px !important; }\n\n/* Icon Box - Border - Light\n-----------------------------------------------------------------*/\n.icon-box.ibox-border.ibox-light .ibox-icon {\n  border-color: #E5E5E5; }\n\n.icon-box.ibox-border.ibox-light i,\n.icon-box.ibox-border.ibox-light img {\n  color: #888; }\n\n/* Icon Box - Border - Dark\n-----------------------------------------------------------------*/\n.icon-box.ibox-border.ibox-dark .ibox-icon {\n  border-color: #333; }\n\n.icon-box.ibox-border.ibox-dark i,\n.icon-box.ibox-border.ibox-dark img {\n  color: rgba(0, 0, 0, 0.87); }\n\n/* Icon Box - Plain\n-----------------------------------------------------------------*/\n.icon-box.ibox-plain .ibox-icon {\n  border: none !important;\n  height: auto !important; }\n  .icon-box.ibox-plain .ibox-icon i,\n  .icon-box.ibox-plain .ibox-icon img {\n    border: none !important;\n    background-color: transparent !important;\n    color: #2196F3;\n    border-radius: 0; }\n  .icon-box.ibox-plain .ibox-icon i {\n    font-size: 48px;\n    line-height: 1 !important; }\n  .icon-box.ibox-plain .ibox-icon img {\n    height: 64px; }\n\n.icon-box.ibox-plain.ibox-image .ibox-icon {\n  width: auto; }\n\n.icon-box.ibox-plain.ibox-image img {\n  width: auto;\n  display: inline-block; }\n\n.icon-box.ibox-plain.ibox-small {\n  padding-left: 42px; }\n  .icon-box.ibox-plain.ibox-small .ibox-icon {\n    width: 28px; }\n    .icon-box.ibox-plain.ibox-small .ibox-icon i {\n      font-size: 28px; }\n    .icon-box.ibox-plain.ibox-small .ibox-icon img {\n      height: 28px; }\n  .icon-box.ibox-plain.ibox-small h3 {\n    font-size: 15px;\n    line-height: 26px;\n    margin-bottom: 10px; }\n  .icon-box.ibox-plain.ibox-small p {\n    margin-left: -42px; }\n\n.icon-box.ibox-plain.ibox-lg .ibox-icon i {\n  font-size: 72px; }\n\n.icon-box.ibox-plain.ibox-lg .ibox-icon img {\n  height: 96px; }\n\n.icon-box.ibox-plain.ibox-light .ibox-icon i,\n.icon-box.ibox-plain.ibox-light .ibox-icon img {\n  color: #888; }\n\n.icon-box.ibox-plain.ibox-dark .ibox-icon i,\n.icon-box.ibox-plain.ibox-dark .ibox-icon img {\n  color: rgba(0, 0, 0, 0.87); }\n\n/* Icon Box - Center\n-----------------------------------------------------------------*/\n.icon-box.ibox-center {\n  padding: 0;\n  text-align: center; }\n  .icon-box.ibox-center.ibox-small {\n    padding-left: 0 !important; }\n  .icon-box.ibox-center .ibox-icon {\n    position: relative;\n    margin: 0 auto; }\n  .icon-box.ibox-center h3 {\n    margin: .8em 0 0; }\n  .icon-box.ibox-center p {\n    font-size: 14px;\n    margin-top: 0em; }\n\n/* Icon Box - Right\n-----------------------------------------------------------------*/\n.icon-box.ibox-right {\n  padding: 0 80px 0 0;\n  text-align: right; }\n  .icon-box.ibox-right.ibox-lg {\n    padding-right: 115px; }\n  .icon-box.ibox-right .ibox-icon {\n    left: auto;\n    right: 0; }\n  .icon-box.ibox-right.ibox-plain.ibox-small {\n    padding: 0 42px 0 0; }\n    .icon-box.ibox-right.ibox-plain.ibox-small p {\n      margin: 0 -42px 0 0; }\n\n/* Dark Theme\n-----------------------------------------------------------------*/\n.theme-gray .icon-box h3,\n.theme-dark .icon-box h3 {\n  color: rgba(255, 255, 255, 0.7); }\n\n/* Boxed\n-----------------------------------------------------------------*/\n.box-body > .icon-box {\n  margin-top: 0; }\n\n.preloaderbar {\n  z-index: 1040;\n  position: fixed;\n  top: 0;\n  left: 0;\n  right: 0;\n  height: 4px;\n  margin-bottom: -4px; }\n  .preloaderbar .bar {\n    position: absolute;\n    width: 100%;\n    height: 0;\n    text-indent: -9999px;\n    background-color: #23b7e5; }\n    .preloaderbar .bar:before {\n      position: absolute;\n      right: 50%;\n      left: 50%;\n      height: 4px;\n      background-color: inherit;\n      content: \"\"; }\n  .preloaderbar.active {\n    -webkit-animation: changebar 2.25s infinite 0.75s;\n            animation: changebar 2.25s infinite 0.75s; }\n  .preloaderbar.active .bar {\n    -webkit-animation: changebar 2.25s infinite;\n            animation: changebar 2.25s infinite; }\n  .preloaderbar.active .bar:before {\n    -webkit-animation: movingbar 0.75s infinite;\n            animation: movingbar 0.75s infinite; }\n\n@-webkit-keyframes movingbar {\n  0% {\n    right: 50%;\n    left: 50%; }\n  99.9% {\n    right: 0;\n    left: 0; }\n  100% {\n    right: 50%;\n    left: 50%; } }\n\n@keyframes movingbar {\n  0% {\n    right: 50%;\n    left: 50%; }\n  99.9% {\n    right: 0;\n    left: 0; }\n  100% {\n    right: 50%;\n    left: 50%; } }\n\n@-webkit-keyframes changebar {\n  0% {\n    background-color: #9CCC65; }\n  33.3% {\n    background-color: #9CCC65; }\n  33.33% {\n    background-color: #ffc107; }\n  66.6% {\n    background-color: #ffc107; }\n  66.66% {\n    background-color: #EF5350; }\n  99.9% {\n    background-color: #EF5350; }\n  100% {\n    background-color: #9CCC65; } }\n\n@keyframes changebar {\n  0% {\n    background-color: #9CCC65; }\n  33.3% {\n    background-color: #9CCC65; }\n  33.33% {\n    background-color: #ffc107; }\n  66.6% {\n    background-color: #ffc107; }\n  66.66% {\n    background-color: #EF5350; }\n  99.9% {\n    background-color: #EF5350; }\n  100% {\n    background-color: #9CCC65; } }\n\n.mdl-card.mdl-card-full-width {\n  width: 100%; }\n\n.card-panel {\n  padding: 20px;\n  margin: 10px 0 20px 0;\n  border-radius: 2px; }\n\n.card {\n  position: relative;\n  overflow: hidden;\n  margin: 10px 0 20px 0;\n  border-radius: 2px; }\n  .card .card-title {\n    font-size: 24px; }\n    .card .card-title.activator {\n      cursor: pointer; }\n  .card .card-action > a {\n    margin-right: 20px;\n    text-transform: uppercase; }\n  .card .card-image {\n    position: relative; }\n    .card .card-image img {\n      border-radius: 2px 2px 0 0;\n      position: relative;\n      left: 0;\n      right: 0;\n      top: 0;\n      bottom: 0;\n      width: 100%; }\n    .card .card-image .card-title {\n      position: absolute;\n      bottom: 0;\n      left: 0;\n      padding: 20px;\n      color: #fff;\n      text-shadow: 0 1px 1px rgba(0, 0, 0, 0.25); }\n  .card .card-content {\n    padding: 20px;\n    border-radius: 0 0 2px 2px; }\n    .card .card-content p {\n      margin: 0;\n      color: inherit; }\n    .card .card-content .card-title {\n      line-height: 48px; }\n    .card .card-content .card-profile-img {\n      position: relative;\n      z-index: 1;\n      margin-top: -70px;\n      border-radius: 50%;\n      width: 100px;\n      height: 100px;\n      padding: 5px;\n      background-color: #fff; }\n      .card .card-content .card-profile-img img {\n        width: 90px;\n        height: 90px;\n        border-radius: 50%; }\n      .card .card-content .card-profile-img.profile-img-sm {\n        margin-top: -50px;\n        width: 60px;\n        height: 60px;\n        padding: 2px; }\n        .card .card-content .card-profile-img.profile-img-sm img {\n          width: 56px;\n          height: 56px;\n          border-radius: 50%; }\n    .card .card-content .card-button .btn-icon {\n      position: relative;\n      z-index: 1;\n      margin-top: -65px; }\n  .card .card-action {\n    border-top: 1px solid rgba(0, 0, 0, 0.05);\n    padding: 20px; }\n    .card .card-action.no-border {\n      border: 0; }\n  .card .card-reveal {\n    padding: 20px;\n    position: absolute;\n    background-color: #FFF;\n    width: 100%;\n    overflow-y: auto;\n    top: 100%;\n    height: 100%;\n    z-index: 1; }\n    .card .card-reveal .card-title {\n      cursor: pointer;\n      display: block; }\n\n.theme-gray .mdl-card,\n.theme-dark .mdl-card {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .mdl-card__supporting-text,\n.theme-dark .mdl-card__supporting-text {\n  color: rgba(255, 255, 255, 0.7); }\n\n.theme-gray .mdl-card {\n  background-color: #4c4c4c; }\n\n.theme-dark .mdl-card {\n  background-color: #424951; }\n\n.mdl-data-table.mdl-data-table-non-numeric td, .mdl-data-table.mdl-data-table-non-numeric th {\n  text-align: left; }\n\n.box.table-box {\n  border-radius: 0; }\n\n.box > .mdl-data-table {\n  width: 100%;\n  background-color: transparent; }\n\n.table-responsive {\n  overflow-x: auto;\n  min-height: 0.01%; }\n  @media screen and (max-width: 767px) {\n    .table-responsive {\n      width: 100%;\n      margin-bottom: 1.125rem;\n      overflow-y: hidden;\n      -ms-overflow-style: -ms-autohiding-scrollbar;\n      border: 1px solid rgba(0, 0, 0, 0.1); }\n      .table-responsive > .mdl-data-table {\n        margin-bottom: 0; }\n        .table-responsive > .mdl-data-table > thead > tr > th,\n        .table-responsive > .mdl-data-table > thead > tr > td,\n        .table-responsive > .mdl-data-table > tbody > tr > th,\n        .table-responsive > .mdl-data-table > tbody > tr > td,\n        .table-responsive > .mdl-data-table > tfoot > tr > th,\n        .table-responsive > .mdl-data-table > tfoot > tr > td {\n          white-space: nowrap; }\n      .table-responsive > .table-bordered {\n        border: 0; }\n        .table-responsive > .table-bordered > thead > tr > th:first-child,\n        .table-responsive > .table-bordered > thead > tr > td:first-child,\n        .table-responsive > .table-bordered > tbody > tr > th:first-child,\n        .table-responsive > .table-bordered > tbody > tr > td:first-child,\n        .table-responsive > .table-bordered > tfoot > tr > th:first-child,\n        .table-responsive > .table-bordered > tfoot > tr > td:first-child {\n          border-left: 0; }\n        .table-responsive > .table-bordered > thead > tr > th:last-child,\n        .table-responsive > .table-bordered > thead > tr > td:last-child,\n        .table-responsive > .table-bordered > tbody > tr > th:last-child,\n        .table-responsive > .table-bordered > tbody > tr > td:last-child,\n        .table-responsive > .table-bordered > tfoot > tr > th:last-child,\n        .table-responsive > .table-bordered > tfoot > tr > td:last-child {\n          border-right: 0; }\n        .table-responsive > .table-bordered > tbody > tr:last-child > th,\n        .table-responsive > .table-bordered > tbody > tr:last-child > td,\n        .table-responsive > .table-bordered > tfoot > tr:last-child > th,\n        .table-responsive > .table-bordered > tfoot > tr:last-child > td {\n          border-bottom: 0; } }\n\n.theme-gray .box > .mdl-data-table th,\n.theme-dark .box > .mdl-data-table th {\n  color: rgba(255, 255, 255, 0.5); }\n\nimg.img30_30 {\n  width: 30px;\n  height: 30px; }\n\nimg.img40_40 {\n  width: 40px;\n  height: 40px; }\n\nimg.img64_64 {\n  width: 64px;\n  height: 64px; }\n\nimg.img80_80 {\n  width: 80px;\n  height: 80px; }\n\n.pricing-table {\n  position: relative;\n  margin-bottom: 20px;\n  background-color: #fcfcfc;\n  color: #999;\n  border-radius: 0.2rem;\n  box-shadow: none;\n  text-align: center;\n  transition: 0.25s ease-out; }\n  .pricing-table:hover {\n    box-shadow: 0 5px 11px 0 rgba(0, 0, 0, 0.18), 0 4px 15px 0 rgba(0, 0, 0, 0.15); }\n  .pricing-table header {\n    background-color: #8c8c8c;\n    text-align: center;\n    padding: 1.25rem 0.75rem; }\n    .pricing-table header h2 {\n      margin: 0;\n      font-size: 1.25rem;\n      line-height: 1;\n      font-weight: normal;\n      text-transform: uppercase;\n      color: #fff; }\n  .pricing-table .pricing-price {\n    padding: 45px 15px;\n    background-color: #999;\n    font-size: 56px;\n    line-height: 1;\n    color: #fff;\n    font-weight: 300; }\n    .pricing-table .pricing-price .pricing-sign {\n      margin-right: 5px;\n      font-size: 24px;\n      font-weight: normal; }\n    .pricing-table .pricing-price .pricing-sub {\n      margin-left: 5px;\n      font-size: 22px;\n      line-height: 1; }\n  .pricing-table .pricing-lead {\n    margin-bottom: 1.5em;\n    font-size: 18px;\n    text-transform: uppercase; }\n    .pricing-table .pricing-lead:after {\n      margin: 0 auto;\n      content: \"\";\n      height: 0px;\n      width: 80px;\n      padding-top: .6em;\n      border-bottom: solid 3px #eff0f3;\n      display: block; }\n  .pricing-table .pricing-plan-details {\n    padding: 1em; }\n    .pricing-table .pricing-plan-details > ul {\n      margin: 0;\n      padding: 0;\n      list-style: none;\n      font-size: 1rem;\n      line-height: 2em; }\n  .pricing-table > footer {\n    padding: 2em;\n    text-align: center; }\n    .pricing-table > footer > a {\n      display: block;\n      margin: 0 auto;\n      padding: 12px;\n      font-size: 14px;\n      line-height: 1;\n      width: 100%;\n      max-width: 200px; }\n  .pricing-table.pricing-table-primary header {\n    background-color: #0d8aee; }\n  .pricing-table.pricing-table-primary .pricing-price {\n    background-color: #2196F3; }\n  .pricing-table.pricing-table-primary:after {\n    border-top: 6px solid #2196F3;\n    border-left: 6px solid #2196F3; }\n  .pricing-table.pricing-table-secondary header, .pricing-table.pricing-table-info header {\n    background-color: #00a5bb; }\n  .pricing-table.pricing-table-secondary .pricing-price, .pricing-table.pricing-table-info .pricing-price {\n    background-color: #00BCD4; }\n  .pricing-table.pricing-table-secondary:after, .pricing-table.pricing-table-info:after {\n    border-top: 6px solid #00BCD4;\n    border-left: 6px solid #00BCD4; }\n  .pricing-table.pricing-table-success header {\n    background-color: #54b359; }\n  .pricing-table.pricing-table-success .pricing-price {\n    background-color: #66BB6A; }\n  .pricing-table.pricing-table-success:after {\n    border-top: 6px solid #66BB6A;\n    border-left: 6px solid #66BB6A; }\n  .pricing-table.pricing-table-warning header {\n    background-color: #edb100; }\n  .pricing-table.pricing-table-warning .pricing-price {\n    background-color: #ffc107; }\n  .pricing-table.pricing-table-warning:after {\n    border-top: 6px solid #ffc107;\n    border-left: 6px solid #ffc107; }\n  .pricing-table.pricing-table-danger header {\n    background-color: #ed3c39; }\n  .pricing-table.pricing-table-danger .pricing-price {\n    background-color: #EF5350; }\n  .pricing-table.pricing-table-danger:after {\n    border-top: 6px solid #EF5350;\n    border-left: 6px solid #EF5350; }\n\n.theme-gray .pricing-table {\n  background-color: #4c4c4c; }\n\n.theme-dark .pricing-table {\n  background-color: #424951; }\n\n.ui-ribbon-container {\n  position: relative; }\n  .ui-ribbon-container .ui-ribbon-wrapper {\n    position: absolute;\n    overflow: hidden;\n    width: 85px;\n    height: 88px;\n    top: -3px;\n    right: -3px; }\n  .ui-ribbon-container .ui-ribbon {\n    position: relative;\n    display: block;\n    text-align: center;\n    font-size: 15px;\n    color: #fff;\n    -webkit-transform: rotate(45deg);\n            transform: rotate(45deg);\n    padding: 7px 0;\n    left: -5px;\n    top: 15px;\n    width: 120px;\n    line-height: 20px;\n    background-color: #495057;\n    box-shadow: 0 0 3px rgba(0, 0, 0, 0.3); }\n    .ui-ribbon-container .ui-ribbon:before, .ui-ribbon-container .ui-ribbon:after {\n      position: absolute;\n      content: \" \";\n      line-height: 0;\n      border-top: 2px solid #495057;\n      border-left: 2px solid transparent;\n      border-right: 2px solid transparent;\n      bottom: -2px; }\n    .ui-ribbon-container .ui-ribbon:before {\n      left: 0;\n      bottom: -1px; }\n    .ui-ribbon-container .ui-ribbon:after {\n      right: 0; }\n  .ui-ribbon-container.ui-ribbon-primary .ui-ribbon {\n    background-color: #2196F3; }\n    .ui-ribbon-container.ui-ribbon-primary .ui-ribbon:before, .ui-ribbon-container.ui-ribbon-primary .ui-ribbon:after {\n      border-top: 2px solid #2196F3; }\n  .ui-ribbon-container.ui-ribbon-success .ui-ribbon {\n    background-color: #66BB6A; }\n    .ui-ribbon-container.ui-ribbon-success .ui-ribbon:before, .ui-ribbon-container.ui-ribbon-success .ui-ribbon:after {\n      border-top: 2px solid #66BB6A; }\n  .ui-ribbon-container.ui-ribbon-info .ui-ribbon {\n    background-color: #00BCD4; }\n    .ui-ribbon-container.ui-ribbon-info .ui-ribbon:before, .ui-ribbon-container.ui-ribbon-info .ui-ribbon:after {\n      border-top: 2px solid #00BCD4; }\n  .ui-ribbon-container.ui-ribbon-warning .ui-ribbon {\n    background-color: #ffc107; }\n    .ui-ribbon-container.ui-ribbon-warning .ui-ribbon:before, .ui-ribbon-container.ui-ribbon-warning .ui-ribbon:after {\n      border-top: 2px solid #ffc107; }\n  .ui-ribbon-container.ui-ribbon-danger .ui-ribbon {\n    background-color: #EF5350; }\n    .ui-ribbon-container.ui-ribbon-danger .ui-ribbon:before, .ui-ribbon-container.ui-ribbon-danger .ui-ribbon:after {\n      border-top: 2px solid #EF5350; }\n\n.sash {\n  display: block;\n  position: absolute;\n  left: 0;\n  right: auto;\n  top: 25px;\n  z-index: 1; }\n  .sash .material-icons {\n    font-size: 14px;\n    height: 20px;\n    height: 20px;\n    line-height: 30px; }\n\n.sash {\n  cursor: default;\n  overflow: visible;\n  pointer-events: none;\n  position: absolute;\n  background-color: #636c72;\n  pointer-events: auto;\n  color: #fff; }\n  .sash > div {\n    position: relative;\n    z-index: 1;\n    height: 30px;\n    line-height: 30px;\n    transition: width 0.1s 0.05s cubic-bezier(0.86, 0, 0.07, 1);\n    width: 25px; }\n    .sash > div:hover {\n      transition-delay: 0;\n      width: 100%; }\n      .sash > div:hover .sash-text {\n        opacity: 1;\n        width: 100%;\n        padding-left: 15px; }\n  .sash .sash-text {\n    display: block;\n    opacity: 0;\n    overflow: hidden;\n    transition: opacity 0.3s, padding 0.25s, width 0.25s;\n    transition-timing-function: cubic-bezier(0.86, 0, 0.07, 1);\n    white-space: nowrap;\n    width: 3em;\n    float: left;\n    margin-left: 5px;\n    padding-right: 20px; }\n  .sash i {\n    position: absolute;\n    right: 1px;\n    margin-left: 5px; }\n    .sash i:before {\n      color: #f5f5f5; }\n  .sash.sash-triangle-right:after {\n    background-color: transparent;\n    border: 15px solid #636c72;\n    border-right-color: transparent;\n    bottom: 0;\n    content: \" \";\n    position: absolute;\n    right: -20px; }\n  .sash.sash-primary {\n    background-color: #2196F3; }\n    .sash.sash-primary.sash-triangle-right:after {\n      border-color: #2196F3;\n      border-right-color: transparent; }\n  .sash.sash-info {\n    background-color: #00BCD4; }\n    .sash.sash-info.sash-triangle-right:after {\n      border-color: #00BCD4;\n      border-right-color: transparent; }\n  .sash.sash-success {\n    background-color: #66BB6A; }\n    .sash.sash-success.sash-triangle-right:after {\n      border-color: #66BB6A;\n      border-right-color: transparent; }\n  .sash.sash-warning {\n    background-color: #ffc107; }\n    .sash.sash-warning.sash-triangle-right:after {\n      border-color: #ffc107;\n      border-right-color: transparent; }\n  .sash.sash-danger {\n    background-color: #EF5350; }\n    .sash.sash-danger.sash-triangle-right:after {\n      border-color: #EF5350;\n      border-right-color: transparent; }\n  .sash.sash-primary {\n    background-color: #2196F3; }\n    .sash.sash-primary.sash-triangle-right:after {\n      border-color: #2196F3;\n      border-right-color: transparent; }\n  .sash.sash-white {\n    background-color: #fff;\n    color: rgba(0, 0, 0, 0.87); }\n    .sash.sash-white.sash-triangle-right:after {\n      border-color: #fff;\n      border-right-color: transparent; }\n\n.testimonial {\n  text-align: center; }\n  .testimonial .testimonial__quote {\n    display: block;\n    text-align: center; }\n    .testimonial .testimonial__quote .material-icons {\n      font-size: 3.5em;\n      line-height: 1em; }\n  .testimonial blockquote:before, .testimonial blockquote:after {\n    content: none; }\n  .testimonial .avatar {\n    border-radius: 50%;\n    max-width: 80px; }\n  .testimonial h5 {\n    font-size: 1rem;\n    margin-bottom: .4em; }\n  .testimonial .title {\n    opacity: .54; }\n\n.testimonial-alt .avatar {\n  max-width: 100px;\n  margin-bottom: 1rem; }\n\n.testimonial-alt blockquote {\n  margin: 0;\n  font-size: 1.25rem;\n  opacity: .9;\n  border-left: 0; }\n\n.testimonial-alt .citation {\n  font-size: 1rem;\n  opacity: .54; }\n\n.ui-timline-container {\n  padding: 15px; }\n\n.ui-timline-left .ui-timeline:before {\n  left: 0; }\n\n@media (min-width: 768px) {\n  .ui-timline-left .ui-timeline .tl-item:before {\n    display: none; } }\n\n@media (min-width: 768px) {\n  .ui-timline-left .ui-timeline .tl-item .tl-caption {\n    margin-left: -55px; } }\n\n@media (min-width: 768px) {\n  .ui-timline-left .ui-timeline .tl-item .tl-body .tl-time {\n    left: auto;\n    right: 15px;\n    color: #636c72; } }\n\n.ui-timeline {\n  display: table;\n  position: relative;\n  table-layout: fixed;\n  width: 100%;\n  border-spacing: 0;\n  border-collapse: collapse; }\n  .ui-timeline:before {\n    background-color: rgba(0, 0, 0, 0.15);\n    bottom: 0px;\n    content: \"\";\n    position: absolute;\n    left: 0;\n    top: 30px;\n    width: 1px;\n    z-index: 0; }\n    @media (min-width: 768px) {\n      .ui-timeline:before {\n        left: 50%; } }\n  .ui-timeline .tl-item {\n    display: table-row;\n    margin-bottom: 5px; }\n    .ui-timeline .tl-item:before {\n      display: none;\n      content: \"\"; }\n      @media (min-width: 768px) {\n        .ui-timeline .tl-item:before {\n          display: block;\n          width: 50%; } }\n    .ui-timeline .tl-item .tl-caption {\n      width: 150px;\n      margin-left: -55px; }\n      @media (min-width: 768px) {\n        .ui-timeline .tl-item .tl-caption {\n          margin-left: -110px; } }\n    @media (min-width: 768px) {\n      .ui-timeline .tl-item.alt {\n        text-align: right; }\n        .ui-timeline .tl-item.alt:before {\n          display: none; }\n        .ui-timeline .tl-item.alt:after {\n          content: \"\";\n          display: block;\n          width: 50%; }\n        .ui-timeline .tl-item.alt .tl-body .tl-entry {\n          margin: 0 35px 15px 0; }\n        .ui-timeline .tl-item.alt .tl-body .tl-time {\n          right: -220px;\n          left: auto;\n          text-align: left; }\n        .ui-timeline .tl-item.alt .tl-body .tl-icon {\n          right: -53px;\n          left: auto; }\n        .ui-timeline .tl-item.alt .tl-body .tl-content:after {\n          right: -16px;\n          left: auto;\n          border: 8px solid transparent;\n          border-left: 8px solid rgba(255, 255, 255, 0.9);\n          border-top: 8px solid rgba(255, 255, 255, 0.9); } }\n    .ui-timeline .tl-item .tl-body {\n      display: table-cell;\n      width: 50%;\n      vertical-align: top; }\n      .ui-timeline .tl-item .tl-body .tl-entry {\n        position: relative;\n        margin: 0 0 15px 36px; }\n      .ui-timeline .tl-item .tl-body .tl-time {\n        z-index: 1;\n        position: absolute;\n        left: auto;\n        right: 15px;\n        top: 0;\n        width: 150px;\n        color: #636c72;\n        line-height: 35px;\n        text-align: right; }\n        @media (min-width: 768px) {\n          .ui-timeline .tl-item .tl-body .tl-time {\n            left: -220px;\n            right: auto;\n            color: rgba(0, 0, 0, 0.87); } }\n      .ui-timeline .tl-item .tl-body .tl-icon {\n        position: absolute;\n        left: -53px;\n        top: 0; }\n        .ui-timeline .tl-item .tl-body .tl-icon .material-icons {\n          font-size: 20px; }\n      .ui-timeline .tl-item .tl-body .tl-content {\n        position: relative;\n        padding: 15px;\n        border-radius: 0.2rem;\n        background-color: rgba(255, 255, 255, 0.9); }\n        .ui-timeline .tl-item .tl-body .tl-content:after {\n          content: \" \";\n          line-height: 0;\n          position: absolute;\n          left: -16px;\n          top: 0;\n          border: 8px solid transparent;\n          border-right: 8px solid rgba(255, 255, 255, 0.9);\n          border-top: 8px solid rgba(255, 255, 255, 0.9); }\n        .ui-timeline .tl-item .tl-body .tl-content h4 {\n          font-size: 18px;\n          line-height: 1.5rem;\n          line-height: 1.2; }\n\n@media (min-width: 768px) {\n  .theme-gray .ui-timeline .tl-item.alt .tl-body .tl-content:after,\n  .theme-dark .ui-timeline .tl-item.alt .tl-body .tl-content:after {\n    border-left: 8px solid rgba(255, 255, 255, 0.05);\n    border-top: 8px solid rgba(255, 255, 255, 0.05); } }\n\n.theme-gray .ui-timeline .tl-item .tl-body .tl-content,\n.theme-dark .ui-timeline .tl-item .tl-body .tl-content {\n  background-color: rgba(255, 255, 255, 0.05); }\n  .theme-gray .ui-timeline .tl-item .tl-body .tl-content:after,\n  .theme-dark .ui-timeline .tl-item .tl-body .tl-content:after {\n    border-right: 8px solid rgba(255, 255, 255, 0.05);\n    border-top: 8px solid rgba(255, 255, 255, 0.05); }\n", ""]);

// exports


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map