import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuperbotnetworkListComponent } from './superbotnetwork-list.component';

describe('SuperbotnetworkListComponent', () => {
  let component: SuperbotnetworkListComponent;
  let fixture: ComponentFixture<SuperbotnetworkListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuperbotnetworkListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuperbotnetworkListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
