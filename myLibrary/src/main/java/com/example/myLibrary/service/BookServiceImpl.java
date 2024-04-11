package com.example.myLibrary.service;

import com.example.myLibrary.dao.BookDao;
import com.example.myLibrary.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;

    @Override
    public List<Book> getBookList(Map map) throws Exception {
        return bookDao.getBookList(map);
    }
}
