import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {APPCONFIG} from "../../../config";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Auth, Env} from "../../../models/project-env.model";
import {ProjectService} from "../../../services/project.service";
import {JobsService} from "../../../services/jobs.service";
import {OrgService} from "../../../services/org.service";
import {Job} from "../../../models/project-job.model";
import {Handler} from "../../dialogs/handler/handler";
import {MatDialog, MatSnackBar} from "@angular/material";
import {SnackbarService} from "../../../services/snackbar.service";
import {RegisterComponent} from "../../dialogs/register/register.component";
import {Project} from "../../../models/project.model";
import {AutoCodeConfig} from "../../../models/project-autocode-config.model";
import {AccountService} from "../../../services/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import { MatStepper } from '@angular/material';
import {AutoSyncComponent} from "../../dialogs/auto-sync/auto-sync.component";


@Component({
  selector: 'app-projects-config',
  templateUrl: './projects-config.component.html',
  styleUrls: ['./projects-config.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService, JobsService]

})


export class ProjectsConfigComponent implements OnInit {
    showSpinner: boolean = false;
    id: string;
    project: Project = new Project();
    auth: Auth = new Auth();
    autoCodeConfig: AutoCodeConfig = new AutoCodeConfig();
    job: Job = new Job();
    envs: Env[] = [];
    @Input() data: Observable<any>;
    env: Env;
    orgs;
    accounts;
    matStepper;// MatStepper;@Input()

    context: string = "New";

    isLinear = false;
    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    thirdFormGroup: FormGroup;

    authTypes = ['Basic', 'OAuth_2_0' , 'Token'];
    grantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
    schemeTypes = ['form', 'header', 'none', 'query'];
    scopeTypes= ['read', 'write'];

    public AppConfig: any;
    numberOfTicks=0;
    availableAuthtype:String;
    constructor(private ref: ChangeDetectorRef,private projectService: ProjectService, private accountService: AccountService, private jobsService: JobsService,
                private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
                public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) {
        console.log('Hello GIT Component');

    }

    ngOnInit() {
        this.AppConfig = APPCONFIG;
        // this.getAccountsForProjectPage();
        this.route.params.subscribe(params => {
            this.id = params['id'];
            if (this.id) {
                this.loadProject(this.id);
                this.getAutoCode();
//       this.getEnvs();
            }
        });


        this.secondFormGroup = this._formBuilder.group({
            openAPISpec: ['', Validators.required]
        });


        /*this.fourthFormGroup = this._formBuilder.group({
          usernameCtrl:  ['', Validators.required],
          passwordCtrl:  ['', Validators.required],
        });

        this.fifthFormGroup = this._formBuilder.group({
          header1Ctrl:  ['', Validators.required],
        });

        grantTypeCtrl:  ['', Validators.required],
          clientIdCtrl:  ['', Validators.required],
          clientSecretCtrl:  ['', Validators.required],
          accessTokenUriCtrl:  ['', Validators.required],
          clientAuthenticationSchemeCtrl:  ['', Validators.required],
          authorizationSchemeCtrl:  ['', Validators.required],
          scopeCtrl:  ['', Validators.required]*/

    }

    loadProject(id: string) {
        this.handler.activateLoader();
        this.projectService.getById(id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.project = results['data'];
            if (!this.project.account) {
                let p: Project = new Project();
                this.project.account = p.account;
            }
            // this.context = this.project.name + " > Edit";
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    // save(matStepper) {
    //     this.matStepper = matStepper;
    //     if (this.project.id) {
    //         this.update();
    //     } else {
    //         this.create();
    //     }
    // }

    // create() {
    //     this.handler.activateLoader();
    //     this.snackbarService.openSnackBar("'Project '" + this.project.name + "' creating...", "");
    //     this.projectService.create(this.project).subscribe(results => {
    //         this.handler.hideLoader();
    //         if (this.handler.handle(results)) {
    //             return;
    //         }
    //         this.snackbarService.openSnackBar("'Project '" + this.project.name + "' created successfully", "");
    //         this.project = results['data'];
    //         if (!this.project.account) {
    //             let p: Project = new Project();
    //             this.project.account = p.account;
    //         }
    //         this.matStepper.next();
    //         this.getAutoCode();
    //     }, error => {
    //         this.handler.hideLoader();
    //         this.handler.error(error);
    //     });
    // }
    //
    // update() {
    //     console.log(this.project);
    //     this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saving...", "");
    //     this.projectService.update(this.project).subscribe(results => {
    //         this.handler.hideLoader();
    //         if (this.handler.handle(results)) {
    //             return;
    //         }
    //         this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saved successfully", "");
    //         this.project = results['data'];
    //         if (!this.project.account) {
    //             let p: Project = new Project();
    //             this.project.account = p.account;
    //         }
    //         this.matStepper.next();
    //         this.getAutoCode();
    //     }, error => {
    //         this.handler.hideLoader();
    //         this.handler.error(error);
    //     });
    // }

    // gotoAutoCode(matStepper) {
    //     this.matStepper = matStepper;
    //     this.matStepper.next();
    // }

    getAutoCode() {
        this.projectService.getAutoCodeConfig(this.id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.autoCodeConfig = results['data'];
            console.log(this.autoCodeConfig);
        });
    }

    saveAutoCode() {
        console.log(this.autoCodeConfig);
        this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saving...", "");

        this.projectService.saveAutoCodeConfig(this.autoCodeConfig, this.project.id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.autoCodeConfig = results['data'];
            this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saved successfully", "");
            // this.matStepper.next();
            this.router.navigate(['/app/projects/' + this.project.id + '/jobs']);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    // gotoEnv(matStepper) {
    //     this.matStepper = matStepper;
    //     this.matStepper.next();
    // }
    //
    // addEnv() {
    //     this.env = new Env();
    //     this.env.auths.push(new Auth);
    //     this.envs.push(this.env);
    // }
    // addAuth(env) {
    //     env.auths.push({});
    // }

    // getEnvs() {
    //     this.projectService.getEnvsByProjectId(this.id).subscribe(results => {
    //         this.handler.hideLoader();
    //         if (this.handler.handle(results)) {
    //             return;
    //         }
    //         this.envs = results['data'];
    //         if (!this.envs) {
    //         }
    //         console.log(this.envs);
    //     });
    // }

    // saveEnv() {
    //     console.log(this.envs);
    //     this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");
    //
    //     this.projectService.saveEnvs(this.envs, this.project.id).subscribe(results => {
    //         this.handler.hideLoader();
    //         if (this.handler.handle(results)) {
    //             return;
    //         }
    //         this.envs = results['data'];
    //         this.snackbarService.openSnackBar("'Project '" + this.project.name + "' environments saved successfully", "");
    //         this.router.navigate(['/app/projects']);
    //     }, error => {
    //         this.handler.hideLoader();
    //         this.handler.error(error);
    //     });
    // }

    getAccountsForProjectPage() {
        this.handler.activateLoader();
        this.accountService.getAccountByAccountType('PROJECT').subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.accounts = results['data'];
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    delete() {
        var r = confirm("Are you sure you want to delete '" + this.project.name + "'?");
        if (r == true) {
            this.projectService.delete(this.project).subscribe(results => {
                if (this.handler.handle(results)) {
                    return;
                }
                this.snackbarService.openSnackBar("'Project '" + this.project.name + "' deleted", "");
                this.router.navigate(['/app/projects']);
            }, error => {
                this.handler.hideLoader();
                this.handler.error(error);
            });

        }
    }

    setAccount(account){
        this.project.account.accountType =  account.accountType;
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(RegisterComponent, {
            width:'800px'
        });

        dialogRef.afterClosed().subscribe(result => {
            this.getAccountsForProjectPage();
        });
    }


    open() {
        const dialogRef = this.dialog.open(AutoSyncComponent, {
            //width:'450px',
            data: this.project
        });
        dialogRef.afterClosed().subscribe(result => {
        });
    }


    projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
    visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    genPolicies = ['None', 'Create'];
    dbs = ['MySQL', 'Oracle', 'Postgres', 'SQLServer', 'MongoDB'];
}
