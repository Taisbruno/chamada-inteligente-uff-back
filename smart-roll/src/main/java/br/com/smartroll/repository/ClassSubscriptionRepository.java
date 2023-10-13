package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import br.com.smartroll.repository.interfaces.IClassSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClassSubscriptionRepository {

    @Autowired
    private IClassSubscriptionRepository classSubscriptionRepository;

    public List<ClassSubscriptionEntity> getAllClassSubscriptions() {
        return classSubscriptionRepository.findAll();
    }

    public List<ClassSubscriptionEntity> getSubscriptionsByCodeAndSemester(long id, String code, String semester) {
        List<ClassSubscriptionEntity> subscriptions = classSubscriptionRepository.findAll();
        List<ClassSubscriptionEntity> selectedSubscriptions = new ArrayList<>();
        for(ClassSubscriptionEntity subscription:  subscriptions){

            if(subscription.semester.equals(semester) && subscription.classCode.equals(code)){
                selectedSubscriptions.add(subscription);
            }
        }
        return selectedSubscriptions;
    }
}
