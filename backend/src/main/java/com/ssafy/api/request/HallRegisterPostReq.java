package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 홀 관리 API ([POST] /api/v1/halls) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("HallRegisterPostRequest")
public class HallRegisterPostReq {

//	@ApiModelProperty(name="hall Num", example="3")
//	int hallNum;
	@ApiModelProperty(name="업체 ID", example="ssafy")
	String companyId;
	@ApiModelProperty(name="Hall Name", example="test홀")
	String hallName;
}
