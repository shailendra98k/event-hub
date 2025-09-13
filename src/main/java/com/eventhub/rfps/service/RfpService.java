package com.eventhub.rfps.service;

import com.eventhub.auth.dto.CustomUserDetails;
import com.eventhub.rfps.dto.RfpRequest;
import com.eventhub.rfps.entity.RFP_STATUS;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.repository.RfpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RfpService {
    @Autowired
    private RfpRepository rfpRepository;

    public Rfp createRfp(RfpRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Rfp rfp = Rfp.builder()
                    .venueId(request.getVenueId())
                    .eventDate(request.getEventDate())
                    .headcount(request.getHeadcount())
                    .budgetMin(request.getBudgetMin())
                    .budgetMax(request.getBudgetMax())
                    .buyerUserId(userId)
                    .notes(request.getNotes())
                    .status(RFP_STATUS.SUBMITTED.name())
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
            return rfpRepository.save(rfp);
        } else {
            throw new IllegalStateException("User not authenticated");
        }


    }

    public Rfp updateRfp(Long rfpId, RfpRequest request) {
        Optional<Rfp> optionalRfp = rfpRepository.findById(rfpId);
        if (optionalRfp.isEmpty()) {
            return null;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Rfp rfp = optionalRfp.get();
            Rfp updateRfRfp = Rfp.builder()
                    .id(rfp.getId())
                    .venueId(request.getVenueId())
                    .buyerUserId(rfp.getBuyerUserId())
                    .eventDate(request.getEventDate())
                    .headcount(request.getHeadcount())
                    .budgetMin(request.getBudgetMin())
                    .budgetMax(request.getBudgetMax())
                    .notes(request.getNotes())
                    .buyerUserId(userId)
                    .status(rfp.getStatus())
                    .createdAt(rfp.getCreatedAt())
                    .updatedAt(Instant.now())
                    .build();
            return rfpRepository.save(updateRfRfp);
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }

    public Optional<Rfp> getRfpById(Long rfpId) {
        return rfpRepository.findById(rfpId);
    }
}

