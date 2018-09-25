import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JenkinsIntegrationComponent } from './jenkins-integration.component';

describe('JenkinsIntegrationComponent', () => {
  let component: JenkinsIntegrationComponent;
  let fixture: ComponentFixture<JenkinsIntegrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JenkinsIntegrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JenkinsIntegrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
