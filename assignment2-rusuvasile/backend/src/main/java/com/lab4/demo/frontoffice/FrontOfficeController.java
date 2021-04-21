package com.lab4.demo.frontoffice;

import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.FRONT_OFFICE;

@RestController
@RequestMapping(FRONT_OFFICE)
@RequiredArgsConstructor
public class FrontOfficeController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }

    @GetMapping("/{keyWord}")
    public  List<BookDTO> search(@PathVariable String keyWord){ return bookService.search(keyWord); }

    @PatchMapping("/sell")
    public void sell(@RequestBody BookDTO book){bookService.sell(book.getId());}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }


    @GetMapping("/downloadPdf")
    public void exportPdfReport(String fileName, HttpServletResponse response) throws IOException, URISyntaxException {
        reportServiceFactory.getReportService(ReportType.PDF).createFile(bookService.findAll());

    }

    @GetMapping("/downloadCsv")
    public void exportCsvReport(String fileName, HttpServletResponse response) throws IOException, URISyntaxException {
        reportServiceFactory.getReportService(ReportType.CSV).createFile(bookService.findAll());
    }

    @GetMapping(
            value = "/getPdf-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        reportServiceFactory.getReportService(ReportType.PDF).createFile(bookService.findAll());
        InputStream in = getClass()
                .getResourceAsStream("main/pdfFile.pdf");
        return IOUtils.toByteArray(in);
    }







}
