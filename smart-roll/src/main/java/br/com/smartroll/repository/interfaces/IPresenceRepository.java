package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPresenceRepository extends JpaRepository<PresenceEntity, Long> {
    // Aqui você pode adicionar consultas personalizadas, se necessário.
}
