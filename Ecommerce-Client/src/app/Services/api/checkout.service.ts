import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { Utils } from './../../utils';
import { SharedService } from '../shared.service';
import { CartService } from './cart.service';
@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor(private auth: AuthService, private http: HttpClient, private shared: SharedService,private cart_api:CartService) {

    this.initializeCheckoutProcess();


  }


  cart: any;
  cart_total: any = 0;
  current_user: any;

  cart_url = environment.server_api_url + 'cart/ShoppingCart';
  cart_item_url = environment.server_api_url + 'cart/ShoppingCartItem';
  public insert_url = environment.server_api_url + 'insert';
  user_address_url = environment.server_api_url + 'user/UserAddress';
  order_header_url = environment.server_api_url + 'order/OrderHeader';
  order_item_url = environment.server_api_url + 'order/OrderItem';


  user_cart: any;
  bizLocks: any;
  bizVersions: any;
  all_user_adresses: any = []
  total_adresses: number = 0;
  total_order_headers: number = 0;
  total_order_items: number = 0;





  initializeCheckoutProcess(){

    this.cart_api.cart_observer.subscribe(
      data => {
        this.cart = data;
        console.log("Got cart",this.cart);
      }
    )

    this.cart_api.cart_total_observer.subscribe(
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

    if (this.auth.isLogined()) {
      console.log("Getting usercart")
      this.getUserAddressFromAllAddresses();


      this.getAllOrderItemsApi().subscribe(
        data => {
          this.total_order_items = data.length;
        }
      )

      this.getAllOrderHeadersApi().subscribe(
        data => {
          this.total_order_headers = data.length;
        }
      )


    }


  }


  insertUserAddress(user_addr: any) {
    this.insertAddressApi(user_addr['addressid']).subscribe(
      data => {

        this.bizLocks['address'] = data['bizLock'];
        this.bizVersions['address'] = data['bizVersion'];
        
        console.log("Sucess inserting new address")
        this.insertUserAddressApi(user_addr, data).subscribe(
          data => {
            console.log("Sucesss in inserting new Useradress")
            this.bizLocks['address'] = data['addressid']['bizLock'];
            this.bizVersions['address'] = data['addressid']['bizVersion'];
            this.shared.updateBizVersionandBizLock(this.bizLocks,this.bizVersions);
            this.createOrder(data)
          },
          error => {
            console.log("Error in inserting new Useradress",error)
          }
        )
      },
      error => {
        console.error("Error in inserting new address",error);

      }
    )
  }
  getUserAddressFromAllAddresses() {
    this.getAllUserAddressApi().subscribe(
      data => {
        for (var obj of data) {
          //console.log("Obj is",obj['userlogin']['emailid'])

          //console.log(this.current_user['emailid'])

          // console.log("Checking "+this.userModel.emailid +" and "+obj['emailid'])
          // console.log("Checking "+this.userModel.password +" and "+obj['password'])
          if (obj['userloginid']['emailid'] == this.current_user['emailid']) {

            this.getUserAddressApi(obj['bizId']).subscribe(
              data => {
                console.log("Address is there");
                console.log(data);
                this.bizLocks['user'] = data['userloginid']["bizLock"]
                this.bizVersions['user'] = data['userloginid']["bizVersion"]
                this.shared.updateBizVersionandBizLock(this.bizLocks, this.bizVersions)
                this.all_user_adresses.push(data)
              },
              err => {
                console.error(err);

              }

            )




            //alert("Successfully logined");
          }

        }
        this.total_adresses = data.length;
      },
      error => {
        console.log(error);
      }
    )

    return this.all_user_adresses;
  }


  initializeCartService() {

  }

 
 



  /* -------------------------------All Api Services------------------------------- */

  placeOrderService(user_addr:any,is_new_address:boolean){
    //Create Order_header
    if(is_new_address){
      //Saving new Addresss
      this.insertUserAddress(user_addr);
 
    }
    else{
      this.createOrder(user_addr)
    }

  
  }

  createOrder(user_addr:any){
    this.insertOrderHeaderApi(user_addr).subscribe(
      order_header=>{
        console.log("Success in inserting order header")
        this.total_order_headers+=1;
        for(var cart_item of this.cart){
          // console.log("Product is ",cart_item)
          this.total_order_items+=1
          var id = this.total_order_items              

          this.insertOrderItemApi(cart_item,order_header,user_addr,id).subscribe(
            order_item=>{
              console.log("Done inserting item")
            },
            error=>{
              console.log("Error in inserting item",error)
            }

          )

        }
        


      },
      error=>{
        console.log("Error in Placing Order header",error)
      }
    )
  

  }
  /* -------------------------------------------------------------------------------------- */
  /* -------------------------------All Api Call Functions------------------------------- */
  /* ------------------------------------------------------------------------------------ */
  getAllUserAddressApi(): Observable<any> {
    return this.http.get(this.user_address_url, { headers: environment.httpHeaders })

  }

  /* Order */
  insertOrderHeaderApi(user_addr: any) {

    let user = Utils.makeJsonObject(this.current_user)
    let addr = Utils.makeJsonObject(user_addr["addressid"])
    user["bizLock"]=this.bizLocks['user']
    user["bizVersion"]=this.bizVersions['user']

    let data ={
      "bizModule": "order",
      "bizDocument": "OrderHeader",
      "orderid": "O"+(this.total_order_headers+1),
      "userloginid": user,
      "subtotal": this.cart_total,
      "grandtotal": this.cart_total,
      "addressid": addr,
      "paymentmethod": "Cash",
      "createdstamp": "",
      "updatedstamp": "",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",

    }


    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }
  insertOrderItemApi(order_item: any, order_header: any, user_addr: any,id:number) {

    let user = Utils.makeJsonObject(this.current_user)
    let addr = Utils.makeJsonObject(user_addr["addressid"])
    user["bizLock"]=this.bizLocks['user']
    user["bizVersion"]=this.bizVersions['user']
    let data = {
      "bizModule": "order",
      "bizDocument": "OrderItem",
      "orderitemid": "OI" + id,
      "orderid": {
        "bizModule": "order",
        "bizDocument": "OrderHeader",
        "orderid": order_header['orderid'],
        "userloginid": user,
        "subtotal": order_header['subtotal'],
        "grandtotal": order_header['grandtotal'],
        "addressid": addr,
        "paymentmethod": "Cash",
        "createdstamp": "",
        "updatedstamp": "",
        "bizId": order_header['bizId'],
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
        "bizVersion": order_header['bizVersion'],
        "bizLock": order_header['bizLock']
      },
      "orderitemseqnum": "1",
      "quantity": order_item["quantity"],
      "unitprice": order_item["price"],
      "createdstamp": "",
      "updatedstamp": "",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }

    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }



  getAllOrderHeadersApi(): Observable<any> {
    return this.http.get(this.order_header_url, { headers: environment.httpHeaders })
  }
  getAllOrderItemsApi(): Observable<any> {
    return this.http.get(this.order_item_url, { headers: environment.httpHeaders })
  }







  /* ----------- */
  insertAddressApi(user: any) {
    let data = {
      "bizModule": "user",
      "bizDocument": "Address",
      "addressid": "A" + (this.total_adresses + 1),
      "address1": user["address1"],
      "address2": user["address2"],
      "city": user["city"],
      "state": user["state"],
      "zipcode": user["zipcode"],
      "country": user["country"],
      "createdstamp": "",
      "updatedstamp": "",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
    }
    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }
  insertUserAddressApi(user_addr: any, addr: any) {
    let user = Utils.makeJsonObject(this.current_user)
    user["bizLock"]=this.bizLocks['user']
    user["bizVersion"]=this.bizVersions['user']
    //let addr = Utils.makeJsonObject(user_addr["addressid"])

    let data = {
      "bizModule": "user",
      "bizDocument": "UserAddress",
      "userloginid": user,
      "addressid": addr,
      "sequenceno": "1",
      "addresstype": user_addr["addresstype"],
      "createdstamp": "",
      "updatedstamp": "",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"


    }


    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }




  getUserAddressApi(id: string): Observable<any> {
    return this.http.get(this.user_address_url + '/' + id, { headers: environment.httpHeaders })

  }


  getUserCartApi(id: string): Observable<any> {
    return this.http.get(this.cart_url + '/' + id, { headers: environment.httpHeaders })

  }





}
