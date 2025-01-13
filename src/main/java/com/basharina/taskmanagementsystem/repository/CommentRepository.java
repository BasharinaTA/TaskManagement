package com.basharina.taskmanagementsystem.repository;

import com.basharina.taskmanagementsystem.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
