package com.example.vvs.domain.subscription.dto;

import com.example.vvs.domain.subscription.entity.Subscription;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class SubscriptionResponseDTO {

    private int period;
    private int insFee;
    private String isApproval;
    private String reason;
    private Timestamp applyDate;
    private Timestamp joinDate;
    private Timestamp endDate;

    @Builder
    public SubscriptionResponseDTO(Subscription subscription) {
        this.period = subscription.getPeriod();
        this.insFee = subscription.getInsFee();
        this.isApproval = subscription.getIsApproval();
        this.reason = subscription.getReason();
        this.applyDate = subscription.getApplyDate();
        this.joinDate = subscription.getJoinDate();
        this.endDate = subscription.getEndDate();
    }
}
