package com.example.task_2.repository;

import com.example.task_2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UserEntity u WHERE u.username = :username")
    Boolean existsByUsername(@Param("username") String username);
}
