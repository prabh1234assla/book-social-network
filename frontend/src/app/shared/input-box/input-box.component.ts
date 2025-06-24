import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-input-box',
  templateUrl: './input-box.component.html',
  styleUrl: './input-box.component.sass'
})
export class InputBoxComponent {
  @Input() input: string = '';
  //@ts-ignore
  @Input() createTicketForm: FormGroup;
}
