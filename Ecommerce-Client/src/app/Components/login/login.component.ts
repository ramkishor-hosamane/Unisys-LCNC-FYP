import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { User } from 'src/app/Models/user';
import { ApiService } from 'src/app/Services/api/api.service';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';
import { SharedService } from 'src/app/Services/shared.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  Users = []
  current_user: any;
  //User object 
  userModel = new User();
  constructor(private api: ApiService, private auth: AuthService, private router: Router, private local_st: LocalStorageService, private session_st: SessionStorageService, private cart_api: CartService,private shared:SharedService) {
    this.auth.current_user_observer.subscribe(
      data => {
        this.current_user = data;
      }
    )

  }

  ngOnInit(): void {
  }
  onSubmitLoginForm() {
    var resp;
    this.auth.loginUser(this.userModel).subscribe(
      data => {
        if(data['msg']=='success')
        {

          this.auth.storeNewToken(data);
          this.api.getDataById(environment.server_api_url+"user/UserLogin",data["userid"]).subscribe(
            user_obj=>{
              console.log("Got user object"+user_obj);
              this.shared.showPopup("Sucessfully logined",'success')
              this.current_user = user_obj;
              this.auth.updateUserSession(this.current_user)
              //console.log(this.auth.isLogined())
              this.auth.autoLogOut();
              this.cart_api.initializeCartService();
              this.router.navigate(['/home']).then()
            }
          );
  
        }
        else
        {
          //Error
          //alert(data['msg'])
          this.shared.showPopup(data['msg'],'error')

          this.userModel = new User()
          
        }
      },
      error => {
        console.log("Wrong Email/Password");
        alert("Wrong Email/Password");
        this.userModel = new User()
        console.log(error);
      }
    );
  }



}