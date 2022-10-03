package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("WeddingUpdatePostRequest")
public class WeddingUpdatePostReq {

    @ApiModelProperty(name="신랑 이름", example="김수정")
    String weddingManNm;
    @ApiModelProperty(name="신부 이름", example="최수정")
    String weddingWomanNm;
    @ApiModelProperty(name="신랑 번호", example="010-7777-7777")
    String weddingManPh;
    @ApiModelProperty(name="신부 번호", example="010-7777-7777")
    String weddingWomanPh;
    @ApiModelProperty(name="결혼 날짜", example="2022-07-22 11:26:25")
    String weddingDate;
    @ApiModelProperty(name="홀이름", example="럭키홀")
    String hallName;
}
