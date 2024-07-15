import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.sass'
})
export class SignupComponent implements OnInit {

  //@ts-ignore
  registerForm : FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
      this.registerForm =  this.fb.group({
        name: ["", [Validators.required]],
        email: ["", [Validators.required, Validators.email]],
        password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(25)]]
      });
  }

  onSubmit(form: FormGroup){
    console.log(form?.valid)
    console.log("hello world!!!")
  }
}
