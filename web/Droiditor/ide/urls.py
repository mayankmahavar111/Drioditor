from django.urls import include, path
from . import views

urlpatterns = [
    path('ml_recommendation/', views.ml_recommendation_view, name="ml_recommendation"),
    path('ml_recommendation/run', views.ml_recommendation_run, name="ml_recommendation_run"),
]
