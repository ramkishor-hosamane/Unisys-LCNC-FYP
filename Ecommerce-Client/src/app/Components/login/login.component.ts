import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    //User object 
    userModel = new User();
  constructor(private auth:AuthService) { }

  ngOnInit(): void {
  }
  onSubmitLoginForm(){
    this.auth.loginUser(this.userModel)
    this.userModel = new User()    
  }
}
