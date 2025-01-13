package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.converter.TaskConverter;
import com.basharina.taskmanagementsystem.model.dto.PageDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.model.dto.TaskExecutorFilter;
import com.basharina.taskmanagementsystem.model.dto.TaskUpdateDto;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Контроллер исполнителя")
@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class UserTaskController {

    private TaskService taskService;
    private UserService userService;
    private TaskConverter taskConverter;

    @Operation(summary = "Получение списка задач")
    @GetMapping
    public PageDto<TaskDto> getTasks(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     TaskExecutorFilter filter) {
        UserEntity executor = userService.getCurrentUser();
        Page<TaskEntity> page = taskService.getAllByExecutor(executor, pageable, filter);
        return new PageDto<>(page.getContent().stream()
                .map(taskConverter::toDto).toList(), page.getNumber(), page.getSize(), page.getTotalElements());
    }

    @Operation(summary = "Редактирование задачи")
    @PutMapping("/{id}")
    public TaskDto editTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDto taskUpdateDto) {
        return taskConverter.toDto(taskService.updateTask(id, taskUpdateDto));
    }
}
