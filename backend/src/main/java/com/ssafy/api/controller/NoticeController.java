package com.ssafy.api.controller;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.NoticeUpdatePostReq;
import com.ssafy.api.response.NoticeRes;
import com.ssafy.api.service.NoticeService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Notice;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공지사항 API", tags = {"Notice"})
@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@PostMapping()
	@ApiOperation(value = "공지사항 등록", notes = "<strong>공지사항내용</strong>를 통해 공지사항을 등록 한다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="공지사항 등록", required = true) NoticeRegisterPostReq registerInfo) {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판굳이 Insert 된 유저 정보를 응답하지 않음.
		Notice notice = noticeService.createNotice(registerInfo);

		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@GetMapping("/{noticeNum}")
	@ApiOperation(value = "notice 상세조회", notes = "<strong>noticeNum</strong>를 통해 notice를 상세조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<NoticeRes> getNoticeInfo(@PathVariable("noticeNum") @ApiParam(value="noticeNum", required = true) int noticeNum) {

		Notice notice = noticeService.getNoticeByNoticeNum(noticeNum);

		return ResponseEntity.status(200).body(NoticeRes.of(notice));
	}

	@GetMapping("/list")
	@ApiOperation(value = "notice 조회", notes = "<strong>notice</strong> 전체리스트를 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<NoticeRes>> getNoticeInfo() {

		List<Notice> notices = noticeService.getNoticeAll();
		Collections.sort(notices,(o1,o2)->o2.getNoticeNum()-o1.getNoticeNum());

		List<NoticeRes> res = new LinkedList<>();

		for (Notice notice: notices) {
			NoticeRes noticeRes = NoticeRes.of(notice);
			res.add(noticeRes);
		}
		return ResponseEntity.status(200).body(res);
	}

	@DeleteMapping("/{noticeNum}")
	@ApiOperation(value = "공지사항 삭제", notes = "<strong>noticeNum </strong>을 통해 공지사항을 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> delete(
			@PathVariable("noticeNum") @ApiParam(value="noticeNum", required = true) int noticeNum) {

		noticeService.deleteNotice(noticeNum);

		return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
	}

	@PatchMapping("/{noticeNum}")
	@ApiOperation(value = "공지사항 수정", notes = "<strong>noticeNun</strong>를 통해 공지사항을 수정 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> update(
			@PathVariable("noticeNum") @ApiParam(value="수정할 회원 아이디") int noticeNum,
			@RequestBody @ApiParam(value="수정할 정보", required = true) NoticeUpdatePostReq updateInfo) {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Notice notice = noticeService.updateNotice(noticeNum, updateInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

}
