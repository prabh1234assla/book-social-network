import { Component, OnInit } from '@angular/core';
import { HttpProviderService } from '../../../service/http-provider.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.sass'
})
export class StudentComponent implements OnInit {
  constructor(private httpProvider: HttpProviderService) { }

  rowEntries: any[] = []

  async loginMe() {
    this.httpProvider.listAllStudents().subscribe({
      next: data => {
        this.rowEntries = data.body;
        console.log(this.rowEntries)
      },
      error: error => {
        alert('There was an error! ' + error);
      }
    })

  }

  ngOnInit(): void {
    this.loginMe();
  }
}
