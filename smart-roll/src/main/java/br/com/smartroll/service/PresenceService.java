package br.com.smartroll.service;

import br.com.smartroll.exception.PresenceNotFoundException;
import br.com.smartroll.exception.RollClosedException;
import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.exception.StudentAlreadyPresentException;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.view.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe de serviço responsável pelas operações relacionadas à presença dos alunos.
 * Esta classe oferece métodos para gerenciar e notificar sobre a presença dos alunos em uma determinada chamada.
 */
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

    /**
     * Método responsável por registrar uma presença de um aluno em uma chamada.
     *
     * @param presenceModel Modelo contendo as informações da presença.
     * @throws RollNotFoundException Se a chamada especificada não for encontrada.
     * @throws RollClosedException Se a chamada especificada já estiver fechada.
     */
    public void submitPresence(PresenceModel presenceModel) throws RollNotFoundException, RollClosedException, StudentAlreadyPresentException {
        if(rollRepository.getRoll(Long.parseLong(presenceModel.rollId)) == null){
            throw new RollNotFoundException(presenceModel.rollId);
        }else if(rollRepository.isRollClosed(Long.parseLong(presenceModel.rollId))){
            throw new RollClosedException(presenceModel.rollId);
        }else if(presenceRepository.isPresent(presenceModel.studentRegistration, Long.parseLong(presenceModel.rollId))){
            throw new StudentAlreadyPresentException(presenceModel.studentRegistration, presenceModel.rollId);
        }

        PresenceEntity presenceEntity = new PresenceEntity(presenceModel);
        presenceRepository.createPresence(presenceEntity);
        submitStudent(presenceModel.studentRegistration, presenceModel.rollId);
        notifyFrontEndAboutActivePresences(presenceModel.rollId);
    }

    /**
     * Método auxiliar para adicionar um aluno à lista de presenças ativas de uma chamada.
     *
     * @param studentRegistration Registro do aluno.
     * @param rollId ID da chamada.
     */
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
     * Método para notificar a interface do usuário sobre as presenças ativas em uma chamada via WebSocket.
     *
     * @param rollId ID da chamada para a qual as notificações devem ser enviadas.
     */
    private void notifyFrontEndAboutActivePresences(String rollId) {
        // Verificar se a chamada está fechada
        if (rollRepository.isRollClosed(Long.parseLong(rollId))) {
            activePresencesByRoll.remove(rollId);
            return; // Encerrar a função para não enviar mensagens para uma chamada fechada
        }

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

    public void updatePresenceStatus(Long id, boolean isPresent) {
        Optional<PresenceEntity> entityOptional = presenceRepository.getPresence(id);

        if (!entityOptional.isPresent()) {
            throw new PresenceNotFoundException("Presence with id " + id + " not found.");
        }

        PresenceEntity entity = entityOptional.get();
        entity.isPresent = isPresent;

        presenceRepository.savePresence(entity);
    }

}
