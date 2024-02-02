package com.vitor.course.service;

import com.vitor.course.domain.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    Lesson save(UUID moduleId, Lesson request);

    List<Lesson> findAllByModule(UUID moduleId);

    Lesson findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(Lesson lessonToBeDeleted);

    void update(UUID moduleId, UUID lessonId, Lesson lesson);
}
