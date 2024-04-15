package com.example.myLibrary.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoUtil {

    @Value("${postImgLocation}")
    private String postImgLocation;

    //업로드 경로
    public String ckUpload(MultipartHttpServletRequest request) { //Multipart- : 이미지나 파일을 가져올 request에 사용
        //에디터의 name="upload"
        MultipartFile uploadFile = request.getFile("upload");

        //8840ebc8-4fe5.jpg
        String fileName = getFileName(uploadFile); //저장할 파일 이름

        ///Users/hyunmac/Desktop/Project/Tempr/blog/post
        String realPath = getPath(request); //파일을 저장할 경로

        ///Users/hyunmac/Desktop/Project/Tempr/blog/post/8840ebc8-4fe5.jpg
        String savePath = realPath + fileName; //파일을 저장할 실제 경로 + 파일명

        //localhost/images/8840ebc8-4fe5.jpg
        String uploadPath = "/images/" + fileName; //웹에서 보는 경로

        uploadFile(savePath, uploadFile);

        System.out.println("uploadPath: " + uploadPath);

        return uploadPath;
    }

    //파일 업로드 메서드
    private void uploadFile(String savePath, MultipartFile uploadFile) {
        File file = new File(savePath); //savePath: 파일을 저장할 경로

        try {
            uploadFile.transferTo(file); //파일이 서버에 저장됨
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드를 실패했습니다.", e);
        }

    }

    //파일 이름 얻는 메서드
    private String getFileName(MultipartFile uploadFile) {
        //파일의 원래 이름: test.jpg
        String originalFileName = uploadFile.getOriginalFilename(); //파일의 원래 이름

        //이미지의 확장자명 구한다.
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

        //이미지명을 랜덤한 숫자로 생성 (절대 겹치지/중복되지 않도록)
        return UUID.randomUUID() + ext; //중복되지 않는 이미지명을 return 해준다.
    }

    //경로 얻는 메서드
    //- 실제 경로 얻어오는 역할
    private String getPath(MultipartHttpServletRequest request) {
        //실제 서버 내 파일 저장 경로
        //postImgLocation=/Users/hyunmac/Desktop/Project/Tempr/blog/post
        String realPath = postImgLocation + "/";
        System.out.println("realPath: " + realPath);

        Path directoryPath = Paths.get(realPath);

        if (!Files.exists(directoryPath)) { //해당 디렉토리가 존재하지 않는다면
            try {
                Files.createDirectories(directoryPath); //디렉토리 생성
            } catch (Exception e) {
                throw new RuntimeException("업로드할 디렉토리가 존재하지 않습니다.", e);
            }
        }

        return realPath;
    }

}
