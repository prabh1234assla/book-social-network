import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMarksComponent } from './create-marks.component';

describe('CreateMarksComponent', () => {
  let component: CreateMarksComponent;
  let fixture: ComponentFixture<CreateMarksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateMarksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMarksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
