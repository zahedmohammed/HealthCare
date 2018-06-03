import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillingNewComponent } from './billing-new.component';

describe('BillingNewComponent', () => {
  let component: BillingNewComponent;
  let fixture: ComponentFixture<BillingNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillingNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
