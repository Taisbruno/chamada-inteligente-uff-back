package br.com.smartroll.service;

import br.com.smartroll.repository.interfaces.IPresenceRepository;
import br.com.smartroll.repository.entity.PresenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PresenceService {
    @Autowired
    private IPresenceRepository presenceRepository;

    public PresenceEntity createPresence(PresenceEntity presenceEntity) {
        return presenceRepository.save(presenceEntity);
    }

    public Optional<PresenceEntity> getPresence(Long id) {
        return presenceRepository.findById(id);
    }

    public void deletePresence(Long id) {
        presenceRepository.deleteById(id);
    }

    public PresenceEntity updatePresence(Long id, PresenceEntity presenceEntity) {
        if (presenceRepository.existsById(id)) {
            presenceEntity.id = id;
            return presenceRepository.save(presenceEntity);
        } else {
            // Manipulação de erro, lançamento de exceção, etc.
        }
        return null; // ou lançar uma exceção, ou usar Optional<PresenceEntity>
    }
}
