import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillsBotDeploymentComponent } from './skills-bot-deployment.component';

describe('SkillsBotDeploymentComponent', () => {
  let component: SkillsBotDeploymentComponent;
  let fixture: ComponentFixture<SkillsBotDeploymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsBotDeploymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsBotDeploymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
