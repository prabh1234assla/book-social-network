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
        name: ["", Validators.required],
        email: ["", Validators.required, Validators.email],
        password: ["", Validators.required, Validators.minLength(8), Validators.maxLength(25)]
      });
  }

  onSubmit(form: FormGroup){
    console.log(form?.valid)
    console.log("hello world!!!")
  }

}
