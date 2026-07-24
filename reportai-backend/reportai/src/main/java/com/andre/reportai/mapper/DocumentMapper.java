package com.andre.reportai.mapper;

import com.andre.reportai.dto.DocumentResponse;
import com.andre.reportai.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponse toResponse(Document document) {
        return new DocumentResponse(
                document.getId(),
                document.getOriginalFilename(),
                document.getContentType(),
                document.getSizeBytes(),
                document.getStatus(),
                document.getCreatedAt()
        );
    }
}
