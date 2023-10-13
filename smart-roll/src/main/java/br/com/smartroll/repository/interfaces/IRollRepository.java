package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRollRepository extends JpaRepository<RollEntity, Long> {

}
