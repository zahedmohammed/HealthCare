import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegionNewComponent } from './region-new.component';

describe('RegionNewComponent', () => {
  let component: RegionNewComponent;
  let fixture: ComponentFixture<RegionNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegionNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegionNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
