package com.ssafy.api.service;

import com.ssafy.api.request.WeddingRegisterPostReq;
import com.ssafy.api.request.WeddingUpdatePostReq;
import com.ssafy.db.entity.Wedding;

import java.util.List;

/**
 *	웨딩 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface WeddingService {

    Wedding createWedding(WeddingRegisterPostReq weddingRegisterInfo);

    void deleteWedding(int weddingNum);

    Wedding getWeddingByWeddingNum(int weddingNum);

    Wedding getWeddingByWeddingDateAndHallName(String weddingDate, String hallName);

    Wedding updateWedding(int weddingNum, WeddingUpdatePostReq weddingUpdateInfo);

    List<Wedding> getWeddingByWeddingDate(String weddingDate);

    List<Wedding> getWeddingByName(String weddingManNm, String weddingWomanNm);

    Wedding endWedding(int weddingNum);

}
