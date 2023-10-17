package com.example.vvs.domain.subscription.controller;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.subscription.dto.SubscriptionRequestDTO;
import com.example.vvs.domain.subscription.dto.SubscriptionResponseDTO;
import com.example.vvs.domain.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<SubscriptionResponseDTO> postSubscription(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                                                    @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.createSubscription(subscriptionRequestDTO, memberDetails.getMember());
    }

    @GetMapping("/subscription/{subscription-id}")
    public SubscriptionResponseDTO getSubscription(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                   @PathVariable("subscription-id") Long subscriptionId) {
        return subscriptionService.findSubscription(memberDetails.getMember(), subscriptionId);
    }

    @GetMapping("/subscription")
    public Page<SubscriptionResponseDTO> getSubscriptionPage(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                             @PageableDefault Pageable pageable) {
        return subscriptionService.findAllSubscriptionPage(memberDetails.getMember().getId(), pageable);
    }

    @GetMapping("/subscription/admin")
    public Page<SubscriptionResponseDTO> getAdminSubscriptionPage(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                                  @PageableDefault Pageable pageable) {
        return subscriptionService.findAllSubscriptionAdminPage(memberDetails.getMember(), pageable);
    }

    @PatchMapping("/subscription/admin/{subscription-id}")
    public MessageDTO updateSubscription(@PathVariable("subscription-id") Long id,
                                         @RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                         @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.updateSubscription(id, subscriptionRequestDTO, memberDetails.getMember());
    }
}
