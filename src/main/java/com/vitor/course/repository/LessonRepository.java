package com.vitor.course.repository;

import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.module.moduleId = :moduleId")
    List<Lesson> findAllLessonIntoModule(@Param("moduleId") UUID moduleId);
}
