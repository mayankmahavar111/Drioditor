import  requests
import json


def postRequest(url,name,email,code):
    data={
        'url':url,
        'name' : name,
        'emailid' : email,
        'code' : code
    }

    print("Hello World")
    try:
        r = requests.post('http://127.0.0.1:8000/recommendation/',data=json.dumps(data))
        print(r.status_code)
    except Exception as e:
        print(e)
    return


postRequest(url="https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N",name='iris',email="mayankmahavar111@gmail.com",code="abcedf")