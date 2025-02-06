package com.basharina.taskmanagementsystem.model.dto;

import com.basharina.taskmanagementsystem.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Запрос обновления задачи для исполнителя")
public class TaskUpdateDto {

    @Schema(description = "Статус", example = "OPEN")
    @Valid
    @NotNull(message = "Статус должен быть заполнен")
    private TaskStatus status;
}
