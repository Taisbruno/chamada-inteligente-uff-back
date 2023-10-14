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

    public List<ClassModel> getClassesByUser(String registration, String classCode, String disciplineCode, String semester){
        List<ClassSubscriptionEntity> classesSubscription = classSubRepository.findClassesByStudent(
                registration, classCode, disciplineCode, semester);
        List<ClassModel> classesModels = new ArrayList<>();
        for(ClassSubscriptionEntity classSubEntity: classesSubscription){
            ClassEntity classEntity = classSubEntity.classEntity;
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
