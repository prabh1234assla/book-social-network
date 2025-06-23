import { Component } from '@angular/core';
import { Banner } from '../../shared/portals/portals.component';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.sass'
})
export class StudentComponent {
  title = 'STUDENT PORTAL';
  subtitle1 = 'Generate Tickets';
  subtitle2 = 'View Entities';

  banners1 = ["enrollments"].map(t => new Banner(t, false))
  banners2 = ["courses", "enrollments", "marks", "fees"].map(t => new Banner(t))
}
