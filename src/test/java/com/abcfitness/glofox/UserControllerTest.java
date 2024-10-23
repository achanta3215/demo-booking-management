package com.abcfitness.glofox;

import com.abcfitness.glofox.user.UserController;
import com.abcfitness.glofox.user.UserDTO;
import com.abcfitness.glofox.user.UserEntity;
import com.abcfitness.glofox.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserCreate_Success() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .firstName("Krishna Sameer")
                .lastName("Achanta")
                .email("achanta3215@gmail.com")
                .build();
        when(userService.saveOrUpdateUser(any(UserDTO.class))).thenReturn(userEntity);

        UserDTO userDTO = new UserDTO("Krishna Sameer", "Achanta", "achanta3215@gmail.com");

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userEntity)));
    }
    @Test
    public void testUserCreate_DuplicateEmail_Conflict() throws Exception {
        // Simulate the service throwing DataIntegrityViolationException when saving a duplicate email
        when(userService.saveOrUpdateUser(any(UserDTO.class)))
                .thenThrow(new DataIntegrityViolationException("Email already in use. Please use a different email."));

        UserDTO userDTO = new UserDTO("Krishna Sameer", "Achanta", "achanta3215@gmail.com");

        // Perform POST request and check for 409 Conflict
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already in use. Please use a different email."));
    }
}

