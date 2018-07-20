import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobslistComponent } from './jobs-list.component';

describe('JobslistComponent', () => {
  let component: JobslistComponent;
  let fixture: ComponentFixture<JobslistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobslistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
