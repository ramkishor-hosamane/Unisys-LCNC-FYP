import requests
import json
from base64 import b64encode

username = "setup"
password = "setup"

true="true"
module = "order"
documents = ["OrderItem","OrderHeader"]
DELETE_URL = "http://localhost:8080/ecommerce/rest/json/delete"

for document in documents:
  GET_URL = "http://localhost:8080/ecommerce/rest/json/"+module+"/"+document
  doc_resp = requests.request("GET",GET_URL,auth=(username, password))  
  doc_dict = json.loads(doc_resp.text)
  for data in doc_dict:
    requests.request("DELETE",DELETE_URL,auth=(username, password),json=data)
    print("Deleted")



# for table in tables:
#   try:        
#     data = load_json_file(table+'.json',table)
#     for row in data:

#       resp = requests.request("PUT",URL,auth=(username, password),json=row)
#       print("done")
#       result_dict = json.loads(resp.text)
#       print(resp.text)

#     print()
#     print()   
#     print(table," done ...")
#     print()
#     print()   
    
#   except Exception as e:
#     print("Something Went Wrong ...",e)