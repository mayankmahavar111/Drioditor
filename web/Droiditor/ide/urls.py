from django.urls import include, path
from . import views

urlpatterns = [
    path('ml_recommendation/', views.ml_recommendation_view),
]
