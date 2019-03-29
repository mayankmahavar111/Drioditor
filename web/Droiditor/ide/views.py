from django.shortcuts import render, redirect
import urllib.request, requests
import json,random



# Create your views here.

def postRequest(url,name,email,code):
    data={
        'url':url,
        'name' : name,
        'emailid' : email,
        'code' : code
    }

    print("Hello World")
    try:
        r = requests.post('http://127.0.0.1:8000/recommendation/',data=data)
        print(r.status_code)
    except Exception as e:
        print(e)
    return

def postTemplate(code,title):
    data={
        'code':code,
        'title':title
    }
    try:
        r=requests.post('http://127.0.0.1:8000/snippets/',data=data)
        print(r.status_code)
    except Exception as e:
        print(e)
    return

def main_page(request):
    template = 'ide/main_page.html'
    return render(request,template)


def ml_recommendation_run(request):
    if request.method == 'POST':
        drive_url=request.POST.get('drive_url')
        dataset_name=request.POST.get('dataset_name')
        email=request.POST.get('email')
        pre_process_code=request.POST.get('pre_process_code')
        print (drive_url+'\n'+dataset_name+'\n'+email)
        postRequest(url=drive_url,name=dataset_name,email=email, code=pre_process_code)
        return redirect('ml_recommendation')
    else:
        template = 'ide/ml_recommendation_form.html'
        return render(request, template)

def ml_template(request):
    if request.method == 'POST':
        drive_url=request.POST.get('drive_url')
        dataset_name=request.POST.get('dataset_name')
        pre_process_code=request.POST.get('pre_process_code')
        pre_process_code=pre_process_code.replace('temp=  downloadDataset(url,name)','temp=  downloadDataset("{}","{}")'.format(drive_url,dataset_name))
        pre_process_code=pre_process_code.replace('data/iris.csv','data/{}.csv'.format(dataset_name))
        print (pre_process_code)
        #postRequest(url=drive_url,name=dataset_name,email=email,code=pre_process_code)
        title =random.randint(0,1000)
        print (title)
        postTemplate(pre_process_code,title)
        #r=request.get(url='http://127.0.0.1:8000/snippets/result/{}'.format(title))
        #data=r.json()
        #print(data)
        data="output"
        return render(request,'ide/ml_template.html',{'drive_url':drive_url,'dataset_name':dataset_name,'pre_process_code':pre_process_code,'data':data})
    else:
        template = 'ide/ml_template.html'
        return render(request, template)

def ml_template_run(request):
    template = 'ide/ml_recommendation_form.html'

