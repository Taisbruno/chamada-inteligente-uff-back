package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    ClassSubscriptionRepository classSubRepository;

    public List<UserEntity> getEnrolledUsersByClassCode(String classCode, String semester) {
        return userRepository.findUsersByClassCodeAndSemester(classCode, semester);
    }

    public UserEntity getUserByCpf(String cpf) {
        return userRepository.findUserByCpf(cpf);
    }

}
