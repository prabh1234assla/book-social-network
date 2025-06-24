import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEnrollmentsComponent } from './create-enrollments.component';

describe('CreateEnrollmentsComponent', () => {
  let component: CreateEnrollmentsComponent;
  let fixture: ComponentFixture<CreateEnrollmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateEnrollmentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEnrollmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
