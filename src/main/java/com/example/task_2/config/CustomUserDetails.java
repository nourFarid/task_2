package com.example.task_2.config;

import com.example.task_2.entity.Roles;
import com.example.task_2.entity.UserEntity;
import com.example.task_2.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {

    private UserRepo userRepo;
    @Autowired
    public CustomUserDetails(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity user= userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found!!!!!!!"));

    return  new User(user.getUsername(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthority(List<Roles> roles){
        return  roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
