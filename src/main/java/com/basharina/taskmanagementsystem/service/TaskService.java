package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;

import java.util.List;

public interface TaskService {

    TaskEntity getById(Integer id);

    List<TaskEntity> getAll();

    List<CommentEntity> getComments(Integer id);

    TaskEntity addTask(TaskDataDto taskDataDto);

    void deleteById(Integer id);
}
