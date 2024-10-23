package com.abcfitness.glofox.coaching;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public ClassEntity saveOrUpdateClass(ClassDTO classDTO) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classDTO.getClassName());
        classEntity.setStartDate(classDTO.getStartDate());
        classEntity.setEndDate(classDTO.getEndDate());
        classEntity.setCapacity(classDTO.getCapacity());
        return classRepository.save(classEntity);
    }

    public Page<ClassEntity> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable);
    }

    public Optional<ClassEntity> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}

