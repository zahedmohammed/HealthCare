import { TestBed, inject } from '@angular/core/testing';

import { SkillSubscriptionService } from './skill-subscription.service';

describe('SkillSubscriptionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SkillSubscriptionService]
    });
  });

  it('should be created', inject([SkillSubscriptionService], (service: SkillSubscriptionService) => {
    expect(service).toBeTruthy();
  }));
});
