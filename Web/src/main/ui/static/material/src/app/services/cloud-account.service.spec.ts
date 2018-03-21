import { TestBed, inject } from '@angular/core/testing';

import { CloudAccountService } from './cloud-account.service';

describe('CloudAccountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CloudAccountService]
    });
  });

  it('should be created', inject([CloudAccountService], (service: CloudAccountService) => {
    expect(service).toBeTruthy();
  }));
});
