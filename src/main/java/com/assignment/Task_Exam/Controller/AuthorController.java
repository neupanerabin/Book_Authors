package com.assignment.Task_Exam.Controller;

// Import the necessary library and packages
import com.assignment.Task_Exam.dao.AuthorRepository;   // Importing author repository for accessing author data
import com.assignment.Task_Exam.dao.BookRepository;
import com.assignment.Task_Exam.model.Author;   // Importing author model class
import com.assignment.Task_Exam.model.Book;
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

    @Autowired
    BookRepository bookRepository;

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

    // Remove a Book from an Author
    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> deleteBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        System.out.println("Deleting book with ID: " + bookId + " from author with ID: " + authorId);  // Logging

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalAuthor.isPresent() && optionalBook.isPresent()) {
            Author author = optionalAuthor.get();
            Book book = optionalBook.get();

            // Check if the book belongs to the author
            if (author.getBooks().contains(book)) {
                // Remove the book from the author's list of books
                author.getBooks().remove(book);

                // Set the book's author to null to break the association
                book.setAuthors(null);
                bookRepository.save(book); // Save the book to update its author association

                // Delete the book from the repository
                bookRepository.delete(book);

                return ResponseEntity.noContent().build(); // Return 204 No Content after successful deletion
            } else {
                System.out.println("Book with ID " + bookId + " does not belong to author with ID " + authorId);  // Logging
                return ResponseEntity.badRequest().build(); // Return 400 Bad Request if book does not belong to author
            }
        } else {
            System.out.println("Author with ID " + authorId + " or book with ID " + bookId + " not found");  // Logging
            return ResponseEntity.notFound().build(); // Return 404 Not Found if author or book is not found
        }
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

