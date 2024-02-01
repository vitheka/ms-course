package com.vitor.course.service.impl;

import com.vitor.course.repository.LessonRepository;
import com.vitor.course.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
}
