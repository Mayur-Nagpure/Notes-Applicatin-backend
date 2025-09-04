package com.demo.controller;

import com.demo.model.Note;
import com.demo.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/public/notes")
public class PublicNoteController {

    private final NoteRepository noteRepository;

    public PublicNoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/{shareToken}")
    public ResponseEntity<Note> getSharedNote(@PathVariable String shareToken) {
        Optional<Note> note = noteRepository.findByShareToken(shareToken);
        if (note.isEmpty() || !note.get().isPublic()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note.get());
    }
}

