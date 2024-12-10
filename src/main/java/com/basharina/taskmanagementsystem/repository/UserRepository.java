package com.basharina.taskmanagementsystem.repository;

import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("from UserEntity p where p.role <> 'ADMIN'")
    List<UserEntity> findUsers();
}
