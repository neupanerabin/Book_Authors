package com.assignment.Task_Exam.Controller;

// Import the necessary libraries and packages
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

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorController authorController;

    private Author testAuthor;

    @BeforeEach
    void setUp() {
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Test Author");
    }

    @Test
    void testGetAllAuthors() {
        // Arrange
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthor);
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        List<Author> result = authorController.getAllAuthors();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAuthor.getName(), result.get(0).getName());
    }

    @Test
    void testGetAuthorById() {
        // Arrange
        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Author> responseEntityFound = authorController.getAuthorById(1L);
        ResponseEntity<Author> responseEntityNotFound = authorController.getAuthorById(2L);

        // Assert for found author
        assertEquals(HttpStatus.OK, responseEntityFound.getStatusCode());
        assertEquals(testAuthor, responseEntityFound.getBody());

        // Assert for author not found
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }


    @Test
    void testInsertAuthor() {
        // Arrange
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        // Act
        ResponseEntity<Author> responseEntity = authorController.insertAuthor(testAuthor);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testAuthor, responseEntity.getBody());
    }

    @Test
    void testUpdateAuthor() {
        // Arrange for author found
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        // Arrange for author not found
        when(authorRepository.existsById(2L)).thenReturn(false);

        // Act and Assert for author found
        ResponseEntity<Author> responseEntityFound = authorController.updateAuthor(1L, testAuthor);
        assertEquals(HttpStatus.OK, responseEntityFound.getStatusCode());
        assertEquals(testAuthor, responseEntityFound.getBody());

        // Act and Assert for author not found
        ResponseEntity<Author> responseEntityNotFound = authorController.updateAuthor(2L, testAuthor);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }


    @Test
    void testDeleteAuthor() {
        // Arrange
        when(authorRepository.existsById(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        // Arrange
        when(authorRepository.existsById(2L)).thenReturn(false);

        // Act
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(2L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(authorRepository, never()).deleteById(2L);
    }

    @Test
    void testDeleteAuthor_InternalServerError() {
        // Arrange
        when(authorRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException()).when(authorRepository).deleteById(1L);

        // Act
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
