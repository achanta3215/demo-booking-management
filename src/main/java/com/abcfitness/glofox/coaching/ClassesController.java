package com.abcfitness.glofox.coaching;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    @Autowired
    private ClassService classService;

    @PostMapping
    public ResponseEntity<ClassEntity> createOrUpdateClass(@Valid @RequestBody ClassDTO classDTO) {
        ClassEntity classEntity = classService.saveOrUpdateClass(classDTO);
        return ResponseEntity.ok(classEntity);
    }

    @GetMapping
    public ResponseEntity<Page<ClassEntity>> getPaginatedClasses(@ParameterObject Pageable pageable) {
        Page<ClassEntity> classEntities = classService.getAllClasses(pageable);
        return ResponseEntity.ok(classEntities);
    }

}
