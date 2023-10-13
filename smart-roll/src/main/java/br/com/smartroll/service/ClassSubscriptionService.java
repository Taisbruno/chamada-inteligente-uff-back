package br.com.smartroll.service;

import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassSubscriptionService {
    @Autowired
    private ClassSubscriptionRepository classSubscriptionRepository;

    public ClassSubscriptionEntity subscribeClass(ClassSubscriptionEntity classSubscription) {
        return classSubscriptionRepository.save(classSubscription);
    }

    public Optional<ClassSubscriptionEntity> getClassSubscription(Long id) {
        return classSubscriptionRepository.findById(id);
    }

    public Iterable<ClassSubscriptionEntity> getAllClassSubscriptions() {
        return classSubscriptionRepository.findAll();
    }

    public void unsubscribeClass(Long id) {
        classSubscriptionRepository.deleteById(id);
    }

    public ClassSubscriptionEntity updateSubscription(Long id, ClassSubscriptionEntity classSubscription) {
        if (classSubscriptionRepository.existsById(id)) {
            classSubscription.id = id;
            return classSubscriptionRepository.save(classSubscription);
        } else {
            // Handle error, throw exception, etc.
        }
        return null; // or throw an exception, or use Optional<ClassSubscriptionEntity>
    }
}
