package com.gisdev.dea.service;

import com.gisdev.dea.dto.bookDto.BookDto;
import com.gisdev.dea.entity.Book;
import com.gisdev.dea.exception.BadRequestException;
import com.gisdev.dea.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBook(Long bookId, BookDto bookDto) {
        Book book = findById(bookId);
        book.setPrice(bookDto.getPrice());
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksInStockPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findBooksInStock(pageable);
        return bookPage.getContent();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Book with id: " + id + " was not found!"));
    }

    public List<Book> getAllBooksInStock() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .filter(book -> book.getStock() != null && book.getStock() > 0)
                .collect(Collectors.toList());
    }
}
