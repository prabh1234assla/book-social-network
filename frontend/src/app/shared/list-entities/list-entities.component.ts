import { Component, Input } from '@angular/core';
import { HttpProviderService } from '../../service/http-provider.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-list-entities',
  templateUrl: './list-entities.component.html',
  styleUrl: './list-entities.component.sass'
})
export class ListEntitiesComponent {
  rowEntries: Array<Record<string, string>> = [];
  columnHeaders: Array<string> = [];
  title: string = "";

  dataToRender: Record<string, Record<string, string>> = {
    "admin" : {
      "listFaculty" : 'listAllFaculty',
      "listStudents" : 'listAllStudents', 
      "listCourses": 'listCoursesForAdminPortal', 
      "listEnrollments" : 'listEnrollmentsForAdminAndFacultyPortal'
    },
    "faculty" : {
      "listFaculty" : 'listAllFaculty', 
      "listStudents" : 'listAllStudents', 
      "listCourses" : 'listCoursesForFacultyPortal', 
      "listEnrollments" : 'listEnrollmentsForAdminAndFacultyPortal'
    },
    "student" : {
      "listCourses" : 'listCoursesForStudent', 
      "listFaculty" : 'listAllFaculty', 
      "listEnrollments" : 'listEnrollmentsForStudent', 
      "listMarks" : 'listMarksOfStudent', 
      "listFees" : 'listFeesOfStudent'
    }
  }

  constructor(private httpProvider: HttpProviderService, private router: Router) { }

  async getDataList() {
    let roleAndTypeOfEntity = this.router.url.split('/');
    console.log(roleAndTypeOfEntity)
    console.log(roleAndTypeOfEntity[1], roleAndTypeOfEntity[1]);

    this.title = `Details Of ${roleAndTypeOfEntity[2].slice(4)}`

    const apiToCall: string = this.dataToRender[roleAndTypeOfEntity[1]][roleAndTypeOfEntity[2]];
    console.log(apiToCall)

    //@ts-ignore
    this.httpProvider[apiToCall]().subscribe({
      //@ts-ignore
      next: data => {
        this.rowEntries = data.body;
        console.log(this.rowEntries)
        if(this.rowEntries != null)
          this.columnHeaders = Object.keys(this.rowEntries[0]);
        else
          this.title = (this.title + " Are Empty")
        console.log(this.rowEntries, this.columnHeaders)
      },
      //@ts-ignore
      error: error => {
        // alert('There was an error! ' + error);
      }
    })

  }

  ngOnInit(): void {
    this.getDataList();
  }
}
