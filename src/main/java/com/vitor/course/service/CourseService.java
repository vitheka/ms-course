package com.vitor.course.service;

import com.vitor.course.domain.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course findById(UUID id);

    void delete(UUID courseId);

    Course save(Course toCourse);

    void update(Course course);

    List<Course> findAll(UUID userId);
}
