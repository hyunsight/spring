package com.example.myLibrary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String bookIntro;
    private String bookPubDate;
    private String bookRegDate;
    private int id;
    private int categoryId;

    private User user;
    private BookCategory bookCategory;
    private BookRent bookRent;
}
