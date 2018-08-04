import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestSuiteNewComponent } from './test-suite-new.component';

describe('TestSuiteNewComponent', () => {
  let component: TestSuiteNewComponent;
  let fixture: ComponentFixture<TestSuiteNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestSuiteNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestSuiteNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
