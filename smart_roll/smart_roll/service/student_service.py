from django.contrib.auth.hashers import make_password
from smart_roll.model.student import Student

class StudentService:

    @staticmethod
    def create_student(name, enrollment_number, username, password):
        hashed_password = make_password(password)
        student = Student.objects.create(
            name=name,
            enrollment_number=enrollment_number,
            username=username,
            password=hashed_password,
        )
        return student
    
    @staticmethod
    def get_student(student_id):
        return Student.objects.get(id=student_id)
    
    @staticmethod
    def get_all_students():
        return Student.objects.all()
    
    @staticmethod
    def update_student(student_id, name=None, enrollment_number=None, username=None, password=None):
        student = Student.objects.get(id=student_id)
        if name:
            student.name = name
        if enrollment_number:
            student.enrollment_number = enrollment_number
        if username:
            student.username = username
        if password:
            student.password = make_password(password)
        student.save()
        return student
    
    @staticmethod
    def delete_student(student_id):
        student = Student.objects.get(id=student_id)
        student.delete()
