import { TestBed, inject } from '@angular/core/testing';

import { IssueTrackerService } from './issue-tracker.service';

describe('IssueTrackerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IssueTrackerService]
    });
  });

  it('should be created', inject([IssueTrackerService], (service: IssueTrackerService) => {
    expect(service).toBeTruthy();
  }));
});
