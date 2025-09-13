package com.eventhub.rfps.controller;

import com.eventhub.rfps.dto.RfpRequest;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.service.RfpService;
import com.eventhub.shared.dto.ErrorDetails;
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
    public ResponseEntity<?> createRfp(@RequestBody @Valid RfpRequest request) {
        try {
            Rfp rfp = rfpService.createRfp(request);
            return ResponseEntity.ok(rfp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ErrorDetails(500, e.getLocalizedMessage()));
        }
    }

    @PutMapping("/{rfpId}")
    public ResponseEntity<?> updateRfp(@PathVariable Long rfpId, @RequestBody RfpRequest request) {
        try {
            Rfp updateRfp = rfpService.updateRfp(rfpId, request);
            if (updateRfp == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updateRfp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ErrorDetails(500, e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{rfpId}")
    public ResponseEntity<Rfp> getRfpById(@PathVariable Long rfpId) {
        Optional<Rfp> optionalRfp = rfpService.getRfpById(rfpId);
        return optionalRfp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
