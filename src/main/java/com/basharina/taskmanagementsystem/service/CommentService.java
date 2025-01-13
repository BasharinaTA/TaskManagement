package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.dto.CommentDataDto;
import com.basharina.taskmanagementsystem.model.entity.CommentEntity;

public interface CommentService {

    CommentEntity addCommentFromAuthor(CommentDataDto comment, Long id);
}
