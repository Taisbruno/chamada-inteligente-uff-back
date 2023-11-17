package br.com.smartroll.service;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.ClassRepository;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PresenceRepository presenceRepository;

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
        return convertEntityToModel(studentsEntity, classCode, semester);
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
        ClassEntity classEntity = classRepository.getClassByCode(classRepository.getClassCodeByRollId(Long.parseLong(idRoll)));
        return convertEntityToModel(studentsEntity, classEntity.classCode, classEntity.semester);
    }

    public List<StudentModel> getFailedStudentsByClassCode(String classCode, String semester) throws ClassroomNotFoundException, UsersNotFoundException, RollsNotFoundException, FailedStudentsNotFoundException {
        if (classRepository.getClassByCode(classCode) == null) {
            throw new ClassroomNotFoundException(classCode);
        }

        List<UserEntity> allStudents = userRepository.getEnrolledUsersByClassCode(classCode, semester);
        if(allStudents.isEmpty()){
            throw new UsersNotFoundException(classCode, semester);
        }
        List<UserEntity> failedStudents = new ArrayList<>();
        int total = classRepository.getTotalByClassCode(classCode);

        for (UserEntity student : allStudents) {
            String studentRegistration = student.registration;

            // Recuperar todas as chamadas para a turma e semestre fornecidos
            List<RollEntity> rolls = rollRepository.getRollsFromClass(classCode, semester);
            if(rolls.isEmpty()){
                throw new RollsNotFoundException(classCode, semester);
            }
            // Contador para presenças válidas
            int validPresences = 0;

            for (RollEntity roll : rolls) {
                // Verificar se o aluno tem uma presença registrada e válida para cada chamada
                if (presenceRepository.isPresent(studentRegistration, roll.id)) {
                    validPresences++;
                }
            }

            // Verificar se o aluno não atende ao critério de presença
            double attendanceRate = ((double) validPresences / rolls.size()) * 100;
            double totalAttendanceRate = ((double) validPresences / total) * 100;
            if (attendanceRate < 75 && totalAttendanceRate < 75) {
                failedStudents.add(student);
            }
        }
        if(failedStudents.isEmpty()){
            throw new FailedStudentsNotFoundException(classCode, semester);
        }
        return convertEntityToModel(failedStudents, classCode, semester);
    }

    private List<StudentModel> convertEntityToModel(List<UserEntity> studentsEntity, String classCode, String semester){
        List<StudentModel> studentsModel = new ArrayList<>();
        for(UserEntity user : studentsEntity){
            if(user.type.equals("student")) {
                StudentModel studentModel = new StudentModel(user.registration, user.name, user.email, user.password);
                calculateFrequencyAndFailed(studentModel, user.registration, classCode, semester);
                studentsModel.add(studentModel);
            }
        }
        return studentsModel;
    }

    private void calculateFrequencyAndFailed(StudentModel studentModel, String studentRegistration, String classCode, String semester) {
        List<RollEntity> rolls = rollRepository.getClosedRollsFromClass(classCode, semester);
        int totalRolls = rolls.size();
        int attendedRolls = 0;

        for (RollEntity roll : rolls) {
            if (presenceRepository.isPresent(studentRegistration, roll.id)) {
                attendedRolls++;
            }
        }

        double frequency = totalRolls > 0 ? (double) attendedRolls / totalRolls * 100 : 0;
        studentModel.frequency = frequency;
        studentModel.failed = frequency < 75;
    }

}
