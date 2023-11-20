package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.RollEntity;
import br.com.smartroll.repository.interfaces.IRollRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repositório que fornece uma camada de abstração sobre o repositório JPA IRollRepository.
 * Esta classe encapsula o acesso direto ao banco de dados e oferece métodos que facilitam
 * a gestão e manipulação de registros de chamada.
 */
@Repository
public class RollRepository {

    @Autowired
    private IRollRepository rollRepository;

    /**
     * Cria um novo registro de chamada no banco de dados.
     *
     * @param rollEntity entidade representando o registro de chamada.
     * @return entidade RollEntity recém-criada.
     */
    public RollEntity createRoll(RollEntity rollEntity) {
        return rollRepository.save(rollEntity);
    }

    public boolean isOpen(Long rollId){
        return rollRepository.isOpen(rollId);
    }

    public boolean hasOpenRollsForClass(String classCode){
        return rollRepository.hasOpenRollsForClass(classCode);
    }

    /**
     * Recupera um registro de chamada do banco de dados usando um identificador.
     *
     * @param id identificador único do registro de chamada.
     * @return uma instância Optional contendo o registro de chamada, se presente.
     */
    public RollEntity getRoll(Long id) {
        return rollRepository.getRoll(id);
    }

    /**
     * Atualiza um registro de chamada existente no banco de dados.
     *
     * @param id identificador do registro de chamada a ser atualizado.
     * @param rollEntity entidade contendo os novos dados da chamada.
     * @return entidade RollEntity atualizada, ou null se o registro não existir.
     */
    public RollEntity updateRoll(Long id, RollEntity rollEntity) {
        if (rollRepository.existsById(id)) {
            rollEntity.id = id;
            return rollRepository.save(rollEntity);
        }
        return null;
    }

    /**
     * Encerra uma chamada atualizando a data/hora de término.
     *
     * @param id identificador único do registro de chamada a ser encerrado.
     */
    public void closeRoll(Long id){
        rollRepository.updateFinishedAtById(id);
    }

    /**
     * Verifica se uma chamada foi encerrada.
     *
     * @param id identificador único do registro de chamada.
     * @return verdadeiro se a chamada foi encerrada, caso contrário falso.
     */
    public boolean isRollClosed(Long id){
        return rollRepository.isCallClosed(id);
    }

    public List<RollEntity> getRollsFromClass(String classCode, String semester) {
        return rollRepository.getRollsFromClass(classCode, semester);
    }

    public List<RollEntity> getClosedRollsFromClass(String classCode, String semester) {
        return rollRepository.getClosedRollsFromClass(classCode, semester);
    }

    public void closeOpenRollByClassCode(String classCode) {
        rollRepository.updateFinishedAtByClassCode(classCode);
    }

    public RollEntity getOpenRoll(String classCode) {
        RollEntity rollEntity = rollRepository.getOpenRoll(classCode);
        return rollEntity;
    }

    public List<RollEntity> findOpenRolls() {
        return rollRepository.findOpenRolls();
    }

    public List<RollEntity> findRollsWithScheduledCloseTime() {
        return rollRepository.findRollsWithScheduledCloseTime();
    }
}
