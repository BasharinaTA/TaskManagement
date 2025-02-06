package com.basharina.taskmanagementsystem;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.dto.JwtAuthenticationResponse;
import com.basharina.taskmanagementsystem.model.dto.SignInRequest;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.repository.TaskRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerIT {
    public static final String AUTHOR_EMAIL = "ivanov@gmail.com";
    public static final String AUTHOR_PASSWORD = "ivanov";

    @LocalServerPort
    private Integer port;

    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        RestAssured.port = port;
        RestAssured.basePath = "/api";
    }

    @Test
    void signInTest() {
        String token = getUserToken(AUTHOR_EMAIL, AUTHOR_PASSWORD);
        assertNotNull(token);
    }

    private String getUserToken(String email, String password) {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail(email);
        signInRequest.setPassword(password);

        return given()
                .contentType(ContentType.JSON)
                .body(signInRequest)
                .log().all()
                .when()
                .post("/auth/sign-in")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().as(JwtAuthenticationResponse.class).getToken();
    }

    public String getAuthorToken() {
        return "Bearer " + getUserToken(AUTHOR_EMAIL, AUTHOR_PASSWORD);
    }

    protected String getToken(String email, String password) {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail(email);
        signInRequest.setPassword(password);

        return "Bearer "  + given()
                .contentType(ContentType.JSON)
                .body(signInRequest)
                .when()
                .post("/auth/sign-in")
                .then()
                .statusCode(200)
                .extract().as(JwtAuthenticationResponse.class).getToken();
    }

    protected <T> T getResponse(String userToken, Map<String, String> params, String path, TypeRef<T> typeRef) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", userToken)
                .when()
                .params(params)
                .get(path)
                .then()
                .statusCode(200)
                .extract().as(typeRef);
    }

    protected <T, E> T putResponse(String userToken, E body, String path, TypeRef<T> typeRef) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", userToken)
                .body(body)
                .when()
                .put(path)
                .then()
                .statusCode(200)
                .extract().as(typeRef);
    }

    protected TaskEntity createTask(String header, String description, TaskStatus status,
                                  Priority priority, UserEntity author, UserEntity executor) {
        TaskEntity task = new TaskEntity();
        task.setHeader(header);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setAuthor(author);
        task.setExecutor(executor);
        return taskRepository.save(task);
    }
}
