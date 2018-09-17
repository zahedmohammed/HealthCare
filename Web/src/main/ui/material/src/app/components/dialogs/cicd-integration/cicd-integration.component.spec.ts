import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CicdIntegrationComponent } from './cicd-integration.component';

describe('CicdIntegrationComponent', () => {
  let component: CicdIntegrationComponent;
  let fixture: ComponentFixture<CicdIntegrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CicdIntegrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CicdIntegrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
