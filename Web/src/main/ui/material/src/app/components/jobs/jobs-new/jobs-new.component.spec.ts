import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsNewComponent } from './jobs-new.component';

describe('JobsNewComponent', () => {
  let component: JobsNewComponent;
  let fixture: ComponentFixture<JobsNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobsNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
