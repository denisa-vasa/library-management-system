package com.gisdev.dea.controller;

import com.gisdev.dea.dto.bookDto.BookDto;
import com.gisdev.dea.entity.Book;
import com.gisdev.dea.service.BookService;
import com.gisdev.dea.util.constant.RestConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(RestConstants.BookController.BASE_PATH)
public class BookController {

    private BookService bookService;

    @PostMapping(RestConstants.BookController.SAVE_BOOK)
    public ResponseEntity<Long> saveBook(@Validated @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook.getId(), HttpStatus.CREATED);
    }

    @PutMapping(RestConstants.BookController.UPDATE_BOOK)
    public ResponseEntity<Long> updateBook(@PathVariable Long bookId,
                                           @Validated @RequestBody BookDto bookDto) {
        bookService.updateBook(bookId, bookDto);
        return new ResponseEntity<>(bookId, HttpStatus.OK);
    }

    @GetMapping(RestConstants.BookController.BOOK_ID)
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.findById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME, @Role.USER_NAME)")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping(RestConstants.BookController.BOOKS_IN_STOCK)
    @PreAuthorize("hasAnyAuthority(@Role.USER_NAME)")
    public ResponseEntity<List<Book>> getAllBooksInStock() {
        List<Book> bookList = bookService.getAllBooksInStock();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping(RestConstants.BookController.BOOKS_IN_STOCK_PAGE)
    @PreAuthorize("hasAnyAuthority(@Role.USER_NAME)")
    public ResponseEntity<List<Book>> getAllBooksInStockPage(@RequestParam int pageNumber,
                                                             @RequestParam int pageSize) {
        List<Book> book = bookService.getAllBooksInStockPage(pageNumber, pageSize);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
