import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsSyncComponent } from './projects-sync.component';

describe('ProjectsSyncComponent', () => {
  let component: ProjectsSyncComponent;
  let fixture: ComponentFixture<ProjectsSyncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectsSyncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectsSyncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
