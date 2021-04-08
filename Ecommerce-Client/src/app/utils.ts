export class Utils {
    public static log(msg:string){
        console.log(msg);
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
        console.log(data)

        return data
      }
}