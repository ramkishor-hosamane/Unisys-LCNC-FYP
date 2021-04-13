export class BillAddress {
    public bizId:string=""; 
    public bizModule:string="user";
    public bizDocument:string="UserAddress";
    public bizCustomer:string="unisys";
    public bizDataGroupId=null;
    public userloginid:any={
      "bizModule": "user",
      "bizDocument": "UserLogin",
      "userloginid": "",
      "emailid": "",
      "mobileno": "",
      "gender": "",
      "firstname": "",
      "lastname": "",
      "password": "",
      "confirmpassword": "",
      "isenabled": true,
      "createdstamp": "",
      "updatedstamp": "",
      "bizId": "",
      "bizCustomer": "unisys",
      "bizDataGroupId": null,
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
      "bizVersion": 1,
      "bizLock": "20210410135750085setup"
    };
    public addressid:any= {
        "bizModule": "user",
        "bizDocument": "Address",
        "addressid": "",
        "address1": "",
        "address2": "",
        "city": "",
        "state": "",
        "zipcode": "",
        "country": "",
        "createdstamp": "",
        "updatedstamp": "",
        "bizId": "",
        "bizCustomer": "unisys",
        "bizDataGroupId": null,
        "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
        "bizVersion": 0,
        "bizLock": "20210407150542171setup"
      }
      public sequenceno =  "1";
      public addresstype = "";
      public createdstamp =  "";
      public updatedstamp =  "";


    constructor()
        {

        }


    
}
