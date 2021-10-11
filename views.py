from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.decorators import api_view
from rest_framework.response import Response 
from .serializers import UserSerializer
from rest_framework import status
from cryptography.fernet import Fernet

from django.contrib.auth.hashers import check_password

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
    users = User.objects.get(username=pk)
    serializer = UserSerializer(users, many=False)
    return Response(serializer.data)

@api_view(['POST'])
def createUser(request):
    key = Fernet.generate_key()
    crypter = Fernet(key)

    serializer = UserSerializer(data=request.data) 

    # If JSON data is vaild, extract username and password
    if serializer.is_valid():
        new_username = serializer.validated_data['username']
        new_password = serializer.validated_data['password']

        try:
            User.objects.get(username=new_username)
        except User.DoesNotExist:
            # Encrypt password and store in database
            encrypt_pw = crypter.encrypt(new_password.encode('utf_8'))
            serializer.validated_data['CrytKey'] = encrypt_pw

            # Save new user to database
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

    # Username already exists, so throw error message
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

   

