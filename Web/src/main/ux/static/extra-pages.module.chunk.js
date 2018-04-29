webpackJsonp(["extra-pages.module"],{

/***/ "../../../../../src/app/extra-pages/404/404.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-err\">\n  <div class=\"err-container text-center\">\n    <div class=\"err\">\n      <h1>404</h1>\n      <h2>Sorry, page not found</h2>\n    </div>\n\n    <div class=\"err-body\">\n      <a mat-raised-button [routerLink]=\"['/']\" class=\"btn-lg\">Go Back to Home Page</a>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/extra-pages/404/404.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Page404Component; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var Page404Component = (function () {
    function Page404Component() {
    }
    Page404Component = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-404',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/404/404.component.html")
        })
    ], Page404Component);
    return Page404Component;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/500/500.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-err\">\n  <div class=\"err-container text-center\">\n    <div class=\"err\">\n      <h1>500</h1>\n      <h2>Sorry, server goes wrong</h2>\n    </div>\n\n    <div class=\"err-body\">\n      <a mat-raised-button [routerLink]=\"['/']\" class=\"btn-lg\">Go Back to Home Page</a>\n    </div>\n  </div>\n</div>\n\n"

/***/ }),

/***/ "../../../../../src/app/extra-pages/500/500.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Page500Component; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var Page500Component = (function () {
    function Page500Component() {
    }
    Page500Component = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-500',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/500/500.component.html")
        })
    ], Page500Component);
    return Page500Component;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/confirm-email/confirm-email.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-auth page-confirm-email\">\n  <div class=\"main-body\">\n    <div class=\"card card-white\">\n      <div class=\"card-content\">\n        <div class=\"logo text-center\">\n          <a [routerLink]=\"['/']\">Confirm Email</a>\n        </div>\n\n        <div>\n          <p class=\"confirm-mail-icon\"><span class=\"material-icons\">mail_outline</span></p>\n          <p class=\"text-center text-small no-margin\">\n             An email has been send to <strong>username@mail.com</strong>. Please check for an email from us and click on the included link to reset your password.\n           </p>\n        </div>\n\n        <div class=\"additional-info\">\n          <span>Return to <a [routerLink]=\"['/extra/login']\">Login</a></span>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/extra-pages/confirm-email/confirm-email.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageConfirmEmailComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var PageConfirmEmailComponent = (function () {
    function PageConfirmEmailComponent() {
    }
    PageConfirmEmailComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-confirm-email',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/confirm-email/confirm-email.component.html")
        })
    ], PageConfirmEmailComponent);
    return PageConfirmEmailComponent;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/extra-pages-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export ExtraPagesRoutes */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ExtraPagesRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__login_login_component__ = __webpack_require__("../../../../../src/app/extra-pages/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__sign_up_sign_up_component__ = __webpack_require__("../../../../../src/app/extra-pages/sign-up/sign-up.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__forgot_password_forgot_password_component__ = __webpack_require__("../../../../../src/app/extra-pages/forgot-password/forgot-password.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__404_404_component__ = __webpack_require__("../../../../../src/app/extra-pages/404/404.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__500_500_component__ = __webpack_require__("../../../../../src/app/extra-pages/500/500.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__confirm_email_confirm_email_component__ = __webpack_require__("../../../../../src/app/extra-pages/confirm-email/confirm-email.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__lock_screen_lock_screen_component__ = __webpack_require__("../../../../../src/app/extra-pages/lock-screen/lock-screen.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__maintenance_maintenance_component__ = __webpack_require__("../../../../../src/app/extra-pages/maintenance/maintenance.component.ts");









var ExtraPagesRoutes = [
    {
        path: '',
        children: [
            { path: '', redirectTo: '/app/dashboard', pathMatch: 'full' },
            { path: 'login', component: __WEBPACK_IMPORTED_MODULE_1__login_login_component__["a" /* PageLoginComponent */] },
            { path: 'sign-up', component: __WEBPACK_IMPORTED_MODULE_2__sign_up_sign_up_component__["a" /* PageSignUpComponent */] },
            { path: 'forgot-password', component: __WEBPACK_IMPORTED_MODULE_3__forgot_password_forgot_password_component__["a" /* PageForgotPasswordComponent */] },
            { path: '404', component: __WEBPACK_IMPORTED_MODULE_4__404_404_component__["a" /* Page404Component */] },
            { path: '500', component: __WEBPACK_IMPORTED_MODULE_5__500_500_component__["a" /* Page500Component */] },
            { path: 'confirm-email', component: __WEBPACK_IMPORTED_MODULE_6__confirm_email_confirm_email_component__["a" /* PageConfirmEmailComponent */] },
            { path: 'lock-screen', component: __WEBPACK_IMPORTED_MODULE_7__lock_screen_lock_screen_component__["a" /* PageLockScreenComponent */] },
            { path: 'maintenance', component: __WEBPACK_IMPORTED_MODULE_8__maintenance_maintenance_component__["a" /* PageMaintenanceComponent */] },
        ]
    }
];
var ExtraPagesRoutingModule = __WEBPACK_IMPORTED_MODULE_0__angular_router__["g" /* RouterModule */].forChild(ExtraPagesRoutes);


/***/ }),

/***/ "../../../../../src/app/extra-pages/extra-pages.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ExtraPagesModule", function() { return ExtraPagesModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__extra_pages_routing_module__ = __webpack_require__("../../../../../src/app/extra-pages/extra-pages-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__login_login_component__ = __webpack_require__("../../../../../src/app/extra-pages/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__sign_up_sign_up_component__ = __webpack_require__("../../../../../src/app/extra-pages/sign-up/sign-up.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__forgot_password_forgot_password_component__ = __webpack_require__("../../../../../src/app/extra-pages/forgot-password/forgot-password.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__404_404_component__ = __webpack_require__("../../../../../src/app/extra-pages/404/404.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__500_500_component__ = __webpack_require__("../../../../../src/app/extra-pages/500/500.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__confirm_email_confirm_email_component__ = __webpack_require__("../../../../../src/app/extra-pages/confirm-email/confirm-email.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__lock_screen_lock_screen_component__ = __webpack_require__("../../../../../src/app/extra-pages/lock-screen/lock-screen.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__maintenance_maintenance_component__ = __webpack_require__("../../../../../src/app/extra-pages/maintenance/maintenance.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};











var ExtraPagesModule = (function () {
    function ExtraPagesModule() {
    }
    ExtraPagesModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_2__extra_pages_routing_module__["a" /* ExtraPagesRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["b" /* MatAutocompleteModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["c" /* MatButtonModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["d" /* MatButtonToggleModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatCardModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["f" /* MatCheckboxModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["g" /* MatChipsModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["h" /* MatDatepickerModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["j" /* MatDialogModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["l" /* MatExpansionModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["m" /* MatGridListModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["n" /* MatIconModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["o" /* MatInputModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["p" /* MatListModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["q" /* MatMenuModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["r" /* MatNativeDateModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["s" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["t" /* MatProgressBarModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["u" /* MatProgressSpinnerModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["v" /* MatRadioModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["w" /* MatRippleModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["x" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["y" /* MatSidenavModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["A" /* MatSliderModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["z" /* MatSlideToggleModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["B" /* MatSnackBarModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["C" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["E" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["F" /* MatTabsModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["G" /* MatToolbarModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["H" /* MatTooltipModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_material__["D" /* MatStepperModule */],
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_3__login_login_component__["a" /* PageLoginComponent */],
                __WEBPACK_IMPORTED_MODULE_4__sign_up_sign_up_component__["a" /* PageSignUpComponent */],
                __WEBPACK_IMPORTED_MODULE_5__forgot_password_forgot_password_component__["a" /* PageForgotPasswordComponent */],
                __WEBPACK_IMPORTED_MODULE_6__404_404_component__["a" /* Page404Component */],
                __WEBPACK_IMPORTED_MODULE_7__500_500_component__["a" /* Page500Component */],
                __WEBPACK_IMPORTED_MODULE_8__confirm_email_confirm_email_component__["a" /* PageConfirmEmailComponent */],
                __WEBPACK_IMPORTED_MODULE_9__lock_screen_lock_screen_component__["a" /* PageLockScreenComponent */],
                __WEBPACK_IMPORTED_MODULE_10__maintenance_maintenance_component__["a" /* PageMaintenanceComponent */],
            ]
        })
    ], ExtraPagesModule);
    return ExtraPagesModule;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/forgot-password/forgot-password.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-auth\">\n  <div class=\"main-body\">\n    <div class=\"card card-white\">\n      <div class=\"card-content\">\n        <div class=\"logo\">\n          <a [routerLink]=\"['/']\">Reset</a>\n        </div>\n\n        <form name=\"material_login_form\" class=\"form-validation\">\n          <fieldset>\n            <mat-input-container class=\"full-width no-margin-bottom\">\n              <input required matInput placeholder=\"Email\" type=\"email\" name=\"email\">\n              <mat-icon matSuffix class=\"material-icons\">mail_outline</mat-icon>\n            </mat-input-container>\n            <p class=\"text-center text-small no-margin\">\n               Enter your email address that you used to register. We'll send you an email with your username and a link to reset your password.\n             </p>\n\n            <div class=\"divider divider-md\"></div>\n            <button [routerLink]=\"['/']\" mat-raised-button type=\"button\" color=\"primary\" class=\"float-right\">Reset</button>\n          </fieldset>\n        </form>\n\n        <div class=\"additional-info\">\n          <a [routerLink]=\"['/extra/login']\">Login</a>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/extra-pages/forgot-password/forgot-password.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageForgotPasswordComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var PageForgotPasswordComponent = (function () {
    function PageForgotPasswordComponent() {
    }
    PageForgotPasswordComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-forgot-password',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/forgot-password/forgot-password.component.html")
        })
    ], PageForgotPasswordComponent);
    return PageForgotPasswordComponent;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/lock-screen/lock-screen.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-lock\">\n\n  <div class=\"lock-centered clearfix\">\n    <div class=\"lock-container\">\n      <section class=\"lock-box\">\n        <div class=\"lock-user\">{{AppConfig.user}}</div>\n        <div class=\"lock-img\"><img src=\"assets/images/g1.jpg\" alt=\"\"></div>\n        <div class=\"lock-pwd\">\n          <form>\n            <div class=\"form-group\">\n              <input type=\"password\" placeholder=\"Password\" class=\"form-control\">\n              <a [routerLink]=\"['/']\" class=\"btn-submit\">\n                <i class=\"material-icons\">keyboard_arrow_right</i>\n              </a>\n            </div>\n          </form>\n        </div>      \n      </section>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/extra-pages/lock-screen/lock-screen.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageLockScreenComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var PageLockScreenComponent = (function () {
    function PageLockScreenComponent() {
    }
    PageLockScreenComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    PageLockScreenComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-lock-screen',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/lock-screen/lock-screen.component.html")
        })
    ], PageLockScreenComponent);
    return PageLockScreenComponent;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/login/login.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-auth\">\n  <div class=\"main-body\">\n    <div class=\"card card-white\">\n      <div class=\"card-content\">\n        <div class=\"logo\">\n          <a [routerLink]=\"['/']\">Login</a>\n        </div>\n\n        <form name=\"material_login_form\" class=\"form-validation\" ng-submit=\"submit()\">\n          <fieldset>\n            <div class=\"form-group\">\n              <mat-form-field class=\"full-width\">\n                <input required matInput type=\"email\" name=\"email\" placeholder=\"Email\">\n                <mat-icon matSuffix class=\"material-icons\">mail_outline</mat-icon>\n              </mat-form-field>\n            </div>\n            <div class=\"form-group\">\n              <mat-form-field class=\"full-width\">\n                <input required matInput type=\"password\" name=\"password\" placeholder=\"Password\">\n                <mat-icon matSuffix class=\"material-icons\">lock_outline</mat-icon>\n              </mat-form-field>\n            </div>\n\n            <div class=\"divider divider-md\"></div>\n            <button [routerLink]=\"['/']\" mat-raised-button type=\"button\" color=\"primary\" class=\"float-right\">Login</button>\n          </fieldset>\n        </form>\n\n        <div class=\"additional-info\">\n          <a [routerLink]=\"['/extra/sign-up']\">Sign up</a>\n          <span class=\"divider-h\"></span>\n          <a [routerLink]=\"['/extra/forgot-password']\">Forgot your password?</a>\n        </div>\n\n      </div>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/extra-pages/login/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageLoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var PageLoginComponent = (function () {
    function PageLoginComponent() {
    }
    PageLoginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-login',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/login/login.component.html")
        })
    ], PageLoginComponent);
    return PageLoginComponent;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/maintenance/maintenance.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-maintenance\">\n\n  <header class=\"top-header text-center\">\n    <a [routerLink]=\"['/']\" class=\"logo\">{{AppConfig.productName}}</a>\n  </header>\n\n  <div class=\"content\">\n    <div class=\"main-content text-center\">\n      <h1>Site is Under Maintenance</h1>\n      <p class=\"text-muted\">It will take about 10-15 minutes</p>\n    </div>\n\n    <div class=\"row\">\n      <div class=\"col-xl-4\">\n        <div class=\"icon-box ibox-center ibox-lg ibox-light ibox-plain\">\n          <div class=\"ibox-icon\">\n            <i class=\"material-icons\">info_outline</i>\n          </div>\n          <h3>Why is the Site Down?</h3>\n          <p>The site is under maintenance because we are integrating new features to it.</p>\n        </div>\n      </div>\n      <div class=\"col-xl-4\">\n        <div class=\"icon-box ibox-center ibox-lg ibox-light ibox-plain\">\n          <div class=\"ibox-icon\">\n            <i class=\"material-icons\">access_time</i>\n          </div>\n          <h3>What is the Downtime?</h3>\n          <p>Normally, it takes about 10-15 minutes, however sometimes it may need a few hours or more.</p>\n        </div>\n      </div>\n      <div class=\"col-xl-4\">\n        <div class=\"icon-box ibox-center ibox-lg ibox-light ibox-plain\">\n          <div class=\"ibox-icon\">\n            <i class=\"material-icons\">mail_outline</i>\n          </div>\n          <h3>Do you need Support?</h3>\n          <p>You may send us an Email at <a href=\"mailto:mail@site.com\">mail@site.com</a> if you need urgent support.</p>\n        </div>\n      </div>\n    </div>\n  </div>\n\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/extra-pages/maintenance/maintenance.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageMaintenanceComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__("../../../../../src/app/config.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var PageMaintenanceComponent = (function () {
    function PageMaintenanceComponent() {
    }
    PageMaintenanceComponent.prototype.ngOnInit = function () {
        this.AppConfig = __WEBPACK_IMPORTED_MODULE_1__config__["a" /* APPCONFIG */];
    };
    PageMaintenanceComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-maintenance',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/maintenance/maintenance.component.html")
        })
    ], PageMaintenanceComponent);
    return PageMaintenanceComponent;
}());



/***/ }),

/***/ "../../../../../src/app/extra-pages/sign-up/sign-up.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-auth\">\n  <div class=\"main-body\">\n    <div class=\"card card-white\">\n      <div class=\"card-content\">\n        <div class=\"logo\">\n          <a [routerLink]=\"['/']\">Sign Up</a>\n        </div>\n\n        <form name=\"material_signup_form\">\n          <fieldset>\n            <div class=\"form-group\">\n              <mat-input-container class=\"full-width\">\n                <input required matInput type=\"text\" name=\"name\" placeholder=\"Username\">\n                <mat-icon matSuffix class=\"material-icons\">perm_identity</mat-icon>\n              </mat-input-container>\n            </div>\n\n            <div class=\"form-group\">\n              <mat-input-container class=\"full-width\">\n                <input required matInput type=\"email\" name=\"email\" placeholder=\"Email\">\n                <mat-icon matSuffix class=\"material-icons\">mail_outline</mat-icon>\n              </mat-input-container>\n            </div>\n\n            <mat-input-container class=\"full-width no-margin-bottom\">\n              <input required matInput type=\"password\" name=\"password\" placeholder=\"Password\">\n              <mat-icon matSuffix class=\"material-icons\">lock_outline</mat-icon>\n            </mat-input-container>\n            <p class=\"text-small no-margin\">By clicking on sign up, you agree to <a href=\"javascript:;\"><i>terms</i></a> and <a href=\"javascript:;\"><i>privacy policy</i></a></p>\n\n            <div class=\"divider divider-md\"></div>\n            <button [routerLink]=\"['/']\" mat-raised-button type=\"button\" color=\"primary\" class=\"float-right\">Sign Up</button>\n          </fieldset>\n        </form>\n\n        <div class=\"additional-info\">\n          <a [routerLink]=\"['/extra/login']\">Login</a>\n        </div>\n      </div>\n    </div> \n  </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/extra-pages/sign-up/sign-up.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageSignUpComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var PageSignUpComponent = (function () {
    function PageSignUpComponent() {
    }
    PageSignUpComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'my-page-sign-up',
            styles: [],
            template: __webpack_require__("../../../../../src/app/extra-pages/sign-up/sign-up.component.html")
        })
    ], PageSignUpComponent);
    return PageSignUpComponent;
}());



/***/ })

});
//# sourceMappingURL=extra-pages.module.chunk.js.map