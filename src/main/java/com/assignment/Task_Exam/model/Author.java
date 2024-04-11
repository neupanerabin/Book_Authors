package com.assignment.Task_Exam.model;

// Import necessary library on here
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")    // create authors table in the database
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // create private variable
    private Long id;    // Unique identifier for author
    private String name;    // Name of the author

    // manage many-to-many relationship
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // Create set and HashSet to hold books associated with this authors
    private Set<Book> books = new HashSet<>();

    // Constructors
    public Author() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    // Convenience methods for managing books
    public void addBook(Book book) {
        books.add(book);    // Add the book to the author's collection of books
        book.getAuthors().add(this);    // Add the author to the book's collection of authors
    }

    public void removeBook(Book book) {
        books.remove(book); // Remove book from the author's collection of books
        book.getAuthors().remove(this); // Remove the author from the book's collection of authors
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);   // check if the authors have the same id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);    // Generate hashcode based on the authors id
    }
}
