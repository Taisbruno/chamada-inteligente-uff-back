package br.com.smartroll.service;

import br.com.smartroll.exception.ClassesNotFoundException;
import br.com.smartroll.exception.UserNotFoundException;
import br.com.smartroll.model.ClassModel;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.RollEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço dedicada a gerenciar operações relacionadas às Classes.
 * Este serviço oferece métodos para recuperar informações de classes com base no registro do usuário e realiza
 * as transformações necessárias das representações de entidade para modelo.
 */
@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Recupera as classes com base no número de registro de um usuário.
     *
     * @param registration O número de registro do usuário.
     * @return Uma lista de ClassModel representando as classes associadas ao usuário.
     * @throws ClassesNotFoundException Se nenhuma classe for encontrada para o registro fornecido.
     * @throws UserNotFoundException Usuário não encontrado
     */
    public List<ClassModel> getClassesByUser(String registration) throws ClassesNotFoundException, UserNotFoundException {
        if(userRepository.getUserByRegistration(registration) == null){
            throw new UserNotFoundException(registration);
        }
        List<ClassEntity> classes = classRepository.findClassesByUserRegistration(registration);
        if(classes.isEmpty()){
            throw new ClassesNotFoundException(registration);
        }
        return convertEntityToModelList(classes);
    }

    private List<ClassModel> convertEntityToModelList(List<ClassEntity> classes){

        List<ClassModel> classesModels = new ArrayList<>();
        ClassModel classModel;
        for(ClassEntity classEntity: classes){
            List<RollEntity> rollEntities = rollRepository.getRollsFromClass(classEntity.classCode, classEntity.semester);
            List<RollModel> rollsModels = new ArrayList<>();
            for(RollEntity rollEntity : rollEntities){
                RollModel rollModel = new RollModel(rollEntity);
                rollModel.class_code = classEntity.classCode;
                rollModel.isOpen = rollRepository.isOpen(rollEntity.id);
                rollsModels.add(rollModel);
            }
            classModel = new ClassModel(
                    classEntity.classCode,
                    classEntity.disciplineCode,
                    classEntity.discipline,
                    classEntity.teacher,
                    classEntity.semester,
                    classEntity.total,
                    rollsModels
            );

            classesModels.add(classModel);
        }
        return classesModels;
    }
}
