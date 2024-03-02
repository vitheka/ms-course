package com.vitor.course.service.impl;

import com.vitor.course.commons.LessonUtils;
import com.vitor.course.commons.ModuleUtils;
import com.vitor.course.domain.Course;
import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import com.vitor.course.exception.NotFoundException;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.repository.ModuleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModuleServiceImplTest {

    @InjectMocks
    private ModuleServiceImpl moduleService;

    @InjectMocks
    private ModuleUtils moduleUtils;

    @InjectMocks
    private LessonUtils lessonUtils;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private LessonRepository lessonRepository;

    private List<Module> modules;

    private List<Lesson> lessons;


    @BeforeEach
    void init() {
        modules = moduleUtils.createModuleList();
        lessons = lessonUtils.createLessonList();
    }

    @Test
    @DisplayName("delete() removes module")
    @Order(1)
    void delete_RemovesModule_WhenSuccessful() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var moduleToBeDeleted = modules.get(0);

        when(lessonRepository.findAllLessonIntoModule(moduleId)).thenReturn(this.lessons);
        doNothing().when(lessonRepository).deleteAll(this.lessons);
        doNothing().when(moduleRepository).delete(moduleToBeDeleted);

        assertThatNoException().isThrownBy(() -> moduleService.delete(moduleToBeDeleted));

    }

    @Test
    @DisplayName("save() Create module")
    @Order(2)
    void save_CreateModule_WhenSuccessful() {

        var moduleToBeSaved = modules.get(0);

        when(moduleRepository.save(moduleToBeSaved)).thenReturn(moduleToBeSaved);

        var module = moduleService.save(moduleToBeSaved);

        assertThat(module).isEqualTo(moduleToBeSaved);
        assertThat(module).isNotNull();
    }


    @Test
    @DisplayName("findModuleIntoCourse() Return module")
    @Order(3)
    void findModuleIntoCourse_ReturnModule_WhenSuccessful() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var courseId = UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899");
        var moduleFound = modules.get(0);

        when(moduleRepository.findModuleIntoCourse(courseId,moduleId)).thenReturn(Optional.of(moduleFound));

        var module = moduleService.findModuleIntoCourse(courseId,moduleId);

        assertThat(module).isNotNull();
        assertThat(module).isEqualTo(moduleFound);

    }

    @Test
    @DisplayName("findModuleIntoCourse() Return module")
    @Order(4)
    void findModuleIntoCourse_ThrowNotFoundException_WhenModuleNotFound() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var courseId = UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899");


        when(moduleRepository.findModuleIntoCourse(courseId,moduleId)).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> moduleService.findModuleIntoCourse(courseId,moduleId))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("findAllByCourse() Return List of modules")
    @Order(5)
    void findAllByCourse_ThrowNotFoundException_WhenModuleNotFound() {

        var courseId = UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899");

        when(moduleRepository.findAllModulesIntoCourse(courseId)).thenReturn(this.modules);

        var modules = moduleService.findAllByCourse(courseId);

        assertThat(modules).isNotNull().isNotEmpty();
        assertThat(modules).hasSameElementsAs(this.modules);

    }

    @Test
    @DisplayName("update() Module")
    @Order(6)
    void update_UpdateModule_WhenSuccessful() {

        var courseId = UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899");
        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var moduleRequest = moduleUtils.createModule();
        var course = Course.builder()
                .courseId(UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899")).build();

        moduleRequest.setCourse(course);

        when(moduleRepository.findModuleIntoCourse(courseId,moduleId)).thenReturn(Optional.of(moduleRequest));
        when(moduleRepository.save(moduleRequest)).thenReturn(moduleRequest);

        assertThatNoException()
                .isThrownBy(() -> moduleService.update(courseId,moduleId,moduleRequest));

    }

    @Test
    @DisplayName("update() Module")
    @Order(7)
    void update_ThrowNotFoundException_WhenModuleNotFound() {

        var courseId = UUID.fromString("b0ede08d-d621-4d7c-a821-ca5d302f5899");
        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var moduleRequest = moduleUtils.createModule();


        when(moduleRepository.findModuleIntoCourse(courseId,moduleId)).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> moduleService.update(courseId,moduleId,moduleRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findById() return Module")
    @Order(8)
    void findById_ReturnModule_WhenSuccessful() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var moduleRequest = moduleUtils.createModule();

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(moduleRequest));

        var module = moduleService.findById(moduleId);

        assertThat(module).isNotNull().isEqualTo(moduleRequest);

    }

    @Test
    @DisplayName("findById() Throw Not Found Exception")
    @Order(9)
    void findById_ThrowNotFoundException_WhenModuleNotFound() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> moduleService.findById(moduleId))
                .isInstanceOf(NotFoundException.class);

    }


}