package com.ssafy.api.service;

import com.ssafy.api.request.HallRegisterPostReq;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.repository.HallRepository;
import com.ssafy.db.repository.HallRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *	Hall 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("HallService")
public class HallServiceImpl implements HallService {
	@Autowired
	HallRepository hallRepository;
	
	@Autowired
	HallRepositorySupport hallRepositorySupport;

	@Override
	public Hall createHall(HallRegisterPostReq hallRegisterInfo) {
		Hall hall = new Hall();

		hall.setCompanyId(hallRegisterInfo.getCompanyId());

		hall.setHallName(hallRegisterInfo.getHallName());

		return hallRepository.save(hall);
	}

	@Override
	public List<Hall> getHallByCompanyId(String companyId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		List<Hall> halls = hallRepository.findByCompanyId(companyId);
		return halls;
	}

	@Override
	public void deleteHall(String companyId, String hallName) {
		hallRepository.delete(getHallByCompanyIdAndHallName(companyId, hallName));
	}

	@Override
	public Hall getHallByCompanyIdAndHallName(String companyId, String hallName) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		Optional<Hall> optHall = hallRepository.findByCompanyIdAndHallName(companyId, hallName);
		return optHall.isPresent() ? optHall.get() : null;
	}
}
