package com.example.vvs.domain.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.member.entity.Member;
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

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private String uploadImageUrl;
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

    private String boardUpload(File uploadFile) throws IOException {

        String fileName = "board" + "/" + UUID.randomUUID();
        uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {

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

    public void deleteFile(String fileName) {

        S3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }




}
