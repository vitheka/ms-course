package com.vitor.course.service.impl;

import com.vitor.course.domain.Lesson;
import com.vitor.course.exception.NotFoundException;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.service.LessonService;
import com.vitor.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    @Transactional
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

    @Override
    public Lesson findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow( () -> new NotFoundException("Lesson not found!"));
    }

    @Transactional
    @Override
    public void delete(Lesson lessonToBeDeleted) {
        lessonRepository.delete(lessonToBeDeleted);
    }

    @Override
    public void update(UUID moduleId, UUID lessonId, Lesson lessonRequest) {

        var lessonToBeUpdate = findLessonIntoModule(moduleId, lessonId);

        lessonToBeUpdate.setTitle(lessonRequest.getTitle());
        lessonToBeUpdate.setDescription(lessonRequest.getDescription());
        lessonToBeUpdate.setVideoUrl(lessonRequest.getVideoUrl());

        lessonRepository.save(lessonToBeUpdate);

    }
}
