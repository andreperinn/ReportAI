package com.andre.reportai.service;

import com.andre.reportai.document.DocumentTextExtractor;
import com.andre.reportai.document.DocumentTextExtractorResolver;
import com.andre.reportai.dto.DocumentResponse;
import com.andre.reportai.entity.Document;
import com.andre.reportai.entity.DocumentStatus;
import com.andre.reportai.exception.ResourceNotFoundException;
import com.andre.reportai.mapper.DocumentMapper;
import com.andre.reportai.repository.DocumentRepository;
import com.andre.reportai.util.FileValidationUtil;
import com.andre.reportai.util.TextTruncationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentTextExtractorResolver extractorResolver;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentRepository documentRepository,
                            DocumentTextExtractorResolver extractorResolver,
                            DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.extractorResolver = extractorResolver;
        this.documentMapper = documentMapper;
    }

    @Transactional
    public DocumentResponse upload(MultipartFile file) {
        FileValidationUtil.validate(file);

        Document document = Document.builder()
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .sizeBytes(file.getSize())
                .status(DocumentStatus.PROCESSING)
                .build();

        try {
            DocumentTextExtractor extractor = extractorResolver.resolve(file);
            String text = extractor.extract(file);
            document.setExtractedText(TextTruncationUtil.truncate(text));
            document.setStatus(DocumentStatus.PROCESSED);
        } catch (RuntimeException e) {
            document.setStatus(DocumentStatus.FAILED);
            documentRepository.save(document);
            throw e;
        }

        Document saved = documentRepository.save(document);
        return documentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public DocumentResponse getById(Long id) {
        return documentMapper.toResponse(findEntity(id));
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> listAll() {
        return documentRepository.findAll().stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    // Usado internamente por ChatService/ReportService, que precisam do texto extraído
    @Transactional(readOnly = true)
    public Document findEntity(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado: " + id));
    }
}
