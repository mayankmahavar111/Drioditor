
from django.conf.urls import url , include
from rest_framework.urlpatterns import format_suffix_patterns
from snippets import views

urlpatterns = [
    url('snippets/$', views.snippet_list),
    url('snippets/(?P<pk>[0-9]{1,10})/$$', views.snippet_detail),
    url('result/(?P<pk>[0-9]{1,10})/$$', views.result),

]
urlpatterns = format_suffix_patterns(urlpatterns)