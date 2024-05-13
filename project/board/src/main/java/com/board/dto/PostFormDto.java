package com.board.dto;

import com.board.entity.Post;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostFormDto {
    private Long id;

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost() {
        return modelMapper.map(this, Post.class);
    }

    //entity -> dto
    public static PostFormDto of(Post post) {
        return modelMapper.map(post, PostFormDto.class); //ItemFormDto 객체로 리턴
    }

}
