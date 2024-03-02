package com.vitor.course.commons;

import com.vitor.course.domain.Lesson;
import com.vitor.course.domain.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LessonUtils {

    public List<Lesson> createLessonList() {

        var firstLesson = Lesson.builder()
                .lessonId(UUID.fromString("b9fe95db-dec7-4457-b4fb-ae79124a2af1"))
                .title("LPVPG")
                .description("Test list")
                .videoUrl("url.video.com")
                .creationDate(LocalDateTime.now())
                .build();

        return new ArrayList<>(List.of(firstLesson, firstLesson));

    }

    public Lesson createLesson() {

        return Lesson.builder()
                .lessonId(UUID.fromString("c637bd34-74eb-4f8c-8f8a-e0ecffd6d8f8"))
                .title("Lesson lonely")
                .description("Lesson test overrated")
                .videoUrl("url.video.com")
                .creationDate(LocalDateTime.now())
                .build();
    }


}
