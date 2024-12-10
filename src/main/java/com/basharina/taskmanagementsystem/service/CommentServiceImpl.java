package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.converter.CommentDataConverter;
import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.ProfileEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TaskService taskService;
    private ProfileService profileService;
    private CommentDataConverter commentDataConverter;

    @Override
    public CommentEntity addComment(CommentDataDto commentDataDto, Integer id) {
        TaskEntity task = taskService.getById(id);
        ProfileEntity profile = profileService.getById(1);
        CommentEntity comment = commentDataConverter.toEntity(commentDataDto, task, profile);
        return commentRepository.save(comment);
    }
}
