package com.assignment.Task_Exam.dao;

import com.assignment.Task_Exam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

}
