import { HttpHeaders } from "@angular/common/http";
import { SessionStorageService } from "ngx-webstorage";

export class Utils {
  constructor(private session_st:SessionStorageService){}
    public static log(msg:string){
        console.log(msg);
    }


    public static getCurrentDateTime():string{

      var cur_date_time = new Date();
      var cur_timestamp = cur_date_time.toString()
  
      // console.log(cur_date_time);
      // console.log(cur_date_time.getDate());
      // console.log(cur_date_time.getDay());
      // console.log(cur_date_time.getMonth());
      var month = cur_timestamp.substring(4,7)
      var year = cur_timestamp.substring(11,15)
      var day = cur_timestamp.substring(8,10)
      var timestamp = cur_timestamp.substring(16,21)
      // console.log("day is"+day)
      // console.log("month is"+month)
      // console.log("year is"+year)
      // console.log("timestamp is"+timestamp)
      var result = day+"-"+month+"-"+year+" "+timestamp;
      console.log("Result is ",result)
      return result;
    }

    public static makeJsonObject(obj:any){
        let data:any={};
        for (const key in obj) {
          if (Object.prototype.hasOwnProperty.call(obj, key)) {
            const element = obj[key];
            // console.log(element)
            // console.log(typeof(element))
            if(typeof(element)=="object" && element!=null)
            {
            //   console.log(key,element,typeof(element))
            //   console.log("Yes object")
              data[key] = this.makeJsonObject(element)
          }
            else{
              data[key] = element;

            }
            
          }
        }

        return data
      }

      public static scrollUp(){
        const scrollToTop = () => {
          const c = document.documentElement.scrollTop || document.body.scrollTop;
          if (c > 0) {
            window.requestAnimationFrame(scrollToTop);
            window.scrollTo(0, c - c / 1.5);
          }
        };
        scrollToTop();      }




        public static getTokenHeader(token:string){
          
            var tokenStr = 'Bearer '+token ;
            var OAuthhttpHeaders = new HttpHeaders().set('Authorization', tokenStr);
            return OAuthhttpHeaders;

        }
    }