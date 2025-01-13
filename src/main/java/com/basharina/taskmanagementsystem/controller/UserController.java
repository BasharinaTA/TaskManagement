package com.basharina.taskmanagementsystem.controller;

import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@Tag(name = "Контроллер пользователей")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Operation(summary = "Получение списка пользователей")
    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }
}
