package com.assignment.Task_Exam.Controller;

import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Book;
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
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
//        testBook.setAuthor("Test Author");
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(testBook.getTitle(), result.get(0).getTitle());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        ResponseEntity<Book> responseEntity = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testBook, responseEntity.getBody());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Book> responseEntity = bookController.getBookById(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testInsertBook() {
        when(bookRepository.save(testBook)).thenReturn(testBook);

        ResponseEntity<Book> responseEntity = bookController.insertBook(testBook);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testBook, responseEntity.getBody());
    }

    @Test
    void testUpdateBooks() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);

        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(testBook)).thenReturn(testBook);

        ResponseEntity<List<Book>> responseEntity = bookController.updateBooks(books);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testUpdateBooks_NotFound() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);

        when(bookRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<List<Book>> responseEntity = bookController.updateBooks(books);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> responseEntity = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.existsById(2L)).thenReturn(false);

        ResponseEntity<Void> responseEntity = bookController.deleteBook(2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(bookRepository, never()).deleteById(2L);
    }
}
