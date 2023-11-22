package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de presença.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade PresenceEntity.
 */
public interface IPresenceRepository extends JpaRepository<PresenceEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PresenceEntity p WHERE p.studentRegistration = :studentRegistration AND p.roll.id = :rollId AND p.isPresent = true")
    boolean isPresent(@Param("studentRegistration") String studentRegistration, @Param("rollId") Long rollId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PresenceEntity p WHERE p.studentRegistration = :studentRegistration AND p.roll.id = :rollId")
    boolean isSubscribed(@Param("studentRegistration") String studentRegistration, @Param("rollId") Long rollId);

    /**
     * Retorna uma presença com base no id da presença.
     * @param id o id da presença.
     * @return PresenceEntity a entidade de presença.
     */
    @Query("SELECT r FROM PresenceEntity r WHERE r.id = ?1")
    PresenceEntity getPresence(Long id);

    /**
     * Verifica se a chamada relacionada a esta presença está aberta com base no ID da presença.
     *
     * @param presenceId O ID da presença para o qual deseja verificar o estado da chamada.
     * @return true se a chamada estiver aberta, false se estiver fechada.
     */
    @Query("SELECT CASE WHEN r.finishedAt IS NULL THEN true ELSE false END FROM RollEntity r WHERE r.id = (SELECT p.roll.id FROM PresenceEntity p WHERE p.id = :presenceId)")
    boolean isRollOpenForPresence(@Param("presenceId") Long presenceId);

    /**
     * Invalida uma presença em chamada aberta com base no id e marca o tempo de saída do aluno no tempo corrente.
     * @param id O id da presença
     * @param exitTime O tempo de saída para ser definido
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.isPresent = false, p.exitTime = :exitTime WHERE p.id = :id")
    void invalidateOpenPresence(@Param("id") long id, @Param("exitTime") String exitTime);

    /**
     * Invalida uma presença em chamada fechada com base no id.
     * @param id O id da presença
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.isPresent = false WHERE p.id = :id")
    void invalidateClosedPresence(@Param("id") long id);

    /**
     * Valida uma presença com base no id.
     * @param id O id da presença
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.isPresent = true, p.exitTime = :exitTime WHERE p.id = :id")
    void validatePresence(@Param("id") long id, @Param("exitTime") String exitTime);

    /**
     * Insere um certificado em uma presença com base no id da presença.
     * @param id id da presença.
     * @param certificate atestado médico a ser anexado.
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.medicalCertificate = :certificate WHERE p.id = :id")
    void updateCertificate(@Param("id") long id, @Param("certificate") String certificate);


    /**
     * Atualiza o tempo de saída para todos os alunos inscritos em uma determinada RollEntity
     * com base em seu id, e que têm o campo isPresent como true ou possuem um certificado médico.
     * @param rollId O id da chamada.
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.exitTime = :exitTime WHERE p.roll.id = :rollId AND (p.isPresent = true OR p.medicalCertificate IS NOT NULL)")
    void markExitTimeForAllPresentInRoll(@Param("rollId") Long rollId, @Param("exitTime") String exitTime);

    /**
     * Busca todas as presenças associadas a um determinado ID de chamada.
     *
     * @param rollId O identificador da chamada para qual as presenças estão associadas.
     * @return Lista de entidades de presença associadas ao ID de chamada fornecido.
     */
    @Query("SELECT p FROM PresenceEntity p WHERE p.roll.id = :rollId")
    List<PresenceEntity> getPresencesByRollId(@Param("rollId") Long rollId);

    /**
     * Método para recuperar o ID da chamada (roll) associada a uma presença específica.
     *
     * @param presenceId O ID da presença para a qual o ID da chamada será recuperado.
     * @return O ID da chamada associada à presença especificada.
     */
    @Query("SELECT p.roll.id FROM PresenceEntity p WHERE p.id = :presenceId")
    Long getIdRollByIdPresence(@Param("presenceId") Long presenceId);

}
