package br.com.smartroll.service;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.repository.PresenceRepository;
import br.com.smartroll.repository.RollRepository;
import br.com.smartroll.repository.UserRepository;
import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.view.PresenceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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

    public ConcurrentHashMap<String, List<PresenceView>> activePresencesByRoll = new ConcurrentHashMap<>();


    /**
     * Método de inicialização pós-construção que é executado automaticamente após a
     * injeção de dependência ter sido concluída para preencher o mapa activePresencesByRoll.
     * Este método pega todas as chamadas (rolls) abertas e para cada chamada,
     * ele pega todas as presenças associadas e as adiciona ao mapa activePresencesByRoll como uma forma de backup
     * da comunicação via Websocket em caso da aplicação parar.
     *
     * @PostConstruct assegura que este método seja executado depois que o bean foi inicializado.
     */
    @PostConstruct
    public void initializeActivePresences() {
        List<RollEntity> openRolls = rollRepository.findOpenRolls();

        for (RollEntity roll : openRolls) {
            Long rollId = roll.id;
            List<PresenceEntity> presencesForRoll = presenceRepository.getPresencesByRollId(rollId);

            for (PresenceEntity presence : presencesForRoll) {
                submitStudent(presence, rollId.toString());
            }
        }
    }

    /**
     * Este método é utilizado para buscar todas as presenças associadas a uma chamada específica.
     *
     * @param idRoll O identificador único da chamada para a qual as presenças são buscadas.
     * @return Uma lista de modelos de presença (PresenceModel) associados à chamada especificada.
     * @throws RollNotFoundException Se a chamada com o idRoll fornecido não for encontrada.
     * @throws PresencesNotFoundException Se nenhuma presença for encontrada para a chamada especificada.
     */
    public List<PresenceModel> getPresencesByRoll(String idRoll) throws RollNotFoundException, PresencesNotFoundException {
        Long id = Long.parseLong(idRoll);
        if(rollRepository.getRoll(id) == null){
            throw new RollNotFoundException(idRoll);
        }
        List<PresenceEntity> presencesForRoll = presenceRepository.getPresencesByRollId(id);
        if(presencesForRoll.isEmpty()){
            throw new PresencesNotFoundException(idRoll);
        }
        List<PresenceModel> presenceModels = new ArrayList<>();
        for(PresenceEntity presenceEntity : presencesForRoll){
            UserEntity userEntity = userRepository.getUserByRegistration(presenceEntity.studentRegistration);
            PresenceModel presenceModel = new PresenceModel(presenceEntity.id, presenceEntity.studentRegistration, presenceEntity.roll.id, presenceEntity.medicalCertificate, presenceEntity.message, presenceEntity.isPresent, presenceEntity.entryTime, presenceEntity.exitTime);
            presenceModel.name = userEntity.name;
            presenceModels.add(presenceModel);
        }
        return presenceModels;
    }

    /**
     * Método responsável por registrar uma presença de um aluno em uma chamada.
     *
     * @param presenceModel Modelo contendo as informações da presença.
     * @throws RollNotFoundException Se a chamada especificada não for encontrada.
     * @throws RollClosedException Se a chamada especificada já estiver fechada.
     * @throws StudentAlreadySubscribedException Se o aluno já estiver inscrito na chamada.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */
    public void submitPresence(PresenceModel presenceModel) throws RollNotFoundException, RollClosedException, StudentAlreadySubscribedException, UserNotFoundException, StudentNotEnrolledInClassException, IOException {
        Long rollId = Long.parseLong(presenceModel.rollId);
        if(rollRepository.getRoll(rollId) == null){
            throw new RollNotFoundException(presenceModel.rollId);
        }else if(rollRepository.isRollClosed(rollId) && presenceModel.medicalCertificate == null){
            throw new RollClosedException(presenceModel.rollId);
        }else if(presenceRepository.isSubscribed(presenceModel.studentRegistration, rollId)){
            throw new StudentAlreadySubscribedException(presenceModel.studentRegistration, presenceModel.rollId);
        }else if(userRepository.getUserByRegistration(presenceModel.studentRegistration) == null){
            throw new UserNotFoundException(presenceModel.studentRegistration);
        }else if(!userRepository.isStudentEnrolledInClass(presenceModel.studentRegistration, rollId)){
            throw new StudentNotEnrolledInClassException(presenceModel.studentRegistration, presenceModel.rollId);
        }

        if (presenceModel.medicalCertificate != null) {
            String certificateUrl = uploadCertificate(Long.parseLong(presenceModel.rollId), presenceModel.medicalCertificate, presenceModel.filename, presenceModel.studentRegistration);
            presenceModel.medicalCertificate = certificateUrl;
        }

        PresenceEntity presenceEntity = new PresenceEntity(presenceModel);
        PresenceEntity createdPresenceEntity = presenceRepository.createPresence(presenceEntity);
        submitStudent(createdPresenceEntity, presenceModel.rollId);
        notifyFrontEndAboutActivePresences(presenceModel.rollId);
    }

    /**
     * Método auxiliar para adicionar um aluno à lista de presenças ativas de uma chamada.
     *
     * @param presenceEntity A entidade da presença.
     * @param rollId O id da chamada.
     */
    private void submitStudent(PresenceEntity presenceEntity, String rollId) {
        UserEntity userEntity = userRepository.getUserByRegistration(presenceEntity.studentRegistration);
        PresenceView presence = new PresenceView(userEntity.name, presenceEntity);

        activePresencesByRoll.compute(rollId, (key, presences) -> {
            if (presences == null) {
                presences = new ArrayList<>();
            }

            // Verificar se o aluno já está na lista
            boolean isAlreadyPresent = presences.stream()
                    .anyMatch(p -> p.studentRegistration.equals(presenceEntity.studentRegistration));

            // Se o aluno não estiver na lista, adicione-o
            if (!isAlreadyPresent) {
                presences.add(presence);
            }

            return presences;
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

        List<PresenceView> activeStudentsForRoll = activePresencesByRoll.getOrDefault(rollId, new ArrayList<>());
        messagingTemplate.convertAndSend("/topic/presences/" + rollId, activeStudentsForRoll);

        // Adicionado print para depurar o rollId
        System.out.println("Calling notifyFrontEndAboutActivePresences with rollId: " + rollId);
        // Adicionado print para depurar a lista de estudantes ativos para esse rollId
        System.out.println("Number of active students for rollId " + rollId + ": " + activeStudentsForRoll.size());
        for(PresenceView student: activeStudentsForRoll){
            System.out.println("Student: " + student.id + " name: "+ student.name + ", Registration: " + student.studentRegistration + ", isPresent: " + student.isPresent);
        }
        System.out.println("Message sent to /topic/presences/" + rollId);
    }

    /**
     * Invalida o status de uma presença para não presente.
     * @param id o id da presença.
     * @throws PresenceNotFoundException presença não encontrada.
     */
    public void invalidatePresenceStatus(String id) throws PresenceNotFoundException {
        long convertedId = Long.parseLong(id);
        PresenceEntity presenceEntity = presenceRepository.getPresence(convertedId);
        if (presenceEntity == null) {
            throw new PresenceNotFoundException(id);
        }
        long rollId = presenceRepository.getIdRollByIdPresence(convertedId);

        // Caso a chamada esteja aberta
        if (presenceRepository.isRollOpenForPresence(convertedId)) {

            String exitTime = LocalDateTime.now().toString();
            presenceRepository.invalidateOpenPresence(convertedId, exitTime);
            // Procurar e atualizar a presença na lista activePresencesByRoll
            List<PresenceView> presences = activePresencesByRoll.getOrDefault(String.valueOf(rollId), new ArrayList<>());
            for (PresenceView presence : presences) {
                if (presence.id.equals(id)) {
                    presence.isPresent = false;
                    presence.exitTime = exitTime;
                    break;
                }
            }
        } else {
            presenceRepository.invalidateClosedPresence(convertedId);
        }

        // Notificar o frontend sobre a mudança
        notifyFrontEndAboutActivePresences(String.valueOf(rollId));
    }

    /**
     * Valida o status de uma presença para presente.
     * @param id o id da presença
     * @throws PresenceNotFoundException presença não encontrada.
     */
    public void validatePresenceStatus(String id) throws PresenceNotFoundException {

        if(presenceRepository.getPresence(Long.parseLong(id)) == null){
            throw new PresenceNotFoundException(id);
        }
        presenceRepository.validatePresence(id);
    }

    /**
     * Responsável por inserir um atestado médico a uma presença.
     * @param id o id da presença.
     * @param certificate a string do atestado médico em base64.
     * @param filename o nome do arquivo que foi enviado.
     * @param studentRegistration matrícula do aluno que submeteu o atestado.
     */
    public void updateCertificate(long id, String certificate, String filename, String studentRegistration) throws PresenceNotFoundException, IOException {
        if(presenceRepository.getPresence(id) == null){
            throw new PresenceNotFoundException(String.valueOf(id));
        }

        String certificateUrl = uploadCertificate(id, certificate, filename, studentRegistration);
        presenceRepository.updateCertificate(id, certificateUrl);
    }

    /**
     * Responsável por realizar o upload de um arquivo para o S3 e retornar a URL de acesso.
     * @param id o id da presença.
     * @param certificate a string do atestado médico em base64.
     * @param filename o nome do arquivo que foi enviado.
     * @param studentRegistration matrícula do aluno que submeteu o atestado.
     */
    public String uploadCertificate(long id, String certificate, String filename, String studentRegistration ) throws IOException {
        S3Service s3Service = new S3Service();

        String mimeType = "application/pdf";

        String extension = filename.split("\\.")[1];

        if (extension.equals("jpg") || extension.equals("jpeg")) {
            mimeType = "image/jpeg";
        } else if (extension.equals("png")) {
            mimeType = "image/png";
        }

        String newFilename = "medical-certificate_" + studentRegistration + "_" + id + "." + extension;

        return s3Service.uploadBase64File(certificate, newFilename, mimeType);
    }

}
