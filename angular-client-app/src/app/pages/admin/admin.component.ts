import { Component } from '@angular/core';
import { Banner } from '../../shared/portals/portals.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.sass'
})
export class AdminComponent {
  title = 'ADMIN PORTAL';
  subtitle1 = 'Generate Tickets';
  subtitle2 = 'View Entities';

  banners1 = ["courses", "enrollments", "fees"].map(t => new Banner(t, false))
  banners2 = ["faculty", "students", "courses"].map(t => new Banner(t))
}
