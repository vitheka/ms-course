package com.vitor.course.repository;

import com.vitor.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT curso FROM Course curso JOIN curso.coursesUsers cc WHERE cc.userId = :userId")
    List<Course> findAll(@Param("userId") UUID userId);
}
