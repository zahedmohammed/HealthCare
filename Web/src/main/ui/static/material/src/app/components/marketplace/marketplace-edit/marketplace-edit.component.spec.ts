import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketplaceEditComponent } from './marketplace-edit.component';

describe('MarketplaceEditComponent', () => {
  let component: MarketplaceEditComponent;
  let fixture: ComponentFixture<MarketplaceEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MarketplaceEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketplaceEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
