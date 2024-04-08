package com.assignment.Task_Exam.Controller;

import com.assignment.Task_Exam.model.Book;
import com.assignment.Task_Exam.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            return ResponseEntity.ok(optionalBook.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert_books")
    public ResponseEntity<Book> insertBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }



    @PutMapping("/{bookId}")
    public ResponseEntity<List<Book>> updateBooks(@RequestBody List<Book> books) {
        List<Book> updatedBooks = new ArrayList<>();

        for (Book book : books) {
            Long bookId = book.getId();
            if (bookId != null && bookRepository.existsById(bookId)) {
                Book updatedBook = bookRepository.save(book);
                updatedBooks.add(updatedBook);
            }
        }

        if (!updatedBooks.isEmpty()) {
            return ResponseEntity.ok(updatedBooks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
