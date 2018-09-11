import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsConfigComponent } from './projects-config.component';

describe('ProjectsConfigComponent', () => {
  let component: ProjectsConfigComponent;
  let fixture: ComponentFixture<ProjectsConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectsConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectsConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
