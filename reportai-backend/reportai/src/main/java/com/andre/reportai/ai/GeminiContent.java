package com.andre.reportai.ai;

import java.util.List;

// role é "user" ou "model" (Gemini não usa "assistant" como a Anthropic/OpenAI)
public record GeminiContent(String role, List<GeminiPart> parts) {

    public static GeminiContent of(String role, String text) {
        return new GeminiContent(role, List.of(new GeminiPart(text)));
    }
}
