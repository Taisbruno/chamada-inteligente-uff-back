package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<RollEntity, Long> {

}
