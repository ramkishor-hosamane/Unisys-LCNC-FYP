import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from 'src/app/Services/api/cart.service';
import { ProductService } from 'src/app/Services/api/product.service';
import { WishlistService } from 'src/app/Services/api/wishlist.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  categoryType:any="";
  public totalRecords : number=0;
  public page : number=1;
  products:Array<any> = []
  public isloading=false;
  is_wishlist_enabled:any;
  
  constructor(private route:ActivatedRoute,private product_api:ProductService,private router:Router,private cart_api:CartService,private wishlist_api:WishlistService) { 
    route.params.subscribe(val => {
      this.categoryType = this.route.snapshot.paramMap.get('categoryType');
      console.log(this.categoryType)
      this.getProducts();

      this.wishlist_api.is_wishlist_enabled_observer.subscribe(
        data => {
          this.is_wishlist_enabled = data;
        }
      )
    });
    }

  ngOnInit(): void {

  }

 
  getProducts = () =>{
    this.product_api.allProduct().subscribe(
      data => {
        this.products = data;
        this.products  = this.products.filter( element => element['categoryid']['categoryname'] == this.categoryType)
        this.totalRecords = this.products.length;

      },
      error => {
        console.log(error);
      }
    )

  }


  updateCart(product:any)
  {
    this.cart_api.addCartItem(product);
    console.log("added");
  }

  updateWishlist(product:any)
  {
    this.wishlist_api.updatewishlistItem(product);
  }

  isInWishlist(product:any){
    var i = this.wishlist_api.isInwishlist(product)
    return i>=0;
  }

}
