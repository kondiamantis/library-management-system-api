package com.library.librarymanagementsystemapi.service;

import com.library.librarymanagementsystemapi.entity.Book;
import com.library.librarymanagementsystemapi.exception.DuplicateResourceException;
import com.library.librarymanagementsystemapi.exception.ResourceNotFoundException;
import com.library.librarymanagementsystemapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book createBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        // Set available copies equal to total copies on creation
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies());
        }

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);

        // Check if ISBN is being changed and if it already exists
        if (!book.getIsbn().equals(bookDetails.getIsbn()) &&
                bookRepository.existsByIsbn(bookDetails.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + bookDetails.getIsbn() + " already exists");
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setGenre(bookDetails.getGenre());
        book.setDescription(bookDetails.getDescription());
        book.setCoverUrl(bookDetails.getCoverUrl());
        book.setTotalCopies(bookDetails.getTotalCopies());
        book.setAvailableCopies(bookDetails.getAvailableCopies());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }
}
