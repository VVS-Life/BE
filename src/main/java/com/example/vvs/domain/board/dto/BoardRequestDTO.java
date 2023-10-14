package com.example.vvs.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
public class BoardRequestDTO {
    // DTO는 client로부터 전송되어온 json형식 데이터를 실제 JAVA 객체로 변화여 DB에 넣기 위해
    // json형식의 데이터를 받는 역할.
    private String title;
    private String content;
    private String isAnswer;
    private String image;
    private String isPublic;
    private Timestamp createdAt;
    private Timestamp modifiedAt;


    // cmd + n으로 생성자 바로 만들면 끝.
    @Builder
    public BoardRequestDTO(String title, String content, String isAnswer, String image, String isPublic, Timestamp createdAt, Timestamp modifiedAt) {
        this.title = title;
        this.content = content;
        this.isAnswer = isAnswer;
        this.image = image;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
