package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.converter.CommentDataConverter;
import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TaskService taskService;
    private UserServiceImpl userService;
    private CommentDataConverter commentDataConverter;

    @Override
    public CommentEntity addCommentFromAuthor(CommentDataDto commentDataDto, Long id) {
        UserEntity user = userService.getCurrentUser();
        TaskEntity task = taskService.getById(id);
//        if (task.getAuthor().getEmail().equals(user.getEmail())) {
            CommentEntity comment = commentDataConverter.toEntity(commentDataDto, task, user);
//        }
//        TaskEntity task = taskService.getByIdAndAuthor(id);
        return commentRepository.save(comment);
    }

//    @Override
//    public CommentEntity addCommentFromExecutor(CommentDataDto commentDataDto, Long id) {
//        TaskEntity task = taskService.getByIdAndExecutor(id);
//        UserEntity author = userService.getCurrentUser();
//        CommentEntity comment = commentDataConverter.toEntity(commentDataDto, task, author);
//        return commentRepository.save(comment);
//    }
}
