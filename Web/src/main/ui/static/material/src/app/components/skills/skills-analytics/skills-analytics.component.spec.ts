import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillsAnalyticsComponent } from './skills-analytics.component';

describe('SkillsAnalyticsComponent', () => {
  let component: SkillsAnalyticsComponent;
  let fixture: ComponentFixture<SkillsAnalyticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsAnalyticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
