package com.vitor.course.repository;

import com.vitor.course.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

    @Query("SELECT module FROM Module module WHERE module.course.courseId = :courseId")
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query("SELECT module FROM Module module WHERE module.course.courseId = :courseId AND module.moduleId = :moduleId")
    Optional<Module> findModuleIntoCourse(@Param("courseId")UUID courseId,@Param("moduleId") UUID moduleId);

}
