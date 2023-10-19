package com.example.vvs.domain.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.exception.ApiException;
import com.example.vvs.exception.ErrorHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.example.vvs.exception.ErrorHandling.FILE_NOT_FOUND;


@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private String uploadImageUrl = "";
    private final AmazonS3Client S3Client;

    public void uploadBoard(List<MultipartFile> multipartFilelist, BoardRequestDTO boardRequestDTO, Member member) throws IOException {

        for (MultipartFile multipartFile : multipartFilelist) {
            if (multipartFile != null) {

                File uploadFile = convert(multipartFile).orElseThrow(
                        () -> new IllegalArgumentException("파일 전환 실패")
                );
                boardUpload(uploadFile);
            }
        }
    }

    // S3에 업로드하기위해선 MultipartFile을 File객체로 변환
    private Optional<File> convert(MultipartFile multipartFile) throws IOException {

        File convertFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    private void boardUpload(File uploadFile) throws IOException {
        String fileName = "board" + "/" + UUID.randomUUID();
        uploadImageUrl  += uploadImageUrl.equals("") ? putS3(uploadFile, fileName) : ", " + putS3(uploadFile, fileName); // 링크 ","로 이어 붙혀서 하나의 String으로 저장.
        removeNewFile(uploadFile); // putS3에서 AWS에 올릴 때 로컬에 파일생성 후 업로드 하게 되는데, 업로드 후 로컬파일이 필요없으니 이를 제거.
    }

    private String putS3(File uploadFile, String fileName) { // S3에 파일 업로드하고 url를 리턴하는 핵심코드!

        S3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return S3Client.getUrl(bucketName, fileName).toString();
    }

    private void removeNewFile(File targetFile) throws IOException {

        if (targetFile.delete()) {
            log.info("File success to delete");
        } else {
            log.info("File fail to delete");
        }
    }

    public String getUploadImageUrl() {
        return uploadImageUrl;
    }
    public void resetUrl() {
        uploadImageUrl ="";
    }


    public void deleteFile(String fileName) { // 실제 s3에서 삭제 안됌
//        if (!S3Client.doesObjectExist(bucketName, fileName)) {
//            throw new ApiException(FILE_NOT_FOUND);
//        }

        S3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }




}
