import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuperbotnetworkMapComponent } from './superbotnetwork-map.component';

describe('SuperbotnetworkMapComponent', () => {
  let component: SuperbotnetworkMapComponent;
  let fixture: ComponentFixture<SuperbotnetworkMapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuperbotnetworkMapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuperbotnetworkMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
