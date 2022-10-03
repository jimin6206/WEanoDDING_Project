package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionUpdatePostRequest")
public class QuestionUpdatePostReq {

    @ApiModelProperty(name="문의사항제목", example="문의할게요")
    String qTitle;
    @ApiModelProperty(name="문의사항내용", example="문의할게 정말 많아요")
    String qContent;
}
