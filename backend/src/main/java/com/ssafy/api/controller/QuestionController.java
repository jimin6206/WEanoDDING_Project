package com.ssafy.api.controller;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.NoticeUpdatePostReq;
import com.ssafy.api.request.QuestionRegisterPostReq;
import com.ssafy.api.request.QuestionUpdatePostReq;
import com.ssafy.api.response.CompanyRes;
import com.ssafy.api.response.NoticeRes;
import com.ssafy.api.response.QuestionRes;
import com.ssafy.api.service.NoticeService;
import com.ssafy.api.service.QuestionService;
import com.ssafy.common.auth.SsafyCompanyDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Company;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Question;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "문의사항 API", tags = {"Question"})
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@PostMapping()
	@ApiOperation(value = "문의사항 등록", notes = "<strong>문의사항</strong>를 통해 공지사항을 등록 한다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="공지사항 등록", required = true) QuestionRegisterPostReq registerInfo) {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판굳이 Insert 된 유저 정보를 응답하지 않음.
		Question question = questionService.createQuestion(registerInfo);
		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@GetMapping("/{qNum}")
	@ApiOperation(value = "question 상세조회", notes = "<strong>qNum</strong>를 통해 question을 상세조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<QuestionRes> getQuestionInfo(@PathVariable("qNum") @ApiParam(value="qNum", required = true) int qNum) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		Question question = questionService.getQuestionByQNum(qNum);


		return ResponseEntity.status(200).body(QuestionRes.of(question));
	}

	@GetMapping("/list")
	@ApiOperation(value = "Question 조회", notes = "<strong>question</strong> 전체리스트를 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<QuestionRes>> getQuestionInfo() {

		List<Question> questions = questionService.getQuestionAll();
		Collections.sort(questions,(o1,o2)->o2.getQNum()-o1.getQNum());

		List<QuestionRes> res = new LinkedList<>();

		for (Question question: questions) {
			QuestionRes questionRes = QuestionRes.of(question);
			res.add(questionRes);
		}
//		Collections.sort(res,Collections.reverseOrder());
		return ResponseEntity.status(200).body(res);
	}


	@DeleteMapping("/{qNum}")
	@ApiOperation(value = "문의사항 삭제", notes = "<strong>qNum </strong>을 통해 공지사항을 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> delete(
			@PathVariable("qNum") @ApiParam(value="qNum", required = true) int qNum) {

		questionService.deleteQuestion(qNum);

		return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
	}

	@PatchMapping("/{qNum}")
	@ApiOperation(value = "문의사항 수정", notes = "<strong>qNum</strong>를 통해 공지사항을 수정 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> update(
			@PathVariable("qNum") @ApiParam(value="qNum") int qNum,
			@RequestBody @ApiParam(value="수정할 정보", required = true) QuestionUpdatePostReq updateInfo) {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Question question = questionService.updateQuestion(qNum, updateInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}


	@GetMapping("/like/{qTitle}")
	@ApiOperation(value = "문의사항 검색(제목으로)", notes = "<strong>qTitle</strong>를 통해 문의사항을 검색한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<QuestionRes>> getQuestionInfoList(@PathVariable("qTitle") @ApiParam(value="qTitle", required = true) String qTitle) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		List<Question> questions = questionService.getQuestionByQTitle(qTitle);
		List<QuestionRes> res = new ArrayList<QuestionRes>();
		for (Question question: questions) {
			QuestionRes questionRes = QuestionRes.of(question);
			res.add(questionRes);
		}

		return ResponseEntity.status(200).body(res);
	}

}
