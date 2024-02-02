package com.vitor.course.service.impl;

import com.vitor.course.commons.CourseUtils;
import com.vitor.course.domain.Course;
import com.vitor.course.repository.CourseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @InjectMocks
    private CourseUtils courseUtils;

    @Mock
    private CourseRepository courseRepository;


    private List<Course> courseList;

    @BeforeEach
    void init() {
        courseList = courseUtils.createCourseList();
    }

    @Test
    @DisplayName("save() create a course")
    @Order(1)
    void save_CreatesCourse_WhenSuccessful() {
    }

    @Test
    @DisplayName("findAll() Returns a list of Courses")
    @Order(4)
    void findAll_ReturnsAListOfCourses_WhenSuccessful() {

        when(courseRepository.findAll()).thenReturn(this.courseList);

        var courses = courseService.findAll();

        assertThat(courses).isNotNull().isNotEmpty();
        assertThat(courses).hasSameElementsAs(this.courseList);
    }

    @Test
    @DisplayName("findAll() Returns a empty list of Courses")
    @Order(5)
    void findAll_ReturnsAEmptyListOfCourses_WhenSuccessful() {

        BDDMockito.when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        var courses = courseService.findAll();

        Assertions.assertThat(courses).isNotNull().isEmpty();
    }

}