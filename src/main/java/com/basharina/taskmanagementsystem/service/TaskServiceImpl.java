package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.converter.TaskDataConverter;
import com.basharina.taskmanagementsystem.exception.BaseException;
import com.basharina.taskmanagementsystem.model.dto.*;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private TaskDataConverter taskDataConverter;

    @Override
    public TaskEntity getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new BaseException(String.format("Задача с id = %d не найдена", id)));
    }

    @Override
    public TaskEntity getByIdAndAuthor(Long id) {
        UserEntity author = userService.getCurrentUser();
        return taskRepository.findByIdAndAuthor(id, author).orElseThrow(() ->
                new BaseException(String.format("Задача с id = %d не найдена", id)));
    }

    @Override
    public TaskEntity getByIdAndExecutor(Long id) {
        UserEntity executor = userService.getCurrentUser();
        return taskRepository.findByIdAndExecutor(id, executor).orElseThrow(() ->
                new BaseException(String.format("Задача с id = %d не найдена", id)));
    }

    @Override
    public List<CommentEntity> getComments(Long id) {
        return getByIdAndAuthor(id).getComments();
    }

    @Override
    public Page<TaskEntity> getAllByAuthor(UserEntity author, Pageable pageable,
                                           TaskAuthorFilter filter) {
//        UserEntity executor = userService.getById(executorId);
        return taskRepository.findAllByAuthor(author, pageable, filter.getHeader(), filter.getStatus(),
                filter.getPriority(), filter.getExecutorId());
    }

    @Override
    public Page<TaskEntity> getAllByExecutor(UserEntity executor, Pageable pageable, TaskExecutorFilter filter) {
        return taskRepository.findAllByExecutor(executor, pageable, filter.getHeader(), filter.getStatus(), filter.getPriority());
    }

    @Override
    public TaskEntity addTask(TaskDataDto taskDataDto) {
        UserEntity executor = userService.getById(taskDataDto.getExecutorId());
        UserEntity author = userService.getCurrentUser();
        TaskEntity task = taskDataConverter.toEntity(taskDataDto, executor, author);
        return taskRepository.save(task);
    }

    @Override
    public TaskEntity updateTask(Long id, AdminTaskUpdateDto adminTaskUpdateDto) {
        TaskEntity task = getByIdAndAuthor(id);
        if (adminTaskUpdateDto.getStatus() != null) {
            task.setStatus(adminTaskUpdateDto.getStatus());
        }
        if (adminTaskUpdateDto.getPriority() != null) {
            task.setPriority(adminTaskUpdateDto.getPriority());
        }
        if (adminTaskUpdateDto.getExecutorId() != null) {
            UserEntity executor = userService.getById(adminTaskUpdateDto.getExecutorId());
            task.setExecutor(executor);
        }
        return taskRepository.save(task);
    }

    @Override
    public TaskEntity updateTask(Long id, TaskUpdateDto taskUpdateDto) {
        TaskEntity task = getByIdAndExecutor(id);
        task.setStatus(taskUpdateDto.getStatus());
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        getByIdAndAuthor(id);
        taskRepository.deleteById(id);
    }
}
