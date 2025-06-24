import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { WebApiService } from './web-api.service';

const apiUrl = "http://localhost:8000/";

const httpLink = {
  signup: apiUrl + "auth/signup",
  signin: apiUrl + "auth/signin",
  listStudents: apiUrl + "user/students", 
  listFaculty: apiUrl + "user/faculty",
  createFees: apiUrl + "fee/",
  listFees: apiUrl + "user/me/fees",
  createMarks: apiUrl + "marks/",
  listMarks: apiUrl + "user/me/marks",
  createEnrollments: apiUrl + "enrollment/",
  listEnrollmentsForAdminAndFaculty: apiUrl + "enrollment",
  listEnrollmentsForStudent: apiUrl + "user/me/enrollments",
  createCourses: apiUrl + "course/",
  listCoursesForAdmin: apiUrl + "course",
  listCoursesForFaculty: apiUrl + "user/me/courseTaught",
  listCoursesForStudent: apiUrl + "user/me/courseStudied"
}

@Injectable({
  providedIn: 'root'
})

export class HttpProviderService {

  constructor(private webapiservice: WebApiService) { }

  public signup(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.signup, JSON.stringify(jsonData));
  }

  public signin(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.signin, JSON.stringify(jsonData));
  }

  public listAllStudents(): Observable<any> {
    return this.webapiservice.get(httpLink.listStudents);
  }

  public listAllFaculty(): Observable<any> {
    return this.webapiservice.get(httpLink.listFaculty);
  }

  public createFeeTicketForStudent(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.createFees, JSON.stringify(jsonData));
  }

  public listFeesOfStudent(): Observable<any> {
    return this.webapiservice.get(httpLink.listFees);
  }

  public createMarksForStudent(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.createMarks, JSON.stringify(jsonData));
  }

  public listMarksOfStudent(): Observable<any> {
    return this.webapiservice.get(httpLink.listMarks);
  }

  public createEnrollments(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.createEnrollments, JSON.stringify(jsonData));
  }

  public listEnrollmentsForAdminAndFacultyPortal(): Observable<any> {
    return this.webapiservice.get(httpLink.listEnrollmentsForAdminAndFaculty);
  }

  public listEnrollmentsForStudent(): Observable<any> {
    return this.webapiservice.get(httpLink.listEnrollmentsForStudent);
  }

  public createCourses(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.createCourses, JSON.stringify(jsonData));
  }

  public listCoursesForAdminPortal(): Observable<any> {
    return this.webapiservice.get(httpLink.listCoursesForAdmin);
  }

  public listCoursesForFacultyPortal(): Observable<any> {
    return this.webapiservice.get(httpLink.listCoursesForFaculty);
  }

  public listCoursesForStudent(): Observable<any> {
    return this.webapiservice.get(httpLink.listCoursesForStudent);
  }
}
