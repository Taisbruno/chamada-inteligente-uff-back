package br.com.smartroll.service;

import br.com.smartroll.repository.interfaces.IRollRepository;
import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RollService {
    @Autowired
    private IRollRepository rollRepository;

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
}
