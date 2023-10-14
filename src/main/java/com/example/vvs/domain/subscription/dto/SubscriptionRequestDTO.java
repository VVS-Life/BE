package com.example.vvs.domain.subscription.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class SubscriptionRequestDTO {

    private int period;
    private int insFee;
    private String isApproval;
    private String reason;
    private Timestamp applyDate;
    private Timestamp joinDate;
    private Timestamp endDate;
    private Long memberId;

    @Builder
    public SubscriptionRequestDTO(int period, int insFee, String isApproval, String reason,
                                  Timestamp applyDate, Timestamp joinDate, Timestamp endDate, Long memberId) {
        this.period = period;
        this.insFee = insFee;
        this.isApproval = isApproval;
        this.reason = reason;
        this.applyDate = applyDate;
        this.joinDate = joinDate;
        this.endDate = endDate;
        this.memberId = memberId;
    }
}
