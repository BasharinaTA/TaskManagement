package com.basharina.taskmanagementsystem.model.dto;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminTaskUpdateDto {

    @NotNull(message = "Статус должен быть заполнен")
    private TaskStatus status;

    @NotNull(message = "Приоритет должен быть заполнен")
    private Priority priority;

    @NotNull(message = "Исполнитель должен быть заполнен")
    private Long ExecutorId;
}
