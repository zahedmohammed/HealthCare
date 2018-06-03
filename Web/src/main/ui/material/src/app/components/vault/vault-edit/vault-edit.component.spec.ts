import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VaultEditComponent } from './vault-edit.component';

describe('VaultEditComponent', () => {
  let component: VaultEditComponent;
  let fixture: ComponentFixture<VaultEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VaultEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VaultEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
