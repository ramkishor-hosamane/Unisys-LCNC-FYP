import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/Services/api/api.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orderId:any="";


  orderHeader:any={};
  orderItems:any=[];
  
  steps = {'shipping':'active','delivery':'disabled'}
  
  constructor(private route:ActivatedRoute,private api:ApiService,private http:HttpClient) {

    route.params.subscribe(val => {
      this.orderId = this.route.snapshot.paramMap.get('orderid');
      console.log("This is the id ",this.orderId)
      this.getOrderData();
    });

    }

    getOrderData(){
      this.api.getData(environment.get_data_by_id_url+"order/OrderHeader/"+this.orderId).subscribe(
        data=>{
            this.orderHeader = data[0];
            console.log(this.orderHeader)
            this.api.getData(environment.get_orderitem_by_orderid_url+this.orderId).subscribe(
              data=>{
                  this.orderItems = data
                  console.log("Came here")
                  this.assignSteps()

              },
              error=>console.log("Error in geting data",error)
            )
        
        },
        error=>console.log("Error in geting data",error)
      );

    }

    assignSteps(){

      switch(this.orderHeader['orderstatus'])
      {
        case "shipped": this.steps["shipping"]="completed"
                        this.steps["delivery"]="active"
                        console.log(this.steps)
                        break;
      }
    }
 ngOnInit() {

    // this.orderId = this.route.snapshot.paramMap.get('orderid');
    // console.log("This is the id ",this.orderId)
    // this.orderHeader = await this.http.get(environment.get_data_by_id_url+"order/OrderHeader/"+this.orderId,{ headers: environment.httpHeaders }).toPromise();
    // console.log(this.orderHeader)
    // this.orderItems = await this.http.get(environment.get_orderitem_by_orderid_url+this.orderId,{ headers: environment.httpHeaders }).toPromise();
    // console.log(this.orderHeader)
    // // this.orderHeader = await this.http.get("http://localhost:8080/ecommerce/api/json/order/OrderItem",{ headers: environment.httpHeaders }).toPromise();
    // console.log(this.orderHeader)
  }
}
