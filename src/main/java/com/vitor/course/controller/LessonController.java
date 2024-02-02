package com.vitor.course.controller;

import com.vitor.course.mapper.LessonMapper;
import com.vitor.course.request.LessonPostRequest;
import com.vitor.course.request.LessonPutRequest;
import com.vitor.course.response.LessonGetResponse;
import com.vitor.course.response.LessonPostResponse;
import com.vitor.course.service.LessonService;
import com.vitor.course.service.util.ParseToJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/v1/modules")
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper mapper;
    private final ParseToJson json;

    @PostMapping("/{moduleId}/lessons")
    public ResponseEntity<LessonPostResponse> saveLesson(@PathVariable UUID moduleId,
                                                         @RequestBody @Valid LessonPostRequest request) {

        log.info("Request body received to register: {}", json.objToJson(request));

        var lesson = mapper.toLesson(request);

        lesson = lessonService.save(moduleId, lesson);

        var response = mapper.toLessonPostResponse(lesson);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID moduleId,
                                             @PathVariable UUID lessonId) {

        var lessonToBeDeleted = lessonService.findLessonIntoModule(moduleId, lessonId);

        lessonService.delete(lessonToBeDeleted);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Void> updateLesson(@PathVariable UUID moduleId,
                                             @PathVariable UUID lessonId,
                                             @RequestBody LessonPutRequest request) {

        var lesson = mapper.toLesson(request);

        lessonService.update(moduleId, lessonId, lesson);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{moduleId}/lessons")
    public ResponseEntity<List<LessonGetResponse>> getAllLessons(@PathVariable UUID moduleId) {

        var lessons = lessonService.findAllByModule(moduleId);

        var response = mapper.toLessonGetResponse(lessons);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonGetResponse> getOneLesson(@PathVariable UUID moduleId,
                                                          @PathVariable UUID lessonId) {

        var lessons = lessonService.findLessonIntoModule(moduleId, lessonId);

        var response = mapper.toLessonGetResponse(lessons);

        return ResponseEntity.ok().body(response);
    }

}
