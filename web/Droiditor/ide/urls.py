from django.urls import include, path
from . import views

urlpatterns = [
    path('',views.main_page,name="main_page"),
    path('ml_recommendation/', views.ml_recommendation_run, name="ml_recommendation"),
    #path('ml_recommendation/', views.ml_recommendation_run, name="ml_recommendation_run"),
    path('ml_template/', views.ml_template, name="ml_template"),
    path('ml_template/run', views.ml_template_run, name="ml_template_run"),
]
