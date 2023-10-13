package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassRepository extends JpaRepository<ClassEntity, Long> {

}
