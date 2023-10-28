package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ScheduleEntity;
import br.com.smartroll.repository.interfaces.IScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleRepository {

    @Autowired
    private IScheduleRepository scheduleRepository;

    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public void createSchedule(ScheduleEntity scheduleEntity){
        scheduleRepository.save(scheduleEntity);
    }
}
