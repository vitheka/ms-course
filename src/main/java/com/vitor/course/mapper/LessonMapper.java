package com.vitor.course.mapper;

import com.vitor.course.domain.Lesson;
import com.vitor.course.request.LessonPostRequest;
import com.vitor.course.response.LessonGetResponse;
import com.vitor.course.response.LessonPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {


    Lesson toLesson(LessonPostRequest request);

    LessonPostResponse toLessonPostResponse(Lesson lessonSaved);

    List<LessonGetResponse> toLessonGetResponse(List<Lesson> lessons);
}
