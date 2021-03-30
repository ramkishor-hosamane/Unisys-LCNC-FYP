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
tables = ['Address','UserAddress','UserLogin','Product','Category','ProductCategoryMember',
          'ShoppingCartItem','ShoppingCart','OrderHeader','OrderItem']
#tables = ['Address','UserAddress']
for table in tables:
  try:        
    data = load_json_file(table+'.json',table)
    for row in data:
      resp = requests.request("PUT",URL,auth=(username, password),json=row)
      print(resp.text)
    print()
    print()   
    print(table," done ...")
    print()
    print()   
    
  except Exception as e:
    print("Something Went Wrong ...",e)