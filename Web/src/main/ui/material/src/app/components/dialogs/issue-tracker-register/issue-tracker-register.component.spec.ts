import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueTrackerRegisterComponent } from './issue-tracker-register.component';

describe('IssueTrackerRegisterComponent', () => {
  let component: IssueTrackerRegisterComponent;
  let fixture: ComponentFixture<IssueTrackerRegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssueTrackerRegisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueTrackerRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
