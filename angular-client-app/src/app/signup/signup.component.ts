import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpProviderService } from '../service/http-provider.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.sass'
})
export class SignupComponent implements OnInit {

  //@ts-ignore
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private httpProvider: HttpProviderService, private router: Router) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ["", [Validators.required, Validators.pattern('^(\-?[a-zA-Z0-9_]+.?)*$')]],
      email: ["", [Validators.required, Validators.email, Validators.pattern('^(@?[a-zA-Z0-9_]+.?)*$')]],
      password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^[a-zA-Z0-9\!@#$.%^&*_\-]{8,40}$')]]
    });
  }

  private setToken(token: string): void {
    localStorage.setItem("token", token);
    console.log(localStorage.getItem("token"))
  }

  async registerMe() {
    this.httpProvider.signup(this.registerForm.value).subscribe({
      next: data => {

        delete this.registerForm.value['email']

        this.httpProvider.signin(this.registerForm.value).subscribe({
          next: data => {
            this.setToken(data.body.token);

            this.router.navigate(['/library']);
          },
          error: error => {
            alert('There was an error! ' + error);
          }
        })

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
