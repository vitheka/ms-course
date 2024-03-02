package com.vitor.course.controller;

import com.vitor.course.CourseClient;
import com.vitor.course.response.UserGetResponse;
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
public class CourseUserController {

    private final CourseClient client;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<List<UserGetResponse>> getAllCoursesByUser(@PathVariable(value = "courseId") UUID courseId) {

        return ResponseEntity.status(HttpStatus.OK).body(client.getAllUsersByCourse(courseId));
    }

    /*@PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                         @RequestBody @Valid SubscriptionRequest request) {

        return null;
    }*/

}
