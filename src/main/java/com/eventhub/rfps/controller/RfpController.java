package com.eventhub.rfps.controller;

import com.eventhub.rfps.dto.RfpRequest;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.service.RfpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rfps")
public class RfpController {
    @Autowired
    private RfpService rfpService;

    @PostMapping
    public ResponseEntity<Rfp> createRfp(@RequestBody @Valid RfpRequest request) {
        Rfp saved = rfpService.createRfp(request);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{rfpId}")
    public ResponseEntity<Rfp> updateRfp(@PathVariable Long rfpId, @RequestBody RfpRequest request) {
        Rfp saved = rfpService.updateRfp(rfpId, request);
        if (saved == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{rfpId}")
    public ResponseEntity<Rfp> getRfpById(@PathVariable Long rfpId) {
        Optional<Rfp> optionalRfp = rfpService.getRfpById(rfpId);
        return optionalRfp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
