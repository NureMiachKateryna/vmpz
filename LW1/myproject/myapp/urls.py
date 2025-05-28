from django.urls import path
from .views import homepage, category_list

urlpatterns = [
    path('', homepage, name='home'),                 
    path('categories/', category_list, name='category_list'),
]