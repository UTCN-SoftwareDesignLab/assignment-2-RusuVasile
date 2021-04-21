package com.lab4.demo.report;

import com.lab4.demo.frontoffice.model.dto.BookDTO;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    void export();

    ReportType getType();

    void createFile(List<BookDTO> books) throws IOException;
}
