package com.andre.reportai.ai;

import java.util.List;

public record GeminiSystemInstruction(List<GeminiPart> parts) {

    public static GeminiSystemInstruction of(String text) {
        return new GeminiSystemInstruction(List.of(new GeminiPart(text)));
    }
}
