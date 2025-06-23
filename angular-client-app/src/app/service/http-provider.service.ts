import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { WebApiService } from './web-api.service';

const apiUrl = "http://localhost:8000/";

const httpLink = {
  signup: apiUrl + "auth/signup",
  signin: apiUrl + "auth/signin",
  createFees: apiUrl + "fee/",
  listFees: apiUrl + "fee",
  createMarks: apiUrl + "marks/",
  listMarks: apiUrl + "marks",
  createEnrollments: apiUrl + "enrollment/",
  listEnrollments: apiUrl + "enrollment",
  createCourses: apiUrl + "course/",
  listCourses: apiUrl + "course"
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

  public createEnrollments(jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.createEnrollments, JSON.stringify(jsonData));
  }
}
