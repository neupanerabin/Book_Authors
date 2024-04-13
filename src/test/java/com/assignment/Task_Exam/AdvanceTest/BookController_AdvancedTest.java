package com.assignment.Task_Exam.AdvanceTest;

// Import static methods for mocking and assertions

import com.assignment.Task_Exam.Controller.BookController;
import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Author;
import com.assignment.Task_Exam.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Define test class
class BookController_AdvancedTest {

    // Mock BookRepository and AuthorRepository
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    // Inject mocks into BookController
    @InjectMocks
    private BookController bookController;

    // Setup method runs before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialize mocks
    }

    // Test for linking an author to a non-existent book
    @Test
    void linkAuthorToBook_BookNotFound_ReturnsNotFound() {
        // Arrange
        when(bookRepository.findById(any())).thenReturn(Optional.empty());  // Mocking findById to return empty Optional
        // Act
        ResponseEntity<Book> response = bookController.linkAuthorToBook(1L, 1L);  // Call controller method
        // Assert
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);  // Check status code
    }

    // Test for linking a non-existent author to a book
    @Test
    void linkAuthorToBook_AuthorNotFound_ReturnsNotFound() {
        // Arrange
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));  // Mocking findById to return a Book
        when(authorRepository.findById(any())).thenReturn(Optional.empty());  // Mocking findById to return empty Optional
        // Act
        ResponseEntity<Book> response = bookController.linkAuthorToBook(1L, 1L);  // Call controller method
        // Assert
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);  // Check status code
    }

    // Test for successfully linking an author to a book
    @Test
    void linkAuthorToBook_SuccessfullyLinksAuthorToBook() {
        // Arrange
        Book book = new Book();  // Create new Book
        Author author = new Author();  // Create new Author
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));  // Mocking findById to return a Book
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));  // Mocking findById to return an Author
        // Act
        ResponseEntity<Book> response = bookController.linkAuthorToBook(1L, 1L);  // Call controller method
        // Assert
        assert (response.getStatusCode()).equals(HttpStatus.OK);  // Check status code
        assert (book.getAuthors().contains(author));  // Check if author is linked to book
    }

    // Parameterized test for deleting an existing book
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    // Test with different book ids
    void deleteBook_BookExists_DeletesBook(long bookId) {
        // Arrange
        when(bookRepository.existsById(bookId)).thenReturn(true);  // Mocking existsById to return true
        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);  // Call controller method
        // Assert
        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);  // Check status code
    }

    // Parameterized test for deleting a non-existent book
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    // Test with different book ids
    void deleteBook_BookDoesNotExist_ReturnsNotFound(long bookId) {
        // Arrange
        when(bookRepository.existsById(bookId)).thenReturn(false);  // Mocking existsById to return false
        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);  // Call controller method
        // Assert
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);  // Check status code
    }
}


