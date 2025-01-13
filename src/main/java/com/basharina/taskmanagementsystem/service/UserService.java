package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserEntity getByEmail(String email);

    UserEntity getById(Long id);

    List<UserEntity> getUsers();

    UserDetailsService userDetailsService();

    UserEntity getCurrentUser();
}
