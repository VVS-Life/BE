package com.example.vvs.domain.subscription.entity;

import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private int period;
    @Column(length = 10, nullable = false)
    private int insFee;
    @Column(length = 10, nullable = false)
    @ColumnDefault("\"가입 대기\"")
    private String isApproval;
    private String reason;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp applyDate;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp joinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp endDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Subscription(SubscriptionRequestDTO subscriptionRequestDTO, Member member) {
        this.period = subscriptionRequestDTO.getPeriod();
        this.insFee = subscriptionRequestDTO.getInsFee();
        this.isApproval = subscriptionRequestDTO.getIsApproval();
        this.reason = subscriptionRequestDTO.getReason();
        this.applyDate = subscriptionRequestDTO.getApplyDate();
        this.joinDate = subscriptionRequestDTO.getJoinDate();
        this.member = member;
        this.endDate = setEndDate();
    }

    public Timestamp setEndDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Timestamp.valueOf(localDateTime.plusYears(5));
    }

    public void update(SubscriptionRequestDTO subscriptionRequestDTO) {
        this.isApproval = subscriptionRequestDTO.getIsApproval();
    }
}
