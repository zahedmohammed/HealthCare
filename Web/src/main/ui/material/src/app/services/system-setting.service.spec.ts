import { TestBed, inject } from '@angular/core/testing';

import { SystemSettingService } from './system-setting.service';

describe('SystemSettingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SystemSettingService]
    });
  });

  it('should be created', inject([SystemSettingService], (service: SystemSettingService) => {
    expect(service).toBeTruthy();
  }));
});
