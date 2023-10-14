package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClassRepository extends JpaRepository<ClassEntity, Long> {
    @Query("SELECT c FROM ClassEntity c JOIN c.classSubscriptions cs WHERE cs.userEntity.registration = :registration")
    List<ClassEntity> findClassesByUserRegistration(@Param("registration") String registration);
}
