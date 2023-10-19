package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.interfaces.IRollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RollRepository {
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
