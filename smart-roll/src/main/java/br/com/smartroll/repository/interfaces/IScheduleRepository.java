package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de Schedule.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade ScheduleEntity.
 */
public interface IScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    /**
     * Consulta agendamentos que possam conflitar com um determinado intervalo de tempo e dia da semana.
     *
     * Um agendamento é considerado em conflito se ele possui o mesmo dia da semana do parâmetro e
     * seu intervalo de tempo sobrepõe ao intervalo de tempo do parâmetro. Existem duas situações
     * possíveis para o conflito:
     * 1. O horário de início do agendamento existente é anterior ou igual ao horário de início do parâmetro
     *    e o horário de término do agendamento existente é posterior ao horário de início do parâmetro.
     * 2. O horário de início do agendamento existente é anterior ao horário de término do parâmetro e
     *    o horário de término do agendamento existente é posterior ou igual ao horário de término do parâmetro.
     *
     * @param dayOfWeek O dia da semana a ser verificado.
     * @param startTime O horário de início a ser verificado.
     * @param endTime O horário de término a ser verificado.
     * @return Lista de agendamentos em conflito com o intervalo de tempo e dia da semana fornecidos.
     */
    @Query("SELECT s FROM ScheduleEntity s WHERE s.dayOfWeek = :dayOfWeek AND ((s.startTime <= :startTime AND s.endTime > :startTime) OR (s.startTime < :endTime AND s.endTime >= :endTime))")
    List<ScheduleEntity> findConflictingSchedules(@Param("dayOfWeek") int dayOfWeek, @Param("startTime") Time startTime, @Param("endTime") Time endTime);
}
