package com.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainPostDto {
    private Long id;
    private String title;
    private String content;

    @QueryProjection
    public MainPostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }
}
