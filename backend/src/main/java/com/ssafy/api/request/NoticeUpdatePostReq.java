package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원수정 API ([PUT] /api/v1/notices/{noticeNum}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("NoticeUpdatePostRequest")
public class NoticeUpdatePostReq {
    @ApiModelProperty(name="공지사항 title", example="update_title")
    String noticeTitle;
    @ApiModelProperty(name="공지사항 content", example="update_content")
    String noticeContent;
}
