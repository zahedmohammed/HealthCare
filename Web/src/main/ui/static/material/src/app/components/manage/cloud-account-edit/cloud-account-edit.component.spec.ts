import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CloudAccountEditComponent } from './cloud-account-edit.component';

describe('CloudAccountEditComponent', () => {
  let component: CloudAccountEditComponent;
  let fixture: ComponentFixture<CloudAccountEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CloudAccountEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CloudAccountEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
