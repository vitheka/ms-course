package com.vitor.course.service.impl;

import com.vitor.course.repository.CourseUserRepository;
import com.vitor.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private CourseUserRepository courseUserRepository;
}
