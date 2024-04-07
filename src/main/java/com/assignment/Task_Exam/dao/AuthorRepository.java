package com.assignment.Task_Exam.dao;

import com.assignment.Task_Exam.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

//    List<Author> findAuthorByBooks(String name);
//
//    List<Author> findById(long id);
//
//    List<Author> findById();
//
//    boolean deleteAuthor(Long authorId);
//
//    Author updateAuthor(Long authorId, Author author);
//    void deleteById(Long id);

}
