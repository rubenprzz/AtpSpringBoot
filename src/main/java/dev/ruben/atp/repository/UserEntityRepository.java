package dev.ruben.atp.repository;

import dev.ruben.atp.auth.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByUsername(String username);

}
