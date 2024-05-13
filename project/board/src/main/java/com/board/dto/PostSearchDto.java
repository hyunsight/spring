package com.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchDto {

    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";


}
