package com.assignment.Task_Exam.model;
import static org.mockito.Mockito.*;

import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.dao.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class AuthorTest {

    @Autowired
//    private Author authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testAddBookToAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

//        authorService.addBook( "Book");

        verify(authorRepository, times(1)).save(author);
    }

    // Add more tests for other service methods
}
