package com.example.task_2.service;


import com.example.task_2.entity.Roles;
import com.example.task_2.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;
    public List<Roles> findAll(){
        return  roleRepo.findAll();
    }
    public Optional<Roles> findById(Long id){
        return  roleRepo.findById(id);
    }

    public Roles save(Roles role){
        return  roleRepo.save(role);
    }
    public Optional<Roles> findByName(String name){
        return roleRepo.findByName(name);
    }
}
