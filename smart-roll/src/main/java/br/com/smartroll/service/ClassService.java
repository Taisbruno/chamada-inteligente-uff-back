package br.com.smartroll.service;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassSubscriptionRepository classSubRepository;

    public List<StudentModel> getEnrolledStudentsByClassId(Long id){
        List<StudentModel> students = new ArrayList<>();
        //classSubRepository.getSubscriptionsByCodeAndSemester()
        return students;
    }

}
