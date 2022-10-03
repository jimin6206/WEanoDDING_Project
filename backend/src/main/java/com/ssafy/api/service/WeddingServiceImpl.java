package com.ssafy.api.service;

import com.ssafy.api.request.WeddingRegisterPostReq;
import com.ssafy.api.request.WeddingUpdatePostReq;
import com.ssafy.db.entity.Guest;
import com.ssafy.db.entity.Wedding;
import com.ssafy.db.repository.GuestRepository;
import com.ssafy.db.repository.WeddingRepository;
import com.ssafy.db.repository.WeddingRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *	웨딩 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("WeddingService")
public class WeddingServiceImpl implements WeddingService {

    @Autowired
    WeddingRepository weddingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    WeddingRepositorySupport weddingRepositorySupport;

    @Override
    public Wedding createWedding(WeddingRegisterPostReq weddingRegisterInfo) {
        Wedding wedding = new Wedding();

        wedding.setWeddingNum(weddingRegisterInfo.getWeddingNum());
        wedding.setCompanyId(weddingRegisterInfo.getCompanyId());
        wedding.setWeddingManNm(weddingRegisterInfo.getWeddingManNm());
        wedding.setWeddingWomanNm(weddingRegisterInfo.getWeddingWomanNm());
        wedding.setWeddingManPh(weddingRegisterInfo.getWeddingManPh());
        wedding.setWeddingWomanPh(weddingRegisterInfo.getWeddingWomanPh());
        wedding.setWeddingDate(weddingRegisterInfo.getWeddingDate());
        wedding.setHallName(weddingRegisterInfo.getHallName());

        return weddingRepository.save(wedding);
    }

    @Override
    public void deleteWedding(int weddingNum) {
        //게스트삭제
        List<Guest> guests = guestRepository.findByWeddingNum(weddingNum);
        for (Guest guest: guests) {
            guestRepository.delete(guest);
        }

        //웨딩삭제
        weddingRepository.delete(getWeddingByWeddingNum(weddingNum));

    }

    @Override
    public Wedding getWeddingByWeddingNum(int weddingNum) {
        // 디비에 웨딩 정보 조회 (weddingNum 를 통한 조회)
        Optional<Wedding> optWedding = weddingRepositorySupport.findWeddingByWeddingNum(weddingNum);

        return  optWedding.isPresent()? optWedding.get() : null;
    }

    @Override
    public Wedding getWeddingByWeddingDateAndHallName(String weddingDate, String hallName) {

        Optional<Wedding> optWedding = weddingRepository.findByWeddingDateAndHallName(weddingDate,hallName);

        return optWedding.isPresent()? optWedding.get() : null;
    }

    @Override
    public Wedding updateWedding(int weddingNum, WeddingUpdatePostReq weddingUpdateInfo) {
        Wedding wedding = getWeddingByWeddingNum(weddingNum);

        wedding.setWeddingManNm(weddingUpdateInfo.getWeddingManNm());
        wedding.setWeddingWomanNm(weddingUpdateInfo.getWeddingWomanNm());
        wedding.setWeddingManPh(weddingUpdateInfo.getWeddingManPh());
        wedding.setWeddingWomanPh(weddingUpdateInfo.getWeddingWomanPh());
        wedding.setWeddingDate(weddingUpdateInfo.getWeddingDate());
        wedding.setHallName(weddingUpdateInfo.getHallName());

        weddingRepository.save(wedding);

        return wedding;
    }

    @Override
    public List<Wedding> getWeddingByWeddingDate(String weddingDate) {
        // 날짜를 통한 웨딩홀정보 조회
        List<Wedding> weddings = weddingRepository.findByWeddingDateContains(weddingDate);

        return weddings;
    }

    @Override
    public List<Wedding> getWeddingByName(String weddingManNm, String weddingWomanNm) {
        // 신랑, 신부 이름을 통한 웨딩홀 정보 조회
        List<Wedding> weddings = weddingRepository.findByWeddingManNmOrWeddingWomanNm(weddingManNm, weddingWomanNm);

        return weddings;
    }

    @Override
    public Wedding endWedding(int weddingNum) {

        Wedding wedding = getWeddingByWeddingNum(weddingNum);

        wedding.setWeddingEnd(1);
        weddingRepository.save(wedding);

        return wedding;
    }

}
