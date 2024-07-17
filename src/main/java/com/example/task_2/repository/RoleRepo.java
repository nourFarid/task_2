package com.example.task_2.repository;

import com.example.task_2.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Long> {

  Optional <Roles> findByName(String name);


}