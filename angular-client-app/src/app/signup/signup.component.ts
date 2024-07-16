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
        name: ["", [Validators.required, Validators.pattern('^(\-?[a-zA-Z0-9_]+.?)*$')]],
        email: ["", [Validators.required, Validators.email, Validators.pattern('^(@?[a-zA-Z0-9_]+.?)*$')]],
        password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^[a-zA-Z0-9\!@#$.%^&*_\-]{8,40}$')]]
      });
  }

  onSubmit(form: FormGroup){
    console.log(form?.valid)
    console.log("hello world!!!")
  }
}
