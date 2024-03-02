package com.vitor.course.controller;

import com.vitor.course.commons.FileUtils;
import com.vitor.course.config.IntegrationTestContainers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LessonControllerTestIT extends IntegrationTestContainers {

    private static final String URL = "/v1/modules";

    @Autowired
    private FileUtils fileUtils;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("save() creates lesson")
    @Order(1)
    @Sql(value = {
            "/sql/init_one_course.sql",
            "/sql/init_one_module.sql"}
    )
    void save_CreatesLesson_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("lesson/post-request-lesson-200.json");
        var expectedResponse = fileUtils.readResourceFile("lesson/post-response-lesson-201.json");
        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .log().all()
                .body(request)
                .when()
                .post(URL + "/" + moduleId + "/lessons")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("lessonId", "creationDate")
                .isEqualTo(expectedResponse);

    }
    @Test
    @DisplayName("delete() remove lesson")
    @Order(2)
    @Sql(value = {
            "/sql/init_one_course.sql",
            "/sql/init_one_module.sql",
            "/sql/init_one_lesson.sql"}
    )
    void delete_RemoveLesson_WhenSuccessful() {

        var moduleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var lessonId = UUID.fromString("5ecbe2a1-4fb4-4fc5-bf3e-4bbf1d836963");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .log().all()
                .when()
                .delete(URL + "/" + moduleId + "/lessons/" + lessonId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all();

    }

    @Test
    @DisplayName("delete() remove lesson")
    @Order(3)
    @Sql(value = {
            "/sql/init_one_course.sql",
            "/sql/init_one_module.sql",
            "/sql/init_one_lesson.sql"}
    )
    void delete_ThrowNotFoundException_WhenLessonIsNotFound() throws Exception {

        var expectedResponse = fileUtils.readResourceFile("lesson/lesson-response-not-found-404.json");
        var invalidModuleId = UUID.fromString("7ba33ecf-bf5a-4ac6-8aee-83e36a7ebdbd");
        var lessonId = UUID.randomUUID();

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .log().all()
                .when()
                .delete(URL + "/" + invalidModuleId + "/lessons/" + lessonId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .isEqualTo(expectedResponse);

    }

}