import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartService } from 'src/app/Services/api/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart:any;
  cart_total:any=0;
  constructor(private cart_api:CartService) { }

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
    this.cart[i]['quantity']+=1;
    this.cart_total+= parseInt(this.cart[i]['productid']['productprice']);

  }
  decreaseQuantity(i:number){
    this.cart[i]['quantity']-=1;
    this.cart_total-= parseInt(this.cart[i]['productid']['productprice']);

    if(this.cart[i]['quantity']==0)
      this.removeProduct(i)

  }

  removeProduct(i:number){
    this.cart.splice(i,1)
  }
}
