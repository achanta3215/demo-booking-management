package com.abcfitness.glofox.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createOrUpdateUser(@Valid @RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.saveOrUpdateUser(userDTO);
        return ResponseEntity.ok(userEntity);
    }
}
