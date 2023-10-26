package br.com.smartroll.service;

import br.com.smartroll.exception.ClassHasOpenRollException;
import br.com.smartroll.exception.RollClosedException;
import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.exception.RollsNotFoundException;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.repository.interfaces.IClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PresenceRepository presenceRepository;


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

    public void createRoll(RollModel rollModel) throws ClassHasOpenRollException {
        if(rollRepository.hasOpenRollsForClass(rollModel.class_code)){
            throw new ClassHasOpenRollException(rollModel.class_code);
        }
        RollEntity rollEntity = new RollEntity(rollModel.longitude, rollModel.latitude, rollModel.class_code);
        rollRepository.createRoll(rollEntity);
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

    public List<RollModel> getHistoricRollsFromClass(String classCode, String semester) throws RollsNotFoundException {
        List<RollEntity> rollsEntity = rollRepository.getClosedRollsFromClass(classCode, semester);

        if (rollsEntity.isEmpty()) {
            throw new RollsNotFoundException(classCode, semester);
        }

        List<RollModel> rollModels = new ArrayList<>();

        for (RollEntity rollEntity : rollsEntity) {
            RollModel rollModel = new RollModel(rollEntity);
            rollModel.isOpen = rollRepository.isOpen(rollEntity.id);
            rollModel.class_code = classRepository.getClassCodeByRollId(rollEntity.id);
            rollModel.presencePercentage = ((double) rollsEntity.size() / classRepository.getTotalByClassCode(classCode)) * 100;

            // Cálculo da média de tempo de presença
            long totalPresenceTimeInSeconds = 0; // Em segundos
            int numberOfStudentsPresent = 0;

            for (PresenceEntity presenceEntity : rollEntity.presences) {
                if (presenceEntity.isPresent) {
                    try {
                        LocalDateTime entryTime = LocalDateTime.parse(presenceEntity.entryTime);
                        LocalDateTime exitTime = LocalDateTime.parse(presenceEntity.exitTime);
                        Duration duration = Duration.between(entryTime, exitTime);

                        totalPresenceTimeInSeconds += duration.getSeconds();
                        numberOfStudentsPresent++;
                    } catch (DateTimeParseException ex) {
                        System.out.println("OIE " + ex.getErrorIndex() + ex.getParsedString());
                    }
                }
            }

            long averagePresenceTimeInSeconds = numberOfStudentsPresent == 0 ? 0 : totalPresenceTimeInSeconds / numberOfStudentsPresent;

            // Convertendo média de segundos para horas, minutos e segundos
            long hours = averagePresenceTimeInSeconds / 3600;
            long minutes = (averagePresenceTimeInSeconds % 3600) / 60;
            long seconds = averagePresenceTimeInSeconds % 60;
            String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            rollModel.presenceTimeAvarage = formattedTime;

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
            rollModels.add(rollModel);
        }
        return rollModels;
    }

}
