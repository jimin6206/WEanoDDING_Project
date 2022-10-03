package com.ssafy.db.repository;

import com.ssafy.db.entity.Guest;
import com.ssafy.db.entity.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByWeddingNum(int weddingNum);
}
