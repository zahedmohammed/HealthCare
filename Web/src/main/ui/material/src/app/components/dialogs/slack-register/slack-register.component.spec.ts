import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SlackRegisterComponent } from './slack-register.component';

describe('SlackRegisterComponent', () => {
  let component: SlackRegisterComponent;
  let fixture: ComponentFixture<SlackRegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SlackRegisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SlackRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
