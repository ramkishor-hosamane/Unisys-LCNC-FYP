import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public product_url = environment.server_api_url + 'product/ProductCategoryMember/';
  //Basic authorization details
  authorizationData = 'Basic ' + btoa('setup' + ':' + 'setup');
  httpHeaders = new HttpHeaders({'Content-type':'application/json','Authorization': this.authorizationData});
  headerOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': this.authorizationData
    })
};
  constructor(private http: HttpClient) { }

  allProduct(): Observable<any> {
    return this.http.get(this.product_url,{headers:this.httpHeaders})
  }
  // addNewProduct(product_dto): Observable<any> {
  //   return this.apiService.post(this.product_url, product_dto);
  // }

  singleProduct(id:string) {
    return this.http.get(this.product_url + id)
  }
  
}
