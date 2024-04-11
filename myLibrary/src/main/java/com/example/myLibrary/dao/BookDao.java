package com.example.myLibrary.dao;

import com.example.myLibrary.dto.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookDao {
    public List<Book> getBookList(Map map) throws Exception;

    public int getDataCount(Map map) throws Exception;

    public Book getBookRead(int bookId) throws Exception;
}
