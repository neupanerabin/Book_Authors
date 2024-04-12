package com.assignment.Task_Exam.Controller;

import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
class BookController_Test {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book book1 = new Book(1L, "Atomic Habits");
    Book book2 = new Book(2L, "Horrer ");
    Book book3 = new Book(3L, "Sad Life");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllBooks() throws Exception {
        List<Book> records = new ArrayList<>(Arrays.asList(book1, book2, book3));
        Mockito.when(bookRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
//                .andExpect((ResultMatcher) jsonPath("$[2].name", is("Horrer")));
    }


    @Test
    void getBookById() throws Exception{
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(java.util.Optional.of(book1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));
//                .andExpect((ResultMatcher) jsonPath("$[2].name", is("Horrer ")));

    }

    @Test
    void insertBook() {
    }

    @Test
    void updateBooks() {
    }

    @Test
    void linkAuthorToBook() {
    }

    @Test
    void deleteBook() {
    }
}