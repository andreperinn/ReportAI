package com.andre.reportai.dto;

import java.time.Instant;

public record ReportResponse(
        Long id,
        Long documentId,
        String content,
        Instant createdAt
) {
}
