package com.assignment.Task_Exam.dao;

// import class and library

import com.assignment.Task_Exam.model.Author;   // Importing the Author model class
import org.springframework.data.jpa.repository.JpaRepository;   // import JpaRepository
import org.springframework.stereotype.Repository;   // import repository annotation


// create repository with extend the JpaRepository class extend
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // provide the CRUD operations for the Author entity


}
