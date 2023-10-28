package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
