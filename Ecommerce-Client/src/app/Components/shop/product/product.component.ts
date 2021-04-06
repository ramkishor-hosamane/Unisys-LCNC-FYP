import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/Services/api/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  product_id:any;
  product:any;
  constructor(private route:ActivatedRoute,private product_api:ProductService,private router:Router) { 
    route.params.subscribe(val => {
      this.product_id = this.route.snapshot.paramMap.get('id');

      this.getProduct();
    });
    }

  ngOnInit(): void {
  }


  getProduct = () =>{
    this.product_api.singleProduct(this.product_id).subscribe(
      data => {
        this.product = data;
        console.log(this.product)
      },
      error => {
        console.log(error);
      }
    )

  }

}
