import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyticsEditComponent } from './analytics-edit.component';

describe('AnalyticsEditComponent', () => {
  let component: AnalyticsEditComponent;
  let fixture: ComponentFixture<AnalyticsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalyticsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalyticsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
