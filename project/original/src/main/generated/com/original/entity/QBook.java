package com.original.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 1152733156L;

    public static final QBook book = new QBook("book");

    public final StringPath bookAuthor = createString("bookAuthor");

    public final EnumPath<com.original.constant.BookCategory> bookCategory = createEnum("bookCategory", com.original.constant.BookCategory.class);

    public final StringPath bookIntro = createString("bookIntro");

    public final StringPath bookName = createString("bookName");

    public final StringPath bookPublisher = createString("bookPublisher");

    public final NumberPath<Integer> bookPubYear = createNumber("bookPubYear", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

