package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getUsers();
}
