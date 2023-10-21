package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositório para operações CRUD e consultas personalizadas relacionadas a entidades de inscrição de classe.
 * Esta interface estende a interface JpaRepository para fornecer operações CRUD padrão para a entidade ClassSubscriptionEntity.
 */
public interface IClassSubscriptionRepository extends JpaRepository<ClassSubscriptionEntity, Long> {

    /**
     * Busca inscrições de classe com base no código da disciplina, código da classe e semestre.
     *
     * @param disciplineCode O código da disciplina.
     * @param classCode O código da classe.
     * @param semester O semestre de interesse.
     * @return Uma lista de entidades de inscrições de classe que atendem aos critérios fornecidos.
     */
    @Query("SELECT s FROM ClassSubscriptionEntity s WHERE s.classEntity.disciplineCode = :disciplineCode AND s.classEntity.classCode = :classCode AND s.semester = :semester")
    List<ClassSubscriptionEntity> findClassesByDisciplineCodeAndClassCodeAndSemester(String disciplineCode, String classCode, String semester);

}
