package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GuestUploadPostRequest")
public class WeddingUploadPostReq {

    @ApiModelProperty(name="결혼식 고유 번호(wedding_num)", example="1")
    String wedding_num;
    @ApiModelProperty(name="신랑 이름(wedding_manNm)", example="김싸피")
    String wedding_manNm;
    @ApiModelProperty(name="신부 이름(wedding_womanNm)", example="이싸피")
    String wedding_womanNm;

}
