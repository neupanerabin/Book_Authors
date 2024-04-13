// Importing necessary packages and classes for testing
package com.assignment.Task_Exam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Author model
public class AuthorTest {

    // Instance variables for Author and Book
    private Author author;
    private Book book1;
    private Book book2;

    // Setting up test data before each test method
    @BeforeEach
    void setUp() {
        // Creating an instance of Author and setting its properties
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        // Creating instances of Book and setting their properties
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
    }

    // Testing the addBook method of Author
    @Test
    void testAddBook() {
        // Calling addBook method with book1
        author.addBook(book1);

        // Asserting that book1 is added to author's books and vice versa
        assertTrue(author.getBooks().contains(book1));
        assertTrue(book1.getAuthors().contains(author));
    }

    // Testing the removeBook method of Author
    @Test
    void testRemoveBook() {
        // Adding book1 and book2 to author
        author.addBook(book1);
        author.addBook(book2);

        // Removing book1 from author
        author.removeBook(book1);

        // Asserting that book1 is removed from author's books and vice versa
        assertFalse(author.getBooks().contains(book1));
        assertFalse(book1.getAuthors().contains(author));

        // Asserting that book2 remains in author's books and vice versa
        assertTrue(author.getBooks().contains(book2));
        assertTrue(book2.getAuthors().contains(author));
    }

    // Testing the equals method of Author
    @Test
    void testEquals() {
        // Creating two Author instances with the same ID
        Author author1 = new Author();
        author1.setId(1L);

        // Creating another Author instance with a different ID
        Author author2 = new Author();
        author2.setId(2L);

        // Asserting that author equals author1 but not author2
        assertEquals(author, author1);
        assertNotEquals(author, author2);
    }

    // Testing the hashCode method of Author
    @Test
    void testHashCode() {
        // Creating two Author instances with the same ID
        Author author1 = new Author();
        author1.setId(1L);

        // Asserting that hash codes of author and author1 are equal
        assertEquals(author.hashCode(), author1.hashCode());
    }
}
