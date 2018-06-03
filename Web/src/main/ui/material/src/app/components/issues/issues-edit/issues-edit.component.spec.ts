import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssuesEditComponent } from './issues-edit.component';

describe('IssuesEditComponent', () => {
  let component: IssuesEditComponent;
  let fixture: ComponentFixture<IssuesEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssuesEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssuesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
