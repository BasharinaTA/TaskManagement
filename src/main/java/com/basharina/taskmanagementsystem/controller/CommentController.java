package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.converter.CommentConverter;
import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.dto.CommentDto;
import com.basharina.taskmanagementsystem.service.CommentService;
import com.basharina.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Контроллер комментариев")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;
    private TaskService taskService;
    private CommentConverter commentConverter;

    @Operation(summary = "Получение списка комментариев")
    @GetMapping("/tasks/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
         return taskService.getComments(id).stream().map(commentConverter::toDto).toList();
    }

    @Operation(summary = "Добавление комментария")
    @PostMapping("/tasks/{id}/comments")
    public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDataDto commentDataDto) {
        return commentConverter.toDto(commentService.addCommentFromAuthor(commentDataDto, id));
    }
}
