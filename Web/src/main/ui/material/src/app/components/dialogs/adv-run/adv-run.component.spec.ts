import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvRunComponent } from './adv-run.component';

describe('AdvRunComponent', () => {
  let component: AdvRunComponent;
  let fixture: ComponentFixture<AdvRunComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvRunComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvRunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
