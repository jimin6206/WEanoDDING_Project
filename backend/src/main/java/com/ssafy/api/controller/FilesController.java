package com.ssafy.api.controller;

import com.ssafy.api.request.WeddingUploadPostReq;
import com.ssafy.api.service.FilesService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.repository.FilesRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

@Api(value = "파일 업로드 API", tags = {"Upload"})
@RestController
@RequestMapping("/api/v1/image")
public class FilesController {

    /**
     * 1. 파일 구분자 운영체제에 맞게 설정
     */

    @Autowired
    FilesService filesService;

    @Autowired
    FilesRepository filesRepository;

    @PostMapping()
    @ApiOperation(value = "웨딩 사진 업로드", notes = "<strong>결혼식 번호와 신랑 이름, 신부 이름</strong>을 통해 업로드한 이미지를 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> fileUpload(
            @RequestPart @ApiParam(value = "저장할 결혼식 정보") WeddingUploadPostReq uploadPostReq,
            @RequestPart("files") @ApiParam(value = "업로드 되는 파일") List<MultipartFile> files
            ) throws Exception {
        String result = filesService.fileUpload(uploadPostReq, files);

        if (result.equals("success")) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "웨딩 사진 목록 불러오기", notes = "<strong>결혼식 번호와 신랑 이름, 신부 이름</strong>을 통해 업로드된 이미지의 경로를 불러온다.")
    @ResponseBody
    public Map<Integer, String> getImageList(
            @RequestBody @ApiParam(value = "저장할 결혼식 정보") WeddingUploadPostReq uploadPostReq
    ) {
        Map<Integer, String> result = filesService.getImageList(uploadPostReq);

        return result;
    }

    @GetMapping(value = "/{fileName}",  produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value = "웨딩 사진 불러오기", notes = "<strong>파일명(부모 폴더 포함)</strong>을 통해 업로드된 이미지를 불러온다.")
    @ResponseBody
    public byte[] displayImage(@PathVariable("fileName") String fileName) throws IOException {
        byte[] result = filesService.displayImage(fileName);

        return result;
    }

}
