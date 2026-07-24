package com.andre.reportai.repository;

import com.andre.reportai.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByDocumentIdOrderByCreatedAtAsc(Long documentId);
}
