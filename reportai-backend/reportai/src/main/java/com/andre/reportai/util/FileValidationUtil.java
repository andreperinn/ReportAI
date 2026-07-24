package com.andre.reportai.util;

import com.andre.reportai.exception.FileTooLargeException;
import com.andre.reportai.exception.UnsupportedFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public final class FileValidationUtil {

    private static final long MAX_SIZE_BYTES = 20L * 1024 * 1024; // 20MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".pdf", ".csv");

    private FileValidationUtil() {
    }

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new UnsupportedFileTypeException("Nenhum arquivo enviado");
        }

        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new FileTooLargeException("Arquivo excede o limite de 20MB");
        }

        String filename = file.getOriginalFilename();
        boolean hasValidExtension = filename != null && ALLOWED_EXTENSIONS.stream()
                .anyMatch(ext -> filename.toLowerCase().endsWith(ext));

        if (!hasValidExtension) {
            throw new UnsupportedFileTypeException("Envie um arquivo PDF ou CSV");
        }
    }
}
