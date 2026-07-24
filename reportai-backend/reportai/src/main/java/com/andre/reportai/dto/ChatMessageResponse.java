package com.andre.reportai.dto;

import com.andre.reportai.entity.MessageRole;

import java.time.Instant;

public record ChatMessageResponse(
        Long id,
        MessageRole role,
        String content,
        Instant createdAt
) {
}
