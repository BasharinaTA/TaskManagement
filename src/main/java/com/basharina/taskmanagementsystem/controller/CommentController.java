package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.converter.CommentConverter;
import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.dto.CommentDto;
import com.basharina.taskmanagementsystem.service.CommentService;
import com.basharina.taskmanagementsystem.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
    private TaskService taskService;
    private CommentConverter commentConverter;

    @GetMapping("/task/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Integer id) {
         return taskService.getComments(id).stream().map(commentConverter::toDto).toList();
    }

    @PostMapping("/task/{id}/comments")
    public CommentDto addComment(@PathVariable Integer id, @RequestBody CommentDataDto commentDataDto) {
        return commentConverter.toDto(commentService.addComment(commentDataDto, id));
    }
}
