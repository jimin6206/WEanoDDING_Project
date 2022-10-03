package com.ssafy.db.repository;

import com.ssafy.db.entity.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeddingRepository extends JpaRepository<Wedding, Long>{
    Optional<Wedding> findByWeddingNum(int weddingNum);

    List<Wedding> findByWeddingDateContains(String weddingDate);

    List<Wedding> findByWeddingManNmOrWeddingWomanNm(String weddingManNm, String weddingWomanNm);

    Optional<Wedding> findByWeddingDateAndHallName(String weddingDate, String hallName);

    List<Wedding> findByCompanyId(String companyId);

}

