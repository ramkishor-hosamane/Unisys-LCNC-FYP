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
  orders = [
    {
      "bizModule": "order",
      "bizDocument": "OrderHeader",
      "orderid": "O1",
      "userloginid": {
        "bizModule": "user",
        "bizDocument": "UserLogin",
        "userloginid": "Ram",
        "emailid": "ram@gmail.com",
        "mobileno": "9085670934",
        "firstname": "Ram",
        "lastname": "Kumar",
        "password": "1",
        "isenabled": true,
        "createdstamp": "17-Apr-2021 11:19",
        "updatedstamp": "17-Apr-2021 11:19",
        "bizId": "c06b9989-7e13-4b50-b4c0-f4505d3654db",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "aab62ff7-2ec1-41ad-aca5-9e721555fd3b",
        "bizVersion": 0,
        "bizLock": "20210417111914551setup"
      },
      "subtotal": "1650.00",
      "grandtotal": "1650.00",
      "addressid": {
        "bizModule": "user",
        "bizDocument": "Address",
        "addressid": "A1",
        "address1": "2201",
        "address2": "Mariyappanpalya",
        "city": "Bangalore",
        "state": "Karnataka",
        "zipcode": "560099",
        "country": "India",
        "createdstamp": "17-Apr-2021 11:22",
        "updatedstamp": "17-Apr-2021 11:22",
        "bizId": "bb676a21-6c95-4248-ac0a-fd8c27b1ccd3",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "aab62ff7-2ec1-41ad-aca5-9e721555fd3b",
        "bizVersion": 0,
        "bizLock": "20210417112224358setup"
      },
      "paymentmethod": "Cash",
      "orderstatus": "created",
      "orderdate": "17-Apr-2021 11:22",
      "createdstamp": "17-Apr-2021 11:22",
      "updatedstamp": "17-Apr-2021 11:22",
      "bizId": "05bc6f64-08eb-45d3-bc97-56a15a9cd090",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
    }
  ]



  routeToOrder(orderid:any){
    this.router.navigate(['/orders/',orderid["orderid"]]).then();
  }


}

