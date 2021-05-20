import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api/api.service';
import { AuthService } from 'src/app/Services/api/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  current_user:any
  constructor(private router:Router,private api:ApiService,private auth:AuthService) {
    this.auth.current_user_observer.subscribe(
      data => {
        this.current_user = data;
      }
    )


    this.api.getData(environment.server_api_url+"order/OrderHeader").subscribe(
      data=>{
        this.orders = []
        for (var order of data) {
          if(order['userloginid']['userloginid']==this.current_user['userloginid']){
            this.orders.push(order)
          }
        }
        console.log("Got orders",data)
      },error=>console.log("error",error)

    )

   }

  ngOnInit(): void {


  }
  orders :any=[];


  routeToOrder(orderid:any){
    this.router.navigate(['/orders/',orderid["orderid"]]).then();
  }


}

