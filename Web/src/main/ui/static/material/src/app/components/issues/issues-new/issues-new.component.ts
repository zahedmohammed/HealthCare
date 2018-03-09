import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';
import { SkillService } from '../../../services/skill.service';
import { Subscription } from '../../../models/subscription.model';


@Component({
  selector: 'app-issues-new',
  templateUrl: './issues-new.component.html',
  styleUrls: ['./issues-new.component.scss'],
  providers: [SkillSubscriptionService, SkillService]
})
export class IssuesNewComponent implements OnInit {
  skills;
  showSpinner: boolean = false;
  entry: Subscription = new Subscription('', '', '', '', 'PRIVATE');
  constructor(private skillSubscriptionService: SkillSubscriptionService, private skillService: SkillService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.listSkills();
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
  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.skillSubscriptionService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to save subscription entry");
    });
  }

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
