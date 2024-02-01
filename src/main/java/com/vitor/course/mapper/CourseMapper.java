package com.vitor.course.mapper;

import com.vitor.course.domain.Course;
import com.vitor.course.response.CourseGetResponse;
import com.vitor.course.request.CoursePostRequest;
import com.vitor.course.response.CoursePostResponse;
import com.vitor.course.request.CoursePutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {


    Course toCourse(CoursePostRequest request);

    CoursePostResponse toCoursePostResponse(Course toCourse);

    Course toCourse(CoursePutRequest request);

    List<CourseGetResponse> toCoursesGetResponse(List<Course> request);

    CourseGetResponse toCourseGetResponse(Course course);
}
