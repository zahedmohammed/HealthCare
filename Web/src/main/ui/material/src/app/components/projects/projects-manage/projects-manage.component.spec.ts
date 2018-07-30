import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsManageComponent } from './projects-manage.component';

describe('ProjectsManageComponent', () => {
  let component: ProjectsManageComponent;
  let fixture: ComponentFixture<ProjectsManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectsManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectsManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
