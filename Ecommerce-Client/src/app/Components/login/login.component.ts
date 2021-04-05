import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    Users = []
    //User object 
    userModel = new User();
  constructor(private auth:AuthService,private router:Router) { }

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
        for(var obj of this.Users){
          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if(obj['emailid'] == this.userModel.emailid && obj['password']==this.userModel.password)
          {
            console.log("Login succssful");
            alert("Successfully logined");
            is_logined = true
            this.router.navigate(['/home']).then()
          }
      
        }
        if(!is_logined){
          console.log("Wrong Email/Password");
          alert("Wrong Email/Password");
          this.userModel = new User()    
  
        }

      },
      error => {
        console.log(error);
      }
    )

  }
}
