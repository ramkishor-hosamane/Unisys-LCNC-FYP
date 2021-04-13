import requests
import json
from base64 import b64encode

URL = "http://localhost:8080/ecommerce/rest/json/insert"
username = "setup"
password = "setup"


def load_json_file(path,expexted_data):
    with open(path) as f:
        data = json.load(f)
        return data[expexted_data]
tables = ['Address','UserLogin','UserAddress','Category','Product','ProductCategoryMember']
#          'ShoppingCartItem','ShoppingCart','OrderHeader','OrderItem']
tables = ['ProductCategoryMember']

product_bizLock ="20210413214253766setup" 
product_bizVersion = 0

category_bizLock ="20210413214253766setup" 
category_bizVersion = 0

for table in tables:
  try:        
    data = load_json_file(table+'.json',table)
    for row in data:
      #Specific to Product Category mmeber
      print("Came here")
      if table=="ProductCategoryMember":
        row["categoryid"]["bizVersion"] = category_bizVersion
        row["categoryid"]["bizLock"] = category_bizLock

        row["productid"]["bizVersion"] = product_bizVersion
        row["productid"]["bizLock"] = product_bizLock
        #print("Came here",row)

      resp = requests.request("PUT",URL,auth=(username, password),json=row)
      print("done")
      result_dict = json.loads(resp.text)
      print(resp.text)
      # product_bizLock = result_dict["productid"]["bizLock"]
      # product_bizVersion = int(result_dict["productid"]["bizVersion"])
      # category_bizVersion = result_dict["categoryid"]["bizLock"]
      # category_bizLock = int(result_dict["categoryid"]["bizVersion"])
    
    print()
    print()   
    print(table," done ...")
    print()
    print()   
    
  except Exception as e:
    print("Something Went Wrong ...",e)