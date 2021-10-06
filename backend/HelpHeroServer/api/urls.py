from django.urls import path
from . import views

urlpatterns = [
    path('', views.apiOverview, name="api-overview"),
    path('user-list/', views.listUsers, name="User_list"),
    path('user-detail/<str:pk>', views.findUser, name="Get_User"),
    path('create-user/', views.createUser, name="Create_User"),
    
]