package br.com.smartroll.service;

import br.com.smartroll.model.ClassModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassSubscriptionRepository classSubRepository;

    public List<ClassModel> getClassesByUser(String registration){
        List<ClassEntity> classes = classRepository.findClassesByUserRegistration(registration);
        List<ClassModel> classesModels = new ArrayList<>();
        for(ClassEntity classEntity: classes){
            ClassModel classModel = new ClassModel(
                    classEntity.classCode,
                    classEntity.disciplineCode,
                    classEntity.discipline,
                    classEntity.teacher,
                    classEntity.semester,
                    classEntity.total
            );
            classesModels.add(classModel);
        }
        return classesModels;
    }

}
