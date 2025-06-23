import { Component } from '@angular/core';

@Component({
  selector: 'app-create-enrollments',
  templateUrl: './create-enrollments.component.html',
  styleUrl: './create-enrollments.component.sass'
})
export class CreateEnrollmentsComponent {
  inputsToTake: Array<string> = ['studentId', 'courseId', 'isRegistered']
  creatingTicketOf: string = 'Enrollments'
  submitMessage: string = 'Submit Enrollment Ticket'
}
