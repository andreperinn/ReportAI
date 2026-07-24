package com.andre.reportai.ai;

import com.andre.reportai.entity.ChatMessage;

import java.util.List;

// Abstração sobre o provedor de IA. Hoje só existe AnthropicAiClient,
// mas trocar de provedor (ou usar mock em teste) não deve afetar o resto do sistema.
public interface AiClient {

    // Responde uma pergunta do usuário sobre o documento, considerando o histórico da conversa
    String chat(String documentText, List<ChatMessage> history, String userMessage);

    // Gera o relatório final a partir do texto do documento e (opcionalmente) da conversa
    String generateReport(String documentText, List<ChatMessage> history, String style);
}
