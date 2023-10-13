package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.repository.interfaces.IClassRepository;
import br.com.smartroll.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    @Autowired
    private IUserRepository userRepository;

    public List<UserEntity> getAllStudents() {
        List<UserEntity> users = userRepository.findAll();
        List<UserEntity> students = new ArrayList<>();
        for(UserEntity user : users){
            if(user.type.equals("student")){
                students.add(user);
            }
        }
        return students;
    }

    public List<UserEntity> getAllTeachers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserEntity> teachers = new ArrayList<>();
        for(UserEntity user : users){
            if(user.type.equals("teacher")){
                teachers.add(user);
            }
        }
        return teachers;
    }
}
