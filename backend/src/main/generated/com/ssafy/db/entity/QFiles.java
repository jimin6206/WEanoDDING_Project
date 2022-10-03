package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFiles is a Querydsl query type for Files
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFiles extends EntityPathBase<Files> {

    private static final long serialVersionUID = 458868725L;

    public static final QFiles files = new QFiles("files");

    public final StringPath fileName = createString("fileName");

    public final StringPath fileOriName = createString("fileOriName");

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Integer> fno = createNumber("fno", Integer.class);

    public final StringPath folderName = createString("folderName");

    public QFiles(String variable) {
        super(Files.class, forVariable(variable));
    }

    public QFiles(Path<? extends Files> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFiles(PathMetadata metadata) {
        super(Files.class, metadata);
    }

}

