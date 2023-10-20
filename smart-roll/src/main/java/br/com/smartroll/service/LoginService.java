package br.com.smartroll.service;

import br.com.smartroll.exception.IncorrectCredentialException;
import br.com.smartroll.exception.UserUnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;

/**
 * Classe de serviço responsável pelas operações de autenticação de usuário.
 * Esta classe oferece métodos para autenticar um usuário com base em suas credenciais.
 */
@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Autentica um usuário com base no CPF e senha fornecidos.
     *
     * @param cpf CPF do usuário a ser autenticado.
     * @param password Senha do usuário a ser autenticado.
     * @return Uma entidade UserEntity correspondente ao usuário autenticado.
     * @throws UserUnauthorizedException Se o usuário com o CPF fornecido não for encontrado.
     * @throws IncorrectCredentialException Se a senha fornecida não corresponder à senha armazenada.
     */
    public UserEntity authenticateUser(String cpf, String password) throws UserUnauthorizedException, IncorrectCredentialException {
        UserEntity user = userRepository.getUserByCpf(cpf);

        if (user != null) {
            if (user.password.equals(password)) {
                return user;
            }else{
                throw new IncorrectCredentialException();
            }
        }else {
            throw new UserUnauthorizedException();
        }
    }
}
