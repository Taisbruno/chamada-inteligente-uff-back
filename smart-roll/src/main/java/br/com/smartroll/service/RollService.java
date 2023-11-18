package br.com.smartroll.service;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.UserEntity;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Classe de serviço responsável pelas operações relacionadas a chamadas.
 */
@Service
public class RollService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @PostConstruct
    public void init() {
        reScheduleUnfinishedRolls();
    }

    public RollModel getRoll(Long id) throws RollNotFoundException {
        RollEntity rollEntity = rollRepository.getRoll(id);
        if(rollEntity == null){
            throw new RollNotFoundException(String.valueOf(id));
        }
        RollModel rollModel = new RollModel(rollEntity);
        rollModel.class_code = classRepository.getClassCodeByRollId(id);
        rollModel.isOpen = rollRepository.isOpen(id);
        return rollModel;
    }

    public RollModel createRoll(RollModel rollModel) throws ClassHasOpenRollException, ClassroomNotFoundException {
        if(classRepository.getClassByCode(rollModel.class_code) == null){
            throw new ClassroomNotFoundException(rollModel.class_code);
        }else if(rollRepository.hasOpenRollsForClass(rollModel.class_code)){
            throw new ClassHasOpenRollException(rollModel.class_code);
        }
        RollEntity rollEntity = new RollEntity(rollModel.longitude, rollModel.latitude, rollModel.class_code);
        RollEntity createdEntity = rollRepository.createRoll(rollEntity);
        RollModel newRollModel = new RollModel(createdEntity);
        newRollModel.class_code = classRepository.getClassCodeByRollId(Long.parseLong(newRollModel.id));
        return newRollModel;
    }

    /**
     * Agenda um horário para fechar uma chamada aberta automaticamente no mesmo dia.
     *
     * @param rollId       O identificador da chamada a ser aberta.
     * @param closeTime    O horário em que a chamada deve ser fechada.
     * @throws RollNotFoundException se a chamada não for encontrada.
     */
    public void closeRollWithScheduledClose(Long rollId, LocalTime closeTime) throws RollNotFoundException, InvalidTimeException {
        RollEntity rollEntity = rollRepository.getRoll(rollId);
        if (rollEntity == null) {
            throw new RollNotFoundException(String.valueOf(rollId));
        }

        LocalDateTime closeDateTime = LocalDateTime.of(LocalDate.now(), closeTime);
        if (closeDateTime.isBefore(rollEntity.createdAt)) {
            throw new InvalidTimeException("Close time must be in the future.");
        }

        // Salvando o horário de agendamento para fechar a chamada aberta manualmente
        rollEntity.scheduledCloseTime = closeDateTime;
        rollRepository.createRoll(rollEntity); // Salva a entidade com o novo horário programado

        scheduleCloseRoll(rollId, closeTime);
    }

    private void reScheduleUnfinishedRolls() {
        List<RollEntity> rollsToBeClosed = rollRepository.findRollsWithScheduledCloseTime();
        if(!rollsToBeClosed.isEmpty()){
            for (RollEntity roll : rollsToBeClosed) {
                scheduleCloseRoll(roll.id, roll.scheduledCloseTime.toLocalTime());
            }
        }
    }

    private void scheduleCloseRoll(Long rollId, LocalTime closeTime) {
        LocalDateTime closeDateTime = LocalDateTime.of(LocalDate.now(), closeTime);

        // Agenda o fechamento da chamada
        taskScheduler.schedule(
                () -> {
                    try {
                        closeRoll(rollId);
                    } catch (RollNotFoundException | RollClosedException e) {
                        // Aqui você pode querer fazer algum log ou tratamento de erro
                        // dependendo de como você quer lidar com falhas no agendamento
                    }
                },
                Date.from(closeDateTime.atZone(ZoneId.systemDefault()).toInstant())
        );
    }


    public void closeRoll(Long id) throws RollNotFoundException, RollClosedException {
        if(rollRepository.getRoll(id) == null){
            throw new RollNotFoundException(String.valueOf(id));
        }else if(isRollClosed(id)){
            throw new RollClosedException(String.valueOf(id));
        }
        rollRepository.closeRoll(id);
        presenceRepository.markExitTimeForAllPresentInRoll(id);
    }

    public boolean isOpen(Long id){
        return rollRepository.isOpen(id);
    }

    public boolean isRollClosed(Long id){
        return rollRepository.isRollClosed(id);
    }

    public List<RollModel> getRollsFromClass(String classCode, String semester) throws RollsNotFoundException {
        List<RollEntity> rollsEntity = rollRepository.getRollsFromClass(classCode, semester);
        if(rollsEntity.isEmpty()){
            throw new RollsNotFoundException(classCode, semester);
        }
        List<RollModel> rollModels = new ArrayList<>();
        for(RollEntity rollEntity : rollsEntity){
            RollModel rollModel = new RollModel(rollEntity);
            rollModel.isOpen = rollRepository.isOpen(rollEntity.id);
            rollModel.class_code = classRepository.getClassCodeByRollId(rollEntity.id);
            rollModels.add(rollModel);
        }
        return rollModels;
    }

    public List<RollModel> getHistoricRollsFromClass(String classCode, String semester) throws RollsNotFoundException, ClassroomNotFoundException {
        List<RollEntity> rollsEntity = rollRepository.getClosedRollsFromClass(classCode, semester);
        verifyClassAndRollExistence(classCode, rollsEntity, semester);

        List<RollModel> rollModels = new ArrayList<>();

        for (RollEntity rollEntity : rollsEntity) {
            RollModel rollModel = new RollModel(rollEntity);
            rollModel.isOpen = rollRepository.isOpen(rollEntity.id);
            rollModel.class_code = classRepository.getClassCodeByRollId(rollEntity.id);

            rollModel.presenceTimeAvarage = calculateAveragePresenceTime(rollEntity.presences);
            calculateFrequencyAndPresence(rollModel, rollEntity, classCode, rollsEntity);

            rollModels.add(rollModel);
        }
        return rollModels;
    }

    private void verifyClassAndRollExistence(String classCode, List<RollEntity> rollsEntity, String semester) throws ClassroomNotFoundException, RollsNotFoundException {
        if (classRepository.getClassByCode(classCode) == null) {
            throw new ClassroomNotFoundException(classCode);
        } else if (rollsEntity.isEmpty()) {
            throw new RollsNotFoundException(classCode, semester);
        }
    }

    private String calculateAveragePresenceTime(List<PresenceEntity> presences) {
        long totalPresenceTimeInSeconds = 0;
        int numberOfStudentsPresent = 0;

        for (PresenceEntity presence : presences) {
            if (presence.isPresent) {
                try {
                    LocalDateTime entryTime = LocalDateTime.parse(presence.entryTime);
                    LocalDateTime exitTime = LocalDateTime.parse(presence.exitTime);
                    Duration duration = Duration.between(entryTime, exitTime);

                    totalPresenceTimeInSeconds += duration.getSeconds();
                    numberOfStudentsPresent++;
                } catch (DateTimeParseException ex) {
                    System.out.println("Erro de Parse: " + ex.getErrorIndex() + ex.getParsedString());
                }
            }
        }

        if (numberOfStudentsPresent == 0) return "00:00:00";

        long averagePresenceTimeInSeconds = totalPresenceTimeInSeconds / numberOfStudentsPresent;
        return formatTime(averagePresenceTimeInSeconds);
    }

    private String formatTime(long timeInSeconds) {
        long hours = timeInSeconds / 3600;
        long minutes = (timeInSeconds % 3600) / 60;
        long seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    private void calculateFrequencyAndPresence(RollModel rollModel, RollEntity rollEntity, String classCode, List<RollEntity> rollsEntity) {
        for (PresenceEntity presenceEntity : rollEntity.presences) {
            PresenceModel presenceModel = new PresenceModel(presenceEntity);
            UserEntity userEntity = userRepository.getUserByRegistration(presenceEntity.studentRegistration);
            presenceModel.name = userEntity.name;

            long count = rollEntity.presences.stream()
                    .filter(p -> p.studentRegistration.equals(presenceEntity.studentRegistration))
                    .count();
            presenceModel.frequency = ((double) count / rollsEntity.size()) * 100;
            presenceModel.failed = !(presenceModel.frequency > 75);
            rollModel.presences.add(presenceModel);
        }
        rollModel.presencePercentage = ((double) rollModel.presences.size() / classRepository.getTotalStudentsByClassCode(classCode)) * 100;
    }


}
