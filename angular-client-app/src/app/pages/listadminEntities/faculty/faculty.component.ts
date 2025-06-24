import { Component } from '@angular/core';
import { HttpProviderService } from '../../../service/http-provider.service';

@Component({
  selector: 'app-faculty',
  templateUrl: './faculty.component.html',
  styleUrl: './faculty.component.sass'
})
export class FacultyComponent {
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
