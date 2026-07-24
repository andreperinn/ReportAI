package com.andre.reportai.exception;

// Lançada quando se tenta conversar/gerar relatório de um documento
// que ainda não terminou de ser processado (ou falhou no processamento)
public class DocumentNotReadyException extends RuntimeException {
    public DocumentNotReadyException(String message) {
        super(message);
    }
}
