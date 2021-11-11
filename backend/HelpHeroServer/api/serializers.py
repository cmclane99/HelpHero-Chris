from rest_framework import serializers
from .models import User, UserLogin

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields ='__all__'

class LoginSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserLogin
        fields = ['username', 'password', 'password_verified']


