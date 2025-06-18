import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { WebApiService } from './web-api.service';

const apiUrl = "http://localhost:8000/";

const httpLink = {
  signup: apiUrl + "auth/signup",
  signin: apiUrl + "auth/signin"
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


}
