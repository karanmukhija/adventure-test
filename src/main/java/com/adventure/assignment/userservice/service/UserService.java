package com.adventure.assignment.userservice.service;

import com.adventure.assignment.userservice.entity.User;
import com.adventure.assignment.userservice.model.UserDTO;
import com.adventure.assignment.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createUser(UserDTO userDTO) {
        ResponseEntity<?> responseEntity = null;

        String hashedPwd = passwordEncoder.encode(userDTO.getPwd());
        userDTO.setPwd(hashedPwd);

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        userDTO = modelMapper.map(savedUser, UserDTO.class);

        if (savedUser.getId() > 0) {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(userDTO);
        }

        log.info("User created successfully");

        return responseEntity;
    }

    public ResponseEntity<?> findUserByUserID(Integer id) {
        ResponseEntity<?> responseEntity = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No User Found");
        }

        log.info("User retrieved successfully ");

        return responseEntity;
    }

    public ResponseEntity<?> deleteUserByUserID(Integer id) {
        userRepository.deleteById(id);
        log.info("User Deleted Successfully ");
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully");
    }

    public ResponseEntity<?> updateUser(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            log.info("User to be updated not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found");
        }

        String hashedPwd = passwordEncoder.encode(userDTO.getPwd());
        userDTO.setPwd(hashedPwd);
        userDTO.setId(id);
        User user = modelMapper.map(userDTO, User.class);

        User savedUser = userRepository.save(user);
        userDTO = modelMapper.map(savedUser, UserDTO.class);

        log.info("User updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
