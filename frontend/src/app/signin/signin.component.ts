import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpProviderService } from '../service/http-provider.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.sass'
})
export class SigninComponent {
  //@ts-ignore
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private httpProvider: HttpProviderService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ["", [Validators.required, Validators.pattern('^(\-?[a-zA-Z0-9_]+.?)*$')]],
      password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^[a-zA-Z0-9\!@#$.%^&*_\-]{8,40}$')]]
    });
  }

  private setToken(token: string): void {
    localStorage.setItem("token", token);
    console.log(localStorage.getItem("token"))
  }

  async loginMe() {
        this.httpProvider.signin(this.loginForm.value).subscribe({
          next: data => {
            let role = data.body.role;
            console.log(role)
            this.setToken(data.body.token);
            
            if (role == 'ADMIN')
              this.router.navigate(['/admin']);
            else if (role == 'FACULTY')
              this.router.navigate(['/faculty']);
            else
              this.router.navigate(['/student']);

          },
          error: error => {
            alert('There was an error! ' + error);
          }
        })

      }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      this.loginMe();
    }
  }

}
