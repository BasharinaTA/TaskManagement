package com.basharina.taskmanagementsystem.model.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String text;
    private String author;
    private LocalDateTime created;
}
