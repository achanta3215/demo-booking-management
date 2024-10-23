package com.abcfitness.glofox.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity saveOrUpdateUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
