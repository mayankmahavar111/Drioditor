from django.http import HttpResponse
from django.template import loader
from django.shortcuts import render, render_to_response

# Create your views here.

def ml_recommendation_view(request):
    template = 'ide/ml_recommendation_form.html'
    return render(request, template)