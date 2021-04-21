package com.lab4.demo.frontoffice;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.FRONT_OFFICE;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FrontOfficeControllerTest extends BaseControllerTest {

    @InjectMocks
    private FrontOfficeController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new FrontOfficeController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(FRONT_OFFICE));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }


    @Test
    void create() throws Exception {
        double random = ThreadLocalRandom.current().nextDouble(0, 1500);
        Integer random2 = ThreadLocalRandom.current().nextInt(0, 1500);

        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(random)
                .quantity(random2)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(FRONT_OFFICE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        double random = ThreadLocalRandom.current().nextDouble(0, 1500);
        Integer random2 = ThreadLocalRandom.current().nextInt(0, 1500);

        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(random)
                .quantity(random2)
                .build();

        when(bookService.edit(reqBook)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBody(FRONT_OFFICE, reqBook);
        result.andExpect(status().isOk()).andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void search() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title("keyword")
                .author(randomString())
                .genre(randomString())
                .price(ThreadLocalRandom.current().nextDouble(0, 1500))
                .quantity(ThreadLocalRandom.current().nextInt(0, 1500))
                .build();
        BookDTO reqBook2 = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author("keyword")
                .genre(randomString())
                .price(ThreadLocalRandom.current().nextDouble(0, 1500))
                .quantity(ThreadLocalRandom.current().nextInt(0, 1500))
                .build();
        bookService.create(reqBook);
        bookService.create(reqBook2);
    }



}