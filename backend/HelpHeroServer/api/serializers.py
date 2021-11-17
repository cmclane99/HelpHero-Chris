from rest_framework import serializers
from .models import User, UserLogin

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields ='__all__'

class UpdateUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'EmergencyContactNameOne', 
            'EmergencyContactRelationOne',
            'EmergencyContactPhoneOne',
            'EmergencyContactNameTwo',
            'EmergencyContactRelationTwo',
            'EmergencyContactPhoneTwo',
            'EmergencyContactNameThree',
            'EmergencyContactRelationThree',
            'EmergencyContactPhoneThree'
        ]

class LoginSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserLogin
        fields = ['username', 'password', 'password_verified']


