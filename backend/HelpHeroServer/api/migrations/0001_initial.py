# Generated by Django 3.2.7 on 2021-10-01 23:35

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(max_length=20)),
                ('password', models.CharField(max_length=20)),
                ('CrytKey', models.CharField(max_length=200)),
                ('EmergencyContactNameOne', models.CharField(max_length=20)),
                ('EmergencyContactRelationOne', models.CharField(max_length=20)),
                ('EmergencyContactPhoneOne', models.CharField(max_length=10)),
                ('EmergencyContactNameTwo', models.CharField(max_length=20)),
                ('EmergencyContactRelationTwo', models.CharField(max_length=20)),
                ('EmergencyContactPhoneTwo', models.CharField(max_length=10)),
                ('EmergencyContactNameThree', models.CharField(max_length=20)),
                ('EmergencyContactRelationThree', models.CharField(max_length=20)),
                ('EmergencyContactPhoneThree', models.CharField(max_length=10)),
            ],
        ),
    ]