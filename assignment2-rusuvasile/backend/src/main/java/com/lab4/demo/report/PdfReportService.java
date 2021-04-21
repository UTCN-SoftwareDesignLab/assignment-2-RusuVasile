package com.lab4.demo.report;

import com.lab4.demo.frontoffice.BookMapper;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    @Override
    public  void export() {


    }


    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public void createFile(List<BookDTO> books) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        pageContentStream.beginText();
        pageContentStream.setFont( PDType1Font.TIMES_ROMAN , 35 );
        pageContentStream.setLeading(25.5f);
        pageContentStream.newLineAtOffset(100,  700);
        pageContentStream.showText("OUT OF STOCK BOOKS");
        pageContentStream.newLine();
        pageContentStream.newLine();
        pageContentStream.setFont( PDType1Font.TIMES_ROMAN , 15 );
        StringBuilder stringBuilder=new StringBuilder();
        for (BookDTO book: books) {
            if(book.getQuantity()<=0) {
                stringBuilder.append(book.getTitle() + "," + book.getAuthor() + " (" + book.getGenre() + ") , " + book.getPrice() + " ron");
                pageContentStream.showText(stringBuilder.toString());
                pageContentStream.newLine();
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
        pageContentStream.endText();
        pageContentStream.close();
        document.save("src/pdfFile.pdf");

    }
}
