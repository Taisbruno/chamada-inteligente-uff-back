from rest_framework import serializers
from smart_roll.model.student import Student

class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = ['id', 'name', 'enrollment_number', 'username', 'password']
