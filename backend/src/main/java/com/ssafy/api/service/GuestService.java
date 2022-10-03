package com.ssafy.api.service;

import com.ssafy.api.request.GuestRegisterPostReq;
import com.ssafy.db.entity.Guest;

import java.util.List;

/**
 *	하객 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface GuestService {

    // 하객 등록
    Guest createGuest(GuestRegisterPostReq guestRegisterPostInfo);

    List<Guest> getGuestByWeddingNum(int weddingNum);

}
