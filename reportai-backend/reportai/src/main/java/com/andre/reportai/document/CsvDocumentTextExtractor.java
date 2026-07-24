package com.andre.reportai.document;

import com.andre.reportai.exception.UnsupportedFileTypeException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDocumentTextExtractor implements DocumentTextExtractor {

    // Limite de linhas convertidas em texto — evita estourar o contexto da IA
    // em CSVs muito grandes. O ideal futuro é resumir/agregar em vez de truncar.
    private static final int MAX_ROWS = 500;

    @Override
    public boolean supports(String contentType, String filename) {
        return "text/csv".equalsIgnoreCase(contentType)
                || (filename != null && filename.toLowerCase().endsWith(".csv"));
    }

    @Override
    public String extract(MultipartFile file) {
        try (var reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreEmptyLines(true)
                     .build()
                     .parse(reader)) {

            List<String> headers = parser.getHeaderNames();
            StringBuilder sb = new StringBuilder();
            sb.append(String.join(" | ", headers)).append("\n");

            int count = 0;
            for (CSVRecord record : parser) {
                if (count >= MAX_ROWS) {
                    sb.append("... (arquivo truncado após ").append(MAX_ROWS).append(" linhas)\n");
                    break;
                }
                List<String> values = new ArrayList<>();
                for (String value : record) {
                    values.add(value);
                }
                sb.append(String.join(" | ", values)).append("\n");
                count++;
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UnsupportedFileTypeException("Não foi possível ler o CSV enviado: " + e.getMessage());
        }
    }
}
