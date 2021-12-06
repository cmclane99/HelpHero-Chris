from django.shortcuts import render
from django.http import JsonResponse, HttpResponse

from rest_framework.decorators import api_view
from rest_framework.response import Response 
from .serializers import UserSerializer, LoginSerializer, UpdateUserSerializer, TaskSerializer
from rest_framework import status
from passlib.hash import pbkdf2_sha256
from .models import User, UserLogin, TaskList


@api_view(['GET'])
def apiOverview(request):

    api_urls = {
        'User_list': '/user-list/',
        'Create_User': '/create-user/',
        'Get_User': '/user-detail/<str:pk>/',
        'User_login': '/login/',
        'Edit_contacts': '/edit-contacts/<str:pk>/',
        'Delete_User': 'delete-user/<str:pk>',
        'Change_Username': 'change-username/<str:pk>',
        'Create_Task': '/create-task/<str:pk>/',
        'Get_Tasks': '/get-tasks/<str:pk>/',
        'Delete_Task': '/delete-task/<str:pk_id>/'
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
            user = User.objects.get(username=entered_username)
        except User.DoesNotExist:
            return Response(loginSerializer.data)

        # Verify password by calling verify_password in models.py
        verify = user.verify_password(entered_password)

        # If true, set 'password_verified' field to true and return serialized data 
        if verify:
            loginSerializer.validated_data['password_verified'] = True
            return Response(loginSerializer.data) 

    # Else, set 'password_verified' field to false and return serialized data 
    loginSerializer.validated_data['password_verified'] = False

    return Response(loginSerializer.data) 

@api_view(['PUT'])
def updateContacts(request, pk):
    
    try:
        user = User.objects.get(username=pk)
    except User.DoesNotExist:
        return Response({'message: User does not exist'})
    
    user_serializer = UpdateUserSerializer(user, data=request.data)

    if user_serializer.is_valid():
        user_serializer.save()
        return Response(user_serializer.data)
    return Response(user_serializer.errors)

@api_view(['POST'])
def DeleteUser(request, pk):
    # Find and delete user
     user = User.objects.get(username=pk)
     user.delete()

     # Find and delete all tasks associated with deleted user
     tasks = TaskList.objects.filter(task_creator=pk)
     tasks.delete()
     return Response({'message: User successfully deleted'})

@api_view(['POST'])
def ChangeUsername(request, pk):
    user = User.objects.get(username=pk)
    serializer2 = UserSerializer(data=request.data)
    if serializer2.is_valid():
        new_username = serializer2.validated_data['username']
        try:
            User.objects.get(username=new_username)
        except User.DoesNotExist:
            serializer2.validated_data['password'] = user.password

            serializer2.validated_data['EmergencyContactNameOne'] = user.EmergencyContactNameOne
            serializer2.validated_data['EmergencyContactRelationOne'] = user.EmergencyContactRelationOne
            serializer2.validated_data['EmergencyContactPhoneOne'] = user.EmergencyContactPhoneOne
        
            serializer2.validated_data['EmergencyContactNameTwo'] = user.EmergencyContactNameTwo
            serializer2.validated_data['EmergencyContactRelationTwo'] = user.EmergencyContactRelationTwo
            serializer2.validated_data['EmergencyContactPhoneTwo'] = user.EmergencyContactPhoneTwo

            serializer2.validated_data['EmergencyContactNameThree'] = user.EmergencyContactNameThree
            serializer2.validated_data['EmergencyContactRelationThree'] = user.EmergencyContactRelationThree
            serializer2.validated_data['EmergencyContactPhoneThree'] = user.EmergencyContactPhoneThree

            # Update task list with new username
            TaskList.objects.filter(task_creator=pk).update(task_creator=new_username)

            serializer2.save()
            user.delete()

            return Response(serializer2.data, status=status.HTTP_201_CREATED)

     # Username already exists, so throw error message
    return Response(serializer2.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def createTask(request, pk):

    taskSerializer = TaskSerializer(data=request.data)

    if taskSerializer.is_valid():

        try:
            User.objects.get(username=pk)
        except User.DoesNotExist:
            return Response(taskSerializer.errors)

        taskSerializer.save()
        return Response(taskSerializer.data)

    return Response(taskSerializer.errors)

@api_view(['GET'])
def listTasks(request, pk):

    try:
        User.objects.get(username=pk)
    except User.DoesNotExist:
        return Response({'message: User does not exist'})

    tasks = TaskList.objects.filter(task_creator=pk)
    serializer = TaskSerializer(tasks, many=True)
    return Response(serializer.data)

@api_view(['DELETE'])
def deleteTask(request, pk_id):

    # pk_id is a unique task id 
    try:
        task = TaskList.objects.get(id=pk_id)
    except TaskList.DoesNotExist:
        return Response({'message: Task not found'})

    task.delete()

    return Response({'message: Task sucessfully deleted'})




    



