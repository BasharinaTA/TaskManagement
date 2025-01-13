package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.dto.AdminTaskUpdateDto;
import com.basharina.taskmanagementsystem.model.dto.TaskAuthorFilter;
import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.dto.TaskUpdateDto;
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

    Page<TaskEntity> getAllByExecutor(UserEntity executor, Pageable pageable, String header, TaskStatus status, Priority priority);

    List<CommentEntity> getComments(Long id);

    TaskEntity addTask(TaskDataDto taskDataDto);

    TaskEntity updateTask(Long id, AdminTaskUpdateDto adminTaskUpdateDto);

    TaskEntity updateTask(Long id, TaskUpdateDto taskUpdateDto);

    void deleteById(Long id);
}
