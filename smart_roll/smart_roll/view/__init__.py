from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

from .student_view import student_view
from .model import Student
from .serializer import StudentSerializer
from .service import StudentService
