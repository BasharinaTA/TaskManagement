package com.basharina.taskmanagementsystem.repository;

import com.basharina.taskmanagementsystem.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
}
