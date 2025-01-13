package com.basharina.taskmanagementsystem.model.dto;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminTaskUpdateDto {

    @NotBlank(message = "Статус должен быть заполнен")
    private TaskStatus status;

    @NotBlank(message = "Приоритет должен быть заполнен")
    private Priority priority;

    @NotBlank(message = "Исполнитель должен быть заполнен")
    private Long ExecutorId;
}
