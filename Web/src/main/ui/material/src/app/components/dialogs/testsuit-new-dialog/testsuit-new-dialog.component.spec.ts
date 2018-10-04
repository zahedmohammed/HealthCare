import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsuitNewDialogComponent } from './testsuit-new-dialog.component';

describe('TestsuitNewDialogComponent', () => {
  let component: TestsuitNewDialogComponent;
  let fixture: ComponentFixture<TestsuitNewDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsuitNewDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsuitNewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
