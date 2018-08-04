import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestSuiteEditComponent } from './test-suite-edit.component';

describe('TestSuiteEditComponent', () => {
  let component: TestSuiteEditComponent;
  let fixture: ComponentFixture<TestSuiteEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestSuiteEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestSuiteEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
