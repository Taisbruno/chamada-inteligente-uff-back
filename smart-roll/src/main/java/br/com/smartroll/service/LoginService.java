package br.com.smartroll.service;

import br.com.smartroll.exception.UserUnauthorizedException;
import br.com.smartroll.view.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity authenticateUser(String cpf, String password) throws UserUnauthorizedException {
        UserEntity user = userRepository.getUserByCpf(cpf);

        if (user != null) {
            if (user.password.equals(password)) {
                return user;
            }
        }else {
            throw new UserUnauthorizedException();
        }

        return null;
    }
}
