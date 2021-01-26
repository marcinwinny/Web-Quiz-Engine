package com.marcinwinny.engine.controller;


import com.marcinwinny.engine.model.User;
import com.marcinwinny.engine.model.UserDto;
import com.marcinwinny.engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //register new user
    @PostMapping(path = "/api/register", consumes = "application/json")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto) {

        if(userRepository.findByUsername(userDto.getEmail()).isEmpty()){
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = new User(userDto.getEmail(), userDto.getPassword());
            user.setRoles("ROLE_USER");
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get all users
    @GetMapping(path = "/api/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    //delete all users
    @DeleteMapping(path = "/api/users/deleteAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setUserRepository(){
        userRepository.deleteAll();
    }


}
