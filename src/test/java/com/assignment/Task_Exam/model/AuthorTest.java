package com.assignment.Task_Exam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {

    private Author author;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
    }

    @Test
    void testAddBook() {
        author.addBook(book1);

        assertTrue(author.getBooks().contains(book1));
        assertTrue(book1.getAuthors().contains(author));
    }

    @Test
    void testRemoveBook() {
        author.addBook(book1);
        author.addBook(book2);

        author.removeBook(book1);

        assertFalse(author.getBooks().contains(book1));
        assertFalse(book1.getAuthors().contains(author));
        assertTrue(author.getBooks().contains(book2));
        assertTrue(book2.getAuthors().contains(author));
    }

    @Test
    void testEquals() {
        Author author1 = new Author();
        author1.setId(1L);

        Author author2 = new Author();
        author2.setId(2L);

        assertEquals(author, author1);
        assertNotEquals(author, author2);
    }

    @Test
    void testHashCode() {
        Author author1 = new Author();
        author1.setId(1L);

        assertEquals(author.hashCode(), author1.hashCode());
    }
}
