package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.dto.*;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskEntity getById(Long id);

    TaskEntity getByIdAndAuthor(Long id);

    TaskEntity getByIdAndExecutor(Long id);

    Page<TaskEntity> getAllByAuthor(UserEntity author, Pageable pageable, TaskAuthorFilter filter);

    Page<TaskEntity> getAllByExecutor(UserEntity executor, Pageable pageable, TaskExecutorFilter filter);

    List<CommentEntity> getComments(Long id);

    TaskEntity addTask(TaskDataDto taskDataDto);

    TaskEntity updateTask(Long id, AdminTaskUpdateDto adminTaskUpdateDto);

    TaskEntity updateTask(Long id, TaskUpdateDto taskUpdateDto);

    void deleteById(Long id);
}
