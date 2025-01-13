package com.basharina.taskmanagementsystem.repository;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import com.basharina.taskmanagementsystem.model.entity.TaskEntity;
import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndAuthor(Long id, UserEntity author);

    Optional<TaskEntity> findByIdAndExecutor(Long id, UserEntity executor);

    @Query("""
            select t from TaskEntity t where t.author = :author 
                                    and (:header is null or t.header like %:header%) 
                                    and (:status is null or t.status = :status)
                                    and (:priority is null or t.priority = :priority)
                                    and (:executorId is null or t.executor.id = :executorId)
            """)
    Page<TaskEntity> findAllByAuthor(UserEntity author, Pageable pageable, String header,
                                     TaskStatus status, Priority priority, Long executorId);

    @Query("""
            select t from TaskEntity t where t.executor = :executor
                                        and (:header is null or t.header like %:header%)
                                        and (:status is null or t.status = :status)
                                        and (:priority is null or t.priority = :priority)
            """)
    Page<TaskEntity> findAllByExecutor(UserEntity executor, Pageable pageable, String header,
                                       TaskStatus status, Priority priority);
}
