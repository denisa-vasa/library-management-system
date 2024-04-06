package com.gisdev.dea.service;

import com.gisdev.dea.entity.LibraryBook;
import com.gisdev.dea.repository.LibraryBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LibraryBookService {

    private LibraryBookRepository libraryBookRepository;

    public void save(LibraryBook libraryBook) {
        libraryBookRepository.save(libraryBook);
    }
}
