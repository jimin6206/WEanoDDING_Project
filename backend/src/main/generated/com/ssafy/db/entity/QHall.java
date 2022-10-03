package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHall is a Querydsl query type for Hall
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHall extends EntityPathBase<Hall> {

    private static final long serialVersionUID = 846138107L;

    public static final QHall hall = new QHall("hall");

    public final StringPath companyId = createString("companyId");

    public final StringPath hallName = createString("hallName");

    public final NumberPath<Integer> hallNum = createNumber("hallNum", Integer.class);

    public QHall(String variable) {
        super(Hall.class, forVariable(variable));
    }

    public QHall(Path<? extends Hall> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHall(PathMetadata metadata) {
        super(Hall.class, metadata);
    }

}

