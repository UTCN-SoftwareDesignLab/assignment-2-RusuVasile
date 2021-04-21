package com.lab4.demo.frontoffice;

import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book actBook = findById(book.getId());
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getTitle());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }


    public List<BookDTO> search (String keyWord){
        List<Book> result=new ArrayList<>();
        List<Book> books=bookRepository.findAll();
        for(Book book: books)
            if(book.getTitle().contains(keyWord) || book.getGenre().contains(keyWord) || book.getAuthor().contains(keyWord))
                result.add(book);
        return result
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findOutOfStock (){
        List<Book> result=new ArrayList<>();
        List<Book> books=bookRepository.findAll();
        for(Book book: books)
            if(book.getQuantity()<=0)
                result.add(book);
        return result
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public void sell(Long id){
        Book actBook = findById(id);

        if(actBook.getQuantity()>0)
            actBook.setQuantity(actBook.getQuantity()-1);
        else
            actBook.setQuantity(actBook.getQuantity());
        bookRepository.save(actBook);
    }


    public void delete(Long id){
        bookRepository.deleteById(id);
    }

}
