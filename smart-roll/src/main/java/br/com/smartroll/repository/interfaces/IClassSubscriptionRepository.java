package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassSubscriptionRepository extends JpaRepository<ClassSubscriptionEntity, Long> {
}
