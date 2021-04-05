import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/app/Models/user';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
  
})
export class AuthService {
  public insert_url = environment.server_api_url + 'insert';
  public users_url = environment.server_api_url + 'user/UserLogin';
  
  //insert_url is 'http://localhost:8080/ecommerce/rest/json/insert'
  
  //Basic authorization details
  authorizationData = 'Basic ' + btoa('setup' + ':' + 'setup');
  httpHeaders = new HttpHeaders({'Content-type':'application/json','Authorization': this.authorizationData});
  headerOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': this.authorizationData
    })
};


  constructor(private http:HttpClient) { }

  //Api call to insert new user
  registerNewUser(user:User)
  {
    
    console.log("Trying to send ")
    console.log(user);
    
    let data = {
      "bizModule": "user",
      "bizDocument": "UserLogin",
      "userloginid": user.userloginid,
      "emailid": user.emailid,
      "mobileno": user.mobileno,
      "gender": user.gender,
      "firstname":user.firstname,
      "lastname": user.lastname,
      "password": user.password,
      "confirmpassword": user.password,
      "isenabled": true,
      "bizCustomer": "unisys",
      "bizDataGroupId": null
    }
    //Make http rest api call    
    return this.http.put<any>(this.insert_url,data,{headers:this.httpHeaders});
  }




  //Api call for login
  loginUser(): Observable<any>{
    console.log("Trying to login user")
    // console.log(user)
    return this.http.get<any>(this.users_url,{headers:this.httpHeaders});
  
  }


}
