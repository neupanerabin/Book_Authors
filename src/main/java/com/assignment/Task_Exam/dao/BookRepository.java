package com.assignment.Task_Exam.dao;

import com.assignment.Task_Exam.model.Book; // Import the Book model Class
import org.springframework.data.jpa.repository.JpaRepository;  // Import JpaRepository for class

// Declare the interface for CRUD operations on the Book entity
public interface BookRepository extends JpaRepository<Book, Long> {
    // It is parameterized with the Book entity and the type of its primary key (Long)
    // This interface extends JpaRepository, which provides methods for performing CRUD operations



}
