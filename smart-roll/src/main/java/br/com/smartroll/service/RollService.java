package br.com.smartroll.service;

import br.com.smartroll.repository.interfaces.IRollRepository;
import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RollService {

    @Autowired
    private IRollRepository rollRepository;

    public ConcurrentHashMap<String, LocalDateTime> activeCalls = new ConcurrentHashMap<>();

    public RollEntity createRoll(RollEntity rollEntity) {
        return rollRepository.save(rollEntity);
    }

    public Optional<RollEntity> getRoll(Long id) {
        return rollRepository.findById(id);
    }

    public void deleteRoll(Long id) {
        rollRepository.deleteById(id);
    }

    public RollEntity updateRoll(Long id, RollEntity rollEntity) {
        if (rollRepository.existsById(id)) {
            rollEntity.id = id;
            return rollRepository.save(rollEntity);
        } else {
        }
        return null;
    }

    public void startCall(String callId) {
        activeCalls.put(callId, LocalDateTime.now());
    }

    public void endCall(String callId) {
        activeCalls.remove(callId);
    }
}
