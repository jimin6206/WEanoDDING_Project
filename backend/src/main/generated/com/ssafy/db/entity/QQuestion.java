package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -1153870424L;

    public static final QQuestion question = new QQuestion("question");

    public final StringPath companyId = createString("companyId");

    public final StringPath qContent = createString("qContent");

    public final StringPath qDate = createString("qDate");

    public final NumberPath<Integer> qNum = createNumber("qNum", Integer.class);

    public final StringPath qTitle = createString("qTitle");

    public QQuestion(String variable) {
        super(Question.class, forVariable(variable));
    }

    public QQuestion(Path<? extends Question> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestion(PathMetadata metadata) {
        super(Question.class, metadata);
    }

}

