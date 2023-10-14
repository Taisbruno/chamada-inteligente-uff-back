package br.com.smartroll.repository.interfaces;

import br.com.smartroll.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByRegistrationIn(List<String> registrations);
    @Query("SELECT u FROM UserEntity u JOIN ClassSubscriptionEntity cs ON u.registration = cs.userEntity.registration WHERE cs.classEntity.classCode = :classCode AND cs.semester = :semester")
    List<UserEntity> findUsersByClassCodeAndSemester(
            @Param("classCode") String classCode,
            @Param("semester") String semester);

}

