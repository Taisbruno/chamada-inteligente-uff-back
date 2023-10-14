package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import br.com.smartroll.repository.interfaces.IClassSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassSubscriptionRepository {

    @Autowired
    private IClassSubscriptionRepository classSubscriptionRepository;

    public List<ClassSubscriptionEntity> getAllClassSubscriptions() {
        return classSubscriptionRepository.findAll();
    }

    public List<ClassSubscriptionEntity> getSubscriptionsByClassCodeAndSemester(
            String disciplineCode, String classCode, String semester) {
        return classSubscriptionRepository.findClassesByDisciplineCodeAndClassCodeAndSemester(
                classCode, disciplineCode, semester);
    }

    public List<ClassSubscriptionEntity> findClassesByStudent(String studentRegistration, String classCode, String disciplineCode, String semester){
        return classSubscriptionRepository.findClassesByStudent(studentRegistration, classCode, disciplineCode, semester);
    }

    /**
     *
     public List<ClassSubscriptionEntity> getSubscriptionsByClassCodeAndSemester(String disciplineCode, String classCode, String semester) {
     List<ClassSubscriptionEntity> subscriptions = classSubscriptionRepository.findAll();
     List<ClassSubscriptionEntity> selectedSubscriptions = new ArrayList<>();
     for(ClassSubscriptionEntity subscription:  subscriptions){

     if(subscription.semester.equals(semester) && subscription.classCode.equals(classCode) && subscription.disciplineCode.equals(disciplineCode)){
     selectedSubscriptions.add(subscription);
     }
     }
     return selectedSubscriptions;
     }
     */


}
