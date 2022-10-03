package com.ssafy.api.response;

import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/questions/{qNum}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("NoticeResponse")
public class NoticeRes {
	@ApiModelProperty(name="공지사항 번호", example="5")
	int noticeNum;
	@ApiModelProperty(name="공지사항 Id", example="웨안오딩")
	String noticeId;
	@ApiModelProperty(name="공지사항 제목", example="test_title")
	String noticeTitle;
	@ApiModelProperty(name="공지사항 내용", example="test_content")
	String noticeContent;
	@ApiModelProperty(name="공지사항 날짜", example="2022-12-21 12:00:00")
	String noticeDate;
	
	public static NoticeRes of(Notice notice) {
		NoticeRes res = new NoticeRes();
		res.setNoticeNum(notice.getNoticeNum());
		res.setNoticeTitle(notice.getNoticeTitle());
		res.setNoticeContent(notice.getNoticeContent());
		res.setNoticeId(notice.getNoticeId());
		res.setNoticeDate(notice.getNoticeDate());
		return res;
	}
}
