package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.PresenceEntity;
import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de presença.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade PresenceEntity.
 */
public interface IPresenceRepository extends JpaRepository<PresenceEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PresenceEntity p WHERE p.studentRegistration = :studentRegistration AND p.roll.id = :rollId AND p.isPresent = true")
    boolean isPresent(@Param("studentRegistration") String studentRegistration, @Param("rollId") Long rollId);

    /**
     * Retorna uma presença com base no id da presença.
     * @param id o id da presença.
     * @return PresenceEntity a entidade de presença.
     */
    @Query("SELECT r FROM PresenceEntity r WHERE r.id = ?1")
    PresenceEntity getPresence(Long id);

    /**
     * Invalida uma presença com base no id e marca o tempo de saída do aluno no tempo corrente.
     * @param id O id da presença
     * @param exitTime O tempo de saída para ser definido
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.isPresent = false, p.exitTime = :exitTime WHERE p.id = :id")
    void invalidatePresence(@Param("id") long id, @Param("exitTime") String exitTime);

    /**
     * Valida uma presença com base no id.
     * @param id O id da presença
     */
    @Transactional
    @Modifying
    @Query("UPDATE PresenceEntity p SET p.isPresent = true WHERE p.id = :id")
    void validatePresence(@Param("id") long id);

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
}
