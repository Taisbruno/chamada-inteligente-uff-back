package br.com.smartroll.service;

import br.com.smartroll.exception.UsersNotFoundException;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassSubscriptionRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço responsável pelas operações relacionadas aos usuários.
 * Esta classe oferece métodos para gerenciar e recuperar informações de usuários e estudantes.
 */
@Service
public class UserService {
    @Autowired
    private ClassSubscriptionRepository classSubRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Método para recuperar uma lista de estudantes inscritos com base no código da turma e no semestre.
     *
     * @param classCode Código da turma.
     * @param semester Semestre da turma.
     * @return Uma lista de modelos de estudantes inscritos.
     * @throws UsersNotFoundException Exceção lançada se não forem encontrados estudantes inscritos para o código da turma e semestre fornecidos.
     */
    public List<StudentModel> getEnrolledStudentsByClassCode(String classCode, String semester) throws UsersNotFoundException {
        List<UserEntity> studentsEntity = userRepository.getEnrolledUsersByClassCode(classCode, semester);
        if(studentsEntity.isEmpty()){
            throw new UsersNotFoundException(classCode, semester);
        }
        List<StudentModel> studentsModel = new ArrayList<>();
        for(UserEntity userEntity : studentsEntity){
            if(userEntity.type.equals("student")){
                StudentModel studentModel = new StudentModel(userEntity.registration, userEntity.name, userEntity.email, userEntity.password);
                studentsModel.add(studentModel);
            }
        }
        return studentsModel;
    }
}
