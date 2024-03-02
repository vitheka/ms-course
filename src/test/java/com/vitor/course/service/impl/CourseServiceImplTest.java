package com.vitor.course.service.impl;

import com.vitor.course.commons.CourseUtils;
import com.vitor.course.commons.LessonUtils;
import com.vitor.course.commons.ModuleUtils;
import com.vitor.course.domain.Course;
import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import com.vitor.course.exception.NotFoundException;
import com.vitor.course.repository.CourseRepository;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.repository.ModuleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @InjectMocks
    private CourseUtils courseUtils;

    @InjectMocks
    private ModuleUtils moduleUtils;

    @InjectMocks
    private LessonUtils lessonUtils;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private LessonRepository lessonRepository;

    private List<Course> courseList;

    private List<Module> moduleList;

    private List<Lesson> lessonList;

    @BeforeEach
    void init() {
        courseList = courseUtils.createCourseList();
        moduleList = moduleUtils.createModuleList();
        lessonList  = lessonUtils.createLessonList();
    }

    @Test
    @DisplayName("delete() Removes course when successful")
    @Order(8)
    void delete_RemovesCourseRemoveModulesIntoCourseAndRemovesLessonIntoModules_WhenSuccessful() {

        var courseId = UUID.fromString("c9bef94d-0202-4fb5-8b98-5d7f9bf5a134");
        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var courseToDelete = courseList.get(0);
        var moduleToDelete = moduleList;
        var lessonToDelete = lessonList;

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseToDelete));
        when(moduleRepository.findAllModulesIntoCourse(courseId)).thenReturn(moduleToDelete);
        when(lessonRepository.findAllLessonIntoModule(moduleId)).thenReturn(lessonToDelete);

        doNothing().when(lessonRepository).deleteAll(lessonToDelete);
        doNothing().when(moduleRepository).deleteAll(moduleToDelete);
        doNothing().when(courseRepository).delete(courseToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> courseService.delete(courseId));
    }

    @Test
    @DisplayName("save() create a course")
    @Order(1)
    void save_CreatesCourse_WhenSuccessful() {

        var courseToSaved = courseUtils.createCourse();

        when(courseRepository.save(courseToSaved)).thenReturn(courseToSaved);

        var course = courseService.save(courseToSaved);

        assertThat(course).isNotNull().hasNoNullFieldsOrProperties();
        assertThat(course).isEqualTo(courseToSaved);
    }

    @Test
    @DisplayName("update() Update a course")
    @Order(2)
    void update_UpdateCourse_WhenSuccessful() {

        var courseToUpdate = this.courseList.get(0);
        courseToUpdate.setDescription("Course Att Description test.");

        when(courseRepository.findById(courseToUpdate.getCourseId())).thenReturn(Optional.of(courseToUpdate));
        when(courseRepository.save(courseToUpdate)).thenReturn(courseToUpdate);

        assertThatNoException().isThrownBy(() -> courseService.update(courseToUpdate));
    }

    @Test
    @DisplayName("update() throwNotFoundException")
    @Order(3)
    void update_ThrowNotFoundException_WhenNoCourseAreFound() {

        var courseToUpdate = this.courseList.get(0);
        courseToUpdate.setDescription("Course Att Description test.");

        when(courseRepository.findById(courseToUpdate.getCourseId())).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> courseService.update(courseToUpdate))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findAll() Returns a list of Courses")
    @Order(4)
    void findAll_ReturnsAListOfCourses_WhenSuccessful() {

        var userId = new UUID(0L, 0L);

        when(courseRepository.findAll(userId)).thenReturn(this.courseList);

        var courses = courseService.findAll(userId);
        assertThat(courses).isNotNull().isNotEmpty();
        assertThat(courses).hasSameElementsAs(this.courseList);
    }

    @Test
    @DisplayName("findAll() Returns a empty list of Courses")
    @Order(5)
    void findAll_ReturnsAEmptyListOfCourses_WhenSuccessful() {

        var userId = UUID.randomUUID();

        when(courseRepository.findAll(userId)).thenReturn(Collections.emptyList());

        var courses = courseService.findAll(userId);

        assertThat(courses).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById() Returns a Course")
    @Order(6)
    void findById_ReturnCourse_WhenSuccessful() {

        var uuid = UUID.fromString("c9bef94d-0202-4fb5-8b98-5d7f9bf5a134");
        var course = courseList.get(0);

        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));

        var courseFound = courseService.findById(uuid);

        assertThat(courseFound).isNotNull().isEqualTo(course);

    }

    @Test
    @DisplayName("findById() ThrowNotFoundException When Are Course Not Found")
    @Order(7)
    void findById_ThrowNotFoundException_WhenAreCourseNotFound() {

        var invalidUuid = UUID.fromString("c9bef94d-0202-4fb5-8b98-5d7f9bf5a999");

        when(courseRepository.findById(invalidUuid)).thenReturn(Optional.empty());

       assertThatException().isThrownBy(() -> courseService.findById(invalidUuid))
               .isInstanceOf(NotFoundException.class);

    }
}