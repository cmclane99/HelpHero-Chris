from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.decorators import api_view
from rest_framework.response import Response 
from .serializers import UserSerializer

from .models import User

# Create your views here.

@api_view(['GET'])
def apiOverview(request):

    api_urls = {
        'User_list' : '/user-list/',
        'Create_User': '/create-user/',
        'Get_User' : '/user-detail/<str:pk>/',
    }
    return Response(api_urls)

@api_view(['GET'])
def listUsers(request):
    users = User.objects.all()
    serializer = UserSerializer(users, many=True)
    return Response(serializer.data)

@api_view(['GET'])
def findUser(request, pk):
    users = User.objects.get(id=pk)
    serializer = UserSerializer(users, many=False)
    return Response(serializer.data)

@api_view(['POST'])
def createUser(request):
    serializer = UserSerializer(data=request.data)
    print(serializer.is_valid())  
    if serializer.is_valid():
        print("1")
        serializer.save()

    return Response(serializer.data)
