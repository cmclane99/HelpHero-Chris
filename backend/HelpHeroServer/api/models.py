from django.db import models

# Create your models here.


class User (models.Model):

    #user's username
    username = models.CharField(max_length=20)

    #user's password, will; eventually be encryoted
    password = models.CharField(max_length=20)
    CrytKey = models.CharField(max_length=200)

    #user's first emergency contact info
    EmergencyContactNameOne = models.CharField(max_length=20)
    EmergencyContactRelationOne = models.CharField(max_length=20)
    EmergencyContactPhoneOne = models.CharField(max_length=10)

    #user's second emergency contact info
    EmergencyContactNameTwo = models.CharField(max_length=20)
    EmergencyContactRelationTwo = models.CharField(max_length=20)
    EmergencyContactPhoneTwo = models.CharField(max_length=10)

    #user's third emergency contact info
    EmergencyContactNameThree = models.CharField(max_length=20)
    EmergencyContactRelationThree = models.CharField(max_length=20)
    EmergencyContactPhoneThree = models.CharField(max_length=10)

    def __str__(self):
        return self.username

    