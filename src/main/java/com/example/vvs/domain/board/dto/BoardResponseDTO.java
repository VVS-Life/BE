package com.example.vvs.domain.board.dto;

import com.example.vvs.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class BoardResponseDTO {
    // DTO는 client로부터 전송되어온 json형식 데이터를 DB에 넣기 위해
    // 실제 JAVA 객체로 변환해주는 기능을 한다.

    //private Long id;
    private Long id;
    private String title;
    private String content;
    private String isAnswer;
    private String image;
    private String isPublic;
    private Timestamp createdAt;
    private Timestamp modifiedAt;


    // BoardRequestDTO -> Board -> BoardResponseDTO
    // 이제는 server에서 client로 보내기 때문에 Java객체를 json으로 보내주기 위해 아래와 객체를 받아 본 응답 객체로 변환한다.

    @Builder
    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.isAnswer = board.getIsAnswer();
        this.image = board.getImage();
        this.isPublic = board.getIsPublic();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
