import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { WebApiService } from './web-api.service';

const apiUrl = "http://localhost:8100/";

const httpLink = {
  signup: apiUrl + "auth/register",
  signin: apiUrl + "auth/login",
  library: apiUrl + "books/",
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

  public getLibrary(): Observable<any> {
    return this.webapiservice.get(httpLink.library);
  }

  public addBookToLibrary(id: number, jsonData: JSON): Observable<any> {
    return this.webapiservice.post(httpLink.library + "?id=" + id, JSON.stringify(jsonData));
  }

  public updateBookInLibrary(id: number, jsonData: JSON): Observable<any> {
    return this.webapiservice.put(httpLink.library + "?id=" + id, JSON.stringify(jsonData));
  }

  public deleteBookFromLibrary(id: number): Observable<any> {
    return this.webapiservice.delete(httpLink.library + "?id=" + id);
  }

  public editBookInLibrary(id: number, patchData: JSON): Observable<any> {
    return this.webapiservice.patch(httpLink.library + "?id=" + id, JSON.stringify(patchData));
  }


}
