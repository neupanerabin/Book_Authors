package com.assignment.Task_Exam.Controller;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String s) {
        System.out.println("Reouurce not found");
    }
}
