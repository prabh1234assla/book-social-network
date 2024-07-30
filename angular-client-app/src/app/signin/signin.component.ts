import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.sass'
})
export class SigninComponent {
  //@ts-ignore
  loginForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
      this.loginForm =  this.fb.group({
        username: ["", [Validators.required, Validators.pattern('^(\-?[a-zA-Z0-9_]+.?)*$')]],
        password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^[a-zA-Z0-9\!@#$.%^&*_\-]{8,40}$')]]
      });
  }

  onSubmit(form: FormGroup){
    console.log(form.valid)
    console.log("hello world!!!")
  }

}
