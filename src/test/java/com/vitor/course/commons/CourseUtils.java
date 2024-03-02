package com.vitor.course.commons;

import com.vitor.course.domain.Course;
import com.vitor.course.enums.CourseLevel;
import com.vitor.course.enums.CourseStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
public class CourseUtils {

    public List<Course> createCourseList() {

        var firstCourse = Course.builder()
                .courseId(UUID.fromString("c9bef94d-0202-4fb5-8b98-5d7f9bf5a134"))
                .name("ingles")
                .description("curso de ingles")
                .imageUrl("")
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .courseStatus(CourseStatus.INPROGRESS)
                .courseLevel(CourseLevel.BEGINNER)
                .userInstructor(UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899"))
                .modules(new HashSet<>())
                .build();

        return new ArrayList<>(List.of(firstCourse, firstCourse));

    }

    public Course createCourse() {

        return Course.builder()
                .courseId(UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899"))
                .name("Spanish")
                .description("Course Spanish")
                .imageUrl("")
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .courseStatus(CourseStatus.INPROGRESS)
                .courseLevel(CourseLevel.BEGINNER)
                .userInstructor(UUID.fromString("c9bef94d-0202-4fb5-8b98-5d7f9bf5a134"))
                .modules(new HashSet<>())
                .build();
    }
}
