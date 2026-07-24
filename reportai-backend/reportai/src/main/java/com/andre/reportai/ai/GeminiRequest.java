package com.andre.reportai.ai;

import java.util.List;

public record GeminiRequest(
        List<GeminiContent> contents,
        GeminiSystemInstruction systemInstruction,
        GeminiGenerationConfig generationConfig
) {
}
