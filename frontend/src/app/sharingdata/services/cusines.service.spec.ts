import { TestBed } from '@angular/core/testing';

import { CuisinesService } from './cusines.service';

describe('CusinesService', () => {
  let service: CuisinesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuisinesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
