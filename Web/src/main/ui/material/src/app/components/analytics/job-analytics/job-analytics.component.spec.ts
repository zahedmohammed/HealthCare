import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobAnalyticsComponent } from './job-analytics.component';

describe('JobAnalyticsComponent', () => {
  let component: JobAnalyticsComponent;
  let fixture: ComponentFixture<JobAnalyticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobAnalyticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
