package com.gisdev.dea.controller;

import com.gisdev.dea.dto.bookDto.BookDto;
import com.gisdev.dea.dto.libraryDto.LibraryDto;
import com.gisdev.dea.entity.Library;
import com.gisdev.dea.service.LibraryService;
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
@RequestMapping(RestConstants.LibraryController.BASE_PATH)
public class LibraryController {

    private LibraryService libraryService;

    @PostMapping(RestConstants.LibraryController.SAVE_LIBRARY)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<Long> saveLibrary(@Validated @RequestBody Library library) {
        Library newLibrary = libraryService.saveLibrary(library);
        return new ResponseEntity<>(newLibrary.getId(), HttpStatus.CREATED);
    }

    @PutMapping(RestConstants.LibraryController.UPDATE_LIBRARY)
    public ResponseEntity<Long> updateLibrary(@PathVariable Long libraryId,
                                              @Validated @RequestBody LibraryDto libraryDto) {
        libraryService.updateLibrary(libraryId, libraryDto);
        return new ResponseEntity<>(libraryId, HttpStatus.OK);
    }

    @GetMapping(RestConstants.LibraryController.LIBRARY_ID)
    public ResponseEntity<Library> getLibraryById(@PathVariable Long libraryId) {
        Library library = libraryService.getLibraryById(libraryId);
        return new ResponseEntity<>(library, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries() {
        List<Library> libraryList = libraryService.getAllLibraries();
        return new ResponseEntity<>(libraryList, HttpStatus.OK);
    }

    @PostMapping(RestConstants.LibraryController.SAVE_BOOKS_IN_LIBRARY)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<String> addBooksToLibrary(@PathVariable(value = RestConstants.ID) Long libraryId,
                                                    @RequestBody List<BookDto> bookDtoList) {
        libraryService.addBooksToLibrary(libraryId, bookDtoList);
        return ResponseEntity.ok("Books were added successfully!");
    }
}
