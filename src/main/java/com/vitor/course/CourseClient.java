package com.vitor.course;

import com.vitor.course.response.UserGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class CourseClient {

    private final RestTemplate restTemplate;

    @Value("${ead.api.url.user}")
    private String uri;

    @Value("${ead.api.endpoint}")
    private String endpoint;

    public List<UserGetResponse> getAllUsersByCourse(UUID courseId) {

        var uriFormatted = UriComponentsBuilder.fromUriString(uri + endpoint)
                .queryParam("courseId", courseId)
                .toUriString();
        var responseType = new ParameterizedTypeReference<List<UserGetResponse>>() {};

        var response = restTemplate.exchange(uriFormatted, HttpMethod.GET, null, responseType);

        return response.getBody();
    }
}
