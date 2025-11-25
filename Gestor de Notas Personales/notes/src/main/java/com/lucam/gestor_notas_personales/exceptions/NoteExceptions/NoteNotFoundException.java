package com.lucam.gestor_notas_personales.exceptions.NoteExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoteNotFoundException(Long id) {
        super(String.format("Nota no encontrada con id: %d", id));
    }

    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
