package com.andre.reportai.controller;

import com.andre.reportai.dto.ChatMessageResponse;
import com.andre.reportai.dto.ChatRequest;
import com.andre.reportai.dto.ChatResponse;
import com.andre.reportai.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents/{id}/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse sendMessage(@PathVariable("id") Long documentId, @Valid @RequestBody ChatRequest request) {
        return chatService.sendMessage(documentId, request.message());
    }

    @GetMapping
    public List<ChatMessageResponse> getHistory(@PathVariable("id") Long documentId) {
        return chatService.getHistory(documentId);
    }
}
