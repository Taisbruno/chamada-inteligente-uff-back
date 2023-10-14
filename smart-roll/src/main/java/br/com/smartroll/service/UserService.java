package br.com.smartroll.service;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private ClassSubscriptionRepository classSubRepository;
    @Autowired
    private UserRepository userRepository;

    public List<StudentModel> getEnrolledStudentsByClassCode(String classCode, String semester){
        List<UserEntity> studentsEntity = userRepository.getEnrolledUsersByClassCode(classCode, semester);
        List<StudentModel> studentsModel = new ArrayList<>();
        for(UserEntity userEntity : studentsEntity){
            StudentModel studentModel = new StudentModel(userEntity.registration, userEntity.name, userEntity.email, userEntity.password);
            studentsModel.add(studentModel);
        }
        return studentsModel;
    }
}
