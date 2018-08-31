import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiCoverageComponent } from './api-coverage.component';

describe('ApiCoverageComponent', () => {
  let component: ApiCoverageComponent;
  let fixture: ComponentFixture<ApiCoverageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApiCoverageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiCoverageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
