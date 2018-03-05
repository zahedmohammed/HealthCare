import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitorAllComponent } from './monitor-all.component';

describe('MonitorAllComponent', () => {
  let component: MonitorAllComponent;
  let fixture: ComponentFixture<MonitorAllComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitorAllComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitorAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
