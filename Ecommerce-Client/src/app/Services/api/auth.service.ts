import { Injectable ,Injector} from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/app/Models/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionStorageService } from 'ngx-webstorage';
import { Utils } from 'src/app/utils';
import { ApiService } from './api.service';
import { SharedService } from '../shared.service';
import { CartService } from './cart.service';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
  
})
export class AuthService {
  public insert_url = environment.server_api_url + 'insert';
  public users_url = environment.server_api_url + 'user/UserLogin';
  public auth_url = environment.server_custom_api_url + 'authenticate';

  //insert_url is 'http://localhost:8080/ecommerce/rest/json/insert'
  




  private current_user_source = new BehaviorSubject<User>(this.session_st.retrieve("username"));
  current_user_observer = this.current_user_source.asObservable();  
  current_user:any;
  



 constructor(private injector:Injector,private router:Router, private http:HttpClient,private session_st:SessionStorageService,private api:ApiService,private shared:SharedService) { 
  if(this.loggedIn()){

      this.api.getDataById(environment.server_api_url+"user/UserLogin",this.session_st.retrieve("userid")).subscribe(
        user_obj=>{
          console.log("Got user object");
          this.current_user = user_obj;
          this.updateUserSession(this.current_user)
        }
      )
      this.current_user_observer.subscribe(
        data =>{
          this.current_user = data
          
        }
      )


      this.autoLogOut();
    }else{console.log("Not logined sorry")}


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

  getAllUsers(): Observable<any>{

    return this.http.get<any>(this.users_url,{headers:environment.httpHeaders});


  }

  //Api call for login
  loginUser(user_obj:any): Observable<any>{
    console.log("Trying to login user")
    // console.log(user)
    
    let data = {
      "emailid":user_obj["emailid"],
      "password":user_obj['password']
    }
 
    return this.http.post<any>(this.auth_url,data,{headers:environment.httpHeaders});
  
  }
  
  logOut(){
    
    this.session_st.clear("token")
    this.session_st.clear("userid")
    this.session_st.clear("token_expr")
    console.log("Loggedout")
    this.current_user=null;
    let cart_service =  this.injector.get(CartService);
    cart_service.destroyCartService()
    this.updateUserSession(this.current_user);
    this.router.navigate(['/login']).then()
  }

  updateUserSession(user:User){
    this.current_user = user;
    this.current_user_source.next(this.current_user);
  }
  isLogined(){
    // console.log("While logging in ")
    // console.log(this.current_user)
    // console.log(this.session_st.retrieve("token"))

    if(this.current_user==null || this.session_st.retrieve("token")==null)
    {
      return false;
    }

    //return this.current_user['emailid']!='';
    // console.log("Observer loginis "+this.current_user['emailid'])
    // console.log("Session loginis "+this.session_st.retrieve("username")['emailid'])

    return true;
  }
  

  storeNewToken(data:any){
    var token = data['token'];
    console.log("Token is ",token);
    var userid = data['userid']
    var token_expr = data['token expiration']
    
    this.session_st.store("token", token)
    this.session_st.store("userid", userid)
    this.session_st.store("token_expr", token_expr)

    
    //this.session_st.store("username", user_obj)


  }

  autoLogOut(){
    var now = Date.parse(""+new Date())
    var expr_date = this.session_st.retrieve("token_expr");
    var day = expr_date.substring(8,10)
    var month = expr_date.substring(4,7)

    if(month=="Apr")
      month="April"
    var time = expr_date.substring(11,19)
    var year ="2021"
    expr_date = day+" "+month+" "+year+" "+time+" "+"GMT+0530"
    
    //console.log(""+d)
    //console.log(expr_date)

    var then = Date.parse(expr_date)
    //var then = Date.parse('30 April 2021 10:41:00 GMT+0530')
    //console.log(now)
    //console.log(then)
    var timegap = then - now;

    //testing
    //timegap = 20000

    var alert_before = timegap-20000;
    if(timegap>0){
      console.log("Session will expire in ",timegap)

    }
    setTimeout(()=>{
      this.shared.showPopup("Session will be expired in "+(20000/1000)+" seconds",'info')

    }
      
    ,alert_before);

    setTimeout(()=>{
      this.shared.showPopup("Session Expired",'info')

    this.logOut();
    }
      
    ,timegap);
   
  }
 

  loggedIn(){
    return !! this.session_st.retrieve("token")
  }

}
