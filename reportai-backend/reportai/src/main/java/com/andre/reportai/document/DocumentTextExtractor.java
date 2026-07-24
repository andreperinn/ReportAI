package com.andre.reportai.document;

import org.springframework.web.multipart.MultipartFile;

// Cada tipo de arquivo suportado (PDF, CSV, ...) implementa essa interface.
// Novos formatos = nova classe aqui, sem tocar no resto do sistema.
public interface DocumentTextExtractor {

    boolean supports(String contentType, String filename);

    String extract(MultipartFile file);
}
