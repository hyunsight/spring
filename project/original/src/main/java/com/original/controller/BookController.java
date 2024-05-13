package com.original.controller;

import com.original.dto.BookRegFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookController {

    @GetMapping(value = "/admin/book/new")
    public String bookRegForm(Model model) {

        model.addAttribute("bookRegFormDto", new BookRegFormDto());
        return "book/bookRegForm";
    }

}
