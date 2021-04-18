import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { User } from 'src/app/Models/user';
import { ApiService } from 'src/app/Services/api/api.service';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  Users = []
  current_user: any;
  is_logined = false;
  //User object 
  userModel = new User();
  constructor(private api: ApiService, private auth: AuthService, private router: Router, private local_st: LocalStorageService, private session_st: SessionStorageService, private cart_api: CartService) {
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
    this.auth.loginUser().subscribe(
      data => {
        //Getting all users
        this.Users = data;
        var is_logined = false;
        //validating user
        for (var obj of this.Users) {
          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if (obj['emailid'] == this.userModel.emailid && obj['password'] == this.userModel.password) {
            this.is_logined = true

            this.api.getDataById(environment.server_api_url + "user/UserLogin", obj["bizId"]).subscribe(
              user_obj => {
                console.log("Getting user obj success")

                console.log("Login succssful");
                //alert("Successfully logined");
                this.session_st.store("username", user_obj)
                this.auth.updateUserSession(user_obj)
                this.cart_api.initializeCartService();
                this.router.navigate(['/home']).then()

              },
              error => {
                console.log("Error in getting user object",error);
              }
            );

          }





        }

        if (!this.is_logined) {
          console.log("Wrong Email/Password");
          alert("Wrong Email/Password");
          this.userModel = new User()
        }

      },
      error => {
        console.log(error);
      }
    );
  }

  // async reload(url: string): Promise<boolean> {
  //   await this.router.navigateByUrl('.', { skipLocationChange: true });
  //   return this.router.navigateByUrl(url);
  // }


}