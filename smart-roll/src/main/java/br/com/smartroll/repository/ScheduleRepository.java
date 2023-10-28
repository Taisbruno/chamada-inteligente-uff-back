package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ScheduleEntity;
import br.com.smartroll.repository.interfaces.IScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

/**
 * Repositório responsável pelas operações de persistência relacionadas a agendamentos.
 */
@Repository
public class ScheduleRepository {

    @Autowired
    private IScheduleRepository scheduleRepository;

    /**
     * Recupera todos os agendamentos armazenados no banco de dados.
     *
     * @return uma lista de entidades de agendamento
     */
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    /**
     * Cria um novo agendamento no banco de dados.
     *
     * @param scheduleEntity a entidade de agendamento a ser persistida
     */
    public void createSchedule(ScheduleEntity scheduleEntity){
        scheduleRepository.save(scheduleEntity);
    }

    public List<ScheduleEntity> findConflictingSchedules(int dayOfWeek, Time startTime, Time endTime){
        return scheduleRepository.findConflictingSchedules(dayOfWeek, startTime, endTime);
    }
}
