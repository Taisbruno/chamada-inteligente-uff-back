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

}
