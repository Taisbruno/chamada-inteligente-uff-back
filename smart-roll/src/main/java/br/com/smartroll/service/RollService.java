package br.com.smartroll.service;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;


    public RollModel getRoll(Long id) throws RollNotFoundException {
        RollEntity rollEntity = rollRepository.getRoll(id);
        if(rollEntity == null){
            throw new RollNotFoundException(String.valueOf(id));
        }
        return new RollModel(rollEntity);
    }

    public void createRoll(RollModel rollModel){
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
            rollModels.add(rollModel);
        }
        return rollModels;
    }

    public List<RollModel> getHistoricRollsFromClass(String classCode, String semester) throws RollsNotFoundException {
        List<RollEntity> rollsEntity = rollRepository.getRollsFromClass(classCode, semester);

        if(rollsEntity.isEmpty()){
            throw new RollsNotFoundException(classCode, semester);
        }
        List<RollModel> rollModels = new ArrayList<>();
        for(RollEntity rollEntity : rollsEntity){
            RollModel rollModel = new RollModel(rollEntity);
            for(PresenceEntity presenceEntity : rollEntity.presences){
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
