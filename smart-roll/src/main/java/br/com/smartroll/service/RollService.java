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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PresenceService presenceService;

    public ConcurrentHashMap<String, LocalDateTime> activeCalls = new ConcurrentHashMap<>();

    public void startStopWatch(String callId) throws RollNotFoundException {
        if(rollRepository.getRoll(Long.parseLong(callId)).isEmpty()){
            throw new RollNotFoundException(callId);
        }
        startCall(callId);

        new Thread(() -> {
            while (this.activeCalls.containsKey(callId)) {
                Duration duration = Duration.between(this.activeCalls.get(callId), LocalDateTime.now());
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();
                long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

                StopWatchView stopwatch = new StopWatchView(String.format("%02d", hours), String.format("%02d", minutes), String.format("%02d", seconds));

                messagingTemplate.convertAndSend("/topic/time/" + callId, stopwatch.toJson());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
                //System.out.println("Sending message for callId " + callId + ": " + stopwatch.toJson());
            }
        }).start();
    }

    public Optional<RollEntity> getRoll(Long id) {
        return rollRepository.getRoll(id);
    }

    public void createRoll(RollModel rollModel){
        RollEntity rollEntity = new RollEntity(rollModel.longitude, rollModel.latitude, rollModel.class_code);
        rollRepository.createRoll(rollEntity);
    }

    public void startCall(String callId) {
        activeCalls.put(callId, LocalDateTime.now());
    }

    public void endCall(String callId) {
        activeCalls.remove(callId);
        presenceService.clearPresencesForRoll(callId);
    }
}
