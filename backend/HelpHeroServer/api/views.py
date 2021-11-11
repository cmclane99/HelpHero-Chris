from django.shortcuts import render
from django.http import JsonResponse, HttpResponse

from rest_framework.decorators import api_view
from rest_framework.response import Response 
from .serializers import UserSerializer, LoginSerializer
from rest_framework import status
from passlib.hash import pbkdf2_sha256
from .models import User


@api_view(['GET'])
def apiOverview(request):

    api_urls = {
        'User_list' : '/user-list/',
        'Create_User': '/create-user/',
        'Get_User' : '/user-detail/<str:pk>/',
        'User_login': '/login/',
        'Delete_User':'delete-user/<str:pk>',
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

    serializer = UserSerializer(data=request.data) 

    # If JSON data is vaild, extract username and password
    if serializer.is_valid():
        new_username = serializer.validated_data['username']
        new_password = serializer.validated_data['password']

        try:
            User.objects.get(username=new_username)
        except User.DoesNotExist:
            # Encrypt password and store in database
            # Rounds = hashing strength (default = 5,000)
            # Salt = randomized seed 
            encrypt_pw = pbkdf2_sha256.encrypt(new_password, rounds=12000, salt_size=32)
            serializer.validated_data['password'] = encrypt_pw

            # Save new user to database
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

    # Username already exists, so throw error message
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def loginUser(request):

    # Data from user is temporarily stored in UserLogin table 
    loginSerializer = LoginSerializer(data=request.data)

    if loginSerializer.is_valid():
        # Store username and password entered by user
        entered_username = loginSerializer.validated_data['username']
        entered_password = loginSerializer.validated_data['password']

        # Check if user exists in database
        try :
            User.objects.get(username=entered_username)
        except User.DoesNotExist:
            return Response(loginSerializer.data)

        # Verify password by calling verify_password in models.py
        user = User.objects.get(username=entered_username)
        verify = user.verify_password(entered_password)

        # If true, set 'password_verified' field to true and return serialized data 
        if verify:
            loginSerializer.validated_data['password_verified'] = True
            return Response(loginSerializer.data) 

    # Else, set 'password_verified' field to false and return serialized data 
    loginSerializer.validated_data['password_verified'] = False
    return Response(loginSerializer.data)


@api_view(['POST'])
def DeleteUser(request, pk):
     user = User.objects.get(username=pk)
     user.delete()
     return Response("Item Successfully Deleted!")
   

