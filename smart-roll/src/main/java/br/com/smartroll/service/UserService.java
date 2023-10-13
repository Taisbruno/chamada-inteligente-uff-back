package br.com.smartroll.service;

import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getUser(String registration) {
        return userRepository.findById(registration);
    }

    public void deleteUser(String registration) {
        userRepository.deleteById(registration);
    }

    public UserEntity updateUser(String registration, UserEntity userEntity) {

        if (userRepository.existsById(registration)) {
            userEntity.registration = registration;
            return userRepository.save(userEntity);
        } else {
        }
        return null;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
