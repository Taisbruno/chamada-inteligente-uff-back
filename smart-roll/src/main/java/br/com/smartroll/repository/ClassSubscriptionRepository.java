package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassSubscriptionRepository extends JpaRepository<ClassSubscriptionEntity, Long> {
}
