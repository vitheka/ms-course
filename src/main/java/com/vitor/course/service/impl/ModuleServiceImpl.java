package com.vitor.course.service.impl;

import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import com.vitor.course.exception.NotFoundException;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.repository.ModuleRepository;
import com.vitor.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Module module) {

        List<Lesson> lessonList = lessonRepository.findAllLessonIntoModule(module.getModuleId());
        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }
        moduleRepository.delete(module);
    }

    @Override
    public Module save(Module module) {
        module.setCreationDate(LocalDateTime.now());
        return moduleRepository.save(module);
    }

    @Override
    public Module findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId)
                .orElseThrow(() -> new NotFoundException("Module not found!"));
    }

    @Override
    public List<Module> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }


}
