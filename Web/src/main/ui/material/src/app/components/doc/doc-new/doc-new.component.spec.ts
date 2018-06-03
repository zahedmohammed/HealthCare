import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocNewComponent } from './doc-new.component';

describe('DocNewComponent', () => {
  let component: DocNewComponent;
  let fixture: ComponentFixture<DocNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
