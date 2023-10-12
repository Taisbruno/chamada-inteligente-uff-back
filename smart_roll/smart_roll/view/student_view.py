from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

from smart_roll.model.student import Student
from smart_roll.serializer.student_serializer import StudentSerializer
from smart_roll.service.student_service import StudentService

@api_view(['GET', 'POST'])
def student_list_create_view(request):
    if request.method == 'GET':
        students = StudentService.get_all_students()
        serializer = StudentSerializer(students, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
    
    elif request.method == 'POST':
        serializer = StudentSerializer(data=request.data)
        if serializer.is_valid():
            StudentService.create_student(
                name=serializer.validated_data['name'],
                enrollment_number=serializer.validated_data['enrollment_number'],
                username=serializer.validated_data['username'],
                password=serializer.validated_data['password'],
            )
            return Response(serializer.validated_data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'PUT', 'DELETE'])
def student_detail_view(request, student_id):
    try:
        student = StudentService.get_student(student_id)
    except Student.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    
    if request.method == 'GET':
        serializer = StudentSerializer(student)
        return Response(serializer.data, status=status.HTTP_200_OK)
    
    elif request.method == 'PUT':
        serializer = StudentSerializer(student, data=request.data)
        if serializer.is_valid():
            StudentService.update_student(
                student_id,
                name=serializer.validated_data.get('name', None),
                enrollment_number=serializer.validated_data.get('enrollment_number', None),
                username=serializer.validated_data.get('username', None),
                password=serializer.validated_data.get('password', None),
            )
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    
    elif request.method == 'DELETE':
        StudentService.delete_student(student_id)
        return Response(status=status.HTTP_204_NO_CONTENT)
