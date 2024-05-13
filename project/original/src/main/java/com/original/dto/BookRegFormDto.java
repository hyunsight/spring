package com.original.dto;

import com.original.constant.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRegFormDto {

    private Long id;

    @NotBlank(message = "도서명은 필수 입력입니다.")
    private String bookName;

    @NotBlank(message = "도서저자는 필수 입력입니다.")
    private String bookAuthor;

    private String bookIntro;

    @NotBlank(message = "출판사는 필수 입력입니다.")
    private String bookPublisher;

    private int bookPubYear;

    private BookCategory bookCategory;

}
