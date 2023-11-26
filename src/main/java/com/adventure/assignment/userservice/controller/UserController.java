package com.adventure.assignment.userservice.controller;

import com.adventure.assignment.userservice.model.UserDTO;
import com.adventure.assignment.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO){
        return  userService.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserByUserID(@PathVariable Integer id){
        return  userService.findUserByUserID(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByUserID(@PathVariable Integer id){
        return userService.deleteUserByUserID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }
}
