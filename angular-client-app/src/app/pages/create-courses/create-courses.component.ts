import { Component } from '@angular/core';

@Component({
  selector: 'app-create-courses',
  templateUrl: './create-courses.component.html',
  styleUrl: './create-courses.component.sass'
})
export class CreateCoursesComponent {
  inputsToTake: Array<string> = ['courseName', 'facultyId', 'studentsId', 'credits']
  creatingTicketOf: string = 'Courses'
  submitMessage: string = 'Submit Course Ticket'
}
