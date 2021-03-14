import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
//import { SignupComponent } from './Components/signup/signup.component';
//import { LoginComponent } from './Components/login/login.component';
import { NotFoundComponent } from './Components/not-found/not-found.component';
import { ProductsComponent } from './Components/products/products.component';
import { ProductComponent } from './Components/product/product.component';

const routes: Routes = [
      { path :'' ,redirectTo:'/home',pathMatch:'full'},
      { path :'products/:productType' , component:ProductsComponent},
      { path :'products/:productType/:id' , component:ProductComponent},

      { path :'home' , component:HomeComponent},
      //{ path :'signup' , component:SignupComponent},
      //{ path :'login' , component:LoginComponent},
      { path :'**' , component:NotFoundComponent},

    
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
