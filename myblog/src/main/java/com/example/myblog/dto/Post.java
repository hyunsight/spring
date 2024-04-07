package com.example.myblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private int postId;
    private String subject;
    private String content;
    private int memberId;
    private String writeDate;
    private String updateDate;
    private int views;
    private String categoryCode;

    //post 가져올 때 카테고리 내용 가져오기 위한 목적
    private Category category;

    private Member member;
}
