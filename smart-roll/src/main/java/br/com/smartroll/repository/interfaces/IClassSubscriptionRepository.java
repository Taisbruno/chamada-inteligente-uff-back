package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassSubscriptionRepository extends JpaRepository<ClassSubscriptionEntity, Long> {
}
