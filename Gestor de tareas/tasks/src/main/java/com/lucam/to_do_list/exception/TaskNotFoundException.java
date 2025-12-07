package com.lucam.to_do_list.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super(String.format("No se encontro la tarea con ID: %d", id));
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

}
