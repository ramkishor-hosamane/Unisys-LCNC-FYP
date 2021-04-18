import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SessionStorageService } from 'ngx-webstorage';
import { User } from 'src/app/Models/user';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';
import { WishlistService } from 'src/app/Services/api/wishlist.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  cart:any;
  wishlist:any;

  current_user:any;
  constructor(private cart_api:CartService,private auth:AuthService,private session_st:SessionStorageService,private route:ActivatedRoute,private wishlist_api:WishlistService) { 
    
    route.params.subscribe(val => {

      this.cart_api.cart_observer.subscribe(
        data =>{
          this.cart = data;
        }
      )
      this.wishlist_api.wishlist_observer.subscribe(
        data =>{
          this.wishlist = data;
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
    this.cart_api.destroyCartService();
  }

  ngOnInit(): void {
  }

}
