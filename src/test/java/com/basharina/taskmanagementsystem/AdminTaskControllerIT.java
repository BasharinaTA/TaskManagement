package com.basharina.taskmanagementsystem;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.dto.AdminTaskUpdateDto;
import com.basharina.taskmanagementsystem.model.dto.PageDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.repository.UserRepository;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdminTaskControllerIT extends AuthControllerIT {
    public static final String HEADER_NAME = "Header";
    public static final String DESCRIPTION_NAME = "Description";
    public static final String ADMIN_EMAIL = "ivanov@gmail.com";
    public static final String USER_EMAIL = "borisov@gmail.com";
    public static final String USER_EMAIL_FOR_UPDATE = "maksimov@gmail.com";
    private String authorToken;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void getToken() {
        authorToken = getAuthorToken();
    }

    @Test
    void getTasksTest() {
        TaskEntity task = createTask();

        PageDto<TaskDto> result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorToken)
                .when()
                .get("/admin")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });

        TaskDto taskDto = result.getContent().get(0);
        String author = task.getAuthor().getName() + " " + task.getAuthor().getSurname();
        String executor = task.getExecutor().getName() + " " + task.getExecutor().getSurname();

        assertEquals(1, result.getContent().size());
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getHeader(), taskDto.getHeader());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getPriority(), taskDto.getPriority());
        assertEquals(author, taskDto.getAuthor());
        assertEquals(executor, taskDto.getExecutor());
        assertEquals(task.getCreated(), taskDto.getCreated());
    }

    @Test
    void getTasksFilterAndSort() {
        TaskEntity firstTask = createTask();
        TaskEntity secondTask = createTask();
        Map<String, String> params = Map.of("header", "Header", "status", "OPEN",
                "priority", "MEDIUM", "executorId", "2");

        PageDto<TaskDto> result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorToken)
                .when()
                .params(params)
                .get("/admin")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });

        List<TaskDto> tasksDto = result.getContent();
        TaskDto firstTaskDto = tasksDto.get(0);
        TaskDto secondTaskDto = tasksDto.get(1);
        String author = secondTask.getAuthor().getName() + " " + secondTask.getAuthor().getSurname();
        String executor = firstTask.getExecutor().getName() + " " + firstTask.getExecutor().getSurname();

        assertEquals(2, tasksDto.size());
        assertEquals(10, result.getLimit());
        assertEquals(0, result.getPageNumber());
        assertEquals(2, result.getTotal());

        assertEquals(firstTask.getId(), secondTaskDto.getId());
        assertEquals(firstTask.getDescription(), secondTaskDto.getDescription());
        assertEquals(firstTask.getPriority(), secondTaskDto.getPriority());
        assertEquals(executor, secondTaskDto.getExecutor());

        assertEquals(secondTask.getHeader(), firstTaskDto.getHeader());
        assertEquals(secondTask.getStatus(), firstTaskDto.getStatus());
        assertEquals(author, firstTaskDto.getAuthor());
        assertEquals(secondTask.getCreated(), firstTaskDto.getCreated());

        List<Long> listId = tasksDto.stream().map(TaskDto::getId).toList();
        List<Long> sortedListId = listId.stream().sorted(Comparator.reverseOrder()).toList();
        assertEquals(sortedListId, listId);

    }

    @Test
    void addTaskTest() {
        TaskDataDto taskDataDto = new TaskDataDto();
        taskDataDto.setHeader(HEADER_NAME);
        taskDataDto.setDescription(DESCRIPTION_NAME);
        taskDataDto.setStatus(TaskStatus.OPEN);
        taskDataDto.setPriority(Priority.MEDIUM);
        taskDataDto.setExecutorId(userRepository.findByEmail(USER_EMAIL).orElseThrow().getId());

        TaskDto result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorToken)
                .body(taskDataDto)
                .when()
                .post("/admin")
                .then()
                .statusCode(201)
                .extract().as(TaskDto.class);

        TaskEntity task  = taskRepository.findById(result.getId()).orElseThrow();
        String author = task.getAuthor().getName() + " " + task.getAuthor().getSurname();
        String executor = task.getExecutor().getName() + " " + task.getExecutor().getSurname();

        assertEquals(taskDataDto.getHeader(), result.getHeader());
        assertEquals(taskDataDto.getDescription(), result.getDescription());
        assertEquals(taskDataDto.getStatus(), result.getStatus());
        assertEquals(taskDataDto.getPriority(), result.getPriority());

        assertEquals(task.getId(), result.getId());
        assertEquals(task.getHeader(), result.getHeader());
        assertEquals((task.getDescription()), result.getDescription());
        assertEquals(task.getStatus(), result.getStatus());
        assertEquals(task.getPriority(), result.getPriority());
        assertEquals(author, result.getAuthor());
        assertEquals(executor, result.getExecutor());
        assertEquals(task.getCreated(), result.getCreated());
    }

    @Test
    void editTaskTest() {
        TaskEntity task = createTask();
        AdminTaskUpdateDto taskUpdateDto = new AdminTaskUpdateDto();
        taskUpdateDto.setStatus(TaskStatus.CLOSED);
        taskUpdateDto.setPriority(Priority.HIGH);
        taskUpdateDto.setExecutorId(userRepository.findByEmail(USER_EMAIL_FOR_UPDATE).orElseThrow().getId());

        TaskDto result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorToken)
                .body(taskUpdateDto)
                .when()
                .put("/admin/" + task.getId())
                .then()
                .statusCode(200)
                .extract().as(TaskDto.class);

        TaskEntity updateTask = taskRepository.findById(task.getId()).orElseThrow();
        String author = updateTask.getAuthor().getName() + " " + updateTask.getAuthor().getSurname();
        String executor = updateTask.getExecutor().getName() + " " + updateTask.getExecutor().getSurname();

        assertEquals(updateTask.getId(), result.getId());
        assertEquals(updateTask.getHeader(), result.getHeader());
        assertEquals(updateTask.getDescription(), result.getDescription());
        assertEquals(updateTask.getStatus(), result.getStatus());
        assertEquals(updateTask.getPriority(), result.getPriority());
        assertEquals(author, result.getAuthor());
        assertEquals(executor, result.getExecutor());
        assertEquals(updateTask.getCreated(), result.getCreated());
    }

    @Test
    void deleteTaskTest() {
        TaskEntity task = createTask();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorToken)
                .when()
                .delete("/admin/" + task.getId())
                .then()
                .statusCode(204);
    }

    private TaskEntity createTask() {
        TaskEntity task = new TaskEntity();
        task.setHeader(HEADER_NAME);
        task.setDescription(DESCRIPTION_NAME);
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(Priority.MEDIUM);
        task.setAuthor(userRepository.findByEmail(ADMIN_EMAIL).orElseThrow());
        task.setExecutor(userRepository.findByEmail(USER_EMAIL).orElseThrow());
        return taskRepository.save(task);
    }
}