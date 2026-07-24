package com.andre.reportai.dto;

// style é opcional; null cai no padrão definido no ReportService
public record ReportRequest(
        String style
) {
}
