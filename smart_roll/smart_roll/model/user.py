from django.db import models

class User(models.Model):
    name = models.CharField(max_length=100)
    enrollment_number = models.CharField(max_length=15, unique=True)
    username = models.CharField(max_length=30, unique=True)
    password = models.CharField(max_length=128)  # Store hashed value

    def __str__(self):
        return self.name
