package com.vitor.course.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitor.course.enums.UserStatus;
import com.vitor.course.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGetResponse {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
