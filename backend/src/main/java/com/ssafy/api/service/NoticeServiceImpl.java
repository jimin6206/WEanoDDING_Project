package com.ssafy.api.service;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.NoticeUpdatePostReq;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.repository.NoticeRepository;
import com.ssafy.db.repository.NoticeRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *	Hall 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	NoticeRepository noticeRepository;

	@Autowired
	NoticeRepositorySupport noticeRepositorySupport;

	@Override
	public Notice createNotice(NoticeRegisterPostReq noticeRegisterInfo) {
		Notice notice = new Notice();

		notice.setNoticeTitle(noticeRegisterInfo.getNoticeTitle());
		notice.setNoticeContent(noticeRegisterInfo.getNoticeContent());
		notice.setNoticeId(noticeRegisterInfo.getNoticeId());
		notice.setNoticeDate(noticeRegisterInfo.getNoticeDate());

		return noticeRepository.save(notice);
	}

//	@Override
//	public List<Hall> getHallByCompanyId(String companyId) {
//		// 디비에 유저 정보 조회 (userId 를 통한 조회).
//		List<Hall> halls = hallRepository.findByCompanyId(companyId);
//		return halls;
//	}
//
	@Override
	public void deleteNotice(int noticeNum) {
		noticeRepository.delete(getNoticeByNoticeNum(noticeNum));
	}

	@Override
	public Notice getNoticeByNoticeNum(int noticeNum) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		Optional<Notice> optNotice = noticeRepository.findByNoticeNum(noticeNum);
		return optNotice.isPresent() ? optNotice.get() : null;
	}

	@Override
	public Notice updateNotice(int noticeNum, NoticeUpdatePostReq noticeUpdateInfo) {
		Notice notice = getNoticeByNoticeNum(noticeNum);
		notice.setNoticeTitle(noticeUpdateInfo.getNoticeTitle());
		notice.setNoticeContent(noticeUpdateInfo.getNoticeContent());
		noticeRepository.save(notice);

		return notice;
	}

	@Override
	public List<Notice> getNoticeAll() {
		List<Notice> notices = noticeRepository.findAll();

		return notices;
	}
}
