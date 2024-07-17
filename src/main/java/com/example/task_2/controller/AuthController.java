package com.example.task_2.controller;

import com.example.task_2.config.JWTGenerator;
import com.example.task_2.entity.Roles;
import com.example.task_2.entity.UserEntity;
import com.example.task_2.repository.RoleRepo;
import com.example.task_2.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

@Autowired
    public AuthController(JWTGenerator jwtGenerator,AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator=jwtGenerator;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody AuthDTO authDTO){
    if(userRepo.existsByUsername(authDTO.getUsername())){
     return  new ResponseEntity<>("Username not found", HttpStatus.BAD_REQUEST);
    }
        UserEntity user=new UserEntity();
    user.setUsername(authDTO.getUsername());
    user.setPassword(passwordEncoder.encode((authDTO.getPassword())));
        Roles roles= roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepo.save(user);
        return new ResponseEntity<>("user created",HttpStatus.OK);
    }



    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
    Authentication authentication=
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authDTO.getUsername(),authDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication)
        ;
        String token= jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);

    }
}
