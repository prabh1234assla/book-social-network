import { Component } from '@angular/core';

@Component({
  selector: 'app-create-fees',
  templateUrl: './create-fees.component.html',
  styleUrl: './create-fees.component.sass'
})
export class CreateFeesComponent {
  inputsToTake: Array<string> = ['studentId', 'feeType', 'amount', 'isPaid']
  creatingTicketOf: string = 'Fees'
  submitMessage: string = 'Submit Fee Ticket'
}
