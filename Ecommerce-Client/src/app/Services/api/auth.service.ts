import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/app/Models/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionStorageService } from 'ngx-webstorage';
import { Utils } from 'src/app/utils';
@Injectable({
  providedIn: 'root'
  
})
export class AuthService {
  public insert_url = environment.server_api_url + 'insert';
  public users_url = environment.server_api_url + 'user/UserLogin';
  //insert_url is 'http://localhost:8080/ecommerce/rest/json/insert'
  





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
      "userloginid": user.firstname,
      "emailid": user.emailid,
      "mobileno": user.mobileno,
      "firstname":user.firstname,
      "lastname": user.lastname,
      "password": user.password,
      "isenabled": true,
      "createdstamp":Utils.getCurrentDateTime(),
      "updatedstamp":Utils.getCurrentDateTime(),
      "bizCustomer": "unisys",
      "bizDataGroupId": null
    }
    //Make http rest api call    
    return this.http.put<any>(this.insert_url,data,{headers:environment.httpHeaders});
    //return new Observable<any>();
  }



  //Api call for login
  loginUser(): Observable<any>{
    console.log("Trying to login user")
    // console.log(user)
    return this.http.get<any>(this.users_url,{headers:environment.httpHeaders});
  
  }
  
  logOut(){
    this.current_user=null;
    this.session_st.clear("username")
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
