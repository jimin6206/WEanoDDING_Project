package com.ssafy.api.controller;

import com.ssafy.api.request.GuestRegisterPostReq;
import com.ssafy.api.response.GuestRes;
import com.ssafy.api.response.HallRes;
import com.ssafy.api.response.QuestionRes;
import com.ssafy.api.service.GuestService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Guest;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Question;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 하객 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "하객 API", tags = {"Guest"})
@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    @Autowired
    GuestService guestService;

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>하객 관련 정보</strong>를 통해 하객 등록을 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="하객등록 정보", required = true) GuestRegisterPostReq registerInfo){

            //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
            Guest guest = guestService.createGuest(registerInfo);

            return ResponseEntity.status(201).body(BaseResponseBody.of(201,"Success"));
    }

    @GetMapping("/list/{weddingNum}")
    @ApiOperation(value = "방명록 조회", notes = "<strong>방명록</strong> 내용을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<GuestRes>> getGuestInfo(
            @PathVariable("weddingNum") @ApiParam(value="찾는 웨딩넘버", required = true) int weddingNum
    ) {

        List<Guest> guests = guestService.getGuestByWeddingNum(weddingNum);

        List<GuestRes> res = new LinkedList<>();

        int size = guests.size();

        for(int i =0; i<5; i++){
            int randomNum = (int)(Math.random()*(size-1));
            GuestRes guestRes = GuestRes.of(guests.get(randomNum));
            res.add(guestRes);
        }

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/listInfo/{weddingNum}")
    @ApiOperation(value = "모든 quest 정보", notes = "<strong>weddingNum</strong>을 통해 모든 하객 정보를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 409, message = "이미 존재하는 사용자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<GuestRes>> getGuestListInfo(
            @PathVariable("weddingNum") @ApiParam(value="찾는 웨딩넘버", required = true) int weddingNum
    ) {

        List<Guest> guests = guestService.getGuestByWeddingNum(weddingNum);

        List<GuestRes> res = new LinkedList<>();

        for(Guest guest : guests){
            GuestRes guestRes = GuestRes.of(guest);
            res.add(guestRes);
        }

        return ResponseEntity.status(200).body(res);
    }


}

