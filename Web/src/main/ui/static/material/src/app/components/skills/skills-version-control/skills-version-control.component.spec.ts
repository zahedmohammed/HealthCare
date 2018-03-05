import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillsVersionControlComponent } from './skills-version-control.component';

describe('SkillsVersionControlComponent', () => {
  let component: SkillsVersionControlComponent;
  let fixture: ComponentFixture<SkillsVersionControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsVersionControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsVersionControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
