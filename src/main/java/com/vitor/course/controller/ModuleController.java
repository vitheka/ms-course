package com.vitor.course.controller;

import com.vitor.course.mapper.ModuleMapper;
import com.vitor.course.request.ModulePostRequest;
import com.vitor.course.response.ModuleGetResponse;
import com.vitor.course.response.ModulePostResponse;
import com.vitor.course.service.CourseService;
import com.vitor.course.service.ModuleService;
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
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;
    private final ModuleMapper mapper;
    private final ParseToJson json;

    @PostMapping("/v1/courses/{courseId}/modules")
    public ResponseEntity<ModulePostResponse> saveModule(@PathVariable UUID courseId,
                                                         @RequestBody @Valid ModulePostRequest request) {

        log.info("Request body received to register: {}", json.objToJson(request));

        var course = courseService.findById(courseId);

        var module = mapper.toModule(request);

        module.setCourse(course);
        module = moduleService.save(module);

        var response = mapper.toModulePostResponse(module);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/v1/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable UUID courseId,
                                             @PathVariable UUID moduleId) {

        var moduleToBeDeleted = moduleService.findModuleIntoCourse(courseId, moduleId);

        moduleService.delete(moduleToBeDeleted);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleGetResponse>> getAllModules(@PathVariable UUID courseId) {

        var module = moduleService.findAllByCourse(courseId);

        var response = mapper.toModuleGetResponse(module);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleGetResponse> getOneModule(@PathVariable UUID courseId,
                                                          @PathVariable UUID moduleId) {

        var module = moduleService.findModuleIntoCourse(courseId, moduleId);

        var response = mapper.toModuleGetResponse(module);

        return ResponseEntity.ok().body(response);
    }
}
