package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.converter.TaskConverter;
import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private TaskConverter taskConverter;

    @GetMapping
    public List<TaskDto> getTasks() {
      return taskService.getAll().stream().map(taskConverter::toDto).toList();
    }

    @PostMapping
    public TaskDto addTask(@RequestBody TaskDataDto task) {
      return taskConverter.toDto(taskService.addTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
