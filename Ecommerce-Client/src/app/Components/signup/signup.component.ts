import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers:[AuthService]
})
export class SignupComponent implements OnInit {

  constructor(private auth:AuthService) { }

  //User object 
  userModel = new User();

  
  ngOnInit(): void {
  }
  //On signing up 
  onSubmitSignupForm()
  {
    //Make api call using auth service
    this.auth.registerNewUser(this.userModel).subscribe(
      data => console.log('Success!',data),
      error => console.error('!error',error)
    )


    alert("Account created Successfully")

    //Make the input fields blank in the page
    this.userModel = new User();
  }
}
