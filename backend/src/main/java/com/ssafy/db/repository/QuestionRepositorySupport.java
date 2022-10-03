package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QNotice;
import com.ssafy.db.entity.QQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class QuestionRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QQuestion qQuestion = QQuestion.question;

}
