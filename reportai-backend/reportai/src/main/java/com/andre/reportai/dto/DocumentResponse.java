package com.andre.reportai.dto;

import com.andre.reportai.entity.DocumentStatus;

import java.time.Instant;

public record DocumentResponse(
        Long id,
        String filename,
        String contentType,
        Long sizeBytes,
        DocumentStatus status,
        Instant createdAt
) {
}
