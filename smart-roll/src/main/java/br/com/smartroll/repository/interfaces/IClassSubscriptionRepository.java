package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClassSubscriptionRepository extends JpaRepository<ClassSubscriptionEntity, Long> {

    @Query("SELECT s FROM ClassSubscriptionEntity s WHERE s.classEntity.disciplineCode = :disciplineCode AND s.classEntity.classCode = :classCode AND s.semester = :semester")
    List<ClassSubscriptionEntity> findClassesByDisciplineCodeAndClassCodeAndSemester(String disciplineCode, String classCode, String semester);

}
