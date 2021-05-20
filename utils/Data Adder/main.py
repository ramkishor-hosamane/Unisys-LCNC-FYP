import requests
import json
from base64 import b64encode

URL = "http://localhost:8080/ecommerce/api/json/insert"
username = "setup"
password = "setup"


def load_json_file(path,expexted_data):
    with open(path) as f:
        data = json.load(f)
        return data[expexted_data]
tables = ['Address','UserLogin','UserAddress','Category','Product','ProductCategoryMember']
#          'ShoppingCartItem','ShoppingCart','OrderHeader','OrderItem']
tables = ['Address','UserLogin','UserAddress','Category','Product','ProductCategoryMember']

PRODUCT_URL = "http://localhost:8080/ecommerce/api/json/product/Product" 
CATEGORY_URL = "http://localhost:8080/ecommerce/api/json/product/Category" 




for table in tables:
  try:        
    data = load_json_file(table+'.json',table)
    for row in data:
      #Specific to Product Category mmeber
      if table=="ProductCategoryMember":
          product_resp = requests.request("GET",PRODUCT_URL+"/"+row['productid']["bizId"],auth=(username, password))
          product_dict = json.loads(product_resp.text) 
          category_resp = requests.request("GET",CATEGORY_URL+"/"+row['categoryid']["bizId"],auth=(username, password))
          category_dict = json.loads(category_resp.text) 
          row["productid"]["bizLock"] = product_dict["bizLock"]
          row["productid"]["bizVersion"] = product_dict["bizVersion"]
          row["categoryid"]["bizLock"] = category_dict["bizLock"]
          row["categoryid"]["bizVersion"] = category_dict["bizVersion"]
          #print(row)
          
      resp = requests.request("PUT",URL,auth=(username, password),json=row)
      print("done")
      result_dict = json.loads(resp.text)
      print(resp.text)
    
    print()
    print()   
    print(table," done ...")
    print()
    print()   

        #for product in result_dict:

  except Exception as e:
    print("Something Went Wrong ...",e)