package com.ssafy.api.response;

import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Wedding;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/questions/{qNum}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("WeddingResponse")
public class WeddingRes {
	@ApiModelProperty(name="결혼식 번호", example="1")
	int weddingNum;
	@ApiModelProperty(name="결혼식 업체", example="ssafy")
	String companyId;
	@ApiModelProperty(name="신랑이름", example="김싸피")
	String weddingManNm;
	@ApiModelProperty(name="신부이름", example="최싸피")
	String weddingWomanNm;
	@ApiModelProperty(name="신랑 전화번호", example="010-1111-1111")
	String weddingManPh;
	@ApiModelProperty(name="신부 전화번호", example="010-2222-2222")
	String weddingWomanPh;
	@ApiModelProperty(name="결혼식 날짜", example="2022-12-21 12:00:00")
	String weddingDate;
	@ApiModelProperty(name="홀 이름", example="1홀")
	String hallName;
	@ApiModelProperty(name="end 여부", example="0")
	int weddingEnd;


	public static WeddingRes of(Wedding wedding) {
		WeddingRes res = new WeddingRes();
		res.setWeddingNum(wedding.getWeddingNum());
		res.setCompanyId(wedding.getCompanyId());
		res.setWeddingManNm(wedding.getWeddingManNm());
		res.setWeddingWomanNm(wedding.getWeddingWomanNm());
		res.setWeddingManPh(wedding.getWeddingManPh());
		res.setWeddingWomanPh(wedding.getWeddingWomanPh());
		res.setWeddingDate(wedding.getWeddingDate());
		res.setHallName(wedding.getHallName());
		res.setWeddingEnd(wedding.getWeddingEnd());
		return res;
	}
}
