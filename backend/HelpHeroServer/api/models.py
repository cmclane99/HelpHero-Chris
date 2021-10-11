from django.db import models

class User (models.Model):

    #user's username
    username = models.CharField(max_length=20, primary_key=True)

    #user's password, will; eventually be encryoted
    password = models.CharField(max_length=20)
    CrytKey = models.CharField(max_length=200)

    #user's first emergency contact info
    EmergencyContactNameOne = models.CharField(max_length=20)
    EmergencyContactRelationOne = models.CharField(max_length=20)
    EmergencyContactPhoneOne = models.CharField(max_length=10)

    #user's second emergency contact info
    EmergencyContactNameTwo = models.CharField(default = '', max_length=20)
    EmergencyContactRelationTwo = models.CharField(default = '', max_length=20)
    EmergencyContactPhoneTwo = models.CharField(default = '', max_length=10)

    #user's third emergency contact info
    EmergencyContactNameThree = models.CharField(default = '', max_length=20)
    EmergencyContactRelationThree = models.CharField(default = '', max_length=20)
    EmergencyContactPhoneThree = models.CharField(default = '', max_length=10)

    def __str__(self):
        return self.username

    