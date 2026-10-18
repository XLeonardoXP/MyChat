package org.example.mychat.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String content;
    @Column
    private LocalDateTime timestamp;

    public Messaggio(){}
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public Messaggio(String username, String content){
        this.username = username;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
