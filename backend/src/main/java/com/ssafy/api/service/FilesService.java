package com.ssafy.api.service;

import com.ssafy.api.request.WeddingUploadPostReq;
import com.ssafy.db.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FilesService {

    void save(Files files);
    String fileUpload(WeddingUploadPostReq uploadPostReq, List<MultipartFile> files) throws IOException;
    byte[] displayImage(String fileName);
    Map<Integer, String> getImageList(WeddingUploadPostReq uploadPostReq);

}
