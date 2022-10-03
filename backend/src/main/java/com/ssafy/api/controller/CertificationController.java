package com.ssafy.api.controller;

import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.response.CertifyRes;
import com.ssafy.api.response.CompanyRes;
import com.ssafy.api.service.CertifyService;
import com.ssafy.api.service.CompanyService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Company;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;



@Api(value = "본인인증 API", tags = {"Certification"})
@RestController
@RequestMapping("/api/v1/certify")
public class CertificationController {

    @Autowired
    CertifyService certifyService;

    String access_token="";

    @PostMapping("/{imp_uid}")
    @ApiOperation(value = "ID 중복 체크", notes = "<strong>아이디</strong>를 통해 중복체크 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> certifyLogin(
            @PathVariable("imp_uid") @ApiParam(value="imp_uid", required = true) String imp_uid) {


        String certificationsInfo = certifyService.certifyLogin(imp_uid);

        return ResponseEntity.status(201).body(CertifyRes.of(certificationsInfo));
    }

}
