import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketplaceNewComponent } from './marketplace-new.component';

describe('MarketplaceNewComponent', () => {
  let component: MarketplaceNewComponent;
  let fixture: ComponentFixture<MarketplaceNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MarketplaceNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketplaceNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
