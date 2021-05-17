import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import {Utils} from './../../utils';
import { SharedService } from '../shared.service';
import { ApiService } from './api.service';
import { SessionStorageService } from 'ngx-webstorage';
@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private auth: AuthService,private session_st:SessionStorageService, private http: HttpClient,private shared:SharedService,private api:ApiService) {
    
    this.initializeCartService()


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
  public cart_biz_url = environment.server_query_url+'cart/GetCartBiz';
  user_cart: any={};
  bizLocks:any;
  bizVersions:any;
  initializeCartService()
  {
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

    this.shared.bizLock_observer.subscribe(
      data => {
        this.bizLocks = data;
      }
    )
    
    this.shared.bizVersion_observer.subscribe(
      data => {
        this.bizVersions = data;
      }
    )
    
    console.log()
    if (this.session_st.retrieve("token")!=null) {
      console.log("Getting usercart")
      this.api.getDataById(environment.server_api_url+"user/UserLogin",this.session_st.retrieve("userid")).subscribe(
        user_obj=>{
          //console.log("Got user object");
          this.current_user = user_obj;
          this.auth.updateUserSession(this.current_user)
          this.getUserCartFromAllCarts();
        }
      )
      

    }

  }

  getUserCartFromAllCarts() {
    this.api.getData(this.cart_url).subscribe(
      data => {

        for (var obj of data) {
          console.log("Obj inside ",obj)
          if (obj['userlogin']['emailid'] == this.current_user['emailid']) {
            this.api.getDataById(this.cart_url,obj['bizId']).subscribe(
              data=>{
                this.user_cart = data;    
                this.shared.updateBiz(data['userlogin']["bizLock"],data['userlogin']["bizVersion"],'user')
                this.shared.updateBiz(data["bizLock"],data["bizVersion"],'cart')
                this.getUserCartItems();

              },
              err=>{
                console.error(err);

              }
              
            )
            break;
          }

        }
      },
      error => {
        console.log(error);
      }
    )
  }
  getUserCartItems(){
    this.api.getData(this.cart_item_url).subscribe(
      data => {

        for (var obj of data) {
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
    //Check if item is already present in cart
    var i = this.isInCart(product)
    if (i >= 0) 
    {
      //If item is present update the quantity and cart total and update User cart via api call
      this.cart[i]['quantity'] = parseInt(this.cart[i]['quantity']) + 1;
      this.cart_total += parseInt(this.cart[i]['productid']['productprice']);
      if (this.auth.isLogined()) {
        console.log("Yes this User logined and updated")
        this.updateCartItemService(this.cart[i]);                
      }
    }
    else {
      product['quantity'] = 1;
      //Logic to to assign cart id
      if(this.cart.length>0)
      {
        var last_element = this.cart[this.cart.length-1]
        var arr = last_element["cartitemid"].split("-")
        var index = Number(arr[arr.length-1])+1
        product['cartitemid'] = this.user_cart["cartid"]+"-"+index;
      }
      else
      {
        product['cartitemid'] = this.user_cart["cartid"]+"-"+1;

      }


      this.cart.push(product);
      this.cart[this.cart.length-1]['price'] = product['productid']['productprice']
      this.cart_total += parseInt(product['productid']['productprice']);
    
    
      if (this.auth.isLogined()) {
      this.insertCartItemService(this.cart[this.cart.length-1]);

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
      if (this.cart[i]['productid']['bizId'] == product['productid']['bizId'])
        return i;
      // console.log("inserted",this.cart[i],product)

    }
    return -1;
  }


  removeCartItem(i:number){
    if(this.auth.isLogined())
    {
      this.deleteCartItemService(i);
    }
    this.cart.splice(i,1)

  }




  /* -------------------------------All Api Services------------------------------- */
  updateCartItemService(product:any){
    this.updateUserCartItemApi(product).subscribe(
      data => {
        console.log('Updating Cartitem Success!',data)
        this.shared.updateBiz(data['cartid']["bizLock"],data['cartid']["bizVersion"],'cart')

      this.updateUserCart();
    },
      error => console.error('Updating Cartitem !error',error)
    )
  }
 
  insertCartItemService(product:any){
    this.user_cart["bizLock"] = this.bizLocks['cart']
    this.user_cart["bizVersion"] = this.bizVersions['cart']
    console.log("Product id is",product['productid'])
    let prod = Utils.makeJsonObject(product['productid'])
    let data = {
      "bizModule": "cart",
      "bizDocument": "ShoppingCartItem",
      "cartitemid": product["cartitemid"],
      "cartid": {
        "bizModule": "cart",
        "bizDocument": "ShoppingCart",
        "cartid": this.user_cart["cartid"],
        "userlogin": Utils.makeJsonObject(this.user_cart["userlogin"]),//
        "subtotal": this.user_cart["subtotal"],
        "grandtotal": this.user_cart["grandtotal"],
        "bizId": this.user_cart["bizId"],
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId":this.user_cart["bizUserId"],
        "bizVersion":this.bizVersions['cart'],
        "bizLock": this.bizLocks['cart']
      },
      "productid": prod,
      "quantity": 1,
      "price":product['price'],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }

      this.api.insertData(this.insert_url, data,).subscribe(
        data => {
          console.log('Success! cartitem is now',data)

          this.shared.updateBiz(data['cartid']["bizLock"],data['cartid']["bizVersion"],'cart')
          this.shared.showPopup("Item added to cart","success");
          this.cart[this.cart.indexOf(product)]["bizId"] = data["bizId"];

          this.updateUserCart();
        },
        error => console.error('Updating Cartitem !error',error)
      )

  }

  deleteCartItemService(i:number){
    var cart_item = this.cart[i];
    this.user_cart["bizLock"]= this.bizLocks['user'];
    this.user_cart["bizVersion"]= this.bizVersions['user'];

    let prod = Utils.makeJsonObject(cart_item['productid'])
    let data = {
      "bizModule": "cart",
      "bizDocument": "ShoppingCartItem",
      "bizId":cart_item["bizId"],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }



        this.api.deleteData(this.delete_url,data).subscribe(
          dat => {
            console.log('Deleting Cartitem Sucess yippe',dat)
            this.updateUserCart();
          
          },
          error => console.error('Deleting Cartitem !!!error',error)

        )
  }
  destroyCartService(){
    this.cart = [];
    this.user_cart = null;
    this.cart_total = 0;
    this.updateCartSource(this.cart,this.cart_total);
  }
  deleteAllCartItemService(){
    while(this.cart.length>0)
    {
      //this.cart_total -= this.cart[0]["price"]

      this.removeCartItem(0)
    }
    this.cart_total = 0
    this.updateUserCart();
    
  }

  createUserCartApi(cart: any,cart_no:number,user:any) {
    let data = 
      {
        "bizModule": "cart",
        "bizDocument": "ShoppingCart",
        "cartid": "C"+cart_no,
        "userlogin": Utils.makeJsonObject(user),
        "subtotal": "0",
        "grandtotal": "0",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
      }
    

    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });
  }

  updateUserCart() {
    this.current_user["bizVersion"]= this.bizVersions['user'],
    this.current_user["bizLock"]= this.bizLocks['user'];
    let user = Utils.makeJsonObject(this.current_user)

    // console.log("---------------")
    // console.log(user)
    // console.log(this.current_user)
    // console.log("---------------")

    let data = 
      {
        "bizModule": "cart",
        "bizDocument": "ShoppingCart",
        "cartid": this.user_cart["cartid"] ,
        "userlogin": user,
        "subtotal": this.cart_total,
        "grandtotal": this.cart_total,
        "bizId":this.user_cart["bizId"],
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
      }
    
    this.api.updateData(this.update_url, data,).subscribe(
      data => {
        console.log('Updating Cart Success!',data)
      
      this.shared.updateBiz(data["bizLock"],data["bizVersion"],'cart')
     
    },
      error => console.error('Updating Cart error',error)
    )
  }



  updateUserCartItemApi(cart_item: any) {
     let prod = Utils.makeJsonObject(cart_item['productid'])
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
         "bizId": this.user_cart["bizId"],
         "bizCustomer": "unisys",
         "bizDataGroupId": null,
         "bizUserId":this.user_cart["bizUserId"],
         "bizVersion": this.bizVersions['cart'],
         "bizLock": this.bizLocks['cart']
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
  

}
















