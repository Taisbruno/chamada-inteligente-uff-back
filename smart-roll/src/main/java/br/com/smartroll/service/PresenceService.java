package br.com.smartroll.service;

import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.model.UserModel;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.view.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ConcurrentHashMap<String, List<StudentView>> activePresencesByRoll = new ConcurrentHashMap<>();

    public void submitPresence(PresenceModel presenceModel) throws RollNotFoundException {
        if(rollRepository.getRoll(Long.parseLong(presenceModel.rollId)).isEmpty()){
            throw new RollNotFoundException(presenceModel.rollId);
        }

        PresenceEntity presenceEntity = new PresenceEntity(presenceModel);
        presenceRepository.createPresence(presenceEntity);
        submitStudent(presenceModel.studentRegistration, presenceModel.rollId);
        notifyFrontEndAboutActivePresences(presenceModel.rollId);
    }

    private void submitStudent(String studentRegistration, String rollId) {
        UserEntity userEntity = userRepository.getUserByRegistration(studentRegistration);
        StudentView student = new StudentView(userEntity.registration, userEntity.name);

        activePresencesByRoll.compute(rollId, (key, students) -> {
            if (students == null) {
                students = new ArrayList<>();
            }
            students.add(student);
            return students;
        });
    }


    /**
     * Método responsável por exibir a lista de alunos inscritos em uma chamada via WebSocket.
     * @param rollId o id da chamada
     */
    private void notifyFrontEndAboutActivePresences(String rollId) {
        List<StudentView> activeStudentsForRoll = activePresencesByRoll.getOrDefault(rollId, new ArrayList<>());
        messagingTemplate.convertAndSend("/topic/presences/" + rollId, activeStudentsForRoll);

        // Adicionado print para depurar o rollId
        System.out.println("Calling notifyFrontEndAboutActivePresences with rollId: " + rollId);
        // Adicionado print para depurar a lista de estudantes ativos para esse rollId
        System.out.println("Number of active students for rollId " + rollId + ": " + activeStudentsForRoll.size());
        for(StudentView student: activeStudentsForRoll){
            System.out.println("Student: " + student.name + ", Registration: " + student.registration);
        }
        System.out.println("Message sent to /topic/presences/" + rollId);
    }

    public void clearPresencesForRoll(String rollId) {
        activePresencesByRoll.remove(rollId);
    }
}
