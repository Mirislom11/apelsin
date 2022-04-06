package com.company.repository.profile;

import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.company.model.enums.ProfileStatus.DELETED;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    @Query("SELECT p FROM ProfileEntity  p WHERE p.status <> :status AND login = :login AND password = :password")
    Optional<ProfileEntity> findByLoginAndPassword(@Param("status") ProfileStatus status, @Param("login") String login, @Param("password") String password);

    @Query("SELECT p FROM ProfileEntity  p WHERE p.status <> :status AND name = :name")
    Optional<ProfileEntity> findByName(@Param("status") ProfileStatus status, @Param("name") String name);

    @Query(value = "SELECT count(*) FROM profile where profile.profile_status != :status AND (login = :login OR email = :email)", nativeQuery = true)
    long getProfileCountByLoginAndPassword(@Param("status") String status, @Param("login") String login, @Param("email") String email);

    @Query("SELECT p FROM ProfileEntity  p WHERE p.status <> :status AND lastName = :lastName")
    Optional<ProfileEntity> findByLastName(@Param("status") ProfileStatus status, @Param("lastName") String lastName);

    @Query("SELECT p FROM ProfileEntity  p WHERE p.status <> :status")
    List<ProfileEntity> findAll(@Param("status") ProfileStatus status);

    @Query("SELECT p FROM ProfileEntity  p WHERE p.status <> :status AND p.id = :id")
    Optional<ProfileEntity> findById(@Param("status") ProfileStatus status, long id);

    boolean existsByEmailOrLogin(String email, String login);
}
