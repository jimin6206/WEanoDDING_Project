package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GuestRegisterPostRequest")
public class GuestRegisterPostReq {
    @ApiModelProperty(name="하객 숫자", example="2")
    int guestNum;
    @ApiModelProperty(name="결혼식장 숫자", example="2")
    int weddingNum;
    @ApiModelProperty(name="하객 이름", example="김싸피")
    String guestName;
    @ApiModelProperty(name="하객 소속", example="ssafy")
    String guestGroup;
    @ApiModelProperty(name="축의금", example="50000")
    int guestMoney;
    @ApiModelProperty(name="방명록", example="결혼 축하해")
    String guestMessage;
    @ApiModelProperty(name="신랑 측, 신부 측", example="1")
    int guestSelect;
    @ApiModelProperty(name="하객 전화번호", example="010-0000-0000")
    String guestPhone;
}
