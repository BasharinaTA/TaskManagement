package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.converter.TaskDataConverter;
import com.basharina.taskmanagementsystem.exception.BaseException;
import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.ProfileEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ProfileService profileService;
    private TaskDataConverter taskDataConverter;

    @Override
    public TaskEntity getById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new BaseException(String.format("Задача с id = %d не найдена", id)));
    }

    @Override
    public List<CommentEntity> getComments(Integer id) {
        return getById(id).getComments();
    }

    @Override
    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity addTask(TaskDataDto taskDataDto) {
        ProfileEntity executor = profileService.getById(taskDataDto.getExecutorId());
        ProfileEntity author = profileService.getById(1);
        TaskEntity task = taskDataConverter.toEntity(taskDataDto, executor, author);
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Integer id) {
        taskRepository.deleteById(id);
    }
}
