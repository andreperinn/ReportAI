package com.andre.reportai.document;

import com.andre.reportai.exception.UnsupportedFileTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class DocumentTextExtractorResolver {

    private final List<DocumentTextExtractor> extractors;

    public DocumentTextExtractorResolver(List<DocumentTextExtractor> extractors) {
        this.extractors = extractors;
    }

    public DocumentTextExtractor resolve(MultipartFile file) {
        return extractors.stream()
                .filter(e -> e.supports(file.getContentType(), file.getOriginalFilename()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedFileTypeException(
                        "Tipo de arquivo não suportado. Envie um PDF ou CSV."));
    }
}
