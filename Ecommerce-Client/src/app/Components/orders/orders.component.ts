import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api/api.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  constructor(private router:Router,private api:ApiService) {

    this.api.getData(environment.server_api_url+"order/OrderHeader").subscribe(
      data=>{
        this.orders = data
      },error=>console.log("error",error)

    )

   }

  ngOnInit(): void {


  }
  orders :any;


  routeToOrder(orderid:any){
    this.router.navigate(['/orders/',orderid["orderid"]]).then();
  }


}

