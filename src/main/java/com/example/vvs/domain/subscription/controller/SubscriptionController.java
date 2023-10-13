package com.example.vvs.domain.subscription.controller;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import com.example.vvs.domain.subscription.dto.SubscriptionResponseDTO;
import com.example.vvs.domain.subscription.service.SubscriptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<SubscriptionResponseDTO> postSubscription(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        return subscriptionService.createSubscription(subscriptionRequestDTO);
    }

    @GetMapping("/subscription/{member-id}")
    public List<SubscriptionResponseDTO> getSubscriptionList(@PathVariable("member-id") Long memberId) {
        return subscriptionService.findAllSubscription(memberId);
    }

    @PatchMapping("/subscription/admin/{subscription-id}")
    public MessageDTO updateSubscription(@PathVariable("subscription-id") Long id, @RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        return subscriptionService.updateSubscription(id, subscriptionRequestDTO);
    }
}
