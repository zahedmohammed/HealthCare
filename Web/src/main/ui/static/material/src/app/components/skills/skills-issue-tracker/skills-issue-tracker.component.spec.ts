import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillsIssueTrackerComponent } from './skills-issue-tracker.component';

describe('SkillsIssueTrackerComponent', () => {
  let component: SkillsIssueTrackerComponent;
  let fixture: ComponentFixture<SkillsIssueTrackerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsIssueTrackerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsIssueTrackerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
