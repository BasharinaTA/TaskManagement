package com.basharina.taskmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageDto<T> {

    private List<T> content;
    private int pageNumber;
    private int limit;
    private long total;
}
