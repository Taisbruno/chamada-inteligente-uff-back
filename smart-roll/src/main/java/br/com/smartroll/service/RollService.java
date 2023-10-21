package br.com.smartroll.service;

import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;


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

    public void closeRoll(Long id) throws RollNotFoundException {
        if(rollRepository.getRoll(id) == null){
            throw new RollNotFoundException(String.valueOf(id));
        }
        rollRepository.closeRoll(id);
    }

    public boolean isRollClosed(Long id){
        return rollRepository.isRollClosed(id);
    }

}
