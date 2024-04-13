// Import necessary libraries and annotations
package com.assignment.Task_Exam.Controller;

// Importing necessary repositories and models

import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Use MockitoExtension to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    // Mocking AuthorRepository to simulate database interactions
    @Mock
    private AuthorRepository authorRepository;

    // Injecting mock dependencies into AuthorController
    @InjectMocks
    private AuthorController authorController;

    // Test data setup
    private Author testAuthor;

    // Setting up test data before each test
    @BeforeEach
    void setUp() {
        // Creating a test author instance
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Test Author");
    }

    // Testing the getAllAuthors method
    @Test
    void testGetAllAuthors() {
        // Arrange
        // Creating a list of authors and adding the test author to it
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthor);
        // Mocking the findAll method to return the list of authors
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        // Calling the getAllAuthors method
        List<Author> result = authorController.getAllAuthors();

        // Assert
        // Checking if the result contains the test author
        assertEquals(1, result.size());
        assertEquals(testAuthor.getName(), result.get(0).getName());
    }

    // Testing the getAuthorById method
    @Test
    void testGetAuthorById() {
        // Arrange
        // Mocking the findById method to return the test author when ID is 1
        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));
        // Mocking the findById method to return empty Optional when ID is 2
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        // Calling the getAuthorById method with different IDs
        ResponseEntity<Author> responseEntityFound = authorController.getAuthorById(1L);
        ResponseEntity<Author> responseEntityNotFound = authorController.getAuthorById(2L);

        // Assert
        // Checking the status code and returned author for each scenario
        assertEquals(HttpStatus.OK, responseEntityFound.getStatusCode());
        assertEquals(testAuthor, responseEntityFound.getBody());

        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }

    // Testing the insertAuthor method
    @Test
    void testInsertAuthor() {
        // Arrange
        // Mocking the save method to return the test author
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        // Act
        // Calling the insertAuthor method
        ResponseEntity<Author> responseEntity = authorController.insertAuthor(testAuthor);

        // Assert
        // Checking the status code and returned author
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testAuthor, responseEntity.getBody());
    }

    // Testing the updateAuthor method
    @Test
    void testUpdateAuthor() {
        // Arrange for author found
        // Mocking the existsById method to return true for ID 1
        when(authorRepository.existsById(1L)).thenReturn(true);
        // Mocking the save method to return the test author
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        // Arrange for author not found
        // Mocking the existsById method to return false for ID 2
        when(authorRepository.existsById(2L)).thenReturn(false);

        // Act and Assert for author found
        // Calling the updateAuthor method
        ResponseEntity<Author> responseEntityFound = authorController.updateAuthor(1L, testAuthor);
        // Checking the status code and returned author
        assertEquals(HttpStatus.OK, responseEntityFound.getStatusCode());
        assertEquals(testAuthor, responseEntityFound.getBody());

        // Act and Assert for author not found
        // Calling the updateAuthor method
        ResponseEntity<Author> responseEntityNotFound = authorController.updateAuthor(2L, testAuthor);
        // Checking the status code
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }

    // Testing the deleteAuthor method
    @Test
    void testDeleteAuthor() {
        // Arrange
        // Mocking the existsById method to return true for ID 1
        when(authorRepository.existsById(1L)).thenReturn(true);

        // Act
        // Calling the deleteAuthor method
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        // Assert
        // Checking the status code and verifying deleteById method is called once
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authorRepository, times(1)).deleteById(1L);
    }

    // Testing the deleteAuthor method when author not found
    @Test
    void testDeleteAuthor_NotFound() {
        // Arrange
        // Mocking the existsById method to return false for ID 2
        when(authorRepository.existsById(2L)).thenReturn(false);

        // Act
        // Calling the deleteAuthor method
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(2L);

        // Assert
        // Checking the status code and verifying deleteById method is never called
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(authorRepository, never()).deleteById(2L);
    }

    // Testing the deleteAuthor method with internal server error
    @Test
    void testDeleteAuthor_InternalServerError() {
        // Arrange
        // Mocking the existsById method to return true for ID 1
        when(authorRepository.existsById(1L)).thenReturn(true);
        // Mocking the deleteById method to throw a runtime exception
        doThrow(new RuntimeException()).when(authorRepository).deleteById(1L);

        // Act
        // Calling the deleteAuthor method
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        // Assert
        // Checking the status code
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
