import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers:[AuthService]
})
export class SignupComponent implements OnInit {

  constructor(private auth:AuthService,private cart_api:CartService,private router:Router) { }

  //User object 
  userModel = new User();

  
  ngOnInit(): void {
  }
  //On signing up 
  onSubmitSignupForm()
  {
    //Make api call using auth service
    this.auth.registerNewUser(this.userModel).subscribe(
      data => {
        console.log('Success!',data)
        this.auth.getAllUsers().subscribe(
          all_users=>{
              this.cart_api.createUserCartApi(data,all_users.length,data).subscribe(
                dat=>console.log("Success"),
                er=>console.log("Error in creating cart",er)
              );
              console.log("Cart created")
          },
          err=>console.log(err)
        )
    
    },
      error => console.error('!error',error)
    )


    alert("Account created Successfully")
    this.router.navigate(['/login']).then()
    //Make the input fields blank in the page
    this.userModel = new User();
  }
}
