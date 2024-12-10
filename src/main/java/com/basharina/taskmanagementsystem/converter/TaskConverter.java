package com.basharina.taskmanagementsystem.converter;

import com.basharina.taskmanagementsystem.model.dto.TaskDto;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskConverter {

    @Mapping(target = "author", expression = """
            java(task.getAuthor().getName() + " " + task.getAuthor().getSurname())""")
    @Mapping(target = "executor", expression = """
            java(task.getExecutor().getName()  + " " + task.getExecutor().getSurname())""")
    TaskDto toDto(TaskEntity task);
}
