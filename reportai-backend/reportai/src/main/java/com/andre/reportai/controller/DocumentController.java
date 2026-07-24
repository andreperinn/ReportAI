package com.andre.reportai.controller;

import com.andre.reportai.dto.DocumentResponse;
import com.andre.reportai.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DocumentResponse> upload(@RequestParam("file") MultipartFile file) {
        DocumentResponse response = documentService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<DocumentResponse> list() {
        return documentService.listAll();
    }

    @GetMapping("/{id}")
    public DocumentResponse getById(@PathVariable Long id) {
        return documentService.getById(id);
    }
}
