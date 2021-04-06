import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SessionStorageService } from 'ngx-webstorage';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  cart:any;
  current_user:any;
  constructor(private cart_api:CartService,private auth:AuthService,private session_st:SessionStorageService,private route:ActivatedRoute) { 
    
    route.params.subscribe(val => {

      this.cart_api.cart_observer.subscribe(
        data =>{
          this.cart = data;
        }
      )
      
      this.auth.current_user_observer.subscribe(
        data =>{
          this.current_user = data;

          
        }
      )



    });
    


  }
  isLogined(){
    return this.auth.isLogined();
  }

  logout(){
    this.auth.logOut();
  }

  ngOnInit(): void {
  }

}
