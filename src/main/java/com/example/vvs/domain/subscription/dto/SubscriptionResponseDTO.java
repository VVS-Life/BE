package com.example.vvs.domain.subscription.dto;

import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.subscription.entity.Subscription;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class SubscriptionResponseDTO {

    private Long id;
    private int period;
    private int insFee;
    private String isApproval;
    private String reason;
    private Timestamp applyDate;
    private Timestamp joinDate;
    private Timestamp endDate;

    private String productName;
    private String username;

    @Builder
    public SubscriptionResponseDTO(Subscription subscription, Product product, Member member) {
        this.id = subscription.getId();
        this.period = subscription.getPeriod();
        this.insFee = subscription.getInsFee();
        this.isApproval = subscription.getIsApproval();
        this.reason = subscription.getReason();
        this.applyDate = subscription.getApplyDate();
        this.joinDate = subscription.getJoinDate();
        this.endDate = subscription.getEndDate();
        this.productName = product.getProductName();
        this.username = member.getUserName();
    }
}
