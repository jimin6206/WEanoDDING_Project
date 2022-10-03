package com.ssafy.api.controller;

import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.request.CompanyUpdatePostReq;
import com.ssafy.api.response.CompanyRes;
import com.ssafy.api.service.CompanyService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Company;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "업체 API", tags = {"Company"})
@RestController
@RequestMapping("/api/v1/companys")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="회원가입 정보", required = true) CompanyRegisterPostReq registerInfo) {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        Company company = companyService.createCompany(registerInfo);

        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

    @DeleteMapping("/{companyId}")
    @ApiOperation(value = "회원 탈퇴", notes = "<strong>아이디</strong>를 통해 회원탈퇴 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> delete(
            @PathVariable("companyId") @ApiParam(value="회원탈퇴 정보", required = true) String companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
    }

    @PatchMapping("/{companyId}")
    @ApiOperation(value = "회원정보 수정", notes = "<strong>아이디</strong>를 통해 회원정보 수정 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> update(
            @PathVariable("companyId") @ApiParam(value="회원정보 수정", required = true) String companyId
            , @RequestBody @ApiParam(value="회원정보 수정", required = true) CompanyUpdatePostReq updateInfo) {

        companyService.updateCompany(companyId, updateInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{companyId}")
    @ApiOperation(value = "ID 중복 체크", notes = "<strong>아이디</strong>를 통해 중복체크 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> duplicate(
            @PathVariable("companyId") @ApiParam(value="찾을 아이디", required = true) String companyId) {
        Company company = companyService.selectCompany(companyId);
        return company != null ? ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID 입니다.")) :
                ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @GetMapping("/me/{companyId}")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CompanyRes> getCompanyInfo(
            @PathVariable("companyId") @ApiParam(value="업체아이디", required = true) String companyId) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        Company company = companyService.selectCompany(companyId);

        return ResponseEntity.status(200).body(CompanyRes.of(company));
    }
}
