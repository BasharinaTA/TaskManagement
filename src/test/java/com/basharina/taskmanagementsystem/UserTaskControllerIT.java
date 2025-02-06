package com.basharina.taskmanagementsystem;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.dto.PageDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.model.dto.TaskUpdateDto;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.repository.UserRepository;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserTaskControllerIT extends AuthControllerIT {
    public static final String AUTHOR_EMAIL = "ivanov@gmail.com";
    public static final String EXECUTOR_EMAIL = "smirnova@gmail.com";
    public static final String EXECUTOR_PASSWORD = "smirnova";
    public static final String HEADER = "Header";
    public static final String DESCRIPTION = "Description";
    private String executorToken;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void getToken() {
        executorToken = getToken(EXECUTOR_EMAIL, EXECUTOR_PASSWORD);
    }

    @Test
    void getTasksTest() {
        UserEntity author = userRepository.findByEmail(AUTHOR_EMAIL).orElseThrow();
        UserEntity executor = userRepository.findByEmail(EXECUTOR_EMAIL).orElseThrow();
        TaskEntity task = createTask(HEADER, DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        Map<String, String> params = new HashMap<>();

        PageDto<TaskDto> result = getResponse(executorToken, params, "/tasks", new TypeRef<>() {
        });

        List<TaskDto> tasksDto = result.getContent();
        TaskDto taskDto = tasksDto.get(0);
        String authorName = task.getAuthor().getName() + " " + task.getAuthor().getSurname();
        String executorName = task.getExecutor().getName() + " " + task.getExecutor().getSurname();

        assertEquals(1, tasksDto.size());
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getHeader(), taskDto.getHeader());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getPriority(), taskDto.getPriority());
        assertEquals(authorName, taskDto.getAuthor());
        assertEquals(executorName, taskDto.getExecutor());
        assertEquals(task.getCreated(), taskDto.getCreated());
    }


    @Test
    void getTasksFiler() {
        UserEntity author = userRepository.findByEmail(AUTHOR_EMAIL).orElseThrow();
        UserEntity executor = userRepository.findByEmail(EXECUTOR_EMAIL).orElseThrow();
        TaskEntity firstTask = createTask("Header1", DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        TaskEntity secondTask = createTask("Header2", DESCRIPTION, TaskStatus.IN_PROGRESS, Priority.LOW, author, executor);
        TaskEntity thirdTask = createTask("Header3", DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        TaskEntity fourthTask = createTask("Header4", DESCRIPTION, TaskStatus.OPEN, Priority.HIGH, author, executor);
        Map<String, String> params = Map.of("header", "Header", "status", "OPEN", "priority", "HIGH");

        PageDto<TaskDto> result = getResponse(executorToken, params, "/tasks", new TypeRef<>() {
        });

        List<TaskDto> tasks = result.getContent();
        TaskDto taskDto = tasks.get(0);

        assertEquals(1, tasks.size());
        assertEquals(10, result.getLimit());
        assertEquals(0, result.getPageNumber());
        assertEquals(1, result.getTotal());

        assertEquals(fourthTask.getHeader(), taskDto.getHeader());
        assertEquals(fourthTask.getStatus(), taskDto.getStatus());
        assertEquals(fourthTask.getPriority(), taskDto.getPriority());
    }

    @Test
    void getTaskSort() {
        UserEntity author = userRepository.findByEmail(AUTHOR_EMAIL).orElseThrow();
        UserEntity executor = userRepository.findByEmail(EXECUTOR_EMAIL).orElseThrow();
        createTask("Header1", DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        createTask("Header2", DESCRIPTION, TaskStatus.IN_PROGRESS, Priority.LOW, author, executor);
        createTask("Header3", DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        createTask("Header4", DESCRIPTION, TaskStatus.OPEN, Priority.HIGH, author, executor);
        Map<String, String> params = new HashMap<>();

        PageDto<TaskDto> result = getResponse(executorToken, params, "/tasks", new TypeRef<>() {
        });

        List<TaskDto> tasks = result.getContent();

        assertEquals(4, tasks.size());
        assertEquals(10, result.getLimit());
        assertEquals(0, result.getPageNumber());
        assertEquals(4, result.getTotal());

        List<Long> listId = tasks.stream().map(TaskDto::getId).toList();
        List<Long> sortedListId = listId.stream().sorted(Comparator.reverseOrder()).toList();

        assertEquals(sortedListId, listId);
    }

    @Test
    void editTaskTest() {
        UserEntity author = userRepository.findByEmail(AUTHOR_EMAIL).orElseThrow();
        UserEntity executor = userRepository.findByEmail(EXECUTOR_EMAIL).orElseThrow();
        TaskEntity task = createTask("Header1", DESCRIPTION, TaskStatus.OPEN, Priority.LOW, author, executor);
        TaskUpdateDto taskUpdateDto = new TaskUpdateDto();
        taskUpdateDto.setStatus(TaskStatus.IN_PROGRESS);

        TaskDto result = putResponse(executorToken, taskUpdateDto, "/tasks/" + task.getId(), new TypeRef<>() {
        });

        TaskEntity updateTask = taskRepository.findById(result.getId()).orElseThrow();
        String authorName = updateTask.getAuthor().getName() + " " + updateTask.getAuthor().getSurname();
        String executorName = updateTask.getExecutor().getName() + " " + updateTask.getExecutor().getSurname();

        assertEquals(updateTask.getId(), result.getId());
        assertEquals(updateTask.getHeader(), result.getHeader());
        assertEquals(updateTask.getDescription(), result.getDescription());
        assertEquals(updateTask.getStatus(), result.getStatus());
        assertEquals(updateTask.getPriority(), result.getPriority());
        assertEquals(authorName, result.getAuthor());
        assertEquals(executorName, result.getExecutor());
        assertEquals(updateTask.getCreated(), result.getCreated());
    }
}
