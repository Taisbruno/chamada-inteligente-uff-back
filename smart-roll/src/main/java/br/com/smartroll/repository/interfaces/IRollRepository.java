package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.RollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de Roll.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade RollEntity.
 */
public interface IRollRepository extends JpaRepository<RollEntity, Long> {

    /**
     * Retorna uma chamada com base no id da chamada.
     * @param id o id da chamada.
     * @return RollEntity a entidade de chamada.
     */
    @Query("SELECT r FROM RollEntity r WHERE r.id = ?1")
    RollEntity getRoll(Long id);

    /**
     * Verifica se a chamada com o ID especificado está aberta.
     *
     * @param rollId O ID da chamada que deseja verificar.
     * @return true se a chamada estiver aberta, false se estiver fechada.
     */
    @Query("SELECT CASE WHEN r.finishedAt IS NULL THEN true ELSE false END FROM RollEntity r WHERE r.id = :rollId")
    boolean isOpen(@Param("rollId") Long rollId);

    /**
     * Verifica se há chamadas em aberto relacionadas a uma turma específica com base no campo finishedAt nulo.
     *
     * @param classCode O código da turma para a qual deseja verificar as chamadas em aberto.
     * @return true se houver chamadas em aberto, false se todas estiverem fechadas.
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RollEntity r WHERE r.classEntity.classCode = :classCode AND r.finishedAt IS NULL")
    boolean hasOpenRollsForClass(@Param("classCode") String classCode);

    /**
     * Atualiza a coluna 'finishedAt' da entidade RollEntity com o timestamp atual
     * para a entidade com o ID especificado.
     *
     * @param id o ID da entidade RollEntity a ser atualizada.
     */
    @Modifying
    @Transactional
    @Query("UPDATE RollEntity r SET r.finishedAt = CURRENT_TIMESTAMP WHERE r.id = ?1")
    void updateFinishedAtById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE RollEntity r SET r.finishedAt = CURRENT_TIMESTAMP WHERE r.classEntity.classCode = ?1 AND r.finishedAt IS NULL")
    void updateFinishedAtByClassCode(String classCode);

    /**
     * Verifica se uma chamada, identificada por seu ID, está fechada ou não.
     * Uma chamada é considerada fechada se a coluna 'finishedAt' não for nula.
     *
     * @param id o ID da entidade RollEntity a ser verificada.
     * @return true se a chamada estiver fechada, false caso contrário.
     */
    @Query("SELECT CASE WHEN r.finishedAt IS NOT NULL THEN true ELSE false END FROM RollEntity r WHERE r.id = ?1")
    boolean isCallClosed(Long id);

    @Query("SELECT r FROM RollEntity r JOIN r.classEntity c WHERE c.classCode = :classCode AND c.semester = :semester")
    List<RollEntity> getRollsFromClass(@Param("classCode") String classCode, @Param("semester") String semester);

    /**
     * Retorna todas as chamadas fechadas de uma turma específica, identificadas pelo código da turma e semestre.
     * Uma chamada é considerada fechada se a coluna 'finishedAt' não for nula.
     *
     * @param classCode o código da turma.
     * @param semester o semestre da turma.
     * @return Lista de entidades RollEntity que são chamadas fechadas.
     */
    @Query("SELECT r FROM RollEntity r JOIN r.classEntity c WHERE c.classCode = :classCode AND c.semester = :semester AND r.finishedAt IS NOT NULL")
    List<RollEntity> getClosedRollsFromClass(@Param("classCode") String classCode, @Param("semester") String semester);

    @Query("SELECT r FROM RollEntity r WHERE r.finishedAt IS NULL")
    List<RollEntity> findOpenRolls();

}
