package com.ssafy.api.service;

import com.ssafy.api.request.GuestRegisterPostReq;
import com.ssafy.db.entity.Guest;
import com.ssafy.db.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *	하객 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("GuestService")
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Guest createGuest(GuestRegisterPostReq guestRegisterPostInfo) {
        Guest guest = new Guest();

        guest.setGuestNum(guestRegisterPostInfo.getGuestNum());
        guest.setWeddingNum(guestRegisterPostInfo.getWeddingNum());
        guest.setGuestName(guestRegisterPostInfo.getGuestName());
        guest.setGuestGroup(guestRegisterPostInfo.getGuestGroup());
        guest.setGuestMoney(guestRegisterPostInfo.getGuestMoney());
        guest.setGuestMessage(guestRegisterPostInfo.getGuestMessage());
        guest.setGuestSelect(guestRegisterPostInfo.getGuestSelect());
        guest.setGuestPhone(guestRegisterPostInfo.getGuestPhone());

        return guestRepository.save(guest);
    }

    @Override
    public List<Guest> getGuestByWeddingNum(int weddingNum) {
        List<Guest> guests = guestRepository.findByWeddingNum(weddingNum);
        return guests;
    }

}
