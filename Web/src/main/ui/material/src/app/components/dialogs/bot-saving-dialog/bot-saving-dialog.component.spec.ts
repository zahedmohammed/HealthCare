import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BotSavingDialogComponent } from './bot-saving-dialog.component';

describe('BotSavingDialogComponent', () => {
  let component: BotSavingDialogComponent;
  let fixture: ComponentFixture<BotSavingDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BotSavingDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BotSavingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
