package com.board.controller;

import com.board.dto.MemberFormDto;
import com.board.entity.Member;
import com.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping(value = "/members/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/members/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";
    }

    @PostMapping(value = "/members/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) return "member/memberForm";

        try {
            //memberFormDto -> Entity
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/"; //회원가입 완료 후 메인페이지로 이동
    }



}
