package com.basharina.taskmanagementsystem.model.dto;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Запрос на получение задач")
public class TaskDataDto {

    @Schema(description = "Заголовок задачи", example = "header 1")
    @Size(max = 50, message = "Заголовок задачи должен содержать не более 50 символов")
    @NotBlank(message = "Заголовок не может быть пустым")
    private String header;

    @Schema(description = "Описание задачи", example = "my_task_description")
    @Size(max = 500, message = "Описание задачи должно содержать не более 500 символов")
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    
    private TaskStatus status;
    private Priority priority;
    private Long executorId;
}
