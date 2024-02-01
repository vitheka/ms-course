package com.vitor.course.service.impl;

import com.vitor.course.domain.Lesson;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.service.LessonService;
import com.vitor.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    @Override
    public Lesson save(UUID moduleId, Lesson request) {

        var moduleFound = moduleService.findById(moduleId);

        request.setModule(moduleFound);
        request.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return lessonRepository.save(request);
    }

    @Override
    public List<Lesson> findAllByModule(UUID moduleId) {

        return lessonRepository.findAllLessonIntoModule(moduleId);
    }
}
