package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de classe.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade ClassEntity.
 */
public interface IClassRepository extends JpaRepository<ClassEntity, Long> {

    /**
     * Busca as classes associadas a um usuário com base em seu registro.
     *
     * @param registration O registro do usuário.
     * @return Uma lista de entidades de classes associadas ao registro do usuário.
     */
    @Query("SELECT c FROM ClassEntity c JOIN c.classSubscriptions cs WHERE cs.userEntity.registration = :registration")
    List<ClassEntity> findClassesByUserRegistration(@Param("registration") String registration);

    /**
     * Retorna o total de aulas de uma determinada turma.
     * @param code o código da turma.
     * @return um inteiro com o total de aulas.
     */
    @Query("SELECT c.total FROM ClassEntity c WHERE c.classCode = :code")
    Integer getTotalByClassCode(@Param("code") String code);

    /**
     * Retorna o total de alunos de uma determinada turma.
     * @param code o código da turma.
     * @return um inteiro com o total de alunos.
     */
    @Query("SELECT COUNT(*) FROM ClassSubscriptionEntity c JOIN UserEntity u on c.registration = u.registration WHERE class_code = '3' AND u.type != 'teacher'")
    Integer getTotalStudentsByClassCode(@Param("code") String code);

    /**
     * Retorna o valor da coluna 'classCode' com base no ID da chamada (RollEntity).
     *
     * @param rollId O ID da chamada (RollEntity).
     * @return O valor da coluna 'classCode' ou null se não encontrado.
     */
    @Query("SELECT r.classEntity.classCode FROM RollEntity r WHERE r.id = :rollId")
    String getClassCodeByRollId(@Param("rollId") Long rollId);

    /**
     * Retorna a entidade "Class" com base no código da classe.
     *
     * @param code O código da classe.
     * @return A entidade "Class" correspondente ao código fornecido.
     */
    @Query("SELECT c FROM ClassEntity c WHERE c.classCode = :code")
    ClassEntity getClassByCode(@Param("code") String code);


}
