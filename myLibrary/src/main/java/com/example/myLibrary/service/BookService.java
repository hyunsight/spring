package com.example.myLibrary.service;

import com.example.myLibrary.dto.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    public List<Book> getBookList(Map map) throws Exception;

    public int getDataCount(Map map) throws Exception;

    public Book getBookRead(int bookId) throws Exception;


}
