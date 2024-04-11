package com.assignment.Task_Exam.Controller;

// Imports class and library
import com.assignment.Task_Exam.model.Book;
import com.assignment.Task_Exam.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Create API
@RestController
@RequestMapping("/books")   // Define base URL api
public class BookController {

    @Autowired  // Autowire BookRepository for dependency injection
    private BookRepository bookRepository;

    // Get all the available books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();    // return all book from repository
    }

    // Get Book with ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);  // Find the book by ID the repository
        if (optionalBook.isPresent()) { // Check if book is present
            return ResponseEntity.ok(optionalBook.get());   // return the book if found
        } else {
            return ResponseEntity.notFound().build();   // Return 404 not found
        }
    }

    // Insert the New Books
    @PostMapping("/insert_books")
    public ResponseEntity<Book> insertBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book); // save the new book in the repository
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook); // Return the saved book with HTTP status 201 Created
    }

    // Update books with bookId
    @PutMapping("/{bookId}")
    public ResponseEntity<List<Book>> updateBooks(@RequestBody List<Book> books) {
        List<Book> updatedBooks = new ArrayList<>();    // Create a list to store updated books

        for (Book book : books) {
            Long bookId = book.getId(); // Get the id of the book
            if (bookId != null && bookRepository.existsById(bookId)) {  // Check if the book is valid and exists in the repository
                Book updatedBook = bookRepository.save(book);   // save the updated book in the repository
                updatedBooks.add(updatedBook);  // Add the updated book to the list of updated books
            }
        }
        if (!updatedBooks.isEmpty()) {  // Check if any books were updated
            return ResponseEntity.ok(updatedBooks); // Return the list of updated books
        } else {
            return ResponseEntity.notFound().build();   // Return 404 not found if no books were updated
        }
    }

    // Delete Book with book Id
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        if (bookRepository.existsById(bookId)) {    // Check if book ID exists in the repository
            bookRepository.deleteById(bookId);  // Delete the book from the repository
            return ResponseEntity.noContent().build(); // Return 204 No Content after successful deletion
        } else {
            return ResponseEntity.notFound().build();   // // Return 404 Not Found if book ID is not found
        }
    }
}
