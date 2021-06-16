import { HttpHeaders } from "@angular/common/http";

export const environment = {
  production: true,
  server_url:'http://localhost:8080/ecommerce/',
  server_rest_url:'http://localhost:8080/ecommerce/rest/json/',

  server_api_url:'http://localhost:8080/ecommerce/api/json/',
  server_custom_api_url:'http://localhost:8080/ecommerce/api/',
  
  server_query_url:'http://localhost:8080/ecommerce/rest/json/query/',
  get_data_by_id_url:'http://localhost:8080/ecommerce/api/json/getData/',
  get_orderitem_by_orderid_url:'http://localhost:8080/ecommerce/api/json/getOrderItemsByOrderHeader/',
  
  //Basic authorization details
  httpHeaders : new HttpHeaders({'Content-type':'application/json','Authorization': 'Basic ' + btoa('setup' + ':' + 'setup')}),

};