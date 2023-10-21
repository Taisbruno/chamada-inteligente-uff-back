package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de presença.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade PresenceEntity.
 */
public interface IPresenceRepository extends JpaRepository<PresenceEntity, Long> {
    // Aqui você pode adicionar consultas personalizadas, se necessário.
}
