package com.andre.reportai.service;

import com.andre.reportai.ai.AiClient;
import com.andre.reportai.dto.ChatMessageResponse;
import com.andre.reportai.dto.ChatResponse;
import com.andre.reportai.entity.ChatMessage;
import com.andre.reportai.entity.Document;
import com.andre.reportai.entity.DocumentStatus;
import com.andre.reportai.entity.MessageRole;
import com.andre.reportai.exception.DocumentNotReadyException;
import com.andre.reportai.mapper.ChatMessageMapper;
import com.andre.reportai.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final DocumentService documentService;
    private final AiClient aiClient;
    private final ChatMessageMapper chatMessageMapper;

    public ChatService(ChatMessageRepository chatMessageRepository,
                        DocumentService documentService,
                        AiClient aiClient,
                        ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.documentService = documentService;
        this.aiClient = aiClient;
        this.chatMessageMapper = chatMessageMapper;
    }

    @Transactional
    public ChatResponse sendMessage(Long documentId, String userMessage) {
        Document document = documentService.findEntity(documentId);

        if (document.getStatus() != DocumentStatus.PROCESSED) {
            throw new DocumentNotReadyException(
                    "Documento ainda não está pronto para conversa (status atual: " + document.getStatus() + ")");
        }

        List<ChatMessage> history = chatMessageRepository.findByDocumentIdOrderByCreatedAtAsc(documentId);

        chatMessageRepository.save(ChatMessage.builder()
                .document(document)
                .role(MessageRole.USER)
                .content(userMessage)
                .build());

        String reply = aiClient.chat(document.getExtractedText(), history, userMessage);

        chatMessageRepository.save(ChatMessage.builder()
                .document(document)
                .role(MessageRole.ASSISTANT)
                .content(reply)
                .build());

        return new ChatResponse(reply);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getHistory(Long documentId) {
        // valida que o documento existe antes de listar o histórico
        documentService.findEntity(documentId);

        return chatMessageRepository.findByDocumentIdOrderByCreatedAtAsc(documentId).stream()
                .map(chatMessageMapper::toResponse)
                .toList();
    }
}
