import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpProviderService } from '../../service/http-provider.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-ticket',
  templateUrl: './create-ticket.component.html',
  styleUrl: './create-ticket.component.sass'
})
export class CreateTicketComponent implements OnInit {
  @Input() inputsToTake: Array<string> = []
  @Input() creatingTicketOf: string = ''
  @Input() submitMessage: string = ''
  
  //@ts-ignore
  createTicketForm: FormGroup;

  constructor(private fb: FormBuilder, private httpProvider: HttpProviderService, private router: Router) { }

  ngOnInit(): void {
    let json: Record<string, Array<any>> = {};

    this.inputsToTake.forEach((item, _) => {
      json[`${item}`] = ["", [Validators.required]];
    });

    this.createTicketForm = this.fb.group(json);
  }

  async submitData() {
    this.creatingTicketOf = "create" + this.creatingTicketOf[0].toUpperCase() + this.creatingTicketOf.slice(1);
    //@ts-ignore
    this.httpProvider[this.creatingTicketOf](this.createTicketForm.value).subscribe({
      //@ts-ignore
      next: data => {
        alert(data)
      },
      //@ts-ignore
      error: error => {
        console.error('There was an error!', error);
      }
    })
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      this.submitData();
    }
  }
}
