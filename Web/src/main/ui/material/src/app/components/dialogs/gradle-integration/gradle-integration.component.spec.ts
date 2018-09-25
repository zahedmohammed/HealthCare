import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GradleIntegrationComponent } from './gradle-integration.component';

describe('GradleIntegrationComponent', () => {
  let component: GradleIntegrationComponent;
  let fixture: ComponentFixture<GradleIntegrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GradleIntegrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradleIntegrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
