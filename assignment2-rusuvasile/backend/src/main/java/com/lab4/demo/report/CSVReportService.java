package com.lab4.demo.report;

import com.lab4.demo.frontoffice.model.dto.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public void export() {

    }

    @Override
    public ReportType getType() {
        return CSV;
    }

    @Override
    public void createFile(List<BookDTO> books) throws IOException {

        CSVWriter csvWriter = new CSVWriter(new FileWriter("Books.csv"));
        String[] header={"id","Title","Author","Genre","Price"} ;
        List<String[]> list = new ArrayList<>();
        list.add(header);
        for(BookDTO book:books)
            if(book.getQuantity()<=0)
            {
                String[] line=new String[7];
                line[1]=(book.getId().toString());
                line[2]=(book.getTitle());
                line[3]=(book.getAuthor());
                line[4]=(book.getGenre());
                line[5]=(book.getPrice().toString());
                list.add(line);
            }
        csvWriter.writeAll(list);
        csvWriter.flush();
        csvWriter.close();
    }
}
