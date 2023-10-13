package br.com.smartroll.service;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import br.com.smartroll.repository.interfaces.IUserRepository;
import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ClassSubscriptionRepository classSubRepository;
    @Autowired
    private UserRepository userRepository;

    public List<StudentModel> getEnrolledStudentsByClassCode(String disciplineCode, String classCode, String semester){
        List<StudentModel> studentsModel = new ArrayList<>();
        List<ClassSubscriptionEntity> subscriptions = classSubRepository.getSubscriptionsByClassCodeAndSemester(disciplineCode, classCode, semester);
        List<UserEntity> studentsEntity = userRepository.getAllStudents();
        for(ClassSubscriptionEntity classSubscriptionEntity : subscriptions){
            for(UserEntity userEntity : studentsEntity){
                if(classSubscriptionEntity.studentRegistration.equals(userEntity.registration)){
                    StudentModel studentModel = new StudentModel(userEntity.registration, userEntity.name, userEntity.email, userEntity.password);
                    studentsModel.add(studentModel);
                }
            }
        }
        return studentsModel;
    }
}
