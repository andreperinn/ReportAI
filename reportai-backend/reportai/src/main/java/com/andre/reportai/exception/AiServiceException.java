package com.andre.reportai.exception;

// Erro ao se comunicar com o provedor de IA (timeout, resposta inválida, etc.)
public class AiServiceException extends RuntimeException {
    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
