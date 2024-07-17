package com.example.task_2.service;


import com.example.task_2.entity.UserEntity;
import com.example.task_2.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<UserEntity> findAll(){
        return  userRepo.findAll();
    }
    public Optional<UserEntity> findById(Long id){
        return  userRepo.findById(id);
    }
    public UserEntity save(UserEntity user){
        return  userRepo.save(user);
    }


//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder ;
//
//    public User register(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepo.save(user);
//    }
//
//    public Optional<User> findByUsername(String username) {
//        return userRepo.findByUsername(username);
//    }
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public  User register(User user){
////        TO HASH PASSWORD
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepo.save(user);
//    }
//
//    public Optional<User> findByEmail(String email){
//        return userRepo.findByEmail(email);
//    }
}
