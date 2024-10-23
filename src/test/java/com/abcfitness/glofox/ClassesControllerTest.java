package com.abcfitness.glofox;

import com.abcfitness.glofox.coaching.ClassDTO;
import com.abcfitness.glofox.coaching.ClassEntity;
import com.abcfitness.glofox.coaching.ClassService;
import com.abcfitness.glofox.coaching.ClassesController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ClassesController.class)
public class ClassesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassService classService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testClassesCreate_Success() throws Exception {
        ClassEntity classEntity = new ClassEntity(1L, "Yoga Class", new java.util.Date(), new java.util.Date(), 30);
        when(classService.saveOrUpdateClass(any(ClassDTO.class))).thenReturn(classEntity);

        ClassDTO classDTO = new ClassDTO("Yoga Class", new java.util.Date(), new java.util.Date(), 30);

        // Perform POST request and check for 200 OK
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(classEntity)));
    }

    @Test
    public void testClassesCreate_ValidationError() throws Exception {
        // Prepare the DTO with validation errors (e.g., className is too short)
        ClassDTO classDTO = new ClassDTO("Yo", new java.util.Date(), new java.util.Date(), 30);

        // Perform POST request and expect validation failure (400 Bad Request)
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetClasses_Success() throws Exception {
        ClassEntity classEntity = new ClassEntity(1L, "Yoga Class", new java.util.Date(), new java.util.Date(), 30);
        Page<ClassEntity> paginatedClasses = new PageImpl<>(Collections.singletonList(classEntity), PageRequest.of(0, 5), 1);

        when(classService.getAllClasses(any(Pageable.class))).thenReturn(paginatedClasses);

        // Perform GET request and check for 200 OK
        mockMvc.perform(get("/classes")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(paginatedClasses)));
    }
}
