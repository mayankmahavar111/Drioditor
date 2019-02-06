from django.shortcuts import render, render_to_response, redirect
import urllib.request
import json



# Create your views here.

def postRequest(url,name,email,code):
    data={
        'url':url,
        'name' : name,
        'emailid' : email,
        'code' : code
    }
    myurl = "http://0.0.0.0:8000/recommendation/"
    req = urllib.request.Request(myurl)
    req.add_header('Content-Type', 'application/json; charset=utf-8')
    jsondata = json.dumps(data)
    jsondataasbytes = jsondata.encode('utf-8')  # needs to be bytes
    req.add_header('Content-Length', len(jsondataasbytes))
    print(jsondataasbytes)
    response = urllib.request.urlopen(req, jsondataasbytes)
    return response


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
        return redirect('ml_recommendation')
    else:
        template = 'ide/ml_recommendation_form.html'
        return render(request, template)

def ml_template(request):
    template = 'ide/ml_template.html'
    return render(request, template)

def ml_template_run(request):
    template = 'ide/ml_recommendation_form.html'
    if request.method == 'POST':
        drive_url=request.POST.get('drive_url')
        dataset_name=request.POST.get('dataset_name')
        email=request.POST.get('email')
        pre_process_code=request.POST.get('pre_process_code')
        request.session['drive_url']=drive_url
        request.session['dataset_name'] =dataset_name
        request.session['email'] =email
        request.session['pre_process_code'] =pre_process_code
        #postRequest(url=drive_url,name=dataset_name,email=email,code=pre_process_code)
        return redirect('ml_template',drive_url="google.com")