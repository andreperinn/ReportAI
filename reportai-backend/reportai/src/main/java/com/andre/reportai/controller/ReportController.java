package com.andre.reportai.controller;

import com.andre.reportai.dto.ReportRequest;
import com.andre.reportai.dto.ReportResponse;
import com.andre.reportai.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents/{id}/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> generate(@PathVariable("id") Long documentId,
                                                     @RequestBody(required = false) ReportRequest request) {
        ReportResponse response = reportService.generate(documentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ReportResponse getLatest(@PathVariable("id") Long documentId) {
        return reportService.getLatestForDocument(documentId);
    }
}
