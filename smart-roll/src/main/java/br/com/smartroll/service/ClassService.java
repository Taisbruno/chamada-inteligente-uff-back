package br.com.smartroll.service;

import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public ClassEntity createClass(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    public Optional<ClassEntity> getClass(Long id) {
        return classRepository.findById(id);
    }

    public Iterable<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public ClassEntity updateClass(Long id, ClassEntity classEntity) {
        if (classRepository.existsById(id)) {
            classEntity.id = id;
            return classRepository.save(classEntity);
        } else {
            // Manipulação de erro, lançamento de exceção, etc.
        }
        return null; // ou lançar uma exceção, ou usar Optional<ClassEntity>
    }
}
