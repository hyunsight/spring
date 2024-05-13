package com.board.service;

import com.board.dto.MainPostDto;
import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import com.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public long savePost(PostFormDto postFormDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = authentication.getName();

        Post post = postFormDto.createPost();

        post.setRegTime(LocalDateTime.now());
        post.setCreatedBy(loginUser);
        post.setCreatedBy(loginUser);
        postRepository.save(post);

        return post.getId();
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        Page<Post> postPage = postRepository.getPostPage(postSearchDto, pageable);
        return postPage;
    }

    public Page<MainPostDto> getMainPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        Page<MainPostDto> mainPostPage =
                postRepository.getMainPostPage(postSearchDto, pageable);

        return mainPostPage;
    }

    @Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행 X) -> 성능향상
    public PostFormDto getPostDtl(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        //Item Entity -> Dto 변환
        PostFormDto postFormDto = PostFormDto.of(post);


        return postFormDto;
    }


    //상품 수정하기 / update는 생명주기(영속성, 비영속성)와 연관이 있다.
    public Long updatePost(PostFormDto postFormDto) throws Exception {
       Post post = postRepository.findById(postFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        //update 실행
        //★한번 select를 진행하면 엔티티가 영속성 컨텍스트에 저장되고
        //변경감지 기능으로 인해 트랜잭션이 커밋 시점에 엔티티와 DB에 저장된 값이 다르다면 JPA에서 update 해준다.
        post.updatePost(postFormDto);

        return post.getId(); //변경한 item의 id 리턴
    }



}
