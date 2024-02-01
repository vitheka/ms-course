package com.vitor.course.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitor.course.enums.CourseLevel;
import com.vitor.course.enums.CourseStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoursePostRequest {

    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'description' is required")
    private String description;
    @NotNull(message = "The field 'userInstructor' is required")
    private UUID userInstructor;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private CourseLevel courseLevel;
}
