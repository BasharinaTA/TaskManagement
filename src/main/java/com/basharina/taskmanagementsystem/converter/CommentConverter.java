package com.basharina.taskmanagementsystem.converter;

import com.basharina.taskmanagementsystem.model.dto.CommentDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommentConverter {

    @Mapping( target = "author", expression = """
    java(comment.getAuthor().getName() + " " + comment.getAuthor().getSurname())""")
    CommentDto toDto(CommentEntity comment);
}
