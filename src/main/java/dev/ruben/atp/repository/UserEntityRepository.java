package dev.ruben.atp.repository;

import dev.ruben.atp.auth.users.model.UserEntity;
import jakarta.persistence.FetchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByUsername(String username );

}
