package com.assignment.Task_Exam.IntegrationTest;


import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Author;
import com.assignment.Task_Exam.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AuthorBookIntegrationTest {

    // Autowire the AuthorRepository and BookRepository for interacting with the database
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    // Test method to check adding an author to a book
    @Test
    void testAddAuthorToBook() {
        // Create an author
        Author author = new Author();
        author.setName("Test Author");

        // Save the author to the database
        authorRepository.save(author);

        // Create a book
        Book book = new Book();
        book.setTitle("Test Book");

        // Add the author to the book's authors set
        book.getAuthors().add(author);

        // Save the book to the database
        bookRepository.save(book);

        // Retrieve the saved book from the database
        Book savedBook = bookRepository.findById(book.getId()).orElse(null);

        // Check if the author is added to the book's authors set
        assertTrue(savedBook != null && savedBook.getAuthors().contains(author));
    }

    // Test method to check adding a book to an author
    @Test
    void testAddBookToAuthor() {
        // Create a book
        Book book = new Book();
        book.setTitle("Test Book");

        // Save the book to the database
        bookRepository.save(book);

        // Create an author
        Author author = new Author();
        author.setName("Test Author");

        // Add the book to the author's books set
        author.getBooks().add(book);

        // Save the author to the database
        authorRepository.save(author);

        // Retrieve the saved author from the database
        Author savedAuthor = authorRepository.findById(author.getId()).orElse(null);

        // Check if the book is added to the author's books set
        assertTrue(savedAuthor != null && savedAuthor.getBooks().contains(book));
    }

    // Test method to check adding an author to a non-existent book
    @Test
    void testAddAuthorToNonExistentBook() {
        // Create an author
        Author author = new Author();
        author.setName("Test Author");

        // Save the author to the database
        authorRepository.save(author);

        // Attempt to add the author to a non-existent book
        Long nonExistentBookId = 100L; // Assuming no book exists with this ID
        assertThrows(EntityNotFoundException.class, () -> {
            Book nonExistentBook = bookRepository.findById(nonExistentBookId)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
            nonExistentBook.getAuthors().add(author);
            bookRepository.save(nonExistentBook);
        });
    }

    // Clean up after each test
    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }
}
