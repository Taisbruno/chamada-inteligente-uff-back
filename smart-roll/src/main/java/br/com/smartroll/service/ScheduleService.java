package br.com.smartroll.service;

import br.com.smartroll.exception.ClassHasOpenRollException;
import br.com.smartroll.exception.InvalidDayOfWeekException;
import br.com.smartroll.exception.InvalidTimeException;
import br.com.smartroll.exception.InvalidTimeFormatException;
import br.com.smartroll.model.ScheduleModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.ScheduleRepository;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.ScheduleEntity;
import br.com.smartroll.repository.interfaces.IRollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @PostConstruct
    public void init() {
        scheduleAllExisting();
    }

    private void scheduleAllExisting() {
        List<ScheduleEntity> allSchedules = scheduleRepository.getAllSchedules();
        LocalDateTime now = LocalDateTime.now();

        for (ScheduleEntity schedule : allSchedules) {
            scheduleTask(schedule, now);
        }
    }

    public void createSchedule(ScheduleModel scheduleModel) throws InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException {
        validateScheduleModel(scheduleModel);
        ScheduleEntity scheduleEntity = new ScheduleEntity(classRepository.getClassByCode(scheduleModel.classCode), scheduleModel.dayOfWeek, Time.valueOf(scheduleModel.startTime), Time.valueOf(scheduleModel.endTime), scheduleModel.longitude, scheduleModel.latitude);
        scheduleRepository.createSchedule(scheduleEntity);

        // Agendar as tarefas com base no novo agendamento
        scheduleTask(scheduleEntity, LocalDateTime.now());
    }

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

    private void createRollsBasedOnSchedule(ScheduleEntity schedule) {
        if (!rollRepository.hasOpenRollsForClass(schedule.classEntity.classCode)) {
            RollEntity rollEntity = new RollEntity(schedule.longitude, schedule.latitude, schedule.classEntity.classCode);
            rollRepository.createRoll(rollEntity);
        }
    }

    private void closeRollBasedOnSchedule(ScheduleEntity schedule) {
        rollRepository.closeOpenRollByClassCode(schedule.classEntity.classCode);
    }

    private String convertTimeToCronExpression(Time time, int dayOfWeek) {
        LocalTime localTime = time.toLocalTime();
        return String.format("0 %d %d ? * %d", localTime.getMinute(), localTime.getHour(), dayOfWeek);
    }

    private void validateScheduleModel(ScheduleModel scheduleModel) throws InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException {
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
    }

}
