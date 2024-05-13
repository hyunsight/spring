package com.original.entity;

import com.original.constant.BookCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_author", nullable = false)
    private String bookAuthor;

    @Lob
    @Column(name = "book_intro", columnDefinition = "longtext")
    private String bookIntro;

    @Column(name = "book_publisher", nullable = false)
    private String bookPublisher;

    @Column(name = "book_pubyear")
    private int bookPubYear;

    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;




}
