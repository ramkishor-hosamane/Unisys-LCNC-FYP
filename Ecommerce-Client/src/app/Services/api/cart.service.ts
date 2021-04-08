import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import {Utils} from './../../utils';
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
      console.log("Getting usercart")
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
  cart_item_url = environment.server_api_url + 'cart/ShoppingCartItem';
  public insert_url = environment.server_api_url + 'insert';
  public update_url = environment.server_api_url + 'update';
  public delete_url = environment.server_api_url + 'delete';

  user_cart: any;


  getAllCarts(): Observable<any> {
    return this.http.get(this.cart_url, { headers: environment.httpHeaders })
  }
  getAllCartItems(): Observable<any> {
    return this.http.get(this.cart_item_url, { headers: environment.httpHeaders })
  }

  getUserCart() {
    this.getAllCarts().subscribe(
      data => {

        for (var obj of data) {
          console.log("Obj is",obj['userlogin']['emailid'])
          console.log(this.current_user['emailid'])

          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if (obj['userlogin']['emailid'] == this.current_user['emailid']) {
            this.user_cart = obj;
             console.log("Cart is there");
             break;
            //alert("Successfully logined");
          }

        }
        this.getUserCartItems();


      },
      error => {
        console.log(error);
      }
    )
  }
  getUserCartItems(){
    this.getAllCartItems().subscribe(
      data => {

        for (var obj of data) {
          // console.log("Obj is",obj['userlogin']['emailid'])
          // console.log(this.current_user['emailid'])

          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if (obj['cartid']['cartid'] == this.user_cart['cartid']) {
             console.log("CartItem is there",obj);
             this.cart.push(obj)
             this.cart_total+= parseInt(obj['price']);

             this.updateCartSource(this.cart,this.cart_total);
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
    

      this.cart[i]['quantity'] = parseInt(this.cart[i]['quantity']) + 1;
      this.cart_total += parseInt(this.cart[i]['productid']['productprice']);
      if (this.auth.isLogined()) {
        console.log("Yes this User logined and updated")
        this.updateUserCart(this.cart[i]).subscribe(
          data => console.log('Success!',data),
          error => console.error('!error',error)
        )
  
      }


    }
    else {
      product['quantity'] = 1;
      product['cartitemid'] = "CI"+(this.cart.length+1);
      this.cart.push(product);
      this.cart_total += parseInt(product['productid']['productprice']);
    if (this.auth.isLogined()) {
      console.log("Yes this User logined")
      this.insertUserCart(product).subscribe(
        data => {
          console.log('Success!',data)
          this.cart[this.cart.indexOf(product)]["bizId"] = data["bizId"];
        },
        error => console.error('!error',error)
      )

    }


    }



    this.updateCartSource(this.cart,this.cart_total);


  }


  updateCartSource(cart:any,cart_total:any){
    this.cart_total_source.next(cart_total)
    this.cart_source.next(cart)
  }

  isInCart(product: any) {
    for (let i = 0; i < this.cart.length; i++) {
      if (this.cart[i]['bizId'] == product['bizId'])
        return i;

    }
    return -1;
  }


  updateUserCart(cart_item: any) {

  //   console.log("Inside")
  //   console.log()
  //   console.log(cart_item)
  //   console.log("Outside")

  //   console.log()
  //  console.log(cart_item['productid'])

   this.user_cart["bizLock"]= "20210407153447577setup";
  //  console.log(this.user_cart)

   //return new Observable()
   let prod = Utils.makeJsonObject(cart_item['productid'])
   //prod["bizLock"]="20210407150543853setup";
   let data = {
     "bizModule": "cart",
     "bizDocument": "ShoppingCartItem",
     "bizId":cart_item["bizId"],

     "cartitemid": cart_item["cartitemid"],
     "cartid": {
       "bizModule": "cart",
       "bizDocument": "ShoppingCart",
       "cartid": this.user_cart["cartid"],
       "userlogin": Utils.makeJsonObject(this.user_cart["userlogin"]),
       "subtotal": this.user_cart["subtotal"],
       "grandtotal": this.user_cart["grandtotal"],
       "bizId": "943b1096-ed84-4f24-80cf-71d96584f8cb",
       "bizCustomer": "unisys",
       "bizDataGroupId": null,
       "bizUserId":this.user_cart["bizUserId"],
       "bizVersion": 0,
       "bizLock": this.user_cart["bizLock"]
     },
     "productid": prod,
     "quantity": cart_item['quantity'],
     "price":cart_item['productid']['productprice'] * cart_item['quantity'],
     "bizCustomer": "unisys",
     "bizDataGroupId": null,
     "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
   }
       //Make http rest api call    
   // console.log("Updating in ",this.insert_url)
   return this.http.post<any>(this.update_url, data, { headers: environment.httpHeaders });




  }


  insertUserCart(cart_item: any) {
    //  console.log("Inside")
    //  console.log()
    // console.log(cart_item['productid'])
    this.user_cart["bizLock"]= "20210407153447577setup";
    // console.log(this.user_cart)

    //return new Observable()
    let prod = Utils.makeJsonObject(cart_item['productid'])
    //prod["bizLock"]="20210407150543853setup";
    let data = {
      "bizModule": "cart",
      "bizDocument": "ShoppingCartItem",
      "cartitemid": cart_item["cartitemid"],
      "cartid": {
        "bizModule": "cart",
        "bizDocument": "ShoppingCart",
        "cartid": this.user_cart["cartid"],
        "userlogin": Utils.makeJsonObject(this.user_cart["userlogin"]),
        "subtotal": this.user_cart["subtotal"],
        "grandtotal": this.user_cart["grandtotal"],
        "bizId": "943b1096-ed84-4f24-80cf-71d96584f8cb",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId":this.user_cart["bizUserId"],
        "bizVersion": 0,
        "bizLock": this.user_cart["bizLock"]
      },
      "productid": prod,
      "quantity": 1,
      "price":cart_item['productid']['productprice'],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }
        //Make http rest api call    
    // console.log("Putting in ",this.insert_url)
    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });
  }

  deleteUserCart(cart_item: any) {
    //  console.log("Inside")
    //  console.log()
    // console.log(cart_item['productid'])
    this.user_cart["bizLock"]= "20210407153447577setup";
    // console.log(this.user_cart)

    //return new Observable()
    let prod = Utils.makeJsonObject(cart_item['productid'])
    //prod["bizLock"]="20210407150543853setup";
    let data = {
      "bizModule": "cart",
      "bizDocument": "ShoppingCartItem",
      "bizId":cart_item["bizId"],

      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }
        //Make http rest api call    
    // console.log("Putting in ",this.insert_url)
    return this.http.request<any>('delete',this.delete_url,{headers: environment.httpHeaders,body:data});
  }
  removeCartItem(i:number){
    if(this.auth.isLogined())
    {

      if(this.auth.isLogined())
      {
        this.deleteUserCart(this.cart[i]).subscribe(
          data => console.log('Success!',data),
          error => console.error('!error',error)
        )
   
      }
      
    }
    this.cart.splice(i,1)

  }



}

















/* let prod={
      "bizModule": "product",
      "bizDocument": "Product",
      "productid": cart_item['productid']["productid"],
      "productname": cart_item['productid']["productname"],
      "productprice": cart_item['productid']["productprice"],
      "productstock": cart_item['productid']["productstock"],
      "smallimg": cart_item['productid']["smallimg"],
      "largeimg": cart_item['productid']["largeimg"],
      "description": cart_item['productid']["description"],
      "bizId": cart_item['productid']["bizId"],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": cart_item['productid']["bizUserId"],
      "bizVersion": cart_item['productid']["bizVersion"],
      "bizLock": "20210407150543853setup"
    }
     */