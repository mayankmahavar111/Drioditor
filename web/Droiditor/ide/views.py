from django.shortcuts import render, render_to_response, redirect

# Create your views here.

def main_page(request):
    template = 'ide/main_page.html'
    return render(request,template)


def ml_recommendation_view(request):
    template = 'ide/ml_recommendation_form.html'
    return render(request, template)

def ml_recommendation_run(request):
    if request.method == 'POST':
        drive_url=request.POST.get('drive_url')
        dataset_name=request.POST.get('dataset_name')
        email=request.POST.get('email')
        pre_process_code=request.POST.get('pre_process_code')
        print (drive_url+'\n'+dataset_name+'\n'+email)
        return redirect('ml_recommendation')

def ml_template(request):
    template = 'ide/ml_template.html'
    return render(request, template)

def ml_template_run(request):
    if request.method == 'POST':
        drive_url=request.POST.get('drive_url')
        dataset_name=request.POST.get('dataset_name')
        email=request.POST.get('email')
        pre_process_code=request.POST.get('pre_process_code')
        print (drive_url+'\n'+dataset_name+'\n'+email+'\n'+pre_process_code)
        return redirect('ml_template')