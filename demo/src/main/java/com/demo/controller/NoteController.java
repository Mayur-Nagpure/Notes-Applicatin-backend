package com.demo.controller;

import com.demo.model.Note;
import com.demo.model.User;
import com.demo.repository.NoteRepository;
import com.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // A private helper method to get the currently authenticated user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found in database"));
    }

    @GetMapping
    public List<Note> getAllNotesForCurrentUser() {
        User currentUser = getCurrentUser();
        return noteRepository.findByUserOrderByUpdatedAtDesc(currentUser);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        User currentUser = getCurrentUser();

        note.setUser(currentUser);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());

        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        Optional<Note> note = noteRepository.findByIdAndUser(id, currentUser);
        return note.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        User currentUser = getCurrentUser();
        Optional<Note> optionalNote = noteRepository.findByIdAndUser(id, currentUser);

        if (optionalNote.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Note note = optionalNote.get();
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setUpdatedAt(LocalDateTime.now());

        Note updatedNote = noteRepository.save(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        if (!noteRepository.existsByIdAndUser(id, currentUser)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<String> shareNote(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        Optional<Note> optionalNote = noteRepository.findByIdAndUser(id, currentUser);
        if (optionalNote.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Note note = optionalNote.get();
        String shareToken = note.getShareToken() == null ? UUID.randomUUID().toString() : note.getShareToken();
        note.setShareToken(shareToken);
        note.setPublic(true);
        noteRepository.save(note);

        return ResponseEntity.ok(shareToken);
    }
}


