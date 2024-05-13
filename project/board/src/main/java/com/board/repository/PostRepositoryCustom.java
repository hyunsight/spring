package com.board.repository;

import com.board.dto.MainPostDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
* query dsl 사용 시 3가지 과정
* 1. 사용자정의 인터페이스 구현
* 2. 사용자정의 인터페이스 작성
* 3. Spring Data JPA 리포지토리에서 사용자 정의 인터페이스 상속
*/

public interface PostRepositoryCustom {

    Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable);

    Page<MainPostDto> getMainPostPage(PostSearchDto postSearchDto, Pageable pageable);

}
