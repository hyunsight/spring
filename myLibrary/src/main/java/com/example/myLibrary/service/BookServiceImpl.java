package com.example.myLibrary.service;

import com.example.myLibrary.dao.BookDao;
import com.example.myLibrary.dto.Book;
import com.example.myLibrary.dto.BookRent;
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

    @Override
    public int getDataCount(Map map) throws Exception {
        return bookDao.getDataCount(map);
    }

    @Override
    public Book getBookRead(int bookId) throws Exception {
        return bookDao.getBookRead(bookId);
    }

    @Override
    public void insertBook(Book book) throws Exception {
        bookDao.insertBook(book);
    }

    @Override
    public void updateBook(Book book) throws Exception {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(int bookId) throws Exception {
        bookDao.deleteBook(bookId);
    }

    @Override
    public void rentBook(BookRent bookRent) throws Exception {
        bookDao.rentBook(bookRent);
    }

    @Override
    public Book getBookById(int bookId) throws Exception {
        return bookDao.getBookById(bookId);
    }
}
