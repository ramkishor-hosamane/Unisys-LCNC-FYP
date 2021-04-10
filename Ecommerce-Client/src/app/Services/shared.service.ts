import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {


  private bizLock_source = new BehaviorSubject<Object>(
    {
      "user":0,
      "cart":0,
      "product":0,
      "order":0      
    }
  );
  bizLock_observer = this.bizLock_source.asObservable();
  bizLock:any;
  

  private bizVersion_source = new BehaviorSubject<Object>(
    {
      "user":0,
      "cart":0,
      "product":0,
      "order":0      
    }
  );
  bizVersion_observer = this.bizVersion_source.asObservable();
  bizVersion: any; 

  constructor() { }


  updateBizVersion(obj:any)
  {
    this.bizVersion_source.next(obj);
  }

  updateBizLock(obj:any)
  {
    this.bizLock_source.next(obj);
  }
  updateBizVersionandBizLock(bizlock:any,bizversion:any){
    this.updateBizLock(bizlock)
    this.updateBizVersion(bizversion)
  }
}
