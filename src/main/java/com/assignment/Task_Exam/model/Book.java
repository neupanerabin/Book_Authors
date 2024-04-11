package com.assignment.Task_Exam.model;
// Import library
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Indicates that this class is an entity and will be mapped to a database table
@Table(name = "books")  // create books table in the database
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // create private variable
    private Long id;    // unique identifier for book
    private String title;   // Name of book

    // Constructor
    public Book(){} // Default constructor required by JPA

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    // Create Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Generate getter for authors property
    public Set<Author> getAuthors() {
        return authors;
    }

    // Generate setter for author property
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    // manage many-to-many relationship
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    @JoinTable(name="book_authors", // create book_authors table in the database  join with the id
            joinColumns = @JoinColumn(name="book_id"),  // Specifies the column name for the author's side of the relationship
            inverseJoinColumns = @JoinColumn(name = "author_id")) // Specifies the column name for the author's side of the relationship

    // Create set and hashSet to hold authors associated with books
    private Set<Author> authors = new HashSet<>();



}
