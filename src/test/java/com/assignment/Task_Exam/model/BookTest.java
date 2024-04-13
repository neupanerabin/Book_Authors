// Importing necessary packages and classes for testing
package com.assignment.Task_Exam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Book model
public class BookTest {

    // Instance variables for Book and Author
    private Book book;
    private Author author1;
    private Author author2;

    // Setting up test data before each test method
    @BeforeEach
    void setUp() {
        // Creating an instance of Book and setting its properties
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        // Creating instances of Author and setting their properties
        author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");
    }

    // Testing the addAuthor method of Book
    @Test
    void testAddAuthor() {
        // Adding author1 to book's authors
        book.getAuthors().add(author1);

        // Asserting that author1 is in book's authors
        assertTrue(book.getAuthors().contains(author1));
    }

    // Testing the removeAuthor method of Book
    @Test
    void testRemoveAuthor() {
        // Adding author1 and author2 to book's authors
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);

        // Removing author1 from book's authors
        book.getAuthors().remove(author1);

        // Asserting that author1 is removed from book's authors and vice versa
        assertFalse(book.getAuthors().contains(author1));
        assertFalse(author1.getBooks().contains(book));

        // Asserting that author2 remains in book's authors
        assertTrue(book.getAuthors().contains(author2));
    }

    // Testing the equals method of Book
    @Test
    void testEquals() {
        // Creating two Book instances with different IDs
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        // Asserting that book1 is not equal to book2
        assertNotEquals(book, book2);
    }
}
