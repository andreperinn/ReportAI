package com.andre.reportai.mapper;

import com.andre.reportai.dto.ReportResponse;
import com.andre.reportai.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportResponse toResponse(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getDocument().getId(),
                report.getContent(),
                report.getCreatedAt()
        );
    }
}
