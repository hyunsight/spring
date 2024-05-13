package com.original.controller;

import com.original.dto.MemberRegFormDto;
import com.original.entity.Member;
import com.original.service.MemberService;
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
    public String RegisterMember(Model model) {
        model.addAttribute("memberRegFormDto", new MemberRegFormDto());
        return "member/memberRegForm";
    }

    @PostMapping(value = "/members/new")
    public String RegisterMember(@Valid MemberRegFormDto memberRegFormDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberRegForm";
        }

        try {
            Member member = Member.createMember(memberRegFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberRegForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("LoginErrorMsg", "이메일 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }
}
