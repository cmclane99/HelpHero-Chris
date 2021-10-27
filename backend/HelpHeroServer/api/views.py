from django.shortcuts import render
from django.http import JsonResponse, HttpResponse

from rest_framework.decorators import api_view
from rest_framework.response import Response 
from .serializers import UserSerializer
from rest_framework import status
from passlib.hash import pbkdf2_sha256

# from django.contrib.auth.hashers import check_password

from .models import User

# Create your views here.

@api_view(['GET'])
def apiOverview(request):

    api_urls = {
        'User_list' : '/user-list/',
        'Create_User': '/create-user/',
        'Get_User' : '/user-detail/<str:pk>/',
        'User_login': '/login/',
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

    # Store username and password entered by user
    entered_username = request.data.get('username')
    entered_password = request.data.get('password')

    # Check if user exists in database
    try :
        User.objects.get(username=entered_username)
    except User.DoesNotExist:
        return Response({'message': "Invalid username, try again"}, status=status.HTTP_401_UNAUTHORIZED)

    # Verify password by calling verify_password in models.py
    user = User.objects.get(username=entered_username)
    verify = user.verify_password(entered_password)

    # If true, return Response object containing message field set to True
    if verify:
        return Response({'message': True}, status=status.HTTP_200_OK) 

    # Else, return Response object containing error message
    return Response({'message': "Invalid credentials, try again"}, status=status.HTTP_401_UNAUTHORIZED)

   

