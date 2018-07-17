import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuitDailogComponent } from './suit-dailog.component';

describe('SuitDailogComponent', () => {
  let component: SuitDailogComponent;
  let fixture: ComponentFixture<SuitDailogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuitDailogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuitDailogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
