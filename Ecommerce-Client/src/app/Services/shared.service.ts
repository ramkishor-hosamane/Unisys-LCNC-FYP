import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

declare function showMessage(msg:any,color:any):any;
@Injectable({
  providedIn: 'root'
})
export class SharedService {

  public alert_colors:any ={'error':'#dc3545','success':'#28a745','info':'#007bff'}

  private bizLock_source = new BehaviorSubject<Object>(
    {
      "user":0,
      "cart":0,
      "product":0,
      "order":0,
      "address":0
      
    }
  );
  bizLock_observer = this.bizLock_source.asObservable();
  bizLocks:any;
  

  private bizVersion_source = new BehaviorSubject<Object>(
    {
      "user":0,
      "cart":0,
      "product":0,
      "order":0,
      "address":0      
    }
  );
  bizVersion_observer = this.bizVersion_source.asObservable();
  bizVersions: any; 

  constructor() { 

    this.bizLock_observer.subscribe(
      data => {
        this.bizLocks = data;
      }
    )
    
    this.bizVersion_observer.subscribe(
      data => {
        this.bizVersions = data;
      }
    )


  }


  updateBizVersion(obj:any)
  {
    this.bizVersion_source.next(obj);
  }

  updateBizLock(obj:any)
  {
    this.bizLock_source.next(obj);
  }
  

  updateBiz(bizLock:any,bizVersion:any,module:string){
    this.bizLocks[module]= bizLock
    this.bizVersions[module]=bizVersion
    this.updateBizLock(this.bizLocks)
    this.updateBizVersion(this.bizVersions)

  }


  showPopup(msg:any,msg_type:any){
    
    showMessage(msg,this.alert_colors[msg_type]);
  }

}
