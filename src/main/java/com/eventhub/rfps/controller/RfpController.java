package com.eventhub.rfps.controller;

import com.eventhub.rfps.dto.RfpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rfps")
public class RfpController {
    @PostMapping("")
    public ResponseEntity<?> submitRfp(@RequestBody RfpRequest request) {
        // TODO: Implement RFP submission (idempotent)
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRfpStatus(@PathVariable Long id, @RequestBody RfpRequest request) {
        // TODO: Implement Accept/Counter/Decline logic
        return ResponseEntity.ok().build();
    }
}
