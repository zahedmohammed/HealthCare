import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RunHistoryComponent } from './run-history.component';

describe('RunHistoryComponent', () => {
  let component: RunHistoryComponent;
  let fixture: ComponentFixture<RunHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RunHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RunHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
