package com.vitor.course.service.impl;

import com.vitor.course.domain.Course;
import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import com.vitor.course.exception.NotFoundException;
import com.vitor.course.repository.CourseRepository;
import com.vitor.course.repository.LessonRepository;
import com.vitor.course.repository.ModuleRepository;
import com.vitor.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;


    @Transactional
    @Override
    public Course save(Course course) {

        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return courseRepository.save(course);
    }

    @Override
    public void update(Course course) {
        findById(course.getCourseId());
        courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found!"));
    }

    @Transactional
    @Override
    public void delete(UUID courseId) {

        var courseToDeleted = findById(courseId);

        List<Module> moduleList = moduleRepository.findAllModulesIntoCourse(courseToDeleted.getCourseId());

        if (!moduleList.isEmpty()) {
            for (Module module : moduleList) {
                List<Lesson> lessonList = lessonRepository.findAllLessonIntoModule(module.getModuleId());
                if (!lessonList.isEmpty()) {
                    lessonRepository.deleteAll(lessonList);
                }
            }
            moduleRepository.deleteAll(moduleList);
        }
        courseRepository.delete(courseToDeleted);
    }

}
