import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { BillAddress } from 'src/app/Models/bill-address';
import { CartService } from 'src/app/Services/api/cart.service';
import { CheckoutService } from 'src/app/Services/api/checkout.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  constructor(private checkout_api:CheckoutService,private cart_api:CartService,private router:Router) {
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
      this.all_user_adresses = this.checkout_api.all_user_adresses;
   }

  ngOnInit(): void {
  }
  private cart_source = new BehaviorSubject<Array<any>>([]);
  cart_observer = this.cart_source.asObservable();
  cart: any;

  private cart_total_source = new BehaviorSubject(0);
  cart_total_observer = this.cart_total_source.asObservable();
  cart_total: any = 0;
  current_user: any;
  all_user_adresses:any=[]
  is_new_address=true;
  is_ordered = false;
  public CheckoutModel:BillAddress=new BillAddress();

  selectAddress(i:any){
    console.log("Change")
    this.Init(this.all_user_adresses[i])
  }

  onSubmitCheckoutForm(){
    if(this.is_new_address){
      this.all_user_adresses.push(this.CheckoutModel["addressid"])
    }
    this.checkout_api.placeOrderService(this.CheckoutModel,this.is_new_address);
    this.is_ordered = true;
  }

  clearAddress(){
    this.CheckoutModel = new BillAddress()
    this.is_new_address=true;

  }
  Init(obj:any){
    this.is_new_address=false;

    this.CheckoutModel.addressid = obj['addressid']
    this.CheckoutModel.userloginid = obj['userloginid']
    this.CheckoutModel.addresstype = obj['addresstype']



  }

  routeHome()
  {
    this.router.navigate(['/home']);
  }
}
