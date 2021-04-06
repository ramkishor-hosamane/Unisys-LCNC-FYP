import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private auth: AuthService, private http: HttpClient) {

    this.cart_observer.subscribe(
      data => {
        this.cart = data;
      }
    )

    this.cart_total_observer.subscribe(
      data => {
        this.cart_total = data;
      }
    )

    this.auth.current_user_observer.subscribe(
      data => {
        this.current_user = data;
      }
    )

    //Get User Cart
    if (this.auth.isLogined()) {
      this.getUserCart();

    }


  }
  private cart_source = new BehaviorSubject<Array<any>>([]);
  cart_observer = this.cart_source.asObservable();
  cart: any;

  private cart_total_source = new BehaviorSubject(0);
  cart_total_observer = this.cart_total_source.asObservable();
  cart_total: any = 0;
  current_user: any;

  cart_url = environment.server_api_url + 'cart/ShoppingCart';
  public insert_url = environment.server_api_url + 'insert';
  public update_url = environment.server_api_url + 'update';

  user_cart: any;


  getAllCarts(): Observable<any> {
    return this.http.get(this.cart_url, { headers: environment.httpHeaders })
  }


  getUserCart() {
    this.getAllCarts().subscribe(
      data => {

        for (var obj of data) {
          // console.log("Obj is",obj['userlogin']['userloginid']['emailid'])
          // console.log(this.current_user['emailid'])

          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if (obj['userlogin']['userloginid']['emailid'] == this.current_user['emailid']) {
            this.user_cart = obj;
            // console.log("Cart is there");
            //alert("Successfully logined");
          }

        }


      },
      error => {
        console.log(error);
      }
    )
  }

  addCartItem(product: any) {
    //console.log(product)



    var i = this.isInCart(product)
    if (i >= 0) {
      // console.log("Product is there price is "+parseInt(this.cart[i]['productid']['productprice']))
      // console.log(this.cart_total)
    
      if (this.auth.isLogined()) {
        console.log("Yes this User logined")
        this.updateUserCart(product)
  
      }
    
      this.cart[i]['quantity'] += 1;
      this.cart_total += parseInt(this.cart[i]['productid']['productprice']);

    }
    else {

    if (this.auth.isLogined()) {
      console.log("Yes this User logined")
      this.insertUserCart(product)

    }
      product['quantity'] = 1;
      this.cart.push(product);
      this.cart_total += parseInt(product['productid']['productprice']);
    }





    this.cart_total_source.next(this.cart_total)
    this.cart_source.next(this.cart)
  }

  isInCart(product: any) {
    for (let i = 0; i < this.cart.length; i++) {
      if (this.cart[i]['bizId'] == product['bizId'])
        return i;

    }
    return -1;
  }


  updateUserCart(cart_item: any) {

  }
  insertUserCart(cart_item: any) {
    console.log("Inside")
    console.log(cart_item['productid'])
    console.log(this.user_cart)
   let data = {
      "bizModule": "cart",
      "bizDocument": "ShoppingCartItem",
      "cartitemid": "CI3",
      "cartid": {
        "bizModule": "cart",
        "bizDocument": "ShoppingCart",
        "cartid": "C1",
        "userlogin": {
            "bizModule": "user",
            "bizDocument": "UserAddress",
            "userloginid": {
                "bizModule": "user",
                "bizDocument": "UserLogin",
                "userloginid": "Akanksha",
                "emailid": "ak@gmail.com",
                "mobileno": "9035416604",
                "gender": " ",
                "firstname": " ",
                "lastname": " ",
                "password": "123",
                "confirmpassword": "123",
                "isenabled": true,
                "createdstamp": "",
                "updatedstamp": "",
                "bizId": "c254662e-969e-4698-bb08-b4e6f7db0a92",
                "bizCustomer": "unisys",
                "bizDataGroupId": null,
                "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542",
                "bizVersion": 0,
                "bizLock": "20210406100323209setup"
            },
            "addressid": {
                "bizModule": "user",
                "bizDocument": "Address",
                "addressid": "A1",
                "address1": "2101",
                "address2": "Rajajinagar",
                "city": "Bangalore",
                "state": "Karnataka",
                "zipcode": "560010",
                "country": "India",
                "createdstamp": "",
                "updatedstamp": "",
                "bizId": "760e6c05-fd5a-456f-907e-4d3b3cc2ca9f",
                "bizCustomer": "unisys",
                "bizDataGroupId": null,
                "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542",
                "bizVersion": 0,
                "bizLock": "20210406102738340setup"
            },
            "sequenceno": "1",
            "addresstype": "Home",
            "createdstamp": "",
            "updatedstamp": "",
            "bizId": "46027133-6fa2-432a-b5ac-cbff0edf15a3",
            "bizCustomer": "unisys",
            "bizDataGroupId": null,
            "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542",
            "bizVersion": 0,
            "bizLock": "20210406102818020setup"
        },
        "subtotal": "0.00",
        "grandtotal": "0.00",
        "bizId": "3bac345d-3ba9-4f58-9870-cc2f0ad6e692",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542",
        "bizVersion": 0,
        "bizLock": "20210406175219507setup"
    },
    "productid": {
        "bizModule": "product",
        "bizDocument": "Product",
        "productid": "P10",
        "productname": "White formal shirt",
        "productprice": "600.00",
        "productstock": "10.00",
        "smallimg": "9dca511a-ce06-49ff-a9a6-c794e850667b",
        "largeimg": "",
        "description": "White and black strip formal shirt",
        "bizId": "224684d7-8618-484b-8355-e701e0d3631f",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542",
        "bizVersion": 0,
        "bizLock": "20210406100839923setup"
    },
      "quantity": "1.00",
      "price": "2.00",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "216fa01c-6644-4884-a5fe-9cd05179f542"
    }
    //Make http rest api call    
    console.log("Putting in ",this.insert_url)
    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });
  }

}
