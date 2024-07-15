import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class WebApiService {

  constructor(private httpClient: HttpClient) { }

  private getHttpOptions(url: string): { headers: HttpHeaders, observe: 'body' } {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache',
      'Pragma': 'no-cache'
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
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.get(
      url,
      httpOptions
    )
      .pipe(
        map((response: any) => (this as any).ReturnResponseData(response)),
        catchError((this as any).handleError)
      );
  }

  post(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.post(
      url,
      model,
      httpOptions)
      .pipe(
        map((response: any) => (this as any).ReturnResponseData(response)),
        catchError((this as any).handleError)
      );
  }

  put(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.put(
      url,
      model,
      httpOptions)
      .pipe(
        map((response: any) => (this as any).ReturnResponseData(response)),
        catchError((this as any).handleError)
      );
  }

  patch(url: string, model: any): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.patch(
      url,
      model,
      httpOptions)
      .pipe(
        map((response: any) => (this as any).ReturnResponseData(response)),
        catchError((this as any).handleError)
      );
  }

  delete(url: string): Observable<any> {
    const httpOptions = this.getHttpOptions(url);

    return this.httpClient.delete(
      url,
      httpOptions
    )
      .pipe(
        map((response: any) => (this as any).ReturnResponseData(response)),
        catchError((this as any).handleError)
      );
  }

}
