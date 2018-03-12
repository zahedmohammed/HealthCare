import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';
import { SkillService } from '../../../services/skill.service';
import { Subscription } from '../../../models/subscription.model';

@Component({
  selector: 'app-issues-edit',
  templateUrl: './issues-edit.component.html',
  styleUrls: ['./issues-edit.component.scss'],
  providers: [SkillSubscriptionService, SkillService]
})
export class IssuesEditComponent implements OnInit {
  skills;
  showSpinner: boolean = false;
  subscription: Subscription = new Subscription('', '', '', '', 'PRIVATE');
  constructor(private skillSubscriptionService: SkillSubscriptionService, private skillService: SkillService,  private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
      }
    });
    //this.listSkills();
  }

  getById(id: string) {
    this.showSpinner = true;
    this.skillSubscriptionService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.subscription = results['data'];
      console.log(this.subscription);
    }, error => {
      console.log("Unable to fetch vault");
    });
  }

  update() {
    console.log(this.subscription);
    this.skillSubscriptionService.update(this.subscription).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to update vault");
    });
  }

  delete() {
    console.log(this.subscription);
    this.skillSubscriptionService.delete(this.subscription).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to delete subscription");
    });
  }

  listSkills() {
    console.log('listing skills');
    this.skillService.get().subscribe(results => {
      if (results['errors']) {
        console.log(results);
        return ;
      }
      this.skills = results['data'];
    });
  }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
}