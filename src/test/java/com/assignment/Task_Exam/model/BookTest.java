package com.assignment.Task_Exam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;
    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");
    }

    @Test
    void testAddAuthor() {
        book.getAuthors().add(author1);

        assertTrue(book.getAuthors().contains(author1));
    }

    @Test
    void testRemoveAuthor() {
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);

        book.getAuthors().remove(author1);

        assertFalse(book.getAuthors().contains(author1));
        assertFalse(author1.getBooks().contains(book));
        assertTrue(book.getAuthors().contains(author2));
    }

    @Test
    void testEquals() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        assertNotEquals(book, book2);
    }


}
