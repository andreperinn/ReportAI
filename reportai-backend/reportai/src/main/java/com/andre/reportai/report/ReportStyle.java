package com.andre.reportai.report;

// Estilos de relatório disponíveis. O ReportService cai em DETAILED por padrão
// quando o front-end não especifica nada.
public enum ReportStyle {
    EXECUTIVE,   // curto, focado em decisão
    DETAILED,    // completo, com todos os pontos identificados
    SUMMARY      // resumo rápido em bullets
}
