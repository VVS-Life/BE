package com.example.vvs.domain.subscription.controller;

import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import com.example.vvs.domain.subscription.dto.SubscriptionResponseDTO;
import com.example.vvs.domain.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    public SubscriptionResponseDTO postSubscription(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        return subscriptionService.createSubscription(subscriptionRequestDTO);
    }
}
