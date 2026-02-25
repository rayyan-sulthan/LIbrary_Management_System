package com.rayyan.library.controller;

import com.rayyan.library.dto.BookRequestDTO;
import com.rayyan.library.dto.BookResponseDTO;
import com.rayyan.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> add(@Valid @RequestBody BookRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.add(dto));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.ok("Book deleted");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> updateById(@PathVariable Long id,
                                                      @Valid @RequestBody BookRequestDTO dto){
        return ResponseEntity.ok(bookService.updateBookById(id, dto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean available,
            Pageable pageable) {

        return ResponseEntity.ok(
                bookService.searchBooks(title, author, minPrice, maxPrice, available, pageable)
        );
    }

    @PostMapping("/{id}/borrow")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookResponseDTO> borrowBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.borrowBook(id));
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookResponseDTO> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.returnBook(id));
    }
}