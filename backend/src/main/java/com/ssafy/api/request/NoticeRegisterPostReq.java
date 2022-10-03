package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 홀 관리 API ([POST] /api/v1/notices) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("NoticeRegisterPostRequest")
public class NoticeRegisterPostReq {

	@ApiModelProperty(name="공지사항 제목", example="test2")
	String noticeTitle;
	@ApiModelProperty(name="공지사항 내용", example="test2")
	String noticeContent;
	@ApiModelProperty(name="공지사항 Id", example="웨안오딩")
	String noticeId;
	@ApiModelProperty(name="공지사항 날짜", example="2022-07-22 11:00:00")
	String noticeDate;

}
