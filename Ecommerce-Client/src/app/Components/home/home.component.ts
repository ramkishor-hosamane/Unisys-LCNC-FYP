import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Services/api/api.service';
import { WishlistService } from 'src/app/Services/api/wishlist.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  image:string=  "assets/images/bg.jpg"
  constructor(private api:ApiService,private wishlist_api:WishlistService) {
    this.wishlist_api.is_wishlist_enabled_observer.subscribe(
      data => {
        this.is_wishlist_enabled = data;
      }
    )
    this.getProducts()

   }
   is_wishlist_enabled:any;

  products:Array<any> = []
  ngOnInit(): void {

  }
  
  getProducts(){
    this.api.getData(environment.server_rest_url + 'product/ProductCategoryMember/',"basic").subscribe(
      data => {
        
        data = this.shuffle(data);
        this.products = data
        this.products.pop();
        this.products.pop();
        
        console.log("Got data",this.products)
      },
      error => {
        console.log(error);
      }
    )

    
  }
  shuffle(array:any) {
    array.sort(() => Math.random() - 0.5);
    return array
  }


  updateCart(product:any){

  }
  
}
