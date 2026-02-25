package com.rayyan.library.services;

import com.rayyan.library.dto.BookRequestDTO;
import com.rayyan.library.dto.BookResponseDTO;
import com.rayyan.library.entity.Book;
import com.rayyan.library.entity.User;
import com.rayyan.library.exception.BookAlreadyBorrowedException;
import com.rayyan.library.exception.BookAlreadyReturnedException;
import com.rayyan.library.exception.BookNotFoundException;
import com.rayyan.library.repository.BookRepository;
import com.rayyan.library.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.rayyan.library.specification.BookSpecification.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public BookResponseDTO add(BookRequestDTO bookRequest){
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());

        Book saved = bookRepository.save(book);
        return convertToDTO(saved);
    }

    public void delete(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public BookResponseDTO getBookById(Long id)  {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        return convertToDTO(book);
    }

    public BookResponseDTO updateBookById(Long id , BookRequestDTO dto){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        book.setAuthor(dto.getAuthor());
        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());

        return convertToDTO(bookRepository.save(book));
    }

    public Page<BookResponseDTO> searchBooks(
            String title,
            String author,
            Double minPrice,
            Double maxPrice,
            Boolean available,
            Pageable pageable) {

        Specification<Book> spec = (root, query, cb) -> cb.conjunction();

        if (title != null && !title.isEmpty()) {
            spec = spec.and(hasTitle(title));
        }

        if (author != null && !author.isEmpty()) {
            spec = spec.and(hasAuthor(author));
        }

        if (minPrice != null) {
            spec = spec.and(priceGreaterThanOrEqual(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(priceLessThanOrEqual(maxPrice));
        }

        if (available != null) {
            spec = spec.and(isAvailable(available));
        }

        return bookRepository.findAll(spec, pageable)
                .map(this::convertToDTO);
    }

    private BookResponseDTO convertToDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.isAvailable()
        );
    }

    @Transactional
    public BookResponseDTO borrowBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (!book.isAvailable()) {
            throw new BookAlreadyBorrowedException("Book is already borrowed");
        }

        // Get logged-in username
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBorrowedCount() >= 3) {
            throw new RuntimeException("Borrow limit exceeded (Max 3 books)");
        }

        book.setAvailable(false);
        book.setBorrowedBy(user);

        user.setBorrowedCount(user.getBorrowedCount() + 1);

        return convertToDTO(bookRepository.save(book));
    }

    @Transactional
    public BookResponseDTO returnBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (book.isAvailable()) {
            throw new BookAlreadyReturnedException("Book is already available");
        }

        User user = book.getBorrowedBy();

        book.setAvailable(true);
        book.setBorrowedBy(null);

        user.setBorrowedCount(user.getBorrowedCount() - 1);

        return convertToDTO(bookRepository.save(book));
    }
}