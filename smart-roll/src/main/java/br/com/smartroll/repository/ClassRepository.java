package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.interfaces.IClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ClassRepository {

    @Autowired
    private IClassRepository classRepository;


    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }
}
