package br.com.smartroll.service;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.ScheduleModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.ScheduleRepository;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.ScheduleEntity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço responsável por manipular operações de agendamento.
 */
@Service
public class ScheduleService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClassRepository classRepository;

    /**
     * Inicializa o serviço e agenda todos os agendamentos existentes.
     */
    @PostConstruct
    public void init() {
        scheduleAllExisting();
    }

    /**
     * Agenda todos os agendamentos existentes no banco de dados.
     */
    private void scheduleAllExisting() {
        List<ScheduleEntity> allSchedules = scheduleRepository.getAllSchedules();
        LocalDateTime now = LocalDateTime.now();

        for (ScheduleEntity schedule : allSchedules) {
            scheduleTask(schedule, now);
        }
    }

    /**
     * Cria um novo agendamento e agenda tarefas com base no novo agendamento.
     *
     * @param scheduleModel o modelo de agendamento a ser criado.
     * @throws InvalidDayOfWeekException se o dia da semana fornecido é inválido.
     * @throws InvalidTimeException se o horário fornecido é inválido.
     * @throws InvalidTimeFormatException se o formato do horário fornecido é inválido.
     * @throws ScheduleConflictException se o agendamento criado conflita com o horário de um agendamento existente.
     * @throws ClassNotFoundException se a turma não foi encontrada.
     */
    public void createSchedule(ScheduleModel scheduleModel) throws InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException, ScheduleConflictException, ClassroomNotFoundException {
        if(classRepository.getClassByCode(scheduleModel.classCode) == null){
            throw new ClassroomNotFoundException(scheduleModel.classCode);
        }
        validateScheduleModel(scheduleModel);
        ScheduleEntity scheduleEntity = new ScheduleEntity(classRepository.getClassByCode(scheduleModel.classCode), scheduleModel.dayOfWeek, Time.valueOf(scheduleModel.startTime), Time.valueOf(scheduleModel.endTime), scheduleModel.longitude, scheduleModel.latitude);
        scheduleRepository.createSchedule(scheduleEntity);

        // Agendar as tarefas com base no novo agendamento
        scheduleTask(scheduleEntity, LocalDateTime.now());
    }

    /**
     * Agenda tarefas com base no agendamento fornecido.
     *
     * @param schedule o agendamento para o qual as tarefas devem ser agendadas.
     * @param now o momento atual.
     */
    private void scheduleTask(ScheduleEntity schedule, LocalDateTime now) {
        LocalDateTime startScheduledDateTime = LocalDateTime.of(LocalDate.now(), schedule.startTime.toLocalTime());
        LocalDateTime endScheduledDateTime = LocalDateTime.of(LocalDate.now(), schedule.endTime.toLocalTime());

        // Se o Roll já deveria ter sido iniciado e ainda não foi fechado, inicie agora
        if (startScheduledDateTime.isBefore(now) && now.isBefore(endScheduledDateTime)) {
            createRollsBasedOnSchedule(schedule);
        }

        // Agende o início do Roll
        if (now.isBefore(startScheduledDateTime)) {
            taskScheduler.schedule(
                    () -> createRollsBasedOnSchedule(schedule),
                    new CronTrigger(convertTimeToCronExpression(schedule.startTime, schedule.dayOfWeek))
            );
        }

        // Agende o fechamento do Roll
        if (now.isBefore(endScheduledDateTime)) {
            taskScheduler.schedule(
                    () -> closeRollBasedOnSchedule(schedule),
                    new CronTrigger(convertTimeToCronExpression(schedule.endTime, schedule.dayOfWeek))
            );
        }
    }

    /**
     * Cria Rolls com base no agendamento fornecido.
     *
     * @param schedule o agendamento com base no qual os Rolls devem ser criados.
     */
    private void createRollsBasedOnSchedule(ScheduleEntity schedule) {
        if (!rollRepository.hasOpenRollsForClass(schedule.classEntity.classCode)) {
            RollEntity rollEntity = new RollEntity(schedule.longitude, schedule.latitude, schedule.classEntity.classCode);
            rollRepository.createRoll(rollEntity);
        }
    }

    /**
     * Fecha Rolls com base no agendamento fornecido.
     *
     * @param schedule o agendamento com base no qual os Rolls devem ser fechados.
     */
    private void closeRollBasedOnSchedule(ScheduleEntity schedule) {
        rollRepository.closeOpenRollByClassCode(schedule.classEntity.classCode);
    }

    /**
     * Converte um horário e dia da semana para uma expressão CRON.
     *
     * @param time o horário a ser convertido.
     * @param dayOfWeek o dia da semana a ser convertido.
     * @return a expressão CRON resultante.
     */
    private String convertTimeToCronExpression(Time time, int dayOfWeek) {
        LocalTime localTime = time.toLocalTime();
        return String.format("0 %d %d ? * %d", localTime.getMinute(), localTime.getHour(), dayOfWeek);
    }

    /**
     * Valida um modelo de agendamento.
     *
     * @param scheduleModel o modelo de agendamento a ser validado.
     * @throws InvalidDayOfWeekException se o dia da semana fornecido é inválido.
     * @throws InvalidTimeException se o horário fornecido é inválido.
     * @throws InvalidTimeFormatException se o formato do horário fornecido é inválido.
     * @throws ScheduleConflictException se houver conflito de intervalo de tempo.
     */
    private void validateScheduleModel(ScheduleModel scheduleModel) throws InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException, ScheduleConflictException {
        // Verificar dia da semana
        if (scheduleModel.dayOfWeek < 0 || scheduleModel.dayOfWeek > 7 || scheduleModel.dayOfWeek == 1) {
            throw new InvalidDayOfWeekException("Invalid day of the week provided. It should be 0, 7 for Sunday or between 2 and 6 for other days.");
        }

        // Verificar consistência dos horários
        if (scheduleModel.startTime != null && scheduleModel.endTime != null) {
            try {
                Time start = Time.valueOf(scheduleModel.startTime);
                Time end = Time.valueOf(scheduleModel.endTime);

                if (start.after(end)) {
                    throw new InvalidTimeException("The start time should be before the end time.");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidTimeFormatException("The provided time format is incorrect. It should be in HH:MM:SS format.");
            }
        } else {
            throw new InvalidTimeException("Start time and end time cannot be null.");
        }

        List<ScheduleEntity> conflictingSchedules = scheduleRepository.findConflictingSchedules(scheduleModel.dayOfWeek, Time.valueOf(scheduleModel.startTime), Time.valueOf(scheduleModel.endTime));

        if (!conflictingSchedules.isEmpty()) {
            throw new ScheduleConflictException("The provided schedule conflicts with an existing schedule.");
        }
    }

    /**
     * Exclui um agendamento com base no id.
     * @param idSchedule id do agendamento.
     * @throws ScheduleNotFoundException lançada quando o agendamento não foi encontrado.
     */
    public void deleteScheduleById(String idSchedule) throws ScheduleNotFoundException {
        if(scheduleRepository.getScheduleById(idSchedule) == null){
            throw new ScheduleNotFoundException(idSchedule);
        }
        scheduleRepository.deleteScheduleById(idSchedule);
    }

    /**
     * Exclui todos os agendamentos associados a uma turma.
     * @param codeClass código da turma.
     * @throws ClassroomNotFoundException lançada quando turma não foi encontrada.
     */
    public void clearAllScheduleByCodeClass(String codeClass) throws ClassroomNotFoundException {
        if(classRepository.getClassByCode(codeClass) == null){
            throw new ClassroomNotFoundException(codeClass);
        }
        scheduleRepository.deleteAllSchedulesByClassCode(codeClass);
    }

    /**
     * Retorna todos os agendamentos associados a uma turma.
     * @param codeClass código da turma.
     * @throws ClassroomNotFoundException lançada quando turma não foi encontrada.
     * @return
     */
    public List<ScheduleModel> getAllScheduleByCodeClass(String codeClass) throws ClassroomNotFoundException, SchedulesNotFoundException {
        if(classRepository.getClassByCode(codeClass) == null){
            throw new ClassroomNotFoundException(codeClass);
        }
        List<ScheduleModel> scheduleModels = new ArrayList<>();
        List<ScheduleEntity> scheduleEntities = scheduleRepository.getAllScheduleByCodeClass(codeClass);
        if(scheduleEntities.isEmpty()){
            throw new SchedulesNotFoundException(codeClass);
        }
        for(ScheduleEntity scheduleEntity : scheduleEntities){
            ScheduleModel scheduleModel = new ScheduleModel(scheduleEntity);
            scheduleModels.add(scheduleModel);
        }
        return scheduleModels;
    }
}
