import { Component, OnInit } from '@angular/core';
import { WishlistService } from 'src/app/Services/api/wishlist.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  wishlist:any
  constructor(private wishlist_api:WishlistService) { 

    this.wishlist_api.wishlist_observer.subscribe(
      data =>{
        this.wishlist = data;
      }
    )

  }

  ngOnInit(): void {
  }



  removeItem(i:number){
    this.wishlist_api.removewishlistItem(i);

  }

}
