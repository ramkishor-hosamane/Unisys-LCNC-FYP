import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from 'src/app/Services/api/auth.service';
import { CartService } from 'src/app/Services/api/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart:any;
  cart_total:any=0;
  constructor(private cart_api:CartService,private auth:AuthService) { }

  ngOnInit(): void {
    this.cart_api.cart_observer.subscribe(
      data =>{
        this.cart = data;
      }
    )

    this.cart_api.cart_total_observer.subscribe(
      data =>{
        this.cart_total = Number(data);
        console.log("This is "+data)
      }
    )
  }

  increaseQuantity(i:number)
  {
    this.cart[i]['quantity'] = parseInt(this.cart[i]['quantity']) + 1;
    this.cart_total+= parseInt(this.cart[i]['productid']['productprice']);
    if (this.auth.isLogined()) {
      console.log("Yes this User logined and updated")
      this.cart_api.updateUserCartItem(this.cart[i]).subscribe(
        data => console.log('Success!',data),
        error => console.error('!error',error)
      )

      this.cart_api.updateUserCart().subscribe(
        data => console.log('Success!',data),
        error => console.error('!error',error)
      )

    }

    this.cart_api.updateCartSource(this.cart,this.cart_total);

  }
  decreaseQuantity(i:number){
    this.cart[i]['quantity'] = parseInt(this.cart[i]['quantity']) - 1;
    this.cart_total-= parseInt(this.cart[i]['productid']['productprice']);
    if(this.cart[i]['quantity']==0)
      {
        this.cart_api.removeCartItem(i)
        
    }
    else
    {
      if(this.auth.isLogined())
      {
        this.cart_api.updateUserCartItem(this.cart[i]).subscribe(
          data => console.log('Success!',data),
          error => console.error('!error',error)
        )
        
        this.cart_api.updateUserCart().subscribe(
          data => console.log('Success!',data),
          error => console.error('!error',error)
        )
      }
  
    }
    
    this.cart_api.updateCartSource(this.cart,this.cart_total);

  }


}
