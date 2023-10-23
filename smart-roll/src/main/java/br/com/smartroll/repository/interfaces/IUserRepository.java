package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de User.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade UserEntity.
 */
public interface IUserRepository extends JpaRepository<UserEntity, String> {
    /**
     * Localiza um usuário com base em seu CPF.
     *
     * @param cpf o CPF do usuário a ser localizado.
     * @return a entidade UserEntity correspondente ao CPF fornecido ou null se nenhum usuário for encontrado.
     */
    UserEntity findUserByCpf(String cpf);

    /**
     * Localiza um usuário com base em sua matrícula.
     *
     * @param registration a matrícula do usuário a ser localizada.
     * @return a entidade UserEntity correspondente à matrícula fornecida ou null se nenhum usuário for encontrado.
     */
    UserEntity findUserByRegistration(String registration);

    /**
     * Localiza uma lista de usuários inscritos em uma classe específica e semestre.
     * Esta consulta faz uso de uma junção entre as entidades UserEntity e ClassSubscriptionEntity.
     *
     * @param classCode o código da classe para a qual se deseja buscar os usuários inscritos.
     * @param semester  o semestre de interesse.
     * @return uma lista de entidades UserEntity dos usuários inscritos na classe e semestre fornecidos.
     */
    @Query("SELECT u FROM UserEntity u JOIN ClassSubscriptionEntity cs ON u.registration = cs.userEntity.registration WHERE cs.classEntity.classCode = :classCode AND cs.semester = :semester")
    List<UserEntity> findUsersByClassCodeAndSemester(
            @Param("classCode") String classCode,
            @Param("semester") String semester);


    @Query("SELECT u FROM UserEntity u JOIN ClassSubscriptionEntity cs ON u.registration = cs.userEntity.registration JOIN RollEntity r ON r.classEntity.classCode = cs.classEntity.classCode WHERE r.id = :idRoll")
    List<UserEntity> findUsersByRoll(@Param("idRoll") Long idRoll);
}
