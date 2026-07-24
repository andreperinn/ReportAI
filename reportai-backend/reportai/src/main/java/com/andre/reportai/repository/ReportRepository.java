package com.andre.reportai.repository;

import com.andre.reportai.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findTopByDocumentIdOrderByCreatedAtDesc(Long documentId);
}
