package com.andre.reportai.dto;

// "reply" — nome do campo já casado com o front-end (aiService.js espera data.reply)
public record ChatResponse(
        String reply
) {
}
