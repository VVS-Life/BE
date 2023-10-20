package com.example.vvs.domain.subscription.controller;

import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.security.MemberDetailsImpl;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/product/{product-id}/subscription")
    public ResponseEntity<SubscriptionResponseDTO> postSubscription(@PathVariable("product-id") Long productId,
                                                                    @RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                                                    @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.createSubscription(productId, subscriptionRequestDTO, memberDetails.getMember().getId());
    }

    @GetMapping("/subscription/{subscription-id}")
    public ResponseEntity<SubscriptionResponseDTO> getSubscription(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                                   @PathVariable("subscription-id") Long subscriptionId) {
        return subscriptionService.findSubscription(memberDetails.getMember(), subscriptionId);
    }

    @GetMapping("/subscription")
    public ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptionList(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.findAllSubscriptionList(memberDetails.getMember().getId());
    }

    @GetMapping("/subscription/admin")
    public ResponseEntity<List<SubscriptionResponseDTO>> getAdminSubscriptionPage(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.findAllSubscriptionAdminPage(memberDetails.getMember().getId());
    }

    @PatchMapping("/subscription/admin/accept/{subscription-id}")
    public ResponseEntity<MessageDTO> updateAcceptSubscription(@PathVariable("subscription-id") Long id,
                                                         @RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                                         @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.updateAcceptSubscription(id, subscriptionRequestDTO, memberDetails.getMember().getId());
    }

    @PatchMapping("/subscription/admin/reject/{subscription-id}")
    public ResponseEntity<MessageDTO> updateRejectSubscription(@PathVariable("subscription-id") Long id,
                                                         @RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                                         @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return subscriptionService.updateRejectSubscription(id, subscriptionRequestDTO, memberDetails.getMember().getId());
    }
}
