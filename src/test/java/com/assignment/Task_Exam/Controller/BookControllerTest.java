package com.assignment.Task_Exam.Controller;

// Importing necessary packages and classes for testing
import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Importing static methods for assertions, mocking, and MockMvc
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Using MockitoExtension for Mockito annotations
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    // MockMvc instance for testing MVC endpoints
    private MockMvc mockMvc;

    // Mocking BookRepository and AuthorRepository
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    // Injecting mocks into BookController
    @InjectMocks
    private BookController bookController;

    // Test data setup
    private Book testBooks;

    // Setting up test data and MockMvc before each test
    @BeforeEach
    public void setUp() {
        // Initializing test book
        testBooks = new Book(1L, "Sample Book");

        // Initializing Mockito annotations
        MockitoAnnotations.initMocks(this);

        // Building MockMvc standalone setup
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // Testing the getAllBooks method
    @Test
    void testGetAllBooks() throws Exception {
        // Creating a list of Book records
        List<Book> records = new ArrayList<>(Arrays.asList(
                new Book(1L, "Rabin"),
                new Book(2L, "robs"),
                new Book(3L, "neupane")
        ));

        // Mocking the findAll method to return the list of records
        Mockito.when(bookRepository.findAll()).thenReturn(records);

        // Performing GET request to /books/books endpoint
        ResultActions response = mockMvc.perform(get("/books/books"));

        // Verifying status code is OK and the returned JSON array has size 3
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    // Testing the getBookById method
    @Test
    void testGetBookById() throws Exception {
        // Mocking findById method to return testBooks when ID is 1
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBooks));

        // Performing GET request to /books/{bookId} endpoint with ID 1
        ResultActions result = mockMvc.perform(get("/books/{bookId}", 1L));

        // Verifying status code is OK and returned book's id and title match testBooks
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBooks.getId()))
                .andExpect(jsonPath("$.title").value(testBooks.getTitle()));
    }

    // Testing the getBookById method when book is not found
    @Test
    void testGetBookById_NotFound() {
        // Mocking findById method to return empty Optional when ID is 2
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        // Calling getBookById method with ID 2
        ResponseEntity<Book> responseEntity = bookController.getBookById(2L);

        // Verifying status code is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // Testing the insertBook method
    @Test
    void testInsertBook() {
        // Mocking save method to return testBooks
        when(bookRepository.save(testBooks)).thenReturn(testBooks);

        // Calling insertBook method
        ResponseEntity<Book> responseEntity = bookController.insertBook(testBooks);

        // Verifying status code is CREATED and returned book matches testBooks
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testBooks, responseEntity.getBody());
    }

    // Testing the updateBooks method
    @Test
    void testUpdateBooks() {
        // Creating a list of books and adding testBooks to it
        List<Book> books = new ArrayList<>();
        books.add(testBooks);

        // Mocking existsById method to return true for ID 1
        when(bookRepository.existsById(1L)).thenReturn(true);

        // Mocking save method to return testBooks
        when(bookRepository.save(testBooks)).thenReturn(testBooks);

        // Calling updateBooks method
        ResponseEntity<List<Book>> responseEntity = bookController.updateBooks(books);

        // Verifying status code is OK and returned list of books matches input list
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    // Testing linkAuthorToBook method when book is not found
    @Test
    void linkAuthorToBook_BookNotFound_ReturnsNotFound() {
        // Mocking findById method to return empty Optional
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        // Calling linkAuthorToBook method with IDs 1L and 1L
        ResponseEntity<Book> response = bookController.linkAuthorToBook(1L, 1L);

        // Verifying status code is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Testing the deleteBook method
    @Test
    void testDeleteBook() {
        // Mocking existsById method to return true for ID 1
        when(bookRepository.existsById(1L)).thenReturn(true);

        // Calling deleteBook method with ID 1
        ResponseEntity<Void> responseEntity = bookController.deleteBook(1L);

        // Verifying status code is NO_CONTENT and deleteById method is called once
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookRepository, times(1)).deleteById(1L);
    }

    // Testing the deleteBook method when book is not found
    @Test
    void testDeleteBook_NotFound() {
        // Mocking existsById method to return false for ID 2
        when(bookRepository.existsById(2L)).thenReturn(false);

        // Calling deleteBook method with ID 2
        ResponseEntity<Void> responseEntity = bookController.deleteBook(2L);

        // Verifying status code is NOT_FOUND and deleteById method is never called
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(bookRepository, never()).deleteById(2L);
    }
}
