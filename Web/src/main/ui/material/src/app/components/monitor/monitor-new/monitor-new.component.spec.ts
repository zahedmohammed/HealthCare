import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitorNewComponent } from './monitor-new.component';

describe('MonitorNewComponent', () => {
  let component: MonitorNewComponent;
  let fixture: ComponentFixture<MonitorNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitorNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitorNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
