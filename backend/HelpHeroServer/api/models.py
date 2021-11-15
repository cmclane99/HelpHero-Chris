from django.db import models
from passlib.hash import pbkdf2_sha256
from django.utils import timezone

class User (models.Model):

    #user's username
    username = models.CharField(max_length=20, primary_key=True)

    #user's encrypted password
    password = models.CharField(max_length=20)

    #user's first emergency contact info
    EmergencyContactNameOne = models.CharField(max_length=20, blank=True)
    EmergencyContactRelationOne = models.CharField(max_length=20, blank=True)
    EmergencyContactPhoneOne = models.CharField(max_length=10, blank=True)

    #user's second emergency contact info
    EmergencyContactNameTwo = models.CharField(default = '', max_length=20, blank=True)
    EmergencyContactRelationTwo = models.CharField(default = '', max_length=20, blank=True)
    EmergencyContactPhoneTwo = models.CharField(default = '', max_length=10, blank=True)

    #user's third emergency contact info
    EmergencyContactNameThree = models.CharField(default = '', max_length=20, blank=True)
    EmergencyContactRelationThree = models.CharField(default = '', max_length=20, blank=True)
    EmergencyContactPhoneThree = models.CharField(default = '', max_length=10, blank=True)

    # Compare raw password against encrypted password
    def verify_password(self, raw_password):
        return pbkdf2_sha256.verify(raw_password, self.password)

    def __str__(self):
        return self.username # field to be shown when called

class UserLogin (models.Model):

    username = models.CharField(max_length=20, primary_key=True)
    password = models.CharField(max_length=20)
    password_verified = models.BooleanField(default=False)

    def __str__(self):
        return self.username # name to be shown when called

    