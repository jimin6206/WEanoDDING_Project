package com.ssafy.db.repository;

import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByqNum(int qNum);

    List<Question> findByqTitleContains(String qTitle);

    List<Question> findByCompanyId(String companyId);

    List<Question> findAll();
}