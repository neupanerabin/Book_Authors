package com.assignment.Task_Exam.Controller;

// Import the necessary library and packages
import com.assignment.Task_Exam.dao.AuthorRepository;   // Importing author repository for accessing author data
import com.assignment.Task_Exam.model.Author;   // Importing author model class
import org.springframework.beans.factory.annotation.Autowired;  //Importing Spring's Autowired annotation for dependency injection
import org.springframework.http.HttpStatus; // Importing HttpStatus for HTTP status code
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for handling HTTP responses
import org.springframework.web.bind.annotation.*; // Importing annotations for creating RESTful APIs
import java.util.List;  // importing list for collections
import java.util.Optional;  // Importing Optional for handling potentially null values

// Controller and API create
@RestController
@RequestMapping("/authors")     // Define base URL for api
public class AuthorController {

    @Autowired  // Autowire AuthorRepository for dependency injection
    AuthorRepository authorRepository;  // create object of AuthorRepository

    // Get all the available lists on here
    @GetMapping("/list-authors")
    public List<Author> getAllAuthors() {   // Get all the List of the authors
        return authorRepository.findAll();  // return the list of all authors
    }

    // Get the author by id
    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) {  // Get the author by id
        Optional<Author> author = authorRepository.findById(authorId);  // find author by id from repository
        // If author is found, return it, otherwise return not found status
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get()); // return the available author
        }
        return ResponseEntity.notFound().build();   // return not found status
    }

    // Insert the new authors
    @PostMapping("/insert")
    public ResponseEntity<Author> insertAuthor(@RequestBody Author author) { // Insert new author
        Author savedAuthor = authorRepository.save(author); // Save the new author in the repository
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor); // return the saved author with created status
    }

    // Update authors by id
    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
       // check whether the id is available in the database or not
        if (authorId == null || !authorRepository.existsById(authorId)) {
            return ResponseEntity.notFound().build();   // return not found status
        }
        author.setId(authorId); // Set the ID of the author for update
        Author updatedAuthor = authorRepository.save(author);   // update the authors in the repository
        return ResponseEntity.ok(updatedAuthor);    // return the updated author
    }

    // Delete Authors by id
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        // check if the id is available in the database or not
        if (!authorRepository.existsById(authorId)) {
            return ResponseEntity.notFound().build();// return not found status if author id is not found
        }
        // implement try and catch conditions
        try {
            authorRepository.deleteById(authorId);  // delete the author by id
            return ResponseEntity.noContent().build();   // return the no content status after successful deletion
        // Exception to catch the error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // return internal server status if deletion fails
        }
    }


}

