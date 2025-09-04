
package com.demo.repository;

import com.demo.model.Note;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserOrderByUpdatedAtDesc(User user);
    Optional<Note> findByIdAndUser(Long id, User user);
    boolean existsByIdAndUser(Long id, User user);

    Optional<Note> findByShareToken(String shareToken);
}
