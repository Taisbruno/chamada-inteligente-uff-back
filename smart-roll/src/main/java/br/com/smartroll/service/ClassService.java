package br.com.smartroll.service;

import br.com.smartroll.exception.ClassesNotFoundException;
import br.com.smartroll.model.ClassModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    private ClassSubscriptionRepository classSubRepository;

    /**
     * Recupera as classes com base no número de registro de um usuário.
     *
     * @param registration O número de registro do usuário.
     * @return Uma lista de ClassModel representando as classes associadas ao usuário.
     * @throws ClassesNotFoundException Se nenhuma classe for encontrada para o registro fornecido.
     */
    public List<ClassModel> getClassesByUser(String registration) throws ClassesNotFoundException {
        List<ClassEntity> classes = classRepository.findClassesByUserRegistration(registration);
        if(classes.isEmpty()){
            throw new ClassesNotFoundException(registration);
        }
        return convertEntityToModelList(classes);
    }

    private List<ClassModel> convertEntityToModelList(List<ClassEntity> classes){

        List<ClassModel> classesModels = new ArrayList<>();
        for(ClassEntity classEntity: classes){
            ClassModel classModel = new ClassModel(
                    classEntity.classCode,
                    classEntity.disciplineCode,
                    classEntity.discipline,
                    classEntity.teacher,
                    classEntity.semester,
                    classEntity.total
            );
            classesModels.add(classModel);
        }
        return classesModels;
    }
}
