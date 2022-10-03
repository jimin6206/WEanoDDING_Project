package com.ssafy.api.service;

import com.ssafy.api.request.WeddingUploadPostReq;
import com.ssafy.db.entity.Files;
import com.ssafy.db.repository.FilesRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    FilesRepository filesRepository;
    private static String systemUrl = "/home/ubuntu/download/";

    public void save(Files files) {
        Files f = new Files();
        f.setFileName(files.getFileName());
        f.setFolderName(files.getFolderName());
        f.setFileOriName(files.getFileOriName());
        f.setFileUrl(files.getFileUrl());

        filesRepository.save(f);
    }

    @Override
    public String fileUpload(WeddingUploadPostReq uploadPostReq, List<MultipartFile> files) throws IOException {
        // 첨부 파일이 들어왔을 경우
        // 네이밍 규칙은 더 생각해보자...
        // 중복 처리도 추가해야됨
        if (!files.isEmpty()) {
            for (MultipartFile mFile : files) {
                // 저장을 위한 File 객체
                Files file = new Files();

                // 첨부파일의 실제 이름
                String sourceFileName = mFile.getOriginalFilename();

                // 첨부파일의 확장자
                String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

                // 새로 지정할 폴더명
                String userFolderName = uploadPostReq.getWedding_num() + "_" + uploadPostReq.getWedding_manNm() + "_" + uploadPostReq.getWedding_womanNm();

                File destinationFile;
                String destinationFileName;
                String fileUrl = systemUrl + userFolderName + "/";

                do {
                    destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                    destinationFile = new File(fileUrl + destinationFileName);
                } while (destinationFile.exists());

                destinationFile.getParentFile().mkdirs();
                mFile.transferTo(destinationFile);

                file.setFileName(destinationFileName);
                file.setFolderName(userFolderName);
                file.setFileOriName(sourceFileName);
                file.setFileUrl(fileUrl);
                save(file);
            }
        }

        return "success";
    }

    @Override
    public byte[] displayImage(String fileName) {
        FileInputStream fis = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String fileDir = systemUrl + fileName.replace('$', '/');

        try {
            fis = new FileInputStream(fileDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while ((readCount = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            fis.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("File Error");
        }

        return fileArray;
    }

    @Override
    public Map<Integer, String> getImageList(WeddingUploadPostReq uploadPostReq) {
        // 파일이 저장되어 있다면, 이 이름의 폴더 안에 있을 것이다.
        String userFolderName = uploadPostReq.getWedding_num() + "_" + uploadPostReq.getWedding_manNm() + "_" + uploadPostReq.getWedding_womanNm();
        List<Files> fileList = filesRepository.findByFolderNameEquals(userFolderName);
        if (fileList.isEmpty()) {
            // 사진이 없는 경우
        }

        Map<Integer, String> resultMap = new HashMap<>();

        int index = 1;
        for (Files file : fileList) {
            resultMap.put(index++, file.getFolderName() + "$" + file.getFileName());
        }

        return resultMap;
    }

}
