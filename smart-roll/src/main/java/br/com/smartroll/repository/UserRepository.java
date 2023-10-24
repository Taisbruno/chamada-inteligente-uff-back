package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório que fornece uma camada de abstração sobre o repositório JPA IUserRepository.
 * Esta classe encapsula o acesso direto ao banco de dados e oferece métodos que facilitam
 * a gestão e manipulação de registros de usuário.
 */
@Repository
public class UserRepository {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Recupera uma lista de usuários matriculados com base no código da classe e semestre.
     *
     * @param classCode código da classe.
     * @param semester semestre de interesse.
     * @return lista de entidades UserEntity que correspondem aos critérios fornecidos.
     */
    public List<UserEntity> getEnrolledUsersByClassCode(String classCode, String semester) {
        return userRepository.findUsersByClassCodeAndSemester(classCode, semester);
    }

    /**
     * Recupera um usuário com base no seu CPF.
     *
     * @param cpf CPF do usuário.
     * @return entidade UserEntity correspondente ao CPF fornecido ou null se não encontrado.
     */
    public UserEntity getUserByCpf(String cpf) {
        return userRepository.findUserByCpf(cpf);
    }

    /**
     * Recupera um usuário com base no seu número de registro.
     *
     * @param registration número de registro do usuário.
     * @return entidade UserEntity correspondente ao número de registro fornecido ou null se não encontrado.
     */
    public UserEntity getUserByRegistration(String registration) {
        return userRepository.findUserByRegistration(registration);
    }

    public List<UserEntity> getEnrolledStudentsByRoll(Long idRoll) {
        return userRepository.findUsersByRoll(idRoll);
    }
}
