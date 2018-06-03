import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportNewComponent } from './support-new.component';

describe('SupportNewComponent', () => {
  let component: SupportNewComponent;
  let fixture: ComponentFixture<SupportNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupportNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupportNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
