import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { SignupComponent } from './Components/signup/signup.component';
import { CartComponent } from './Components/cart/cart.component';
import { ShopComponent } from './Components/shop/shop.component';
import { ProductComponent } from './Components/shop/product/product.component';
import { NotFoundComponent } from './Components/shared/not-found/not-found.component';
import { CheckoutComponent } from './Components/checkout/checkout.component';
import { ContactComponent } from './Components/contact/contact.component';
import { OrdersComponent } from './Components/orders/orders.component';
import { OrderComponent } from './Components/orders/order/order.component';


const routes: Routes = [
  { path :'' ,redirectTo:'/home',pathMatch:'full'},
  { path :'shop/:categoryType' , component:ShopComponent},
  { path :'shop/:categoryType/:id' , component:ProductComponent},
  { path :'shop' , component:ShopComponent},
  { path :'home' , component:HomeComponent},
  { path :'signup' , component:SignupComponent},
  { path :'login' , component:LoginComponent},
  { path :'logout' , component:HomeComponent},
  { path :'orders' , component:OrdersComponent},
  { path :'orders/:orderid' , component:OrderComponent},

  { path :'cart' , component:CartComponent},
  { path :'checkout' , component:CheckoutComponent},
  { path :'contact' , component:ContactComponent},


  { path :'**' , component:NotFoundComponent},


];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
