import { Injectable } from '@angular/core';
import { CanActivate,Router} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './api/auth.service';
import { SharedService } from './shared.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private auth:AuthService,private router:Router,private shared:SharedService){}
  
  canActivate():boolean{
    if(this.auth.loggedIn()){
      return true
    }
    else
    {
      this.shared.showPopup("You need to login",'info')
      this.router.navigate(['/login'])
      return false;
    }
  }
  
}
