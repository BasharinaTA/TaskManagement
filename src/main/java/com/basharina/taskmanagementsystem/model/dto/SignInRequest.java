package com.basharina.taskmanagementsystem.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Адрес электронной почты", example = "jon@gmail.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Size(min = 5, max = 320, message = "Адрес электронной почты должен содержать от 5 до 320 символов")
    @Email(message = "Адрес электронной почты должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 5, max = 20, message = "Длина пароля должна быть от 5 до 20 символов")
    private String password;
}
