package com.example.vvs.domain.reply.entity;

import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.reply.dto.ReplyRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false) //
    private String content;

    @CreatedDate // 생성시간 자동 입력 anotation
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @LastModifiedDate // 수정시간 자동 입력 anotation
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;

    @ManyToOne(fetch = LAZY) // 조인
    @JoinColumn(name = "member_id") // 참조테이블명_컬럼명
    private Member member; // 참조 테이블

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Reply(ReplyRequestDTO replyRequestDTO, Member member, Board board) {
        this.content = replyRequestDTO.getContent();
        this.member = member;
        this.board = board;
    }

    public void update(ReplyRequestDTO replyRequestDTO){
        content = replyRequestDTO.getContent();
    }
}
