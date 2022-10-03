package com.ssafy.api.service;

import com.ssafy.api.request.HallRegisterPostReq;
import com.ssafy.db.entity.Hall;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface HallService {

	Hall createHall(HallRegisterPostReq hallRegisterInfo);
	List<Hall> getHallByCompanyId(String companyId);
	void deleteHall(String companyId, String hallName);

	Hall getHallByCompanyIdAndHallName(String companyId, String hallName);

}
