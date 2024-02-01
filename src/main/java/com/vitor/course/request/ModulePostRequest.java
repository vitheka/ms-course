package com.vitor.course.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModulePostRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
