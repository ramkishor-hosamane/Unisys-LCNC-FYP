import requests
import json
from base64 import b64encode

URL = "http://localhost:8080/ecommerce/rest/json/insert"


def load_json_file(path,expexted_data):
    with open(path) as f:
        data = json.load(f)
        return data[expexted_data]
all_address = load_json_file('Address.json','Addresses')

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

resp = requests.request("PUT",URL,auth=(username, password),json=j) 
print(resp.text)
