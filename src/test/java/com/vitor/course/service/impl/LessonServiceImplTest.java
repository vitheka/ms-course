package com.vitor.course.service.impl;

import com.vitor.course.commons.LessonUtils;
import com.vitor.course.commons.ModuleUtils;
import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import com.vitor.course.repository.LessonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LessonServiceImplTest {

    @InjectMocks
    public LessonServiceImpl lessonService;

    @InjectMocks
    public LessonUtils lessonUtils;

    @InjectMocks
    public ModuleUtils moduleUtils;

    @Mock
    public ModuleServiceImpl moduleService;

    @Mock
    public LessonRepository lessonRepository;

    private List<Lesson> lessonList;

    private List<Module> moduleList;

    @BeforeEach
    void init() {
        lessonList = lessonUtils.createLessonList();
        moduleList = moduleUtils.createModuleList();
    }

    @Test
    @DisplayName("save() creates lesson")
    @Order(1)
    void save_CreatesLesson_WhenSuccessful() {

        var moduleExpected = this.moduleList.get(0);
        var moduleId = moduleExpected.getModuleId();

        var lessonRequest = this.lessonList.get(0);

        when(lessonRepository.save(lessonRequest)).thenReturn(lessonRequest);
        when(moduleService.findById(moduleId)).thenReturn(moduleExpected);

        var lesson = lessonService.save(moduleId, lessonRequest);

        assertThat(lesson)
                .isEqualTo(lessonRequest)
                .hasNoNullFieldsOrProperties();

    }

    @Test
    @DisplayName("findAllByModule() Return List Of Lessons")
    @Order(2)
    void findAllByModule_ReturnListOfLessons_WhenSuccessful() {
        var moduleId = moduleList.get(0).getModuleId();

        when(lessonRepository.findAllLessonIntoModule(moduleId)).thenReturn(this.lessonList);

        var lessons = lessonService.findAllByModule(moduleId);

        assertThat(lessons)
                .isNotNull()
                .hasSameElementsAs(this.lessonList);
    }

    @Test
    @DisplayName("findAllByModule() Return Empty List Of Lessons")
    @Order(3)
    void findAllByModule_ReturnEmptyListOfLessons_WhenLessonsAreNotFound() {

        when(lessonRepository.findAllLessonIntoModule(any())).thenReturn(Collections.emptyList());

        var lessons = lessonService.findAllByModule(any());

        assertThat(lessons).isNotNull().isEmpty();
    }



    @Test
    @DisplayName("findAllByModule() Return Empty List Of Lessons")
    @Order(4)
    void findLessonIntoModule_ReturnLesson_WhenSuccessful() {

        var lessonRequest = lessonUtils.createLesson();
        var moduleId = moduleUtils.createModule().getModuleId();
        var lessonId = lessonUtils.createLesson().getLessonId();

        when(lessonRepository.findLessonIntoModule(moduleId,lessonId))
                .thenReturn(Optional.of(lessonRequest));

        var lessonResponse = lessonService.findLessonIntoModule(moduleId,lessonId);

        assertThat(lessonResponse).isNotNull().isEqualTo(lessonRequest);
    }


}