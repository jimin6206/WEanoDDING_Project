package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("WeddingRegisterPostRequest")
public class WeddingRegisterPostReq {
    @ApiModelProperty(name="결혼식장 숫자", example="5")
    int weddingNum;
    @ApiModelProperty(name="결혼식업체 아이디", example="ssafy")
    String companyId;
    @ApiModelProperty(name="신랑 이름", example="김싸피")
    String weddingManNm;
    @ApiModelProperty(name="신부 이름", example="최싸피")
    String weddingWomanNm;
    @ApiModelProperty(name="신랑 번호", example="010-3333-3333")
    String weddingManPh;
    @ApiModelProperty(name="신부 번호", example="010-4444-4444")
    String weddingWomanPh;
    @ApiModelProperty(name="웨딩 시간", example="2013-12-18 12:12:12")
    String weddingDate;
    @ApiModelProperty(name="홀이름", example="싸피홀")
    String hallName;

}
