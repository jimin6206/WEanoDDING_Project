package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGuest is a Querydsl query type for Guest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGuest extends EntityPathBase<Guest> {

    private static final long serialVersionUID = 460143446L;

    public static final QGuest guest = new QGuest("guest");

    public final StringPath guestGroup = createString("guestGroup");

    public final StringPath guestMessage = createString("guestMessage");

    public final NumberPath<Integer> guestMoney = createNumber("guestMoney", Integer.class);

    public final StringPath guestName = createString("guestName");

    public final NumberPath<Integer> guestNum = createNumber("guestNum", Integer.class);

    public final StringPath guestPhone = createString("guestPhone");

    public final NumberPath<Integer> guestSelect = createNumber("guestSelect", Integer.class);

    public final NumberPath<Integer> weddingNum = createNumber("weddingNum", Integer.class);

    public QGuest(String variable) {
        super(Guest.class, forVariable(variable));
    }

    public QGuest(Path<? extends Guest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuest(PathMetadata metadata) {
        super(Guest.class, metadata);
    }

}

