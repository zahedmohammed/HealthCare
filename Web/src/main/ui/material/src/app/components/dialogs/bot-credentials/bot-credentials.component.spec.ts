import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BotCredentialsComponent } from './bot-credentials.component';

describe('BotCredentialsComponent', () => {
  let component: BotCredentialsComponent;
  let fixture: ComponentFixture<BotCredentialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BotCredentialsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BotCredentialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
