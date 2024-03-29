package com.vitor.course.repository;

import com.vitor.course.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.module.moduleId = :moduleId")
    List<Lesson> findAllLessonIntoModule(@Param("moduleId") UUID moduleId);

    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.module.moduleId = :moduleId AND lesson.lessonId = :lessonId")
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);
}
