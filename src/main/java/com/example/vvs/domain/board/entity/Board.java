package com.example.vvs.domain.board.entity;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert // 디폴트값으로 저장하기 위해 null이 주어지면 제외 해주는 어노테이션
@EntityListeners(AuditingEntityListener.class) // 생성/수정시간 자동 입력을 위한 antation(application에 @EnableJpaAuditing 도 넣어줘야함)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false) //
    private String content;
    @Column(length = 10, nullable = false)
    @ColumnDefault("\"답변 대기\"")
    private String isAnswer;
    private String image;
    @Column(length = 10, nullable = false)
    private String isPublic;
    @CreatedDate // 생성시간 자동 입력 anotation
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @LastModifiedDate // 수정시간 자동 입력 anotation
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;

    @ManyToOne(fetch = LAZY) // 조인
    @JoinColumn(name = "member_id") // 참조테이블명_컬럼명
    private Member member; // 참조 테이블


    // BoardRequestDTO가 json형식의 데이터를 받았으면
    // Board 객체의 생성자를 통해서 Board객체를 만들어준다.
    @Builder
    public Board(BoardRequestDTO boardRequestDTO, String image , Member member) {
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.isAnswer = boardRequestDTO.getIsAnswer(); // 등록시 얘는 null이지만 DB에는 디폴트값인 "답변 대기"로 들어간다.
        this.image = image; //boardRequestDTO.getImage();
        this.isPublic = boardRequestDTO.getIsPublic();
        this.createdAt = boardRequestDTO.getCreatedAt();
        this.modifiedAt = boardRequestDTO.getModifiedAt();
        this.member = member; // 참조 테이블
    }

    // 이렇게 적으면 update메소드 호출할 때도 변수 하나씩 get해줘야하지만, 아래 새메소드처럼 객체를 주면 바로 처리완.
    //public void update(String titile, String content, String isPublic, String image) {
    //    this.title = titile;
    //    this.content = content;
    //    this.isPublic = isPublic;
    //    this.image = image;
    //}
    public void update(BoardRequestDTO boardRequestDTO) {
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.isPublic = boardRequestDTO.getIsPublic();
        this.image = boardRequestDTO.getImage();
    }
}
