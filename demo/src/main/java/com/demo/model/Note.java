//package com.demo.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "notes")
//public class Note {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String title;
//
//    @Column(columnDefinition = "TEXT")
//    private String content;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    private LocalDateTime updatedAt = LocalDateTime.now();
//
//    private String shareToken;
//
//    private boolean isPublic;
//
//    // Getters and setters
//
//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//
//    public String getTitle() {return title;}
//    public void setTitle(String title) {this.title = title;}
//
//    public String getContent() {return content;}
//    public void setContent(String content) {this.content = content;}
//
//    public LocalDateTime getCreatedAt() {return createdAt;}
//    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
//
//    public LocalDateTime getUpdatedAt() {return updatedAt;}
//    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
//
//    public String getShareToken() {return shareToken;}
//    public void setShareToken(String shareToken) {this.shareToken = shareToken;}
//
//    public boolean isPublic() {return isPublic;}
//    public void setPublic(boolean isPublic) {this.isPublic = isPublic;}
//}

package com.demo.model;
import com.demo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isPublic = false;
    private String shareToken;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }

    public String getShareToken() { return shareToken; }
    public void setShareToken(String shareToken) { this.shareToken = shareToken; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

