package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWedding is a Querydsl query type for Wedding
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWedding extends EntityPathBase<Wedding> {

    private static final long serialVersionUID = 671933266L;

    public static final QWedding wedding = new QWedding("wedding");

    public final StringPath companyId = createString("companyId");

    public final StringPath hallName = createString("hallName");

    public final StringPath weddingDate = createString("weddingDate");

    public final StringPath weddingManNm = createString("weddingManNm");

    public final StringPath weddingManPh = createString("weddingManPh");

    public final NumberPath<Integer> weddingNum = createNumber("weddingNum", Integer.class);

    public final StringPath weddingWomanNm = createString("weddingWomanNm");

    public final StringPath weddingWomanPh = createString("weddingWomanPh");

    public QWedding(String variable) {
        super(Wedding.class, forVariable(variable));
    }

    public QWedding(Path<? extends Wedding> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWedding(PathMetadata metadata) {
        super(Wedding.class, metadata);
    }

}

