import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpProviderService } from '../service/http-provider.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.sass'
})
export class SignupComponent implements OnInit {

  //@ts-ignore
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private httpProvider: HttpProviderService) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ["", [Validators.required, Validators.pattern('^(\-?[a-zA-Z0-9_]+.?)*$')]],
      email: ["", [Validators.required, Validators.email, Validators.pattern('^(@?[a-zA-Z0-9_]+.?)*$')]],
      password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^[a-zA-Z0-9\!@#$.%^&*_\-]{8,40}$')]]
    });
  }

  async registerMe() {
    console.log('dkkd')
    this.httpProvider.signup(this.registerForm.value).subscribe({
      next: data => {
        console.log(data)
      },
      error: error => {
        console.error('There was an error!', error);
      }
    })
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      this.registerMe();
    }
  }
}
