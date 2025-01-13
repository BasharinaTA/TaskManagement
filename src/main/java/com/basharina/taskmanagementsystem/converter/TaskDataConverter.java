package com.basharina.taskmanagementsystem.converter;

import com.basharina.taskmanagementsystem.model.dto.TaskDataDto;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskDataConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "status", source = "taskDataDto.status")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "executor", source = "executor")
    TaskEntity toEntity(TaskDataDto taskDataDto, UserEntity executor, UserEntity author);
}
