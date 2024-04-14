package com.example.myLibrary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRent {
    private int rentId;
    private String rentDate;
    private String rentStatus;
    private int id;
    private int bookId;

    private User user;
    private Book book;
}
