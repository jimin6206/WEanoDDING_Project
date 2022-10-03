package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionRegisterPostRequest")
public class QuestionRegisterPostReq {

    @ApiModelProperty(name="업체 ID", example="ssafy")
    String companyId;
    @ApiModelProperty(name="문의사항제목", example="question test")
    String qTitle;
    @ApiModelProperty(name="문의사항내용", example="question test")
    String qContent;
    @ApiModelProperty(name="문의사항날짜", example="2022-07-21 12:00:00")
    String qDate;
}
