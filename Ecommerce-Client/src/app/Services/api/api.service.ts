import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  OAuthhttpHeaders = new HttpHeaders({'Content-type':'application/json','Authorization': 'Basic ' + btoa('setup' + ':' + 'setup')});

  constructor(private http:HttpClient) { }


  public getData(url:string): Observable<any>{
    return this.http.get(url, { headers: environment.httpHeaders })
  }
  public insertData(url:string,data:any){
    return this.http.put<any>(url,data, { headers: environment.httpHeaders });
  }

  public updateData(url:string,data:any){
    return this.http.post<any>(url,data, { headers: environment.httpHeaders });
  }

  public deleteData(url:string,data:any){
    return this.http.request<any>('delete',url,{headers: environment.httpHeaders,body:data});

  }

  public getDataById(url:string,id:string): Observable<any>{
    return this.http.get(url+'/'+id, { headers: environment.httpHeaders })
  }
  

  public async getSingleProduct(id:string){
    console.log("Porduct id is "+id);

    let resp:any =  await this.http.get(environment.server_api_url+"product/Product/"+id,{ headers: environment.httpHeaders }).toPromise();
    console.log()
    var data  = resp.data;
    return resp;
  }
  

}
