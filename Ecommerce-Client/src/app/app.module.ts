import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { SignupComponent } from './Components/signup/signup.component';
import { CartComponent } from './Components/cart/cart.component';
import { ShopComponent } from './Components/shop/shop.component';
import { ProductComponent } from './Components/shop/product/product.component';
import { NavbarComponent } from './Components/shared/navbar/navbar.component';
import { FooterComponent } from './Components/shared/footer/footer.component';
import { LoaderComponent } from './Components/shared/loader/loader.component';
import { NotFoundComponent } from './Components/shared/not-found/not-found.component';
import { AlertComponent } from './Components/shared/alert/alert.component';
import { CheckoutComponent } from './Components/checkout/checkout.component';
import { ContactComponent } from './Components/contact/contact.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { OrdersComponent } from './Components/orders/orders.component';
import { OrderComponent } from './Components/orders/order/order.component';
import { ImageComponent } from './Components/shared/image/image.component';
import { WishlistComponent } from './Components/wishlist/wishlist.component';
import { AuthGuard } from './Services/auth.guard';
import { AuthService } from './Services/api/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    CartComponent,
    ShopComponent,
    ProductComponent,
    NavbarComponent,
    FooterComponent,
    LoaderComponent,
    NotFoundComponent,
    AlertComponent,
    CheckoutComponent,
    ContactComponent,
    OrdersComponent,
    OrderComponent,
    ImageComponent,
    WishlistComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [AuthService,AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
