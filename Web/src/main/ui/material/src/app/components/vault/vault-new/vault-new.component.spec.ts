import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VaultNewComponent } from './vault-new.component';

describe('VaultNewComponent', () => {
  let component: VaultNewComponent;
  let fixture: ComponentFixture<VaultNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VaultNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VaultNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
