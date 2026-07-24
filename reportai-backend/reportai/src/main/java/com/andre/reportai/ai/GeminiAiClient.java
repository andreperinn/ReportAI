package com.andre.reportai.ai;

import com.andre.reportai.entity.ChatMessage;
import com.andre.reportai.exception.AiServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiAiClient implements AiClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiAiClient.class);

    private static final String CHAT_SYSTEM_PROMPT = """
            Você é um assistente que responde perguntas EXCLUSIVAMENTE com base no
            conteúdo do documento fornecido. Se a resposta não estiver no documento,
            diga isso claramente em vez de inventar informação. Responda em português,
            de forma direta e objetiva.
            """;

    private static final String REPORT_SYSTEM_PROMPT = """
            Você gera relatórios profissionais em português, em formato Markdown,
            a partir do conteúdo de um documento e (quando houver) do histórico de
            perguntas e respostas sobre ele. O relatório deve ter: um resumo executivo,
            os principais pontos identificados, e conclusões. Seja objetivo e evite
            floreios — o leitor é alguém que precisa decidir algo com base nisso.
            """;

    private final RestClient restClient;
    private final AiProperties aiProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiAiClient(RestClient geminiRestClient, AiProperties aiProperties) {
        this.restClient = geminiRestClient;
        this.aiProperties = aiProperties;
    }

    @Override
    public String chat(String documentText, List<ChatMessage> history, String userMessage) {
        List<GeminiContent> contents = new ArrayList<>();

        // Texto do documento entra como primeiro turno "user" pra servir de contexto,
        // mantendo o histórico real da conversa separado e limpo.
        contents.add(GeminiContent.of("user", "Documento:\n\n" + documentText));
        contents.add(GeminiContent.of("model", "Entendido, li o documento. Pode perguntar."));

        for (ChatMessage msg : history) {
            String role = msg.getRole().name().equals("USER") ? "user" : "model";
            contents.add(GeminiContent.of(role, msg.getContent()));
        }

        contents.add(GeminiContent.of("user", userMessage));

        return call(CHAT_SYSTEM_PROMPT, contents);
    }

    @Override
    public String generateReport(String documentText, List<ChatMessage> history, String style) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Documento:\n\n").append(documentText).append("\n\n");

        if (history != null && !history.isEmpty()) {
            prompt.append("Perguntas e respostas feitas sobre o documento:\n");
            for (ChatMessage msg : history) {
                prompt.append("- ").append(msg.getRole()).append(": ").append(msg.getContent()).append("\n");
            }
        }

        if (style != null && !style.isBlank()) {
            prompt.append("\nEstilo solicitado para o relatório: ").append(style).append("\n");
        }

        prompt.append("\nGere o relatório completo agora.");

        List<GeminiContent> contents = List.of(GeminiContent.of("user", prompt.toString()));
        return call(REPORT_SYSTEM_PROMPT, contents);
    }

    private String call(String systemPrompt, List<GeminiContent> contents) {
        try {
            GeminiRequest request = new GeminiRequest(
                    contents,
                    GeminiSystemInstruction.of(systemPrompt),
                    new GeminiGenerationConfig(aiProperties.getMaxTokens())
            );

            String rawResponse = restClient.post()
                    .uri("/{model}:generateContent", aiProperties.getModel())
                    .body(request)
                    .retrieve()
                    .body(String.class);

            return extractText(rawResponse);
        } catch (AiServiceException e) {
            log.error("Falha ao chamar a API do Gemini: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Falha ao chamar a API do Gemini: {}", e.getMessage(), e);
            throw new AiServiceException("Falha ao chamar o serviço de IA (Gemini)", e);
        }
    }

    // Resposta da Gemini vem em candidates[0].content.parts[].text
    private String extractText(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            JsonNode parts = root.path("candidates").path(0).path("content").path("parts");

            StringBuilder text = new StringBuilder();
            if (parts.isArray()) {
                for (JsonNode part : parts) {
                    text.append(part.path("text").asText());
                }
            }

            if (text.isEmpty()) {
                throw new AiServiceException("Resposta da IA veio vazia ou em formato inesperado: " + rawResponse, null);
            }

            return text.toString();
        } catch (AiServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new AiServiceException("Não foi possível interpretar a resposta da IA", e);
        }
    }
}
