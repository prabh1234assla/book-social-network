import { Component } from '@angular/core';
import { Banner } from '../../shared/portals/portals.component';

@Component({
  selector: 'app-faculty',
  templateUrl: './faculty.component.html',
  styleUrl: './faculty.component.sass'
})
export class FacultyComponent {
  title = 'FACULTY PORTAL';
  subtitle1 = 'Generate Tickets';
  subtitle2 = 'View Entities';

  banners1 = ["enrollments", "marks"].map(t => new Banner(t, false))
  banners2 = ["faculty", "students", "courses", "enrollments"].map(t => new Banner(t))
}
