package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.interfaces.IPresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repositório que fornece uma camada de abstração sobre o repositório JPA IPresenceRepository.
 * Esta classe encapsula o acesso direto ao banco de dados e oferece métodos que facilitam a gestão
 * e manipulação de registros de presença.
 */
@Repository
public class PresenceRepository {

    @Autowired
    private IPresenceRepository presenceRepository;

    /**
     * Cria um novo registro de presença no banco de dados.
     *
     * @param presenceEntity entidade representando o registro de presença.
     * @return entidade PresenceEntity recém-criada.
     */
    public PresenceEntity createPresence(PresenceEntity presenceEntity) {
        return presenceRepository.save(presenceEntity);
    }

    public boolean isPresent(String registration, Long id){
        return presenceRepository.isPresent(registration, id);
    }

    public boolean isRollOpenForPresence(long presenceId){
        return presenceRepository.isRollOpenForPresence(presenceId);
    }

    public void invalidateOpenPresence(long id, String exitTime) {
        presenceRepository.invalidateOpenPresence(id, exitTime);
    }

    public void invalidateClosedPresence(long id) {
        presenceRepository.invalidateClosedPresence(id);
    }

    public void validatePresence(String id) {
        presenceRepository.validatePresence(Long.parseLong(id));
    }

    public void markExitTimeForAllPresentInRoll(Long rollId){
        String exitTime = LocalDateTime.now().toString();
        presenceRepository.markExitTimeForAllPresentInRoll(rollId, exitTime);
    }

    /**
     * Recupera um registro de presença do banco de dados usando um identificador.
     *
     * @param id identificador único do registro de presença.
     * @return uma instância Optional contendo o registro de presença, se presente.
     */
    public PresenceEntity getPresence(Long id) {
        return presenceRepository.getPresence(id);
    }

    /**
     * Método responsável por inserir um certificado em uma presença.
     * @param id o id da presença.
     * @param certificate a string em base64 do certificado.
     */
    public void updateCertificate(long id, String certificate){
        presenceRepository.updateCertificate(id, certificate);
    }

    /**
     * Exclui um registro de presença do banco de dados usando um identificador.
     *
     * @param id identificador único do registro de presença a ser excluído.
     */
    public void deletePresence(Long id) {
        presenceRepository.deleteById(id);
    }

    /**
     * Atualiza um registro de presença existente no banco de dados.
     *
     * @param id identificador do registro de presença a ser atualizado.
     * @param presenceEntity entidade contendo os novos dados de presença.
     * @return entidade PresenceEntity atualizada, ou null se o registro não existir.
     */
    public PresenceEntity updatePresence(Long id, PresenceEntity presenceEntity) {
        if (presenceRepository.existsById(id)) {
            presenceEntity.id = id;
            return presenceRepository.save(presenceEntity);
        } else {
            // Manipulação de erro, lançamento de exceção, etc.
        }
        return null; // ou lançar uma exceção, ou usar Optional<PresenceEntity>
    }

    public PresenceEntity savePresence(PresenceEntity presenceEntity) {
        return presenceRepository.save(presenceEntity);
    }
}
