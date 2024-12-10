package com.basharina.taskmanagementsystem.service;

import com.basharina.taskmanagementsystem.exception.BaseException;
import com.basharina.taskmanagementsystem.model.entity.ProfileEntity;
import com.basharina.taskmanagementsystem.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;

    @Override
    public ProfileEntity getById(Integer id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new BaseException(String.format("Задача с id = %d не найдена", id)));
    }
}
