package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.converter.TaskConverter;
import com.basharina.taskmanagementsystem.model.dto.*;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.service.TaskService;
import com.basharina.taskmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Контроллер автора")
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminTaskController {

    private TaskService taskService;
    private UserService userService;
    private TaskConverter taskConverter;

    @Operation(summary = "Получение списка задач")
    @GetMapping
    public PageDto<TaskDto> getTasks(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     TaskAuthorFilter filter) {
        UserEntity admin = userService.getCurrentUser();
        Page<TaskEntity> page = taskService.getAllByAuthor(admin, pageable, filter);
        return new PageDto<>(page.getContent().stream()
                .map(taskConverter::toDto).toList(), page.getNumber(), page.getSize(), page.getTotalElements());
    }

    @Operation(summary = "Добавление задачи")
    @PostMapping
    public ResponseEntity<TaskDto> addTask(@Valid @RequestBody TaskDataDto task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskConverter.toDto(taskService.addTask(task)));
    }

    @Operation(summary = "Редактирование задачи")
    @PutMapping("/{id}")
    public TaskDto editTask(@PathVariable Long id, @Valid @RequestBody AdminTaskUpdateDto adminTaskUpdateDto) {
        return taskConverter.toDto(taskService.updateTask(id, adminTaskUpdateDto));
    }

    @Operation(summary = "Удаление задачи")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
