package com.gisdev.dea.service;

import com.gisdev.dea.dto.bookDto.BookDto;
import com.gisdev.dea.dto.libraryDto.LibraryDto;
import com.gisdev.dea.entity.Book;
import com.gisdev.dea.entity.LibraryBook;
import com.gisdev.dea.entity.Library;
import com.gisdev.dea.exception.BadRequestException;
import com.gisdev.dea.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {

    private LibraryRepository libraryRepository;
    private BookService bookService;
    private LibraryBookService libraryBookService;

    public Library saveLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public Library findById(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("Library with id " + libraryId + " was not found."));
    }

    public void updateLibrary(Long libraryId, LibraryDto libraryDto) {
        Library library = findById(libraryId);
        library.setNumProducts(libraryDto.getNumProducts());
        libraryRepository.save(library);
    }

    public Library getLibraryById(Long libraryId) {
        return findById(libraryId);
    }

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    //adds books to library based on libraryId
    @Transactional(rollbackFor = Exception.class)
    public void addBooksToLibrary(Long libraryId, List<BookDto> bookDtoList) {
        Library library = findById(libraryId);

        if (bookDtoList.isEmpty()) {
            throw new BadRequestException("List of books should not be empty!");
        }
        //for each book that comes from libraDtoList it check if it exists in db
        for (BookDto bookDto : bookDtoList) {
            //if the book is not present it is created
            Book book = new Book();
            buildBook(book, bookDto);
            bookService.saveBook(book);

            //if the book exists it is added
            LibraryBook libraryBook = new LibraryBook();
            libraryBook.setBook(book);
            libraryBook.setLibrary(library);

            libraryBookService.save(libraryBook);
        }
    }

    public void buildBook (Book book, BookDto bookDto) {
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setSector(bookDto.getSector());
    }
}
