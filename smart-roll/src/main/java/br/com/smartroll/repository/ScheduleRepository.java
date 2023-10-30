package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ScheduleEntity;
import br.com.smartroll.repository.interfaces.IScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Retorna uma lista de agendamentos com horários conflitantes.
     * @param dayOfWeek dia da semana em formato CRON.
     * @param startTime tempo de início.
     * @param endTime tempo de fim.
     * @return lista de agendamentos.
     */
    public List<ScheduleEntity> findConflictingSchedules(int dayOfWeek, Time startTime, Time endTime){
        return scheduleRepository.findConflictingSchedules(dayOfWeek, startTime, endTime);
    }

    /**
     * Exclui um agendamento com base no id.
     * @param idSchedule id do agendamento.
     */
    public void deleteScheduleById(String idSchedule) {
        scheduleRepository.deleteScheduleById(Long.parseLong(idSchedule));
    }

    /**
     * Retorna um agendamento com base no id.
     * @param idSchedule id do agendamento.
     * @return entidade ScheduleEntity.
     */
    public ScheduleEntity getScheduleById(String idSchedule) {
        return scheduleRepository.getScheduleById(Long.parseLong(idSchedule));
    }

    /**
     * Exclui todos os agendamentos associados a uma turma.
     * @param codeClass código da turma.
     */
    public void deleteAllSchedulesByClassCode(String codeClass) {
        scheduleRepository.deleteAllSchedulesByClassCode(codeClass);
    }

    /**
     * Retorna uma lista de agendamentos associados a uma turma.
     * @param codeClass código da turma.
     * @return lista de ScheduleEntity.
     */
    public List<ScheduleEntity> getAllScheduleByCodeClass(String codeClass) {
        return scheduleRepository.getAllScheduleByCodeClass(codeClass);
    }
}
