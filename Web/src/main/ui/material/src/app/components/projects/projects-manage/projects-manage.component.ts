import {RegisterComponent} from './../../dialogs/register/register.component';
import {Component, OnInit, Inject} from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import {ProjectService} from '../../../services/project.service';
import {OrgService} from '../../../services/org.service';
import {JobsService} from '../../../services/jobs.service';
import {AccountService} from '../../../services/account.service';
import {Account} from '../../../models/account.model';
import {Project} from '../../../models/project.model';
import {AutoCodeConfig} from '../../../models/project-autocode-config.model';
import {Env, Auth} from '../../../models/project-env.model';
import {Job} from '../../../models/project-job.model';
import {OrgUser} from '../../../models/org.model';
import {Handler} from '../../dialogs/handler/handler';
import {APPCONFIG} from '../../../config';
import {MatSnackBar, MatSnackBarConfig, MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {SnackbarService} from '../../../services/snackbar.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatStepper} from '@angular/material';
import {ChangeDetectorRef, Input} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DeleteDialogComponent} from "../../dialogs/delete-dialog/delete-dialog.component";

@Component({
    selector: 'app-projects-manage',
    templateUrl: './projects-manage.component.html',
    styleUrls: ['./projects-manage.component.scss'],
    providers: [ProjectService, OrgService, SnackbarService, JobsService]
})
export class ProjectsManageComponent implements OnInit {

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

    authTypes = ['Basic', 'OAuth_2_0', 'Token'];
    grantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
    schemeTypes = ['form', 'header', 'none', 'query'];
    scopeTypes = ['read', 'write'];

    public AppConfig: any;
    numberOfTicks = 0;
    availableAuthtype: String;
    projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
    visibilities = ['PRIVATE', 'ORG_PUBLIC'];
    genPolicies = ['None', 'Create'];
    dbs = ['MySQL', 'Oracle', 'Postgres', 'SQLServer', 'MongoDB'];

    constructor(private ref: ChangeDetectorRef, private projectService: ProjectService, private accountService: AccountService, private jobsService: JobsService,
                private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
                public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) {
    }

    ngOnInit() {
        this.AppConfig = APPCONFIG;
        this.getAccountsForProjectPage();
        this.route.params.subscribe(params => {
            this.id = params['id'];
            if (this.id) {
                this.loadProject(this.id);
            }
        });

        this.firstFormGroup = this._formBuilder.group({
            nameCtrl: [{value: "name", disabled: false}, Validators.required]
        });

        this.secondFormGroup = this._formBuilder.group({
            openAPISpec: ['', Validators.required]
        });

        this.thirdFormGroup = this._formBuilder.group({
            nameCtrl: ['', Validators.required],
            urlCtrl: ['', Validators.required],
            authTypeCtrl: ['', Validators.required],
        });
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
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    save() {
        if (this.project.id) {
            this.update();
        } else {
            this.create();
        }
    }

    create() {
        this.handler.activateLoader();
        this.snackbarService.openSnackBar("'Project '" + this.project.name + "' creating...", "");
        this.projectService.create(this.project).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.snackbarService.openSnackBar("'Project '" + this.project.name + "' created successfully", "");
            this.project = results['data'];
            if (!this.project.account) {
                let p: Project = new Project();
                this.project.account = p.account;
            }
            this.router.navigate(['/app/projects/' + this.project.id + '/jobs']);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    update() {
        console.log(this.project);
        this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saving...", "");
        this.projectService.update(this.project).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saved successfully", "");
            this.project = results['data'];
            if (!this.project.account) {
                let p: Project = new Project();
                this.project.account = p.account;
            }
            this.router.navigate(['/app/projects/' + this.project.id + '/jobs']);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    gotoAutoCode(matStepper) {
        this.matStepper = matStepper;
        this.matStepper.next();
    }

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

    gotoEnv(matStepper) {
        this.matStepper = matStepper;
        this.matStepper.next();
    }

    addEnv() {
        this.env = new Env();
        this.env.auths.push(new Auth);
        this.envs.push(this.env);
    }

    addAuth(env) {
        env.auths.push({});
    }

    getEnvs() {
        this.projectService.getEnvsByProjectId(this.id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.envs = results['data'];
            if (!this.envs) {
            }
            console.log(this.envs);
        });
    }

    saveEnv() {
        console.log(this.envs);
        this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");

        this.projectService.saveEnvs(this.envs, this.project.id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.envs = results['data'];
            this.snackbarService.openSnackBar("'Project '" + this.project.name + "' environments saved successfully", "");
            this.router.navigate(['/app/projects']);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

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
        let dialogRef = this.dialog.open(DeleteDialogComponent, {
            data: this.project.name + ' project'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result != null) {
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
        });
    }

    setAccount(account) {
        this.project.account.accountType = account.accountType;
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(RegisterComponent, {
            width: '800px'
        });

        dialogRef.afterClosed().subscribe(result => {
            this.getAccountsForProjectPage();
        });
    }
}
