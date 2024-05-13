package com.board.entity;


import com.board.dto.PostFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
public class Post extends BaseEntity {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="post_title", nullable = false, length = 100) //not null여부, 컬럼 크기지정
    private String title;

    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(name="post_content", nullable = false, columnDefinition = "longtext")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //FK
    private Member member;

    public void updatePost(PostFormDto postFormDto) {
        this.title = postFormDto.getTitle();
        this.content = postFormDto.getContent();
    }

}
