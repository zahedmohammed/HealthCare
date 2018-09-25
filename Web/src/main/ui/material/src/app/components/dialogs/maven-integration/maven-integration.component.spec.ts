import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MavenIntegrationComponent } from './maven-integration.component';

describe('MavenIntegrationComponent', () => {
  let component: MavenIntegrationComponent;
  let fixture: ComponentFixture<MavenIntegrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MavenIntegrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MavenIntegrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
