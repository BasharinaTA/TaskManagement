package com.basharina.taskmanagementsystem;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.dto.PageDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.repository.TaskRepository;
import com.basharina.taskmanagementsystem.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerIT {
    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6Iml2YW5vdkBnbWFpbC5jb20iLCJzdWIiOiJpdmFub3ZAZ21haWwuY29tIiwiaWF0IjoxNzM1MzAxMDA4LCJleHAiOjE3MzU0NDUwMDh9.kflBveeBvH45cnEgmVE-fiJ_MvCxysj6GKhsd5XztiA";
    public static final String HEADER_NAME = "Header";
    public static final String DESCRIPTION_NAME = "Description";
    public static final String ADMIN_EMAIL = "ivanov@gmail.com";
    public static final String USER_EMAIL = "borisov@gmail.com";
    @LocalServerPort
    private Integer port;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void getTasksTest() {
        TaskEntity task = new TaskEntity();
        task.setHeader(HEADER_NAME);
        task.setDescription(DESCRIPTION_NAME);
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(Priority.MEDIUM);
        task.setAuthor(userRepository.findByEmail(ADMIN_EMAIL).orElseThrow());
        task.setExecutor(userRepository.findByEmail(USER_EMAIL).orElseThrow());
        task = taskRepository.save(task);

        PageDto<TaskDto> result = given()
                .header(new Header("Authorization", TOKEN))
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {});

        assertEquals(1, result.getContent().size());
        TaskDto taskDto = result.getContent().get(0);
        assertEquals(HEADER_NAME, taskDto.getHeader());
        assertEquals(DESCRIPTION_NAME, taskDto.getDescription());
        assertEquals(TaskStatus.OPEN, taskDto.getStatus());
        assertEquals(Priority.MEDIUM, taskDto.getPriority());
        assertEquals(task.getId(), taskDto.getId());
    }
}
