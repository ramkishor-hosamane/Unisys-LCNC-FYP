import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
    ContactComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
