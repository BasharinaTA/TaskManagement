package com.basharina.taskmanagementsystem.converter;

import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommentDataConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "task", source = "task")
    @Mapping(target = "author", source = "author")
    CommentEntity toEntity(CommentDataDto comment, TaskEntity task, UserEntity author);
}
