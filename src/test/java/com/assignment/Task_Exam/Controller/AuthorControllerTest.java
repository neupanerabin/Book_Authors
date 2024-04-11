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
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthor);

        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorController.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals(testAuthor.getName(), result.get(0).getName());
    }

    @Test
    void testGetAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));

        ResponseEntity<Author> responseEntity = authorController.getAuthorById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testAuthor, responseEntity.getBody());
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Author> responseEntity = authorController.getAuthorById(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testInsertAuthor() {
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        ResponseEntity<Author> responseEntity = authorController.insertAuthor(testAuthor);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testAuthor, responseEntity.getBody());
    }

    @Test
    void testUpdateAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        ResponseEntity<Author> responseEntity = authorController.updateAuthor(1L, testAuthor);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testAuthor, responseEntity.getBody());
    }

    @Test
    void testUpdateAuthor_NotFound() {
        when(authorRepository.existsById(2L)).thenReturn(false);

        ResponseEntity<Author> responseEntity = authorController.updateAuthor(2L, testAuthor);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        when(authorRepository.existsById(2L)).thenReturn(false);

        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(authorRepository, never()).deleteById(2L);
    }

    @Test
    void testDeleteAuthor_InternalServerError() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException()).when(authorRepository).deleteById(1L);

        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
