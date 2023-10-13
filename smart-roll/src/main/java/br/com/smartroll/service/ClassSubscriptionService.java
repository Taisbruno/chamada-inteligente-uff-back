package br.com.smartroll.service;

import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassSubscriptionService {
    @Autowired
    private ClassSubscriptionRepository classSubscriptionRepository;

}
