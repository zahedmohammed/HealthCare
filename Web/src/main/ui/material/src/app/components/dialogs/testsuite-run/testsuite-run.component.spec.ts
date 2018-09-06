import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsuiteRunComponent } from './testsuite-run.component';

describe('TestsuiteRunComponent', () => {
  let component: TestsuiteRunComponent;
  let fixture: ComponentFixture<TestsuiteRunComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsuiteRunComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsuiteRunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
