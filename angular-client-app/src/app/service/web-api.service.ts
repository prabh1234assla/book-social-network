import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class WebApiService {

  constructor(private httpClient: HttpClient) { }

  private getHttpOptions(url: string): { headers: HttpHeaders, observe: 'body' } {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (!(url.endsWith('/auth/signin') || url.endsWith('/auth/signup'))) {
      const token = this.getToken();
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return {
      headers: headers,
      observe: 'response' as 'body'
    };
  }

  private getToken(): string | null {
    return localStorage.getItem("token");
  }

  get(url: string): Observable<any> {
    console.log('sjjsjjs')
    const httpOptions = this.getHttpOptions(url);
    return this.httpClient.get(
      url,
      httpOptions
    );
  }

  post(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.post(
      url,
      model,
      httpOptions);
  }

  put(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.put(
      url,
      model,
      httpOptions);
  }

  patch(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    console.log(httpOptions)

    return this.httpClient.patch(
      url,
      model,
      httpOptions);
  }

  delete(url: string): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.delete(
      url,
      httpOptions
    );
  }

}
