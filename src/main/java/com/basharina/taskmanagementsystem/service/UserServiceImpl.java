package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.model.entity.UserEntity;
import com.basharina.taskmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findUsers();
    }


    /**
     * Получение пользователя по email пользователя
     *
     * @return пользователь
     */
    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    @Override
    public UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }
}
