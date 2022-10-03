package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QWedding;
import com.ssafy.db.entity.Wedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 웨딩 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class WeddingRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QWedding qWedding = QWedding.wedding;

    public Optional<Wedding> findWeddingByWeddingNum(int weddingNum){
        Wedding wedding = jpaQueryFactory.select(qWedding).from(qWedding)
                .where(qWedding.weddingNum.eq(weddingNum)).fetchOne();
        if(wedding == null) return Optional.empty();
        return Optional.ofNullable(wedding);
    }

}
