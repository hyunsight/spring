package com.original.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookImg is a Querydsl query type for BookImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookImg extends EntityPathBase<BookImg> {

    private static final long serialVersionUID = -1484974785L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookImg bookImg = new QBookImg("bookImg");

    public final QBook book;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public QBookImg(String variable) {
        this(BookImg.class, forVariable(variable), INITS);
    }

    public QBookImg(Path<? extends BookImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookImg(PathMetadata metadata, PathInits inits) {
        this(BookImg.class, metadata, inits);
    }

    public QBookImg(Class<? extends BookImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBook(forProperty("book")) : null;
    }

}

