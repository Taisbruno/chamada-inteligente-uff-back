package br.com.smartroll.service;

import br.com.smartroll.exception.ClassroomNotFoundException;
import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.exception.UsersNotFoundException;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.RollEntity;
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
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RollRepository rollRepository;

    /**
     * Método para recuperar uma lista de estudantes inscritos com base no código da turma e no semestre.
     *
     * @param classCode Código da turma.
     * @param semester Semestre da turma.
     * @return Uma lista de modelos de estudantes inscritos.
     * @throws UsersNotFoundException Exceção lançada se não forem encontrados estudantes inscritos para o código da turma e semestre fornecidos.
     * @throws ClassroomNotFoundException Exceção lançada se a turma não for encontrada.
     */
    public List<StudentModel> getEnrolledStudentsByClassCode(String classCode, String semester) throws UsersNotFoundException, ClassroomNotFoundException {
        if(classRepository.getClassByCode(classCode) == null){
            throw new ClassroomNotFoundException(classCode);
        }
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

    /**
     * Retorna os alunos inscritos em uma chamada com base no id da chamada.
     * @param idRoll id da chamada.
     * @return uma lista de StudentModel.
     * @throws UsersNotFoundException Lançada quando não foram encontrados usuários inscritos na chamada.
     * @throws RollNotFoundException Lançada quando a chamada não foi encontrada.
     */
    public List<StudentModel> getEnrolledStudentsByRoll(String idRoll) throws UsersNotFoundException, RollNotFoundException {
        long convertedId = Long.parseLong(idRoll);
        if(rollRepository.getRoll(convertedId) == null){
            throw new RollNotFoundException(idRoll);
        }
        List<UserEntity> studentsEntity = userRepository.getEnrolledStudentsByRoll(convertedId);
        if(studentsEntity.isEmpty()){
            throw new UsersNotFoundException(idRoll);
        }
        List<StudentModel> studentsModel = new ArrayList<>();
        for(UserEntity user : studentsEntity){
            if(user.type.equals("student")) {
                StudentModel studentModel = new StudentModel(user.registration, user.name, user.email, user.password);
                studentsModel.add(studentModel);
            }
        }
        return studentsModel;
    }
}
