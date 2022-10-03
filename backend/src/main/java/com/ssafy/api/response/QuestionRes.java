package com.ssafy.api.response;

import com.ssafy.db.entity.Company;
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
@ApiModel("QuestionResponse")
public class QuestionRes {
	@ApiModelProperty(name="문의사항 번호", example="5")
	int qNum;
	@ApiModelProperty(name="업체 Id", example="ssafy")
	String companyId;
	@ApiModelProperty(name="문의사항 제목", example="test_title")
	String qTitle;
	@ApiModelProperty(name="문의사항 내용", example="test_content")
	String qContent;
	@ApiModelProperty(name="문의사항 날짜", example="2022-12-21 12:00:00")
	String qDate;
	
	public static QuestionRes of(Question question) {
		QuestionRes res = new QuestionRes();
		res.setQNum(question.getQNum());
		res.setCompanyId(question.getCompanyId());
		res.setQTitle(question.getQTitle());
		res.setQContent(question.getQContent());
		res.setQDate(question.getQDate());
		return res;
	}
}
