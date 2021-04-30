import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { Utils } from 'src/app/utils';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  tokenStr = 'Bearer ';
  OAuthhttpHeaders:any;
  constructor(private http:HttpClient,private session_st:SessionStorageService) { 
    this.tokenStr = 'Bearer '+ this.session_st.retrieve('token');
    this.OAuthhttpHeaders = new HttpHeaders().set('Authorization', this.tokenStr);



  }


  public getData(url:string,authtype:any = 'oauth'): Observable<any>{
    if(authtype =='basic')
      return this.http.get(url, { headers: environment.httpHeaders})
    else
      return this.http.get(url, { headers: Utils.getTokenHeader(this.session_st.retrieve('token'))})
      
  }
  public insertData(url:string,data:any,authtype:any = 'oauth'){

    if(authtype =='basic')
      return this.http.put<any>(url,data, { headers: environment.httpHeaders });
    else
      return this.http.put<any>(url,data, { headers: Utils.getTokenHeader(this.session_st.retrieve('token'))});

    }

  public updateData(url:string,data:any,authtype:any = 'oauth'){
    if(authtype =='basic')  
        return this.http.post<any>(url,data, { headers: environment.httpHeaders });
    return this.http.post<any>(url,data, { headers: Utils.getTokenHeader(this.session_st.retrieve('token')) });

      }

  public deleteData(url:string,data:any,authtype:any = 'oauth'){
    if(authtype =='basic')  
      return this.http.request<any>('delete',url,{headers: environment.httpHeaders,body:data});
    return this.http.request<any>('delete',url,{headers: Utils.getTokenHeader(this.session_st.retrieve('token')),body:data});

  }

  public getDataById(url:string,id:string,authtype:any = 'oauth'): Observable<any>{
    if(authtype =='basic')  
        return this.http.get(url+'/'+id, { headers: environment.httpHeaders })
    else
        return this.http.get(url+'/'+id, { headers: Utils.getTokenHeader(this.session_st.retrieve('token')) })
        
  
      }
  

  public async getSingleProduct(id:string){
    console.log("Porduct id is "+id);

    let resp:any =  await this.http.get(environment.server_api_url+"product/Product/"+id,{ headers: environment.httpHeaders }).toPromise();
    console.log()
    var data  = resp.data;
    return resp;
  }
  

}
