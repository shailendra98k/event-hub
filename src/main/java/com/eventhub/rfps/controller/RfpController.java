package com.eventhub.rfps.controller;

import com.eventhub.rfps.dto.RfpRequest;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.repository.RfpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rfps")
public class RfpController {
    @Autowired
    private RfpRepository rfpRepository;

    @PostMapping
    public ResponseEntity<Rfp> createRfp(@RequestBody RfpRequest request) {
        Rfp rfp = Rfp.builder()
                .venueId(request.venueId)
                .eventDate(request.eventDate)
                .headcount(request.headcount)
                .budgetMin(request.budgetMin)
                .budgetMax(request.budgetMax)
                .notes(request.notes)
                .status("submitted")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        Rfp saved = rfpRepository.save(rfp);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{rfpId}")
    public ResponseEntity<Rfp> updateRfp(@PathVariable Long rfpId, @RequestBody RfpRequest request) {
        Optional<Rfp> optionalRfp = rfpRepository.findById(rfpId);
        if (optionalRfp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Rfp rfp = optionalRfp.get();
        rfp = Rfp.builder()
                .id(rfp.getId())
                .venueId(request.venueId)
                .buyerUserId(rfp.getBuyerUserId())
                .eventDate(request.eventDate)
                .headcount(request.headcount)
                .budgetMin(request.budgetMin)
                .budgetMax(request.budgetMax)
                .notes(request.notes)
                .status(rfp.getStatus())
                .createdAt(rfp.getCreatedAt())
                .updatedAt(Instant.now())
                .build();
        Rfp saved = rfpRepository.save(rfp);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{rfpId}")
    public ResponseEntity<Rfp> getRfpById(@PathVariable Long rfpId) {
        Optional<Rfp> optionalRfp = rfpRepository.findById(rfpId);
        return optionalRfp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
