import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsuitEditDialogComponent } from './testsuit-edit-dialog.component';

describe('TestsuitEditDialogComponent', () => {
  let component: TestsuitEditDialogComponent;
  let fixture: ComponentFixture<TestsuitEditDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsuitEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsuitEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
