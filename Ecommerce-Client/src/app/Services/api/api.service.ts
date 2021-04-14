import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient) { }


  public getData(url:string): Observable<any>{
    return this.http.get(url, { headers: environment.httpHeaders })
  }

  public getDataById(url:string,id:string): Observable<any>{
    return this.http.get(url+'/'+id, { headers: environment.httpHeaders })
  }
  
  

}
