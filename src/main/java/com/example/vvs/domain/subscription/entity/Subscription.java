package com.example.vvs.domain.subscription.entity;

import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private int period;
    @Column(length = 10, nullable = false)
    private int insFee;
    @Column(length = 10, nullable = false)
    @ColumnDefault("가입 대기")
    private String isApproval;
    private String reason;
    @ColumnDefault("sysdate")
    private Timestamp applyDate;
    @ColumnDefault("sysdate")
    private Timestamp joinDate;
    @NotNull
    private Timestamp paymentDate;
    @NotNull
    private Timestamp endDate;

    @Builder
    public Subscription(SubscriptionRequestDTO subscriptionRequestDTO) {
        this.period = subscriptionRequestDTO.getPeriod();
        this.insFee = subscriptionRequestDTO.getInsFee();
        this.isApproval = subscriptionRequestDTO.getIsApproval();
        this.reason = subscriptionRequestDTO.getReason();
        this.applyDate = subscriptionRequestDTO.getApplyDate();
        this.joinDate = subscriptionRequestDTO.getJoinDate();
        this.paymentDate = subscriptionRequestDTO.getPaymentDate();
        this.endDate = subscriptionRequestDTO.getEndDate();
    }
}
