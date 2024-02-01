package com.vitor.course.controller;

import com.vitor.course.mapper.CourseMapper;
import com.vitor.course.response.CourseGetResponse;
import com.vitor.course.request.CoursePostRequest;
import com.vitor.course.response.CoursePostResponse;
import com.vitor.course.request.CoursePutRequest;
import com.vitor.course.service.CourseService;
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
@RequestMapping("/v1/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Log4j2
public class CourseController {

    private final CourseMapper mapper;
    private final ParseToJson json;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CoursePostResponse> saveCourse(@RequestBody @Valid CoursePostRequest request) {

        log.info("Request body received to register: {}", json.objToJson(request));
        var toCourse = mapper.toCourse(request);

        toCourse = courseService.save(toCourse);

        var response = mapper.toCoursePostResponse(toCourse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {

        courseService.delete(courseId);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> updateCourse(@RequestBody @Valid CoursePutRequest request) {

        var course = mapper.toCourse(request);

        courseService.update(course);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseGetResponse>> getAllCourses() {

        var courses = courseService.findAll();

        var response = mapper.toCoursesGetResponse(courses);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseGetResponse> getOneCourse(@PathVariable UUID courseId) {

        var course = courseService.findById(courseId);

        var response = mapper.toCourseGetResponse(course);

        return ResponseEntity.ok().body(response);
    }
}
