import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectlistComponent } from './projects-list.component';

describe('ProjectlistComponent', () => {
  let component: ProjectlistComponent;
  let fixture: ComponentFixture<ProjectlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
