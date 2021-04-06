import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/app/Models/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionStorageService } from 'ngx-webstorage';
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




  private current_user_source = new BehaviorSubject<User>(this.session_st.retrieve("username"));
  current_user_observer = this.current_user_source.asObservable();  
  current_user:any;
  



  constructor(private http:HttpClient,private session_st:SessionStorageService) { 

    this.current_user_observer.subscribe(
      data =>{
        this.current_user = data;
      }
    )

  }

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
  
  logOut(){
    this.current_user=null;
    this.updateUserSession(this.current_user);

  }

  updateUserSession(user:User){
    this.current_user = user;
    this.current_user_source.next(this.current_user);
  }
  isLogined(){
    if(this.current_user==null || this.session_st.retrieve("username")==null)
    {
      return false;
    }

    //return this.current_user['emailid']!='';
    // console.log("Observer loginis "+this.current_user['emailid'])
    // console.log("Session loginis "+this.session_st.retrieve("username")['emailid'])

    return this.session_st.retrieve("username")['emailid']!='';
  }
  

}
