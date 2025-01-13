package com.basharina.taskmanagementsystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDataDto {

    @NotBlank(message = "Текст должен быть заполнен")
    private String text;
}
