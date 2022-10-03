package com.ssafy.api.controller;

import com.ssafy.api.request.HallRegisterPostReq;
import com.ssafy.api.response.HallRes;
import com.ssafy.api.service.HallService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Hall;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "홀 관리 API", tags = {"Hall"})
@RestController
@RequestMapping("/api/v1/halls")
public class HallController {
	
	@Autowired
	HallService hallService;
	
	@PostMapping()
	@ApiOperation(value = "Hall 등록", notes = "<strong>company_id와 hall_name</strong>를 통해 hall을 등록 한다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) HallRegisterPostReq registerInfo) {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Hall hall = hallService.createHall(registerInfo);
		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}


	@GetMapping("/{companyId}")
	@ApiOperation(value = "hall 검색", notes = "<strong>companyId</strong>를 통해 hall을 검색한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자"),
			@ApiResponse(code = 500, message = "서버 오류")
	})

	public ResponseEntity<List<HallRes>> select(
			@PathVariable("companyId") @ApiParam(value="찾을 아이디", required = true) String companyId) {

		List<Hall> halls = hallService.getHallByCompanyId(companyId);
		List<HallRes> res = new ArrayList<HallRes>();
		for(Hall h : halls){
			HallRes hallres = HallRes.of(h);
			res.add(hallres);
		}


		return ResponseEntity.status(200).body(res);
	}

	@DeleteMapping("/{companyId}/{hallName}")
	@ApiOperation(value = "Hall 삭제", notes = "<strong>업체명과 홀이름</strong>을 통해 홀을 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> delete(
			@PathVariable("companyId") @ApiParam(value="업체 아이디", required = true) String companyId,
			@PathVariable("hallName") @ApiParam(value="hall 이름", required = true) String hallName) {

		hallService.deleteHall(companyId, hallName);

		return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
	}

}
