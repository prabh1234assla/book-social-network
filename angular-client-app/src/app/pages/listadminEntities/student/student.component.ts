import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.sass'
})
export class StudentComponent implements OnInit {
    ngOnInit(): void {
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
}
