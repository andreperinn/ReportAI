package com.andre.reportai.service;

import com.andre.reportai.ai.AiClient;
import com.andre.reportai.dto.ReportRequest;
import com.andre.reportai.dto.ReportResponse;
import com.andre.reportai.entity.ChatMessage;
import com.andre.reportai.entity.Document;
import com.andre.reportai.entity.DocumentStatus;
import com.andre.reportai.entity.Report;
import com.andre.reportai.exception.DocumentNotReadyException;
import com.andre.reportai.exception.ResourceNotFoundException;
import com.andre.reportai.mapper.ReportMapper;
import com.andre.reportai.repository.ChatMessageRepository;
import com.andre.reportai.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final DocumentService documentService;
    private final AiClient aiClient;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository,
                          ChatMessageRepository chatMessageRepository,
                          DocumentService documentService,
                          AiClient aiClient,
                          ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.documentService = documentService;
        this.aiClient = aiClient;
        this.reportMapper = reportMapper;
    }

    @Transactional
    public ReportResponse generate(Long documentId, ReportRequest request) {
        Document document = documentService.findEntity(documentId);

        if (document.getStatus() != DocumentStatus.PROCESSED) {
            throw new DocumentNotReadyException(
                    "Documento ainda não está pronto para gerar relatório (status atual: " + document.getStatus() + ")");
        }

        List<ChatMessage> history = chatMessageRepository.findByDocumentIdOrderByCreatedAtAsc(documentId);
        String style = request != null ? request.style() : null;

        String content = aiClient.generateReport(document.getExtractedText(), history, style);

        Report report = Report.builder()
                .document(document)
                .content(content)
                .build();

        return reportMapper.toResponse(reportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public ReportResponse getLatestForDocument(Long documentId) {
        documentService.findEntity(documentId);

        Report report = reportRepository.findTopByDocumentIdOrderByCreatedAtDesc(documentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum relatório gerado ainda para o documento " + documentId));

        return reportMapper.toResponse(report);
    }
}
