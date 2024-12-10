package com.basharina.taskmanagementsystem.repository;

import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
}
