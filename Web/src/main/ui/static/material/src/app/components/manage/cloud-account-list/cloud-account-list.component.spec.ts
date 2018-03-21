import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CloudAccountListComponent } from './cloud-account-list.component';

describe('CloudAccountListComponent', () => {
  let component: CloudAccountListComponent;
  let fixture: ComponentFixture<CloudAccountListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CloudAccountListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CloudAccountListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
