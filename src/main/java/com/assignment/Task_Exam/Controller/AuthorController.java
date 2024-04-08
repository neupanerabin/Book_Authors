package com.assignment.Task_Exam.Controller;

import com.assignment.Task_Exam.dao.AuthorRepository;
import com.assignment.Task_Exam.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/list-authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author.get());
    }

    @PostMapping("/save")
    public ResponseEntity<List<Author>> createAuthors(@RequestBody List<Author> authors) {
        List<Author> savedAuthors = new ArrayList<>();

        for (Author author : authors) {
            Author savedAuthor = authorRepository.save(author);
            savedAuthors.add(savedAuthor);
        }

        return ResponseEntity.ok(savedAuthors);
    }


    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
        if (authorId == null || !authorRepository.existsById(authorId)) {
            return ResponseEntity.notFound().build();
        }

        // Ensure the ID is set for update
        author.setId(authorId);

        Author updatedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(updatedAuthor);
    }



    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            return ResponseEntity.notFound().build();
        }
        authorRepository.deleteById(authorId);
        return ResponseEntity.noContent().build();
    }
}