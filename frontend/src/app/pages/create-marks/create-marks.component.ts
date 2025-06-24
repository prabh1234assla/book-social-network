import { Component } from '@angular/core';

@Component({
  selector: 'app-create-marks',
  templateUrl: './create-marks.component.html',
  styleUrl: './create-marks.component.sass'
})
export class CreateMarksComponent {
  inputsToTake: Array<string> = ['studentId', 'courseId', 'marks']
  creatingTicketOf: string = 'Marks'
  submitMessage: string = 'Submit Mark Ticket'
}
