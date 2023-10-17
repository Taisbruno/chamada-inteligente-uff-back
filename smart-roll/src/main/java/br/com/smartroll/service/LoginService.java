package br.com.smartroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity authenticateUser(String cpf, String password) {
        UserEntity user = userRepository.getUserByCpf(cpf);

        if (user != null) {
            if (user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }
}
