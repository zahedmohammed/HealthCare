import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AutoSyncSaveConfigComponent } from './auto-sync-save-config.component';

describe('AutoSyncSaveConfigComponent', () => {
  let component: AutoSyncSaveConfigComponent;
  let fixture: ComponentFixture<AutoSyncSaveConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AutoSyncSaveConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AutoSyncSaveConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
