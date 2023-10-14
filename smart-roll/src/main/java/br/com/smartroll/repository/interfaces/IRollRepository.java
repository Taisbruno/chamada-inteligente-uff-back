package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRollRepository extends JpaRepository<RollEntity, Long> {

}
