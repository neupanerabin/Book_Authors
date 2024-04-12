package com.assignment.Task_Exam.Controller;

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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    private Book testBook;

    Book record1 = new Book(1L,"Rabin");
    Book record2 = new Book(2L,"robs");
    Book record3 = new Book(3L,"neupane");

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(record1,record2,record3));
        Mockito.when(bookRepository.findAll()).thenReturn(records);

        ResultActions response = mockMvc.perform(get("/books/books"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name").value("Rabin"));
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
        testBook = new Book(1L, "Test Book"); // Initialize testBook
        books.add(testBook);

        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(testBook)).thenReturn(testBook);

        ResponseEntity<List<Book>> responseEntity = bookController.updateBooks(books);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
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
