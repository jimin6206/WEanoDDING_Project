package com.ssafy.api.controller;

import com.ssafy.api.request.WeddingRegisterPostReq;
import com.ssafy.api.request.WeddingUpdatePostReq;
import com.ssafy.api.response.WeddingRes;
import com.ssafy.api.service.WeddingService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Wedding;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 웨딩 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "웨딩 API", tags = {"Wedding"})
@RestController
@RequestMapping("api/v1/weddings")
public class WeddingController {

    @Autowired
    WeddingService weddingService;

    @PostMapping()
    @ApiOperation(value = "웨딩 등록", notes = "<strong>웨딩 관련 정보</strong>를 통해 웨딩 등록을 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<WeddingRes> register(
            @RequestBody @ApiParam(value = "웨딩정보 등록", required = true) WeddingRegisterPostReq registerInfo) {

        //임의로 리턴된 Wedding 인스턴스. 현재 코드는 웨딩 등록 성공 여부만 판단하기 때문에 굳이 Insert 된 웨딩 정보를 응답하지 않음.
        Wedding wedding = weddingService.createWedding(registerInfo);



        Wedding wedding_res = weddingService.getWeddingByWeddingDateAndHallName(wedding.getWeddingDate(),wedding.getHallName());

        return ResponseEntity.status(201).body(WeddingRes.of(wedding_res));
    }

    @DeleteMapping("/{weddingNum}")
    @ApiOperation(value = "결혼식 삭제", notes = "<strong>WeddingNum</strong>을 통해 결혼식을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> delete(
            @PathVariable("weddingNum") @ApiParam(value = "삭제할 weddingNum", required = true) int weddingNum) {

        weddingService.deleteWedding(weddingNum);

        return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
    }

    @PatchMapping("/{weddingNum}")
    @ApiOperation(value = "결혼식장 정보 수정", notes = "<strong>weddingNum</strong>를 통해 회원정보를 수정 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> update(
            @PathVariable("weddingNum") @ApiParam(value="수정할 weddingNum") int weddingNum,
            @RequestBody @ApiParam(value="수정할 정보", required = true) WeddingUpdatePostReq updateInfo) {

        Wedding wedding = weddingService.updateWedding(weddingNum, updateInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/date/{weddingDate}")
    @ApiOperation(value = "날짜에 의한 웨딩홀 검색", notes = "<strong>weddingDate</strong>를 통해 웨딩홀을 검색한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<WeddingRes>> getWeddingInfoListDate(@PathVariable("weddingDate") @ApiParam(value="weddingDate", required = true) String weddingDate) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        List<Wedding> weddings = weddingService.getWeddingByWeddingDate(weddingDate);
        List<WeddingRes> res = new ArrayList<WeddingRes>();
        for (Wedding wedding: weddings) {
            WeddingRes weddingRes = WeddingRes.of(wedding);
            res.add(weddingRes);
        }

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/name/{weddingManNm}")
    @ApiOperation(value = "이름에 의한 웨딩홀 검색", notes = "<strong>name</strong>를 통해 웨딩홀을 검색한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<WeddingRes>> getWeddingInfoListName(@PathVariable("weddingManNm") @ApiParam(value="weddingManNm", required = true) String weddingManNm) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        List<Wedding> weddings = weddingService.getWeddingByName(weddingManNm, weddingManNm);
        List<WeddingRes> res = new ArrayList<WeddingRes>();
        for (Wedding wedding: weddings) {
            WeddingRes weddingRes = WeddingRes.of(wedding);
            res.add(weddingRes);
        }

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/num/{weddingNum}")
    @ApiOperation(value = "웨팅넘버로 웨딩정보 조회", notes = "<strong>Number</strong>를 통해 웨딩홀을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<WeddingRes> getWeddingInfo(@PathVariable("weddingNum") @ApiParam(value="weddingNum", required = true) int weddingNum) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        Wedding wedding = weddingService.getWeddingByWeddingNum(weddingNum);

        return ResponseEntity.status(200).body(WeddingRes.of(wedding));
    }



    @PatchMapping("/end/{weddingNum}")
    @ApiOperation(value = "결혼식장 완료", notes = "<strong>weddingNum</strong>를 통해 결혼식이 끝난걸로 수정 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> update_weddingEnd(
            @PathVariable("weddingNum") @ApiParam(value="수정할 weddingNum") int weddingNum) {

        Wedding wedding = weddingService.endWedding(weddingNum);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


}

