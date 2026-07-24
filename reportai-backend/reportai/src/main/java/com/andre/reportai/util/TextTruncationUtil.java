package com.andre.reportai.util;

public final class TextTruncationUtil {

    // Limite de caracteres para não estourar o contexto do modelo, não gastar
    // tokens à toa, e manter a resposta rápida — o documento inteiro é
    // reenviado em toda mensagem do chat, então esse valor afeta diretamente
    // a velocidade da conversa. 100k chars ainda cobre documentos bem grandes.
    private static final int MAX_CHARS = 100_000;

    private TextTruncationUtil() {
    }

    public static String truncate(String text) {
        if (text == null) return "";
        if (text.length() <= MAX_CHARS) return text;
        return text.substring(0, MAX_CHARS) + "\n\n... (conteúdo truncado)";
    }
}
