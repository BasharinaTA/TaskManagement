package com.basharina.taskmanagementsystem.model.dto;

import com.basharina.taskmanagementsystem.model.Priority;
import com.basharina.taskmanagementsystem.model.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDataDto {

    private String header;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private Integer executorId;
}
