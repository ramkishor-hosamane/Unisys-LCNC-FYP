import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { Utils } from './../../utils';
import { SharedService } from '../shared.service';
import { CartService } from './cart.service';
import { ApiService } from './api.service';
import { Product } from 'src/app/Models/product';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor(private api:ApiService,private auth: AuthService, private http: HttpClient, private shared: SharedService,private cart_api:CartService) {
    this.initializeCheckoutProcess();


  }

  private ordering_status_source = new BehaviorSubject<String>("checkout");
  ordering_status_observer = this.ordering_status_source.asObservable();
  ordering_status: any;  

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
  order_id:any="";





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

    this.ordering_status_observer.subscribe(
      data=>{
        this.ordering_status = data
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

  updateOrdering_status(order_status:any){
    this.ordering_status_source.next(order_status);

  }


  insertUserAddress(user_addr: any) {
    this.insertAddressApi(user_addr['addressid']).subscribe(
      data => {
        this.shared.updateBiz(data['bizLock'],data['bizVersion'],'address');
        
        console.log("Sucess inserting new address")
        this.insertUserAddressApi(user_addr, data).subscribe(
          data => {
            console.log("Sucesss in inserting new Useradress")

            this.shared.updateBiz(data['addressid']['bizLock'],data['addressid']['bizVersion'],'address');
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
    this.api.getData(this.user_address_url).subscribe(
      data => {
        for (var obj of data) {
          if (obj['userloginid']['emailid'] == this.current_user['emailid']) {
            this.api.getDataById(this.user_address_url,obj['bizId']).subscribe(
              data => {
                console.log("Address is there");
                console.log(data);
                this.shared.updateBiz(data['userloginid']["bizLock"], data['userloginid']["bizVersion"],'user')
                this.all_user_adresses.push(data)
              },
              err => {
                console.error(err);

              }

            )
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

  
 
 



  /* -------------------------------All Api Services------------------------------- */

  placeOrderService(user_addr:any,is_new_address:boolean){
    this.order_id = "";
    //Create Order_header
    if(is_new_address){
      //Saving new Addresss
      this.insertUserAddress(user_addr);
 
    }
    else{
      this.createOrder(user_addr)
    }  
  }

  async insertOrderItemOnebyOne(order_header:any,user_addr:any,i:number){
    if(i<0)
    {
      this.cart_api.deleteAllCartItemService()
      setTimeout(() => 
            {
              this.ordering_status ="success";
              this.order_id = order_header["orderid"];
              
              this.updateOrdering_status(this.ordering_status);

            },
            2000);
      return;

    }

    var cart_item = this.cart[i];
    this.total_order_items+=1
    var id = (i+1)              

    let product_res:any = await this.http.get(environment.server_api_url+"product/Product/"+(cart_item["productid"]["bizId"]),{ headers: environment.httpHeaders }).toPromise(); 
    console.log(product_res)
    console.log(product_res.data)
    
    this.insertOrderItemApi(cart_item,product_res,order_header,user_addr,id).subscribe(
      order_item=>{
        console.log("Done inserting item")
        i-=1;
        this.shared.updateBiz(order_item["orderid"]["bizLock"],order_item["orderid"]["bizVersion"],'order')        
        this.insertOrderItemOnebyOne(order_header,user_addr,i)
      },
      error=>{
        console.log("Error in inserting item",error)
      }

    )

  }

  createOrder(user_addr:any){
    this.insertOrderHeaderApi(user_addr).subscribe(
      order_header=>{
        console.log("Success in inserting order header")
        this.total_order_headers+=1;
        this.shared.updateBiz(order_header["bizLock"],order_header["bizVersion"],'order')
        this.insertOrderItemOnebyOne(order_header,user_addr,this.cart.length-1)
      },
      error=>{
        console.log("Error in Placing Order header",error)
      }
    )
  

  }
  /* -------------------------------------------------------------------------------------- */
  /* -------------------------------All Api Call Functions------------------------------- */
  /* ------------------------------------------------------------------------------------ */


  /* Order */
  insertOrderHeaderApi(user_addr: any) {

    let user = Utils.makeJsonObject(this.current_user)
    let addr = Utils.makeJsonObject(user_addr["addressid"])
    var date = Utils.getCurrentDateTime()
    user["bizLock"]=this.bizLocks['user']
    user["bizVersion"]=this.bizVersions['user']
    console.log("ORder data is",Utils.getCurrentDateTime())
    let data ={
      "bizModule": "order",
      "bizDocument": "OrderHeader",
      "orderid": "O"+(this.total_order_headers+1),
      "userloginid": user,
      "subtotal": this.cart_total,
      "grandtotal": this.cart_total,
      "addressid": addr,
      "paymentmethod": "Cash",
      "orderstatus":"created",
      "orderdate":date,
      "createdstamp":date,
      "updatedstamp":date,
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",

    }


    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }

  
  insertOrderItemApi(order_item: any,product_res:any, order_header: any, user_addr: any,id:number) {
    
    let user = Utils.makeJsonObject(this.current_user)
    let addr = Utils.makeJsonObject(user_addr["addressid"])
    var date = Utils.getCurrentDateTime()

    // console.log(product_res.data)
    // console.log(product_res)
    
    // console.log(JSON.stringify(product_res.data))
    user["bizLock"]=this.bizLocks['user']
    user["bizVersion"]=this.bizVersions['user']
    // console.log("Before inserting order item jsut checking")
    // console.log(order_item)

    // console.log(order_item["price"])
    // console.log("---->")
    let data = {
      "bizModule": "order",
      "bizDocument": "OrderItem",
      "orderitemid": order_header['orderid']+"-" + id,
      "orderid": {
        "bizModule": "order",
        "bizDocument": "OrderHeader",
        "orderid": order_header['orderid'],
        "userloginid": user,
        "subtotal": order_header['subtotal'],
        "grandtotal": order_header['grandtotal'],
        "addressid": addr,
        "orderstatus":order_header["orderstatus"],
        "paymentmethod": "Cash",
        "orderdate":date,
        "createdstamp": date,
        "updatedstamp": date,
        "bizId": order_header['bizId'],
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
        "bizVersion": this.bizVersions["order"],
        "bizLock": this.bizLocks["order"]
      },

      "productid":product_res,
      "quantity": order_item["quantity"],
      "unitprice": order_item["price"],
      "createdstamp": Utils.getCurrentDateTime(),
      "updatedstamp": Utils.getCurrentDateTime(),
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
      "createdstamp": Utils.getCurrentDateTime(),
      "updatedstamp": Utils.getCurrentDateTime(),
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

    let data = {
      "bizModule": "user",
      "bizDocument": "UserAddress",
      "userloginid": user,
      "addressid": addr,
      "sequenceno": "1",
      "addresstype": user_addr["addresstype"],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"


    }


    return this.http.put<any>(this.insert_url, data, { headers: environment.httpHeaders });

  }







  // getUserCartApi(id: string): Observable<any> {
  //   return this.http.get(this.cart_url + '/' + id, { headers: environment.httpHeaders })

  // }





}
