import requests
import json
from base64 import b64encode

URL = "http://localhost:8080/ecommerce/rest/json/insert"


def load_json_file(path,expexted_data):
    with open(path) as f:
        data = json.load(f)
        return data[expexted_data]
#all_address = load_json_file('Address.json','Addresses')

username = "setup"
password = "setup"
# userAndPass = b64encode(bytes(username+":"+password), "utf-8").decode("ascii")
# headers = { 'Authorization' : 'Basic {}'.format(userAndPass) }

j = {
    "bizModule": "user",
    "bizDocument": "Address",
    "addressid": "102",
    "address1": "PSN Appartment",
    "address2": "Yellowfield",
    "city": "Chennai",
    "state": "TamilNadu",
    "zipcode": "560098",
    "country": "India",
    "createdstamp": "",
    "updatedstamp": "",
    "bizCustomer": "unisys",
    "bizUserId": "6d8fed1b-21a1-43ba-8709-4d88ed42e2f8"
  }
data={
    "bizModule": "product",
    "bizDocument": "ProductCategoryMember",
    "categoryid": {
      "bizModule": "product",
      "bizDocument": "Category",
      "categoryid": "C1",
      "sequencenum": "1",
      "categoryname": "women",
      "bizId": "68122f2a-83ed-47b3-8ba7-ef5ceec52831",
      "bizCustomer": "unisys",
       
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
      "bizVersion": 0,
      "bizLock": "20210407150543405setup"
    },
    "productid": {
      "bizModule": "product",
      "bizDocument": "Product",
      "productid": "P1",
      "productname": "Purple dress",
      "productprice": "300",
      "productstock": "10",
      "smallimg": "e8819b1a-a024-4d3e-ba2d-6e99d4db94aa",
      "largeimg": "",
      "description": "Purple color shiffon dress",
      "bizId": "0de2e51f-aa62-4815-9754-ceae6e11aea0",
      "bizCustomer": "unisys",
       
      "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba",
      "bizVersion": 0,
      "bizLock": "20210407150543853setup"
    },
    "sequencenum": "1",
    "bizId": "8c0a0f33-ae79-4355-9881-894673b2ef24",
    "bizCustomer": "unisys",
    "bizUserId": "863c5a8c-7901-4e46-971d-99efebac54ba"
  }

resp = requests.request("PUT",URL,auth=(username, password),json=data) 
print(resp.text)
