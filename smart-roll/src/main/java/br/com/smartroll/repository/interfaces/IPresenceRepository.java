package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de presença.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade PresenceEntity.
 */
public interface IPresenceRepository extends JpaRepository<PresenceEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PresenceEntity p WHERE p.studentRegistration = :studentRegistration AND p.roll.id = :rollId AND p.isPresent = true")
    boolean isPresent(@Param("studentRegistration") String studentRegistration, @Param("rollId") Long rollId);

}
