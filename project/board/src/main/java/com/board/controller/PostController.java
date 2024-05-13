package com.board.controller;

import com.board.dto.MainPostDto;
import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/post/new")
    public String postForm(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/postForm";
    }

    @PostMapping(value = "/post/new")
    public String postNew(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) return "post/postForm";

        try {
            postService.savePost(postFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품 등록 중 에러가 발생했습니다.");
            return "post/postForm";
        }

        return "redirect:/post/board"; // 상품 등록이 문제없이 완료되면 index 화면으로 이동
    }

    @GetMapping(value = "/post/list")
    String postList(Model model, PostSearchDto postSearchDto,
                        @RequestParam(value = "page") Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainPostDto> posts = postService.getMainPostPage(postSearchDto, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("postSearchDto", postSearchDto);
        model.addAttribute("maxPage", 5);

        return "post/postList";
    }

    @GetMapping(value = "/post/{postId}")
    public String postDtl(Model model, @PathVariable("postId") Long postId) {
        PostFormDto postFormDto = postService.getPostDtl(postId);
        model.addAttribute("post", postFormDto);
        return "post/postDtl";
    }

    //상품 수정페이지 보기
    @GetMapping(value = "/post/update/{postId}")
    public String postDtl(@PathVariable("postId") Long postId, Model model) {
        try {
            PostFormDto postFormDto = postService.getPostDtl(postId);
            model.addAttribute("postFormDto", postFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "정보를 가져오는 도중 에러가 발생했습니다.");

            //에러발생시 비어있는 객체를 넘겨준다.
            model.addAttribute("postFormDto", new PostFormDto());
            return "post/postModifyForm"; //등록화면으로 다시 이동
        }

        return "post/postModifyForm";
    }

    @PostMapping(value = "/post/{postId}")
    public String postUpdate(@Valid PostFormDto postFormDto, Model model, BindingResult bindingResult,
                             @PathVariable("postId") Long postId) {

        PostFormDto getPostFormDto = postService.getPostDtl(postId);


        try {
            postService.updatePost(postFormDto);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품 수정중 에러가 발생했습니다.");
            model.addAttribute("postFormDto", getPostFormDto);
            return "post/postModifyForm";
        }

        return "redirect:/";
    }

}
