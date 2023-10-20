package br.com.smartroll.service;

import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.interfaces.IRollRepository;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.view.StopWatchView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;


    public Optional<RollEntity> getRoll(Long id) {
        return rollRepository.getRoll(id);
    }

    public void createRoll(RollModel rollModel){
        RollEntity rollEntity = new RollEntity(rollModel.longitude, rollModel.latitude, rollModel.class_code);
        rollRepository.createRoll(rollEntity);
    }

    public void closeRoll(Long id){
        rollRepository.closeRoll(id);
    }

    public boolean isRollClosed(Long id){
        return rollRepository.isRollClosed(id);
    }

}
