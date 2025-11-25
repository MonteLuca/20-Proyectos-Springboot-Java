package com.lucam.gestor_notas_personales.services;

import com.lucam.gestor_notas_personales.entities.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Note note);
    List<Note> getAllNotes();
    Note getNoteById(Long id);
    Note updateNoteById(Note note, Long id);
    void deleteNote(Long id);

}