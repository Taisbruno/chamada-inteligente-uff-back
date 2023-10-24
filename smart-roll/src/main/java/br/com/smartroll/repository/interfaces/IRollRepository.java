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
     * Atualiza a coluna 'finishedAt' da entidade RollEntity com o timestamp atual
     * para a entidade com o ID especificado.
     *
     * @param id o ID da entidade RollEntity a ser atualizada.
     */
    @Modifying
    @Transactional
    @Query("UPDATE RollEntity r SET r.finishedAt = CURRENT_TIMESTAMP WHERE r.id = ?1")
    void updateFinishedAtById(Long id);

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

}
