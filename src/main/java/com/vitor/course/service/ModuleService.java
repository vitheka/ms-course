package com.vitor.course.service;

import com.vitor.course.domain.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void delete(Module module);

   Module save(Module module);

    Module findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<Module> findAllByCourse(UUID courseId);


    void update(UUID courseId, UUID moduleId, Module request);

    Module findById(UUID moduleId);
}
