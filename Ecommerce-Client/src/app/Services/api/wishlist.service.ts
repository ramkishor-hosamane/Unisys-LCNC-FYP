import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import {Utils} from './../../utils';
import { SharedService } from '../shared.service';
import { ApiService } from './api.service';
@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  constructor(private auth: AuthService, private http: HttpClient,private shared:SharedService,private api:ApiService) {

    this.initializeWishlistService();

  }

  private wishlist_source = new BehaviorSubject<Array<any>>([]);
  wishlist_observer = this.wishlist_source.asObservable();
  wishlist: any;

  private is_wishlist_enabled_source = new BehaviorSubject<Boolean>(true);
  is_wishlist_enabled_observer = this.is_wishlist_enabled_source.asObservable();
  is_wishlist_enabled: any;  

  current_user: any;

  wishlist_url = environment.server_api_url + 'ShoppingList/ShoppingListItem';
  user_wishlist_url = environment.server_custom_api_url+"getWishlistItemsByUser/";
  public insert_url = environment.server_api_url + 'insert';
  public update_url = environment.server_api_url + 'update';
  public delete_url = environment.server_api_url + 'delete';
  public wishlist_biz_url = environment.server_query_url+'wishlist/GetwishlistBiz';
  bizLocks:any;
  bizVersions:any;
  initializeWishlistService()
  {
    this.wishlist_observer.subscribe(
      data => {
        this.wishlist = data;
      }
    )

    this.is_wishlist_enabled_observer.subscribe(
      data => {
        this.is_wishlist_enabled = data;
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

    this.api.getData(environment.server_api_url+"admin/Settings","basic").subscribe(

      data =>{
        console.log(data);
        this.is_wishlist_enabled = data[0]["wishlist"];
        this.is_wishlist_enabled_source.next(this.is_wishlist_enabled);
      },
      error=>console.log("Error in getting settings",error)

    )    



    if (this.auth.isLogined()) {
      console.log("Getting userwishlist")
      this.getUserwishlistItems();
      

    }

  }

  
  getUserwishlistItems(){
    this.api.getData(this.user_wishlist_url+this.current_user["userloginid"]).subscribe(
      data => {

        for (var obj of data) {
        //  if (obj['wishlistid']['wishlistid'] == this.wishlist['wishlistid']) {
             console.log("wishlistItem is there",obj);
             this.wishlist.push(obj)
             this.updatewishlistSource(this.wishlist);
            // }

        }


      },
      error => {
        console.log(error);
      }
    )
  }



updatewishlistItem(product: any) {
    //Check if item is already present in wishlist
    var i = this.isInwishlist(product)
    if (i >= 0) 
    {
      //If item is present update the quantity and wishlist total and update User wishlist via api call
      if (this.auth.isLogined()) {
        console.log("Yes this User logined and updated")
        //this.updatewishlistItemService(this.wishlist[i]);  
        this.removewishlistItem(i);              
      }
    }
    else {


      if (this.auth.isLogined()) {
        console.log("Came here Wishlist")
        this.wishlist.push(product);
        this.wishlist[this.wishlist.length-1]['price'] = product['productid']['productprice']    
      
      this.insertwishlistItemService(this.wishlist[this.wishlist.length-1]);

    }


    }



    this.updatewishlistSource(this.wishlist);

  }
  

  updatewishlistSource(wishlist:any){
    this.wishlist_source.next(wishlist)
  }

  isInwishlist(product: any) {
    for (let i = 0; i < this.wishlist.length; i++) {
      if (this.wishlist[i]['productid']['bizId'] == product['productid']['bizId'])
        return i;
      // console.log("inserted",this.wishlist[i],product)

    }
    return -1;
  }


  removewishlistItem(i:number){
    if(this.auth.isLogined())
    {
      this.deletewishlistItemService(i);
    }
    this.wishlist.splice(i,1)

  }




  /* -------------------------------All Api Services------------------------------- */

 
  insertwishlistItemService(product:any){
    
    // this.current_user["bizLock"] = this.bizLocks['user']
    // this.current_user["bizVersion"] = this.bizVersions['user']
    let user = Utils.makeJsonObject(this.current_user)
    
    let prod = Utils.makeJsonObject(product['productid'])
    let cat_id = Utils.makeJsonObject(product['categoryid'])
    
    let data = {
      "bizModule": "wishlist",
      "bizDocument": "ShoppingListItem",
      "userloginid":user,
      "categoryid": cat_id,
      "productid": prod,
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "ec8e20f1-5183-414a-93e2-4fac021416aa",
    }
      console.log("Actual product")
      console.log(product)
      console.log("PUshing data",data)
      this.api.insertData(this.insert_url, data).subscribe(
        data => {
          console.log('Success! wishlistitem is now',data)

          // this.shared.updateBiz(data['wishlistid']["bizLock"],data['wishlistid']["bizVersion"],'wishlist')

          this.wishlist[this.wishlist.indexOf(product)]["bizId"] = data["bizId"];
        },
        error => console.error('Updating wishlistitem !error',error)
      )

  }

  deletewishlistItemService(i:number){
    var wishlist_item = this.wishlist[i];
    // this.wishlist["bizLock"]= this.bizLocks['user'];
    // this.wishlist["bizVersion"]= this.bizVersions['user'];

    // let prod = Utils.makeJsonObject(wishlist_item['productid'])
    let data = {
      "bizModule": "wishlist",
      "bizDocument": "ShoppingListItem",
      "bizId":wishlist_item["bizId"],
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }



        this.api.deleteData(this.delete_url,data).subscribe(
          dat => {
            console.log('Deleting wishlistitem Sucess yippe',dat)          
          },
          error => console.error('Deleting wishlistitem !!!error',error)

        )
  }
  destroywishlistService(){
    this.wishlist = [];
    this.wishlist = null;
    this.updatewishlistSource(this.wishlist);
  }
  deleteAllwishlistItemService(){
    while(this.wishlist.length>0)
    {
      //this.wishlist_total -= this.wishlist[0]["price"]

      this.removewishlistItem(0)
    }    
  }




 
}