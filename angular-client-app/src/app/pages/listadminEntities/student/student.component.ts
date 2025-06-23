import { Component, OnInit } from '@angular/core';
import { HttpProviderService } from '../../../service/http-provider.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.sass'
})
export class StudentComponent implements OnInit {
 constructor(private httpProvider: HttpProviderService) { }

 async loginMe() {
   this.httpProvider.listAllStudents().subscribe({
     next: data => {
       const jsonBody = data.body;

       
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

  ngOnInit(): void {
 
  }
}
