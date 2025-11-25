package com.lucam.gestor_notas_personales.services.impl;

import com.lucam.gestor_notas_personales.entities.Note;
import com.lucam.gestor_notas_personales.exceptions.NoteExceptions.NoteNotFoundException;
import com.lucam.gestor_notas_personales.repositories.NoteRepository;
import com.lucam.gestor_notas_personales.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    @Transactional
    public Note createNote(Note note) {
        log.info("Creando nueva nota con titulo {}", note.getTitle());
        Note saveNote = noteRepository.save(note);
        log.info("Nota guardada con ID: {}", saveNote.getId());
        return saveNote;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> getAllNotes() {
        log.info("Buscando todas las notas disponibles");
        List<Note> notes = noteRepository.findAll();
        log.info("Se encontraron {} notas", notes.size());
        return notes;
    }

    @Override
    @Transactional(readOnly = true)
    public Note getNoteById(Long id) {
        log.info("Obteniendo nota con ID: {}", id);
        return noteRepository.findById(id).orElseThrow(() -> {
            log.error("Nota con ID {} no encontrada", id);
            return new NoteNotFoundException(id);
        });
    }

    @Override
    @Transactional
    public Note updateNoteById(Note note, Long id) {

        log.info("Actualizando nota con ID: {}", id);

        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Intento de actualizar nota existente con ID: {}", id);
                    return new NoteNotFoundException(id);
                });

        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());

        Note noteUpdated = noteRepository.save(existingNote);
        log.info("Se actualizo la nota con ID: {}", id);
        return noteUpdated;
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {

        log.info("Se esta eliminando la nota con ID: {}", id);
        if (!noteRepository.existsById(id)) {
            log.error("Error al eliminar la nota con ID: {}", id);
            throw new NoteNotFoundException(id);
        }

        noteRepository.deleteById(id);
        log.info("Se elimino correctamente la nota con ID: {}", id);
    }
}
